package org.eclipse.imp.leg.parser;

import org.eclipse.imp.leg.parser.Ast.*;
import lpg.runtime.*;
import org.eclipse.imp.parser.IParser;
import java.util.Hashtable;
import java.util.Stack;

public class LEGParser implements RuleAction, IParser
{
    private PrsStream prsStream;
    
    private static ParseTable prsTable = new LEGParserprs();
    private BacktrackingParser btParser;

    public BacktrackingParser getParser() { return btParser; }
    private void setResult(Object object) { btParser.setSym1(object); }
    public Object getRhsSym(int i) { return btParser.getSym(i); }

    public int getRhsTokenIndex(int i) { return btParser.getToken(i); }
    public IToken getRhsIToken(int i) { return prsStream.getIToken(getRhsTokenIndex(i)); }
    
    public int getRhsFirstTokenIndex(int i) { return btParser.getFirstToken(i); }
    public IToken getRhsFirstIToken(int i) { return prsStream.getIToken(getRhsFirstTokenIndex(i)); }

    public int getRhsLastTokenIndex(int i) { return btParser.getLastToken(i); }
    public IToken getRhsLastIToken(int i) { return prsStream.getIToken(getRhsLastTokenIndex(i)); }

    public int getLeftSpan() { return btParser.getFirstToken(); }
    public IToken getLeftIToken()  { return prsStream.getIToken(getLeftSpan()); }

    public int getRightSpan() { return btParser.getLastToken(); }
    public IToken getRightIToken() { return prsStream.getIToken(getRightSpan()); }

    public int getRhsErrorTokenIndex(int i)
    {
        int index = btParser.getToken(i);
        IToken err = prsStream.getIToken(index);
        return (err instanceof ErrorToken ? index : 0);
    }
    public ErrorToken getRhsErrorIToken(int i)
    {
        int index = btParser.getToken(i);
        IToken err = prsStream.getIToken(index);
        return (ErrorToken) (err instanceof ErrorToken ? err : null);
    }

    public void reset(ILexStream lexStream)
    {
        prsStream = new PrsStream(lexStream) {
        	@Override
        	public String[] orderedTerminalSymbols() {
        		return LEGParserprs.orderedTerminalSymbols;
        	}
        };

        try
        {
            prsStream.remapTerminalSymbols(orderedTerminalSymbols(), LEGParserprs.EOFT_SYMBOL);
        }
        catch(NullExportedSymbolsException e) {
        }
        catch(NullTerminalSymbolsException e) {
        }
        catch(UnimplementedTerminalsException e)
        {
            java.util.ArrayList unimplemented_symbols = e.getSymbols();
            System.out.println("The Lexer will not scan the following token(s):");
            for (int i = 0; i < unimplemented_symbols.size(); i++)
            {
                Integer id = (Integer) unimplemented_symbols.get(i);
                System.out.println("    " + LEGParsersym.orderedTerminalSymbols[id.intValue()]);               
            }
            System.out.println();                        
        }
        catch(UndefinedEofSymbolException e)
        {
            throw new Error(new UndefinedEofSymbolException
                                ("The Lexer does not implement the Eof symbol " +
                                 LEGParsersym.orderedTerminalSymbols[LEGParserprs.EOFT_SYMBOL]));
        } 
    }

    public LEGParser() {}
    
    public LEGParser(ILexStream lexStream)
    {
        reset(lexStream);
    }
    
    public String[] orderedTerminalSymbols() { return LEGParsersym.orderedTerminalSymbols; }
    public String getTokenKindName(int kind) { return LEGParsersym.orderedTerminalSymbols[kind]; }
    public int getEOFTokenKind() { return LEGParserprs.EOFT_SYMBOL; }
    public PrsStream getParseStream() { return prsStream; }

    public Object parser()
    {
        return parser(null, Integer.MAX_VALUE);
    }
    
