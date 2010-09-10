package org.eclipse.imp.leg.compiler;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import lpg.runtime.IAst;
import lpg.runtime.IToken;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.builder.BuilderUtils;
import org.eclipse.imp.builder.MarkerCreator;
import org.eclipse.imp.builder.MarkerCreatorWithBatching;
import org.eclipse.imp.leg.Activator;
import org.eclipse.imp.leg.parser.LEGParseController;
import org.eclipse.imp.leg.parser.Ast.ASTNode;
import org.eclipse.imp.leg.parser.Ast.ASTNodeToken;
import org.eclipse.imp.leg.parser.Ast.AbstractVisitor;
import org.eclipse.imp.leg.parser.Ast.IType;
import org.eclipse.imp.leg.parser.Ast.Iexpression;
import org.eclipse.imp.leg.parser.Ast.IprimitiveType;
import org.eclipse.imp.leg.parser.Ast.assignmentStmt;
import org.eclipse.imp.leg.parser.Ast.block;
import org.eclipse.imp.leg.parser.Ast.declaration;
import org.eclipse.imp.leg.parser.Ast.declarationList;
import org.eclipse.imp.leg.parser.Ast.declarationStmt__declaration_ASSIGN_expression_SEMICOLON;
import org.eclipse.imp.leg.parser.Ast.declarationStmt__declaration_SEMICOLON;
import org.eclipse.imp.leg.parser.Ast.expression__expression_DIVIDE_term;
import org.eclipse.imp.leg.parser.Ast.expression__expression_EQUAL_term;
import org.eclipse.imp.leg.parser.Ast.expression__expression_GREATER_term;
import org.eclipse.imp.leg.parser.Ast.expression__expression_LESS_term;
import org.eclipse.imp.leg.parser.Ast.expression__expression_MINUS_term;
import org.eclipse.imp.leg.parser.Ast.expression__expression_NOTEQUAL_term;
import org.eclipse.imp.leg.parser.Ast.expression__expression_PLUS_term;
import org.eclipse.imp.leg.parser.Ast.expression__expression_TIMES_term;
import org.eclipse.imp.leg.parser.Ast.functionCall;
import org.eclipse.imp.leg.parser.Ast.functionDeclaration;
import org.eclipse.imp.leg.parser.Ast.functionDeclarationList;
import org.eclipse.imp.leg.parser.Ast.functionHeader;
import org.eclipse.imp.leg.parser.Ast.functionStmt;
import org.eclipse.imp.leg.parser.Ast.identifier;
import org.eclipse.imp.leg.parser.Ast.ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement;
import org.eclipse.imp.leg.parser.Ast.ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement;
import org.eclipse.imp.leg.parser.Ast.primitiveType__boolean;
import org.eclipse.imp.leg.parser.Ast.primitiveType__double;
import org.eclipse.imp.leg.parser.Ast.primitiveType__int;
import org.eclipse.imp.leg.parser.Ast.returnStmt;
import org.eclipse.imp.leg.parser.Ast.statementList;
import org.eclipse.imp.leg.parser.Ast.term__DoubleLiteral;
import org.eclipse.imp.leg.parser.Ast.term__NUMBER;
import org.eclipse.imp.leg.parser.Ast.term__false;
import org.eclipse.imp.leg.parser.Ast.term__true;
import org.eclipse.imp.leg.parser.Ast.whileStmt;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.model.ModelFactory;
import org.eclipse.imp.model.ModelFactory.ModelException;
import org.eclipse.imp.parser.IMessageHandler;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.SymbolTable;

public class LEGCompiler {
    private static final String sClassNameMacro= "$FILE$";

    private static final String sTemplateHeader= "public class "
            + sClassNameMacro + " {\n"
            + "\tpublic static void main(String[] args) {\n" + "\t\tnew "
            + sClassNameMacro + "().main();\n"
            + "\t\tSystem.out.println(\"done.\");\n" + "\t}\n";

