package org.eclipse.imp.leg.parser;

import org.eclipse.imp.leg.parser.Ast.*;
import lpg.runtime.*;
import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

public class LEGParser implements RuleAction, IParser
{
    private PrsStream prsStream = null;
    
    private boolean unimplementedSymbolsWarning = false;

    private static ParseTable prsTable = new LEGParserprs();
    public ParseTable getParseTable() { return prsTable; }

    private DeterministicParser dtParser = null;
    public DeterministicParser getParser() { return dtParser; }

    private void setResult(Object object) { dtParser.setSym1(object); }
    public Object getRhsSym(int i) { return dtParser.getSym(i); }

    public int getRhsTokenIndex(int i) { return dtParser.getToken(i); }
    public IToken getRhsIToken(int i) { return prsStream.getIToken(getRhsTokenIndex(i)); }
    
    public int getRhsFirstTokenIndex(int i) { return dtParser.getFirstToken(i); }
    public IToken getRhsFirstIToken(int i) { return prsStream.getIToken(getRhsFirstTokenIndex(i)); }

    public int getRhsLastTokenIndex(int i) { return dtParser.getLastToken(i); }
    public IToken getRhsLastIToken(int i) { return prsStream.getIToken(getRhsLastTokenIndex(i)); }

    public int getLeftSpan() { return dtParser.getFirstToken(); }
    public IToken getLeftIToken()  { return prsStream.getIToken(getLeftSpan()); }

    public int getRightSpan() { return dtParser.getLastToken(); }
    public IToken getRightIToken() { return prsStream.getIToken(getRightSpan()); }

    public int getRhsErrorTokenIndex(int i)
    {
        int index = dtParser.getToken(i);
        IToken err = prsStream.getIToken(index);
        return (err instanceof ErrorToken ? index : 0);
    }
    public ErrorToken getRhsErrorIToken(int i)
    {
        int index = dtParser.getToken(i);
        IToken err = prsStream.getIToken(index);
        return (ErrorToken) (err instanceof ErrorToken ? err : null);
    }

    public void reset(ILexStream lexStream)
    {
        prsStream = new PrsStream(lexStream);
        dtParser.reset(prsStream);

        try
        {
            prsStream.remapTerminalSymbols(orderedTerminalSymbols(), prsTable.getEoftSymbol());
        }
        catch(NullExportedSymbolsException e) {
        }
        catch(NullTerminalSymbolsException e) {
        }
        catch(UnimplementedTerminalsException e)
        {
            if (unimplementedSymbolsWarning) {
                java.util.ArrayList unimplemented_symbols = e.getSymbols();
                System.out.println("The Lexer will not scan the following token(s):");
                for (int i = 0; i < unimplemented_symbols.size(); i++)
                {
                    Integer id = (Integer) unimplemented_symbols.get(i);
                    System.out.println("    " + LEGParsersym.orderedTerminalSymbols[id.intValue()]);               
                }
                System.out.println();
            }
        }
        catch(UndefinedEofSymbolException e)
        {
            throw new Error(new UndefinedEofSymbolException
                                ("The Lexer does not implement the Eof symbol " +
                                 LEGParsersym.orderedTerminalSymbols[prsTable.getEoftSymbol()]));
        }
    }
    
    public LEGParser()
    {
        try
        {
            dtParser = new DeterministicParser(prsStream, prsTable, (RuleAction) this);
        }
        catch (NotDeterministicParseTableException e)
        {
            throw new Error(new NotDeterministicParseTableException
                                ("Regenerate LEGParserprs.java with -NOBACKTRACK option"));
        }
        catch (BadParseSymFileException e)
        {
            throw new Error(new BadParseSymFileException("Bad Parser Symbol File -- LEGParsersym.java. Regenerate LEGParserprs.java"));
        }
    }

    public LEGParser(ILexStream lexStream)
    {
        this();
        reset(lexStream);
    }

    public int numTokenKinds() { return LEGParsersym.numTokenKinds; }
    public String[] orderedTerminalSymbols() { return LEGParsersym.orderedTerminalSymbols; }
    public String getTokenKindName(int kind) { return LEGParsersym.orderedTerminalSymbols[kind]; }            
    public int getEOFTokenKind() { return prsTable.getEoftSymbol(); }
    public IPrsStream getIPrsStream() { return prsStream; }