    public Object parser(Monitor monitor)
    {
        return parser(monitor, Integer.MAX_VALUE);
    }
    
    public Object parser(int error_repair_count)
    {
        return parser(null, error_repair_count);
    }

    public Object parser(Monitor monitor, int error_repair_count)
    {
        try
        {
            btParser = new BacktrackingParser(monitor, prsStream, prsTable, (RuleAction) this);
        }
        catch (NotBacktrackParseTableException e)
        {
            throw new Error(new NotBacktrackParseTableException
                                ("Regenerate LEGParserprs.java with -BACKTRACK option"));
        }
        catch (BadParseSymFileException e)
        {
            throw new Error(new BadParseSymFileException("Bad Parser Symbol File -- LEGParsersym.java"));
        }

        try
        {
            return (Object) btParser.fuzzyParse(error_repair_count);
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
    

    public class SymbolTable extends Hashtable {
        SymbolTable parent;
        SymbolTable(SymbolTable parent) { this.parent = parent; }
        public IAst findDeclaration(String name) {
            IAst decl = (IAst) get(name);
            return (decl != null
                          ? decl
                          : parent != null ? parent.findDeclaration(name) : null);
        }
        public SymbolTable getParent() { return parent; }
    }
    
    Stack symbolTableStack = null;
    SymbolTable topLevelSymbolTable = null;
    public SymbolTable getTopLevelSymbolTable() { return topLevelSymbolTable; }

    //
    // TODO: In the future, the user will be able to identify scope structures
    // (special non terminals such as block and functionDeclaration below) in
    // the grammar specification that carry symbol table information. The class
    // associated with such symbols will implement a special IScope interface and
    // will be required to specify an implementation of the method "getSymbolTable"
    // that is defined in IScope. Thus, the implementation of this funftion will
    // be simpler as it would only need to search for an instance of IScope.
    //
    public SymbolTable getEnclosingSymbolTable(IAst n) {
        for ( ; n != null; n = n.getParent())
            if (n instanceof block)
                 return ((block) n).getSymbolTable();
            else if (n instanceof functionDeclaration0)
                 return ((functionDeclaration0) n).getSymbolTable();
        return getTopLevelSymbolTable();
    }

    public void resolve(ASTNode root) {
        if (root != null) {
            symbolTableStack = new Stack();
            topLevelSymbolTable = new SymbolTable(null);
            symbolTableStack.push(topLevelSymbolTable);
            root.accept(new SymbolTableVisitor());
        }
    }
    
    /*
     * A visitor for ASTs.  Its purpose is to build a symbol table
     * for declared symbols and resolved identifier in expressions.
     */
    private final class SymbolTableVisitor extends AbstractVisitor {
        public void unimplementedVisitor(String s) { /* Useful for debugging: System.out.println(s); */ }
        
        public void emitError(IToken id, String message) {
           if (prsStream.getMessageHandler() != null ) {
            prsStream.getMessageHandler().handleMessage(ParseErrorCodes.NO_MESSAGE_CODE,
                                              prsStream.getLexStream().getLocation(id.getStartOffset(), id.getEndOffset()),
                                              prsStream.getLexStream().getLocation(0, 0),
                                              prsStream.getFileName(),
                                              new String [] { message });
                                              }
        }
        
        
        public void emitError(ASTNode node, String message) {
          if (prsStream.getMessageHandler() != null ) {
            prsStream.getMessageHandler().handleMessage(
                ParseErrorCodes.NO_MESSAGE_CODE,
                prsStream.getLexStream().getLocation(
                    node.getLeftIToken().getStartOffset(), node.getRightIToken().getEndOffset()),
                prsStream.getLexStream().getLocation(0, 0),
                prsStream.getFileName(),
                new String [] { message });
                }
        }

       public void emitError(int startOffset, int endOffset, String message) {
         if (prsStream.getMessageHandler() != null ) {
            prsStream.getMessageHandler().handleMessage(
                ParseErrorCodes.NO_MESSAGE_CODE,
                prsStream.getLexStream().getLocation(startOffset, endOffset),
                prsStream.getLexStream().getLocation(0, 0),
                prsStream.getFileName(),
                new String [] { message });
                }
        }

        
        public boolean visit(block n) {
            n.setSymbolTable((SymbolTable) symbolTableStack.push(new SymbolTable((SymbolTable) symbolTableStack.peek())));
            return true;
        }

        public void endVisit(block n) { symbolTableStack.pop(); }

        public boolean visit(functionDeclaration0 n) {
            functionHeader fh = n.getfunctionHeader();
            IToken id = fh.getidentifier().getLeftIToken();
            SymbolTable symbol_table = (SymbolTable) symbolTableStack.peek();
            if (symbol_table.get(id.toString()) == null)
           	     // SMS 11 Jun 2007; pursuant to fixing bug #190
                 //symbol_table.put(id.toString(), fh);
                 symbol_table.put(id.toString(), n);
            else emitError(id, "Illegal redeclaration of " + id.toString());

            //
            // Add a symbol table for the parameters
            //
            n.setSymbolTable((SymbolTable) symbolTableStack.push(new SymbolTable((SymbolTable) symbolTableStack.peek())));

            return true;
        }
        
        public void endVisit(functionDeclaration0 n) { symbolTableStack.pop(); }

        public boolean visit(declaration0 n) {
            IToken id = n.getidentifier().getLeftIToken();
            SymbolTable symbol_table = (SymbolTable) symbolTableStack.peek();
            if (symbol_table.get(id.toString()) == null)
                 symbol_table.put(id.toString(), n);
            else emitError(id, "Illegal redeclaration of " + id.toString());
            return true;
        }

        public boolean visit(identifier0 n) {
            IToken id = n.getIDENTIFIER();
            IAst decl = ((SymbolTable) symbolTableStack.peek()).findDeclaration(id.toString());
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
            // Rule 1:  pattern ::=
            //
            case 1: {
                //#line 89 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(null);
                break;
            } 
            //
            // Rule 2:  pattern ::= statement
            //
            case 2:
                break; 
            //
            // Rule 3:  pattern ::= expression
            //
            case 3:
                break; 
            //
            // Rule 4:  pattern ::= functionDeclaration
            //
            case 4:
                break; 
            //
            // Rule 5:  pattern ::= compilationUnit
            //
            case 5:
                break; 
            //
            // Rule 6:  compilationUnit ::= $Empty
            //
            case 6: {
                //#line 95 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new functionDeclarationList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 7:  compilationUnit ::= compilationUnit functionDeclaration
            //
            case 7: {
                //#line 96 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                ((functionDeclarationList)getRhsSym(1)).add((IfunctionDeclaration)getRhsSym(2));
                break;
            } 
            //
            // Rule 8:  compilationUnit ::= METAVARIABLE_functionDeclarations
            //
            case 8: {
                //#line 97 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new functionDeclarationList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 9:  functionDeclaration ::= functionHeader block
            //
            case 9: {
                //#line 99 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new functionDeclaration0(LEGParser.this, getLeftIToken(), getRightIToken(),
                                             (functionHeader)getRhsSym(1),
                                             (block)getRhsSym(2))
                );
                break;
            } 
            //
            // Rule 10:  functionDeclaration ::= METAVARIABLE_functionDeclaration
            //
            case 10: {
                //#line 105 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new functionDeclaration1(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 11:  functionHeader ::= Type identifier ( parameters )
            //
            case 11: {
                //#line 107 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new functionHeader(getLeftIToken(), getRightIToken(),
                                       (IType)getRhsSym(1),
                                       (Iidentifier)getRhsSym(2),
                                       new ASTNodeToken(getRhsIToken(3)),
                                       (declarationList)getRhsSym(4),
                                       new ASTNodeToken(getRhsIToken(5)))
                );
                break;
            } 
            //
            // Rule 12:  parameters ::= $Empty
            //
            case 12: {
                //#line 109 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new declarationList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 13:  parameters ::= parameterList
            //
            case 13:
                break; 
            //
            // Rule 14:  parameters ::= METAVARIABLE_parameters
            //
            case 14: {
                //#line 111 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new declarationList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 15:  parameterList ::= declaration
            //
            case 15: {
                //#line 113 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new declarationList((Ideclaration)getRhsSym(1), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 16:  parameterList ::= parameterList , declaration
            //
            case 16: {
                //#line 114 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                ((declarationList)getRhsSym(1)).add((Ideclaration)getRhsSym(3));
                break;
            } 
            //
            // Rule 17:  parameterList ::= META_VARIABLE_parameterList
            //
            case 17: {
                //#line 115 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new declarationList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 18:  declaration ::= primitiveType identifier
            //
            case 18: {
                //#line 117 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new declaration0(getLeftIToken(), getRightIToken(),
                                     (IprimitiveType)getRhsSym(1),
                                     (Iidentifier)getRhsSym(2))
                );
                break;
            } 
            //
            // Rule 19:  declaration ::= METAVARIABLE_declaration
            //
            case 19: {
                //#line 118 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new declaration1(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 20:  stmtList ::= $Empty
            //
            case 20: {
                //#line 120 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new statementList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 21:  stmtList ::= stmtList statement
            //
            case 21: {
                //#line 121 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                ((statementList)getRhsSym(1)).add((Istatement)getRhsSym(2));
                break;
            } 
            //
            // Rule 22:  stmtList ::= METAVARIABLE_statements
            //
            case 22: {
                //#line 122 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new statementList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 23:  statement ::= declarationStmt
            //
            case 23:
                break; 
            //
            // Rule 24:  statement ::= assignmentStmt
            //
            case 24:
                break; 
            //
            // Rule 25:  statement ::= ifStmt
            //
            case 25:
                break; 
            //
            // Rule 26:  statement ::= returnStmt
            //
            case 26:
                break; 
            //
            // Rule 27:  statement ::= whileStmt
            //
            case 27:
                break; 
            //
            // Rule 28:  statement ::= block
            //
            case 28:
                break; 
            //
            // Rule 29:  statement ::= functionStmt
            //
            case 29:
                break; 
            //
            // Rule 30:  statement ::= ;
            //
            case 30: {
                //#line 131 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new statement0(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 31:  statement ::= METAVARIABLE_statement
            //
            case 31: {
                //#line 132 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new statement1(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 32:  block ::= { stmtList }
            //
            case 32: {
                //#line 134 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new block(LEGParser.this, getLeftIToken(), getRightIToken(),
                              new ASTNodeToken(getRhsIToken(1)),
                              (statementList)getRhsSym(2),
                              new ASTNodeToken(getRhsIToken(3)))
                );
                break;
            } 
            //
            // Rule 33:  declarationStmt ::= declaration ;
            //
            case 33: {
                //#line 142 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new declarationStmt0(getLeftIToken(), getRightIToken(),
                                         (Ideclaration)getRhsSym(1),
                                         new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            } 
            //
            // Rule 34:  declarationStmt ::= declaration = expression ;
            //
            case 34: {
                //#line 143 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new declarationStmt1(getLeftIToken(), getRightIToken(),
                                         (Ideclaration)getRhsSym(1),
                                         new ASTNodeToken(getRhsIToken(2)),
                                         (Iexpression)getRhsSym(3),
                                         new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            } 
            //
            // Rule 35:  Type ::= primitiveType
            //
            case 35:
                break; 
            //
            // Rule 36:  Type ::= void
            //
            case 36: {
                //#line 146 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new Type0(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 37:  Type ::= METAVARIABLE_Type
            //
            case 37: {
                //#line 147 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new Type1(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 38:  primitiveType ::= boolean
            //
            case 38: {
                //#line 149 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new primitiveType0(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 39:  primitiveType ::= double
            //
            case 39: {
                //#line 150 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new primitiveType1(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 40:  primitiveType ::= int
            //
            case 40: {
                //#line 151 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new primitiveType2(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 41:  assignmentStmt ::= identifier = expression ;
            //
            case 41: {
                //#line 153 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new assignmentStmt(getLeftIToken(), getRightIToken(),
                                       (Iidentifier)getRhsSym(1),
                                       new ASTNodeToken(getRhsIToken(2)),
                                       (Iexpression)getRhsSym(3),
                                       new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            } 
            //
            // Rule 42:  assignmentStmt ::= BadAssignment
            //
            case 42:
                break; 
            //
            // Rule 43:  ifStmt ::= if ( expression ) statement
            //
            case 43: {
                //#line 155 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new ifStmt0(getLeftIToken(), getRightIToken(),
                                new ASTNodeToken(getRhsIToken(1)),
                                new ASTNodeToken(getRhsIToken(2)),
                                (Iexpression)getRhsSym(3),
                                new ASTNodeToken(getRhsIToken(4)),
                                (Istatement)getRhsSym(5))
                );
                break;
            } 
            //
            // Rule 44:  ifStmt ::= if ( expression ) statement else statement
            //
            case 44: {
                //#line 156 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new ifStmt1(getLeftIToken(), getRightIToken(),
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
            // Rule 45:  whileStmt ::= while ( expression ) statement
            //
            case 45: {
                //#line 158 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
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
            // Rule 46:  returnStmt ::= return expression ;
            //
            case 46: {
                //#line 160 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new returnStmt(getLeftIToken(), getRightIToken(),
                                   new ASTNodeToken(getRhsIToken(1)),
                                   (Iexpression)getRhsSym(2),
                                   new ASTNodeToken(getRhsIToken(3)))
                );
                break;
            } 
            //
            // Rule 47:  expression ::= expression + term
            //
            case 47: {
                //#line 162 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expression0(getLeftIToken(), getRightIToken(),
                                    (Iexpression)getRhsSym(1),
                                    new ASTNodeToken(getRhsIToken(2)),
                                    (Iterm)getRhsSym(3))
                );
                break;
            } 
            //
            // Rule 48:  expression ::= expression - term
            //
            case 48: {
                //#line 163 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expression1(getLeftIToken(), getRightIToken(),
                                    (Iexpression)getRhsSym(1),
                                    new ASTNodeToken(getRhsIToken(2)),
                                    (Iterm)getRhsSym(3))
                );
                break;
            } 
            //
            // Rule 49:  expression ::= expression * term
            //
            case 49: {
                //#line 164 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expression2(getLeftIToken(), getRightIToken(),
                                    (Iexpression)getRhsSym(1),
                                    new ASTNodeToken(getRhsIToken(2)),
                                    (Iterm)getRhsSym(3))
                );
                break;
            } 
            //
            // Rule 50:  expression ::= expression / term
            //
            case 50: {
                //#line 165 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expression3(getLeftIToken(), getRightIToken(),
                                    (Iexpression)getRhsSym(1),
                                    new ASTNodeToken(getRhsIToken(2)),
                                    (Iterm)getRhsSym(3))
                );
                break;
            } 
            //
            // Rule 51:  expression ::= expression > term
            //
            case 51: {
                //#line 166 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expression4(getLeftIToken(), getRightIToken(),
                                    (Iexpression)getRhsSym(1),
                                    new ASTNodeToken(getRhsIToken(2)),
                                    (Iterm)getRhsSym(3))
                );
                break;
            } 
            //
            // Rule 52:  expression ::= expression < term
            //
            case 52: {
                //#line 167 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expression5(getLeftIToken(), getRightIToken(),
                                    (Iexpression)getRhsSym(1),
                                    new ASTNodeToken(getRhsIToken(2)),
                                    (Iterm)getRhsSym(3))
                );
                break;
            } 
            //
            // Rule 53:  expression ::= expression == term
            //
            case 53: {
                //#line 168 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expression6(getLeftIToken(), getRightIToken(),
                                    (Iexpression)getRhsSym(1),
                                    new ASTNodeToken(getRhsIToken(2)),
                                    (Iterm)getRhsSym(3))
                );
                break;
            } 
            //
            // Rule 54:  expression ::= expression != term
            //
            case 54: {
                //#line 169 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expression7(getLeftIToken(), getRightIToken(),
                                    (Iexpression)getRhsSym(1),
                                    new ASTNodeToken(getRhsIToken(2)),
                                    (Iterm)getRhsSym(3))
                );
                break;
            } 
            //
            // Rule 55:  expression ::= term
            //
            case 55:
                break; 
            //
            // Rule 56:  expression ::= METAVARIABLE_expression
            //
            case 56: {
                //#line 171 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expression8(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 57:  term ::= NUMBER
            //
            case 57: {
                //#line 173 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new term0(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 58:  term ::= DoubleLiteral
            //
            case 58: {
                //#line 174 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new term1(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 59:  term ::= true
            //
            case 59: {
                //#line 175 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new term2(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 60:  term ::= false
            //
            case 60: {
                //#line 176 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new term3(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 61:  term ::= identifier
            //
            case 61:
                break; 
            //
            // Rule 62:  term ::= functionCall
            //
            case 62:
                break; 
            //
            // Rule 63:  term ::= METAVARIABLE_term
            //
            case 63: {
                //#line 179 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new term4(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 64:  functionCall ::= identifier ( expressions )
            //
            case 64: {
                //#line 181 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new functionCall(getLeftIToken(), getRightIToken(),
                                     (Iidentifier)getRhsSym(1),
                                     new ASTNodeToken(getRhsIToken(2)),
                                     (expressionList)getRhsSym(3),
                                     new ASTNodeToken(getRhsIToken(4)))
                );
                break;
            } 
            //
            // Rule 65:  functionStmt ::= functionCall ;
            //
            case 65: {
                //#line 183 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new functionStmt(getLeftIToken(), getRightIToken(),
                                     (functionCall)getRhsSym(1),
                                     new ASTNodeToken(getRhsIToken(2)))
                );
                break;
            } 
            //
            // Rule 66:  expressions ::= $Empty
            //
            case 66: {
                //#line 185 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expressionList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 67:  expressions ::= expressionList
            //
            case 67:
                break; 
            //
            // Rule 68:  expressions ::= METAVARIABLE_expressions
            //
            case 68: {
                //#line 187 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expressionList(getLeftIToken(), getRightIToken(), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 69:  expressionList ::= expression
            //
            case 69: {
                //#line 189 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new expressionList((Iexpression)getRhsSym(1), true /* left recursive */)
                );
                break;
            } 
            //
            // Rule 70:  expressionList ::= expressionList , expression
            //
            case 70: {
                //#line 190 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                ((expressionList)getRhsSym(1)).add((Iexpression)getRhsSym(3));
                break;
            } 
            //
            // Rule 71:  identifier ::= IDENTIFIER
            //
            case 71: {
                //#line 192 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new identifier0(LEGParser.this, getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 72:  identifier ::= METAVARIABLE_identifier
            //
            case 72: {
                //#line 199 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new identifier1(getRhsIToken(1))
                );
                break;
            } 
            //
            // Rule 73:  BadAssignment ::= identifier = MissingExpression
            //
            case 73: {
                //#line 202 "C:/Workspace/org.eclipse.imp.lpg.metatooling/templates/btParserTemplateF.gi"
                setResult(
                    new BadAssignment(getLeftIToken(), getRightIToken(),
                                      (Iidentifier)getRhsSym(1),
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