    private static final String sTemplateFooter= "}\n";

    Stack<String> fTranslationStack= new Stack<String>();

    //public static final String PROBLEM_MARKER_ID= Activator.kPluginID + ".$PROBLEM_ID$";
    public String PROBLEM_MARKER_ID= "leg.imp.builder.problem";

    private IMessageHandler fMsgHandler;

    private boolean fSemanticErrorFlag;

    public LEGCompiler(String problem_marker_id) {
        if (problem_marker_id != null) {
            PROBLEM_MARKER_ID= problem_marker_id;
        }
    }

    public LEGCompiler() {
        this("leg.imp.builder.problem");
    }

    private void issueMessage(String msg, ASTNode n) {
        issueMessage(msg, n.getLeftIToken(), n.getRightIToken());
    }

    private void issueMessage(String msg, IToken tok) {
        issueMessage(msg, tok, tok);
    }

    private void issueMessage(String msg, IToken leftTok, IToken rightTok) {
        fMsgHandler.handleSimpleMessage(msg, leftTok.getStartOffset(), rightTok.getEndOffset()+1,
                leftTok.getColumn(), rightTok.getEndColumn(), leftTok.getLine(), rightTok.getEndLine());
    }

    private Map<identifier, declaration> fBindings= new HashMap<identifier, declaration>();

    @SuppressWarnings("serial")
    private class Scope extends HashMap<String, declaration> { }

    private class BindingVisitor extends AbstractVisitor {
        private Stack<Scope> fScopeStack= new Stack<Scope>();

        @Override
        public void unimplementedVisitor(String s) { }

        @Override
        public void endVisit(declaration n) {
            identifier id= n.getidentifier();
            fScopeStack.peek().put(id.getIDENTIFIER().toString(), n);
        }
        @Override
        public boolean visit(block n) {
            fScopeStack.push(new Scope());
            return true;
        }
        @Override
        public void endVisit(block n) {
            fScopeStack.pop();
        }
        @Override
        public boolean visit(functionDeclaration n) {
            fScopeStack.push(new Scope());
            return true;
        }
        @Override
        public void endVisit(functionDeclaration n) {
            fScopeStack.pop();
        }
        @Override
        public boolean visit(identifier n) {
            if (n.getParent() instanceof functionHeader || n.getParent() instanceof declaration) {
                return true;
            }
            String name= n.getIDENTIFIER().toString();
            boolean found= false;
            for(Scope s: fScopeStack) {
                if (s.containsKey(name)) {
                    fBindings.put(n, s.get(name));
                    found= true;
                    break;
                }
            }
            if (!found) {
                issueMessage("Undeclared identifier: " + name, n);
                fSemanticErrorFlag= true;
            }
            return true;
        }
    }

    enum NodeType { UNKNOWN_TYPE, VOID_TYPE, BOOLEAN_TYPE, INTEGER_TYPE, DOUBLE_TYPE;
        public String toString() {
            if (this == VOID_TYPE) return "void";
            if (this == BOOLEAN_TYPE) return "boolean";
            if (this == INTEGER_TYPE) return "int";
            if (this == DOUBLE_TYPE) return "double";
            return "<unknown>";
        }
    };

    private class TypecheckingVisitor extends AbstractVisitor {
        private Map<ASTNode,NodeType> fTypeMap= new HashMap<ASTNode, NodeType>();

        @Override
        public void unimplementedVisitor(String s) { }

        @Override
        public void endVisit(expression__expression_PLUS_term n) {
            Iexpression lhs= n.getexpression();
            Iexpression rhs= n.getterm();
            ASTNodeToken opNode= n.getPLUS();
            IToken opToken= opNode.getIToken();
            assertSameType((ASTNode) lhs, (ASTNode) rhs, "+", opToken, opToken);
        }

