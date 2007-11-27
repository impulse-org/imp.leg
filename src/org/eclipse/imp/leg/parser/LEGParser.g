%options package=org.eclipse.imp.leg.parser
%options template=btParserTemplateF.gi
%options import_terminals=LEGLexer.gi
%options parent_saved,automatic_ast=toplevel,visitor=preorder,ast_directory=./Ast,ast_type=ASTNode

%Globals   
    /.import org.eclipse.imp.parser.IParser;
    import java.util.Hashtable;
    import java.util.Stack;
    ./ 
%End 

%Define
    $ast_class /.Object./
    $additional_interfaces /., IParser./
%End

%Terminals
         COMMA ::= ','
         SEMICOLON ::= ';'
         PLUS ::= '+'
         MINUS ::= '-'
         ASSIGN ::= '='
         TIMES ::= '*'
         DIVIDE ::= '/'
         GREATER ::= '>'
         LESS ::= '<'
         EQUAL ::= '=='
         NOTEQUAL ::= '!='
         LEFTPAREN ::= '('
         RIGHTPAREN ::= ')'
         LEFTBRACE ::= '{'
         RIGHTBRACE ::= '}'
%End

%Start
    pattern
%End

%Recover
   MissingExpression
%End

%Rules
    pattern ::= 
              | statement
              | expression
              | functionDeclaration
              | compilationUnit
              
    compilationUnit$$functionDeclaration ::= %empty
                                           | compilationUnit functionDeclaration
                                           | METAVARIABLE_functionDeclarations

    functionDeclaration ::= functionHeader block
     /.
        $action_type.SymbolTable symbolTable;
        public void setSymbolTable($action_type.SymbolTable symbolTable) { this.symbolTable = symbolTable; }
        public $action_type.SymbolTable getSymbolTable() { return symbolTable; }
    ./
    | METAVARIABLE_functionDeclaration
    
    functionHeader ::= Type identifier '(' parameters ')'
    
    parameters$$declaration ::= %empty
                              | parameterList
                              | METAVARIABLE_parameters

    parameterList$$declaration ::= declaration
                                 | parameterList ',' declaration
                                 | METAVARIABLE_parameterList
                                                            
    declaration ::= primitiveType identifier
                 | METAVARIABLE_declaration

    stmtList$$statement ::= %empty
                          | stmtList statement
                          | METAVARIABLE_statements
                          
    statement ::= declarationStmt
                | assignmentStmt
                | ifStmt
                | returnStmt
                | whileStmt
                | block
                | functionStmt
                | ';'
                | METAVARIABLE_statement

    block ::= '{' stmtList '}'
    /.
        $action_type.SymbolTable symbolTable;
        public void setSymbolTable($action_type.SymbolTable symbolTable) { this.symbolTable = symbolTable; }
        public $action_type.SymbolTable getSymbolTable() { return symbolTable; }
    ./
  

    declarationStmt ::= declaration ';'
                      | declaration '=' expression ';'
                       
    Type ::= primitiveType
           | void
           | METAVARIABLE_Type

    primitiveType ::= boolean
                    | double
                    | int
                              
    assignmentStmt ::= identifier '=' expression ';'
                     | BadAssignment
    ifStmt ::= if '(' expression ')' statement
             | if '(' expression ')' statement else statement

    whileStmt ::= while '(' expression ')' statement

    returnStmt ::= return expression ';'

    expression ::= expression '+' term
                 | expression '-' term
                 | expression '*' term
                 | expression '/' term
                 | expression '>' term
                 | expression '<' term
                 | expression '==' term
                 | expression '!=' term
                 | term
                 | METAVARIABLE_expression
                 
    term ::= NUMBER
           | DoubleLiteral
           | true
           | false
           | identifier
           | functionCall
           | METAVARIABLE_term
           
    functionCall ::= identifier '(' expressions ')'

    functionStmt ::= functionCall ';'
    
    expressions$$expression ::= %empty
                              | expressionList
                              | METAVARIABLE_expressions
                              
    expressionList$$expression ::= expression
                                 | expressionList ',' expression

    identifier ::= IDENTIFIER
    /.
        IAst decl;
        public void setDeclaration(IAst decl) { this.decl = decl; }
        public IAst getDeclaration() { return decl; }
    ./
    
    | METAVARIABLE_identifier
    

    BadAssignment ::= identifier '=' MissingExpression 
%End


%Headers
    /.
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

        public void resolve($ast_type root) {
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
    ./
%End