    /**
     * @deprecated replaced by {@link #getIPrsStream()}
     *
     */
    public PrsStream getPrsStream() { return prsStream; }

    /**
     * @deprecated replaced by {@link #getIPrsStream()}
     *
     */
    public PrsStream getParseStream() { return prsStream; }

    public Object parser()
    {
        return parser(null, 0);
    }
        
    public Object parser(Monitor monitor)
    {
        return parser(monitor, 0);
    }
        
    public Object parser(int error_repair_count)
    {
        return parser(null, error_repair_count);
    }
        
    public Object parser(Monitor monitor, int error_repair_count)
    {
        dtParser.setMonitor(monitor);

        try
        {
            return (Object) dtParser.parse();
        }
        catch (BadParseException e)
        {
            prsStream.reset(e.error_token); // point to error token

            DiagnoseParser diagnoseParser = new DiagnoseParser(prsStream, prsTable);
            diagnoseParser.diagnose(e.error_token);
        }

        return null;
    }

    //
    // Additional entry points, if any
    //
    

    Stack<SymbolTable<IAst>> symbolTableStack = null;
    SymbolTable<IAst> topLevelSymbolTable = null;
    public SymbolTable<IAst> getTopLevelSymbolTable() { return topLevelSymbolTable; }

    //
    // TODO: In the future, the user will be able to identify scope structures
    // (special non terminals such as block and functionDeclaration below) in
    // the grammar specification that carry symbol table information. The class
    // associated with such symbols will implement a special IScope interface and
    // will be required to specify an implementation of the method "getSymbolTable"
    // that is defined in IScope. Thus, the implementation of this funftion will
    // be simpler as it would only need to search for an instance of IScope.
    //
    public SymbolTable<IAst> getEnclosingSymbolTable(IAst n) {
        for ( ; n != null; n = n.getParent())
            if (n instanceof block)
                 return ((block) n).getSymbolTable();
            else if (n instanceof functionDeclaration)
                 return ((functionDeclaration) n).getSymbolTable();
        return getTopLevelSymbolTable();
    }

    public void resolve(ASTNode root) {
        if (root != null) {
            symbolTableStack = new Stack<SymbolTable<IAst>>();
            topLevelSymbolTable = new SymbolTable<IAst>(null);
            symbolTableStack.push(topLevelSymbolTable);
            root.accept(new SymbolTableVisitor());
        }
    }
    
    /*
     * A visitor for ASTs.  Its purpose is to build a symbol table
     * for declared symbols and resolved identifier in expressions.
     */
    private final class SymbolTableVisitor extends AbstractVisitor {
        IPrsStream prs = getIPrsStream();
        ILexStream lex = prs.getILexStream();

        public void unimplementedVisitor(String s) { /* Useful for debugging: System.out.println(s); */ }

        public void emitError(IToken id, String message) {
            lex.getMessageHandler().handleMessage(ParseErrorCodes.NO_MESSAGE_CODE,
                                                  lex.getLocation(id.getStartOffset(), id.getEndOffset()),
                                                  lex.getLocation(0, 0),
                                                  prs.getFileName(),
                                                  new String [] { message });
        }

        public void emitError(IAst node, String message) {
            lex.getMessageHandler().handleMessage(
                ParseErrorCodes.NO_MESSAGE_CODE,
                lex.getLocation(
                    node.getLeftIToken().getStartOffset(), node.getRightIToken().getEndOffset()),
                lex.getLocation(0, 0),
                prs.getFileName(),
                new String [] { message });
        }

        public void emitError(int startOffset, int endOffset, String message) {
            lex.getMessageHandler().handleMessage(
                ParseErrorCodes.NO_MESSAGE_CODE,
                lex.getLocation(startOffset, endOffset),
                lex.getLocation(0, 0),
                prs.getFileName(),
                new String [] { message });
        }

        public boolean visit(block n) {
            n.setSymbolTable(symbolTableStack.push(new SymbolTable<IAst>(symbolTableStack.peek())));
            return true;
        }

        public void endVisit(block n) { symbolTableStack.pop(); }

        public boolean visit(functionDeclaration n) {
            functionHeader fh = n.getfunctionHeader();
            IToken id = fh.getidentifier().getIToken();
            SymbolTable<IAst> symbol_table = symbolTableStack.peek();
            if (symbol_table.get(id.toString()) == null)
           	     // SMS 11 Jun 2007; pursuant to fixing bug #190
                 //symbol_table.put(id.toString(), fh);
                 symbol_table.put(id.toString(), n);
            else emitError(id, "Illegal redeclaration of " + id.toString());

            //
            // Add a symbol table for the parameters
            //
            n.setSymbolTable(symbolTableStack.push(new SymbolTable<IAst>(symbolTableStack.peek())));

            return true;
        }