        @Override
        public void endVisit(expression__expression_MINUS_term n) {
            Iexpression lhs= n.getexpression();
            Iexpression rhs= n.getterm();
            ASTNodeToken opNode= n.getMINUS();
            IToken opToken= opNode.getIToken();
            assertSameType((ASTNode) lhs, (ASTNode) rhs, "-", opToken, opToken);
        }

        @Override
        public void endVisit(expression__expression_TIMES_term n) {
            Iexpression lhs= n.getexpression();
            Iexpression rhs= n.getterm();
            ASTNodeToken opNode= n.getTIMES();
            IToken opToken= opNode.getIToken();
            assertSameType((ASTNode) lhs, (ASTNode) rhs, "*", opToken, opToken);
        }

        @Override
        public void endVisit(expression__expression_DIVIDE_term n) {
            Iexpression lhs= n.getexpression();
            Iexpression rhs= n.getterm();
            ASTNodeToken opNode= n.getDIVIDE();
            IToken opToken= opNode.getIToken();
            assertSameType((ASTNode) lhs, (ASTNode) rhs, "/", opToken, opToken);
        }

        @Override
        public void endVisit(term__NUMBER n) {
            fTypeMap.put(n, NodeType.INTEGER_TYPE);
        }

        @Override
        public void endVisit(term__DoubleLiteral n) {
            fTypeMap.put(n, NodeType.DOUBLE_TYPE);
        }

        @Override
        public void endVisit(term__false n) {
            fTypeMap.put(n, NodeType.BOOLEAN_TYPE);
        }

        @Override
        public void endVisit(term__true n) {
            fTypeMap.put(n, NodeType.BOOLEAN_TYPE);
        }

        @Override
        public void endVisit(assignmentStmt n) {
            identifier lhs= n.getidentifier();
            Iexpression rhs= n.getexpression();

            assertSameType(lhs, (ASTNode) rhs, "=", n.getLeftIToken(), n.getRightIToken());
        }

        private void assertSameType(ASTNode lhs, ASTNode rhs, String op, IToken blameLeft, IToken blameRight) {
            NodeType lhsType= fTypeMap.get(lhs);
            NodeType rhsType= fTypeMap.get(rhs);

            if (lhsType != null && rhsType != null) {
                if (!lhsType.equals(rhsType)) {
                    issueMessage("Operands of '" + op + "' are of incompatible types: " + lhsType + " vs " + rhsType,
                                 blameLeft, blameRight);
                    fSemanticErrorFlag= true;
                }
            }
        }

        @Override
        public void endVisit(identifier n) {
            if (fBindings.containsKey(n)) {
                declaration decl= fBindings.get(n);
                NodeType type= primitiveTypeToNodeType(decl.getprimitiveType());
                fTypeMap.put(n, type);
            }
        }

        private NodeType primitiveTypeToNodeType(IprimitiveType type) {
            if (type instanceof primitiveType__boolean) {
                return NodeType.BOOLEAN_TYPE;
            } else if (type instanceof primitiveType__double) {
                return NodeType.DOUBLE_TYPE;
            } else if (type instanceof primitiveType__int) {
                return NodeType.INTEGER_TYPE;
            }
            return NodeType.UNKNOWN_TYPE;
        }

        @Override
        public boolean visit(declaration n) {
            IprimitiveType type= n.getprimitiveType();
            NodeType nt= primitiveTypeToNodeType(type);
            fTypeMap.put(n.getidentifier(), nt);
            return true;
        }

        @Override
        public void endVisit(declarationStmt__declaration_ASSIGN_expression_SEMICOLON n) {
            identifier lhs= n.getdeclaration().getidentifier();
            Iexpression rhs= n.getexpression();

            assertSameType(lhs, (ASTNode) rhs, "=", n.getLeftIToken(), n.getRightIToken());
        }
    }

    private final class TranslatorVisitor extends AbstractVisitor {
        SymbolTable<IAst> innerScope;

        @Override
        public void unimplementedVisitor(String s) {
            // System.err.println("Don't know how to translate node type '" + s + "'.");
        }

        // START_HERE
        // Provide appropriate visitor methods (like the following examples)
        // for the node types in your AST
        @Override
        public void endVisit(statementList n) {
            StringBuffer buff= new StringBuffer();

            for(int i= 0; i < n.size(); i++) {
                buff.insert(0, fTranslationStack.pop() + "\n");
            }
            fTranslationStack.push(buff.toString());
        }

        @Override
        public void endVisit(assignmentStmt n) {
            String rhs= (String) fTranslationStack.pop();
            String lhs= (String) fTranslationStack.pop();
            fTranslationStack.push("//#line " + n.getRightIToken().getEndLine()
                    + "\n\t\t" + lhs + " = " + rhs + ";"
                    + "\n\t\tSystem.out.println(\"" + lhs + " = \" + " + lhs
                    + ");");
        }

        @Override
        public void endVisit(expression__expression_PLUS_term n) {
            String right= (String) fTranslationStack.pop();
            String left= (String) fTranslationStack.pop();
            fTranslationStack.push(left + "+" + right);
        }

        @Override
        public void endVisit(expression__expression_MINUS_term n) {
            String right= (String) fTranslationStack.pop();
            String left= (String) fTranslationStack.pop();
            fTranslationStack.push(left + "-" + right);
        }

        @Override
        public void endVisit(expression__expression_TIMES_term n) {
            String right= (String) fTranslationStack.pop();
            String left= (String) fTranslationStack.pop();
            fTranslationStack.push(left + "*" + right);
        }

        @Override
        public void endVisit(expression__expression_DIVIDE_term n) {
            String right= (String) fTranslationStack.pop();
            String left= (String) fTranslationStack.pop();
            fTranslationStack.push(left + "/" + right);
        }

        @Override
        public void endVisit(expression__expression_GREATER_term n) {
            String right= (String) fTranslationStack.pop();
            String left= (String) fTranslationStack.pop();
            fTranslationStack.push(left + ">" + right);
        }

        @Override
        public void endVisit(expression__expression_LESS_term n) {
            String right= (String) fTranslationStack.pop();
            String left= (String) fTranslationStack.pop();
            fTranslationStack.push(left + "<" + right);
        }

        @Override
        public void endVisit(expression__expression_EQUAL_term n) {
            String right= (String) fTranslationStack.pop();
            String left= (String) fTranslationStack.pop();
            fTranslationStack.push(left + " == " + right);
        }

        @Override
        public void endVisit(expression__expression_NOTEQUAL_term n) {
            String right= (String) fTranslationStack.pop();
            String left= (String) fTranslationStack.pop();
            fTranslationStack.push(left + " != " + right);
        }

        @Override
        public void endVisit(declarationStmt__declaration_ASSIGN_expression_SEMICOLON n) {
            String decl= (String) fTranslationStack.pop();
            fTranslationStack.push("//#line " + n.getRightIToken().getEndLine()
                    + "\n\t\t" + decl + ";");
        }

        @Override
        public void endVisit(declarationStmt__declaration_SEMICOLON n) {
            String rhs= (String) fTranslationStack.pop();
            String decl= (String) fTranslationStack.pop();
            fTranslationStack.push("//#line " + n.getRightIToken().getEndLine()
                    + "\n\t\t" + decl + '=' + rhs + ";");
        }

        @Override
        public void endVisit(declaration n) {
            fTranslationStack.pop(); // discard identifier's trivial translation - we know what it is
            fTranslationStack.push("\t\t" + n.getprimitiveType() + " "
                    + n.getidentifier());
        }

        @Override
        public boolean visit(block n) {
            innerScope= n.getSymbolTable();
            return true;
        }

        @Override
        public void endVisit(block n) {
            innerScope= innerScope.getParent();
            String body= (String) fTranslationStack.pop();
            fTranslationStack.push("{\n" + body + "\t\t}\n");
        }