        public void endVisit(functionDeclaration n) { symbolTableStack.pop(); }

        public boolean visit(declaration n) {
            IToken id = n.getidentifier().getIToken();
            SymbolTable<IAst> symbol_table = symbolTableStack.peek();
            if (symbol_table.get(id.toString()) == null)
                 symbol_table.put(id.toString(), n);
            else emitError(id, "Illegal redeclaration of " + id.toString());
            return true;
        }

        public boolean visit(identifier n) {
            IToken id = n.getIDENTIFIER();
            IAst decl = symbolTableStack.peek().findDeclaration(id.toString());
            if (decl == null)
                 emitError(id, "Undeclared variable " + id.toString());
            else n.setDeclaration(decl);
            return true;
        }
    } // End SymbolTableVisitor

    public void ruleAction(int ruleNumber)
    {
        switch (ruleNumber)
        {

            //
            // Rule 1:  compilationUnit ::= $Empty
            //
            case 1: {
                setResult(
                    new functionDeclarationList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 2:  compilationUnit ::= compilationUnit functionDeclaration
            //
            case 2: {
                ((functionDeclarationList)getRhsSym(1)).add((functionDeclaration)getRhsSym(2));
                break;
            }
            //
            // Rule 3:  functionDeclaration ::= functionHeader block
            //
            case 3: {
                setResult(
                    new functionDeclaration(LEGParser.this, getLeftIToken(), getRightIToken(),
                                            (functionHeader)getRhsSym(1),
                                            (block)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 4:  functionHeader ::= Type identifier ( parameters )
            //
            case 4: {
                setResult(
                    new functionHeader(getLeftIToken(), getRightIToken(),
                                       (IType)getRhsSym(1),
                                       (identifier)getRhsSym(2),
                                       new ASTNodeToken(getRhsIToken(3)),
                                       (declarationList)getRhsSym(4),
                                       new ASTNodeToken(getRhsIToken(5)))
                );
                break;
            }
            //
            // Rule 5:  parameters ::= $Empty
            //
            case 5: {
                setResult(
                    new declarationList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 6:  parameters ::= parameterList
            //
            case 6:
                break;
            //
            // Rule 7:  parameterList ::= declaration
            //
            case 7: {
                setResult(
                    new declarationList((declaration)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 8:  parameterList ::= parameterList , declaration
            //
            case 8: {
                ((declarationList)getRhsSym(1)).add((declaration)getRhsSym(3));
                break;
            }
            //
            // Rule 9:  declaration ::= primitiveType identifier
            //
            case 9: {
                setResult(
                    new declaration(getLeftIToken(), getRightIToken(),
                                    (IprimitiveType)getRhsSym(1),
                                    (identifier)getRhsSym(2))
                );
                break;
            }
            //
            // Rule 10:  stmtList ::= $Empty
            //
            case 10: {
                setResult(
                    new statementList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 11:  stmtList ::= stmtList statement
            //
            case 11: {
                ((statementList)getRhsSym(1)).add((Istatement)getRhsSym(2));
                break;
            }
            //
            // Rule 12:  statement ::= declarationStmt
            //
            case 12:
                break;
            //
            // Rule 13:  statement ::= assignmentStmt
            //
            case 13:
                break;
            //
            // Rule 14:  statement ::= ifStmt
            //
            case 14:
                break;
            //
            // Rule 15:  statement ::= returnStmt
            //
            case 15:
                break;
            //
            // Rule 16:  statement ::= whileStmt
            //
            case 16:
                break;
            //
            // Rule 17:  statement ::= block
            //
            case 17:
                break;
            //
            // Rule 18:  statement ::= functionStmt
            //
            case 18:
                break;
            //
            // Rule 19:  statement ::= ;
            //
            case 19: {
                setResult(
                    new statement(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 20:  block ::= { stmtList }
            //
            case 20: {
                setResult(
                    new block(LEGParser.this, getLeftIToken(), getRightIToken(),
                              new ASTNodeToken(getRhsIToken(1)),
                              (statementList)getRhsSym(2),
                              new ASTNodeToken(getRhsIToken(3)))
                );
                break;
            }
            //
            // Rule 21:  declarationStmt ::= declaration ;
            //
            case 21: {
                setResult(
                    new declarationStmt__declaration_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                               (declaration)getRhsSym(1),
                                                               new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 22:  declarationStmt ::= declaration = expression ;
            //
            case 22: {
                setResult(
                    new declarationStmt__declaration_ASSIGN_expression_SEMICOLON(getLeftIToken(), getRightIToken(),
                                                                                 (declaration)getRhsSym(1),
                                                                                 new ASTNodeToken(getRhsIToken(2)),
                                                                                 (Iexpression)getRhsSym(3),
                                                                                 new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            }
            //
            // Rule 23:  Type ::= primitiveType
            //
            case 23:
                break;
            //
            // Rule 24:  Type ::= void
            //
            case 24: {
                setResult(
                    new Type(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 25:  primitiveType ::= boolean
            //
            case 25: {
                setResult(
                    new primitiveType__boolean(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 26:  primitiveType ::= double
            //
            case 26: {
                setResult(
                    new primitiveType__double(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 27:  primitiveType ::= int
            //
            case 27: {
                setResult(
                    new primitiveType__int(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 28:  assignmentStmt ::= identifier = expression ;
            //
            case 28: {
                setResult(
                    new assignmentStmt(getLeftIToken(), getRightIToken(),
                                       (identifier)getRhsSym(1),
                                       new ASTNodeToken(getRhsIToken(2)),
                                       (Iexpression)getRhsSym(3),
                                       new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            }
            //
            // Rule 29:  assignmentStmt ::= BadAssignment
            //
            case 29:
                break;
            //
            // Rule 30:  ifStmt ::= if ( expression ) statement
            //
            case 30: {
                setResult(
                    new ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement(getLeftIToken(), getRightIToken(),
                                                                             new ASTNodeToken(getRhsIToken(1)),
                                                                             new ASTNodeToken(getRhsIToken(2)),
                                                                             (Iexpression)getRhsSym(3),
                                                                             new ASTNodeToken(getRhsIToken(4)),
                                                                             (Istatement)getRhsSym(5))
                );
                break;
            }
            //
            // Rule 31:  ifStmt ::= if ( expression ) statement else statement
            //
            case 31: {
                setResult(
                    new ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement(getLeftIToken(), getRightIToken(),
                                                                                            new ASTNodeToken(getRhsIToken(1)),
                                                                                            new ASTNodeToken(getRhsIToken(2)),
                                                                                            (Iexpression)getRhsSym(3),
                                                                                            new ASTNodeToken(getRhsIToken(4)),
                                                                                            (Istatement)getRhsSym(5),
                                                                                            new ASTNodeToken(getRhsIToken(6)),
                                                                                            (Istatement)getRhsSym(7))
                );
                break;
            }
            //
            // Rule 32:  whileStmt ::= while ( expression ) statement
            //
            case 32: {
                setResult(
                    new whileStmt(getLeftIToken(), getRightIToken(),
                                  new ASTNodeToken(getRhsIToken(1)),
                                  new ASTNodeToken(getRhsIToken(2)),
                                  (Iexpression)getRhsSym(3),
                                  new ASTNodeToken(getRhsIToken(4)),
                                  (Istatement)getRhsSym(5))
                );
                break;
            }
            //
            // Rule 33:  returnStmt ::= return expression ;
            //
            case 33: {
                setResult(
                    new returnStmt(getLeftIToken(), getRightIToken(),
                                   new ASTNodeToken(getRhsIToken(1)),
                                   (Iexpression)getRhsSym(2),
                                   new ASTNodeToken(getRhsIToken(3)))
                );
                break;
            }
            //
            // Rule 34:  expression ::= expression + term
            //
            case 34: {
                setResult(
                    new expression__expression_PLUS_term(getLeftIToken(), getRightIToken(),
                                                         (Iexpression)getRhsSym(1),
                                                         new ASTNodeToken(getRhsIToken(2)),
                                                         (Iterm)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 35:  expression ::= expression - term
            //
            case 35: {
                setResult(
                    new expression__expression_MINUS_term(getLeftIToken(), getRightIToken(),
                                                          (Iexpression)getRhsSym(1),
                                                          new ASTNodeToken(getRhsIToken(2)),
                                                          (Iterm)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 36:  expression ::= expression * term
            //
            case 36: {
                setResult(
                    new expression__expression_TIMES_term(getLeftIToken(), getRightIToken(),
                                                          (Iexpression)getRhsSym(1),
                                                          new ASTNodeToken(getRhsIToken(2)),
                                                          (Iterm)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 37:  expression ::= expression / term
            //
            case 37: {
                setResult(
                    new expression__expression_DIVIDE_term(getLeftIToken(), getRightIToken(),
                                                           (Iexpression)getRhsSym(1),
                                                           new ASTNodeToken(getRhsIToken(2)),
                                                           (Iterm)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 38:  expression ::= expression > term
            //
            case 38: {
                setResult(
                    new expression__expression_GREATER_term(getLeftIToken(), getRightIToken(),
                                                            (Iexpression)getRhsSym(1),
                                                            new ASTNodeToken(getRhsIToken(2)),
                                                            (Iterm)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 39:  expression ::= expression < term
            //
            case 39: {
                setResult(
                    new expression__expression_LESS_term(getLeftIToken(), getRightIToken(),
                                                         (Iexpression)getRhsSym(1),
                                                         new ASTNodeToken(getRhsIToken(2)),
                                                         (Iterm)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 40:  expression ::= expression == term
            //
            case 40: {
                setResult(
                    new expression__expression_EQUAL_term(getLeftIToken(), getRightIToken(),
                                                          (Iexpression)getRhsSym(1),
                                                          new ASTNodeToken(getRhsIToken(2)),
                                                          (Iterm)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 41:  expression ::= expression != term
            //
            case 41: {
                setResult(
                    new expression__expression_NOTEQUAL_term(getLeftIToken(), getRightIToken(),
                                                             (Iexpression)getRhsSym(1),
                                                             new ASTNodeToken(getRhsIToken(2)),
                                                             (Iterm)getRhsSym(3))
                );
                break;
            }
            //
            // Rule 42:  expression ::= term
            //
            case 42:
                break;
            //
            // Rule 43:  term ::= NUMBER
            //
            case 43: {
                setResult(
                    new term__NUMBER(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 44:  term ::= DoubleLiteral
            //
            case 44: {
                setResult(
                    new term__DoubleLiteral(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 45:  term ::= true
            //
            case 45: {
                setResult(
                    new term__true(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 46:  term ::= false
            //
            case 46: {
                setResult(
                    new term__false(getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 47:  term ::= identifier
            //
            case 47:
                break;
            //
            // Rule 48:  term ::= functionCall
            //
            case 48:
                break;
            //
            // Rule 49:  functionCall ::= identifier ( expressions )
            //
            case 49: {
                setResult(
                    new functionCall(getLeftIToken(), getRightIToken(),
                                     (identifier)getRhsSym(1),
                                     new ASTNodeToken(getRhsIToken(2)),
                                     (expressionList)getRhsSym(3),
                                     new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            }
            //
            // Rule 50:  functionStmt ::= functionCall ;
            //
            case 50: {
                setResult(
                    new functionStmt(getLeftIToken(), getRightIToken(),
                                     (functionCall)getRhsSym(1),
                                     new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            }
            //
            // Rule 51:  expressions ::= $Empty
            //
            case 51: {
                setResult(
                    new expressionList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 52:  expressions ::= expressionList
            //
            case 52:
                break;
            //
            // Rule 53:  expressionList ::= expression
            //
            case 53: {
                setResult(
                    new expressionList((Iexpression)getRhsSym(1), true /* left recursive */)
                );
                break;
            }
            //
            // Rule 54:  expressionList ::= expressionList , expression
            //
            case 54: {
                ((expressionList)getRhsSym(1)).add((Iexpression)getRhsSym(3));
                break;
            }
            //
            // Rule 55:  identifier ::= IDENTIFIER
            //
            case 55: {
                setResult(
                    new identifier(LEGParser.this, getRhsIToken(1))
                );
                break;
            }
            //
            // Rule 56:  BadAssignment ::= identifier = MissingExpression
            //
            case 56: {
                setResult(
                    new BadAssignment(getLeftIToken(), getRightIToken(),
                                      (identifier)getRhsSym(1),
                                      new ASTNodeToken(getRhsIToken(2)),
                                      new ASTNodeToken(getRhsIToken(3)))
                );
                break;
            }
    
            default:
                break;
        }
        return;
    }
}