        @Override
        public void endVisit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement n) {
            String then= (String) fTranslationStack.pop();
            String cond= (String) fTranslationStack.pop();
            fTranslationStack.push("//#line " + n.getRightIToken().getEndLine()
                    + "\n\t\tif (" + cond + ")\n\t\t\t" + then + "\n");
        }

        @Override
        public void endVisit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement n) {
            String elseStmt= (n.getelse() != null) ? (String) fTranslationStack.pop() : null;
            String then= (String) fTranslationStack.pop();
            String cond= (String) fTranslationStack.pop();
            fTranslationStack.push("//#line " + n.getRightIToken().getEndLine()
                    + "\n\t\tif (" + cond + ")\n\t\t\t" + then
                    + "\nelse\n\t\t\t" + elseStmt + "\n");
        }

        @Override
        public void endVisit(whileStmt n) {
            String body= (String) fTranslationStack.pop();
            String cond= (String) fTranslationStack.pop();
            fTranslationStack.push("//#line " + n.getRightIToken().getEndLine()
                    + "\n\t\twhile (" + cond + ") " + body);
        }

        @Override
        public boolean visit(identifier n) {
            fTranslationStack.push(n.getIDENTIFIER().toString());
            return false;
        }

        @Override
        public boolean visit(term__NUMBER n) {
            fTranslationStack.push(n.getNUMBER().toString());
            return true;
        }

        @Override
        public void endVisit(functionDeclaration n) {
            IType retType= n.getfunctionHeader().getType();
            String body= (String) fTranslationStack.pop();
            String funcName= n.getfunctionHeader().getidentifier().toString();
            declarationList formals= n.getfunctionHeader().getparameters();
            StringBuffer buff= new StringBuffer("\t");
            buff.append(retType.toString()).append(' ').append(funcName)
                    .append('(');
            if (formals != null) {
                for(int i= 0; i < formals.size(); i++) {
                    if (i > 0)
                        buff.append(',');
                    fTranslationStack.pop(); // discard trivial translation of formal arg
                    declaration formal= formals.getdeclarationAt(i);
                    buff.append(formal.getprimitiveType().toString()).append(
                            ' ').append(formal.getidentifier().toString());
                }
            }
            buff.append(") ");
            buff.append(body);
            buff.append("\n");
            fTranslationStack.pop(); // discard function name
            fTranslationStack.push(buff.toString());
        }

        @Override
        public void endVisit(returnStmt n) {
            String retVal= (String) fTranslationStack.pop();
            fTranslationStack.push("\t\tSystem.out.println(\"returning \" + ("
                    + retVal + "));\n" + "//#line "
                    + n.getRightIToken().getEndLine() + "\n\t\treturn "
                    + retVal + ";\n");
        }

        @Override
        public void endVisit(term__DoubleLiteral n) {
            fTranslationStack.push(n.toString());
        }

        @Override
        public void endVisit(term__false n) {
            fTranslationStack.push(n.toString());
        }

        @Override
        public void endVisit(term__true n) {
            fTranslationStack.push(n.toString());
        }

        @Override
        public void endVisit(functionStmt n) {
            String call= (String) fTranslationStack.pop();
            fTranslationStack.push("//#line " + n.getRightIToken().getEndLine()
                    + "\n\t\t" + call + ";");
        }

        @Override
        public void endVisit(functionCall n) {
            String funcName= n.getidentifier().toString();
            // SMS 21 May 2007:  some decls are functionHeaders, evidently
            //functionDeclaration func= (functionDeclaration) innerScope.findDeclaration(funcName);
            //int numArgs= func.getfunctionHeader().getparameters().size();
            int numArgs= 0;
            Object decl= innerScope.findDeclaration(funcName);
            if (decl instanceof functionDeclaration) {
                numArgs= ((functionDeclaration) decl).getfunctionHeader()
                        .getparameters().size();
            } else if (decl instanceof functionHeader) {
                numArgs= ((functionHeader) decl).getparameters().size();
            }

            StringBuffer buff= new StringBuffer();
            buff.append(funcName).append('(');
            Stack<String> actualArgs= new Stack<String>();
            for(int arg= 0; arg < numArgs; arg++) {
                actualArgs.push(fTranslationStack.pop());
            }
            for(int arg= 0; arg < numArgs; arg++) {
                if (arg > 0)
                    buff.append(',');
                buff.append(actualArgs.pop());
            }
            buff.append(")");
            fTranslationStack.pop(); // discard function name
            fTranslationStack.push(buff.toString());
        }

        @Override
        public void endVisit(functionDeclarationList n) {
            StringBuffer buff= new StringBuffer();
            for(int i= 0; i < n.size(); i++) {
                buff.append(fTranslationStack.pop());
            }
            fTranslationStack.push(buff.toString());
        }
        //*/
    }

    public String getFileContents(IFile file) {
        try {
            return BuilderUtils.getFileContents(file);
        } catch (Exception e) {
            System.err.println("LEGCompiler.getFileContents(..):  " + e.getMessage());
        }
        return "";
    }

    public void compile(IFile file, IProgressMonitor mon) {
        if (file == null) {
            Activator.getInstance().writeErrorMsg("LEGCompiler.compile(): Can't compile a null file.");
            return;
        }
        IProject project= file.getProject();
        if (project == null) {
            Activator.getInstance().writeErrorMsg("LEGCompiler.compile(): project for file '" + file.getName() + "' is null.");
            return;
        }
        ISourceProject sourceProject= null;
        try {
            sourceProject= ModelFactory.open(project);
        } catch (ModelException me) {
            Activator.getInstance().logException("Exception opening source project for " + project.getName(), me);
            return;
        }
        IParseController parseController= new LEGParseController();

        // Marker creator handles error messages from the parse controller
        fMsgHandler= new MarkerCreator(file, PROBLEM_MARKER_ID);
        //		MarkerCreatorWithBatching markerCreator = new MarkerCreatorWithBatching(file, parseController, PROBLEM_MARKER_ID);

        // If we have a kind of parser that might be receptive, tell it
        // what types of problem marker the builder will create
        parseController.getAnnotationTypeInfo().addProblemMarkerType(PROBLEM_MARKER_ID);

        parseController.initialize(file.getProjectRelativePath(), sourceProject, fMsgHandler);

        parseController.parse(getFileContents(file), mon);

        ASTNode currentAst= (ASTNode) parseController.getCurrentAst();

        if (fMsgHandler instanceof MarkerCreatorWithBatching) {
            ((MarkerCreatorWithBatching) fMsgHandler).flush(mon);
        }

        if (currentAst == null) {
            Activator.getInstance().writeErrorMsg("LEGCompiler.compile(..): unable to compile due to syntax errors (no AST).");
            return;
        }

        String fileExten= file.getFileExtension();
        String fileBase= file.getName().substring(0, file.getName().length() - fileExten.length() - 1);

        fSemanticErrorFlag= false;

        currentAst.accept(new BindingVisitor());
        if (fSemanticErrorFlag) {
            return;
        }

        currentAst.accept(new TypecheckingVisitor());
        if (fSemanticErrorFlag) {
            return;
        }

        currentAst.accept(new TranslatorVisitor());

        IFile javaFile= project.getFile(file.getProjectRelativePath().removeFileExtension().addFileExtension("java"));
        String javaSource= sTemplateHeader.replaceAll(sClassNameMacro.replaceAll("\\$", "\\\\\\$"), fileBase)
                + fTranslationStack.pop() + sTemplateFooter;
        ByteArrayInputStream bais= new ByteArrayInputStream(javaSource.getBytes());

        try {
            if (!javaFile.exists()) {
                javaFile.create(bais, true, mon);
            } else {
                javaFile.setContents(bais, true, false, mon);
            }
        } catch (CoreException ce) {
            System.err.println(ce.getMessage());
        }
    }
}
