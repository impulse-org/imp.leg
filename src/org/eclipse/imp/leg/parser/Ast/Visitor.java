package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

public interface Visitor extends IAstVisitor
{
    boolean visit(ASTNode n);
    void endVisit(ASTNode n);

    boolean visit(ASTNodeToken n);
    void endVisit(ASTNodeToken n);

    boolean visit(functionDeclarationList n);
    void endVisit(functionDeclarationList n);

    boolean visit(functionDeclaration n);
    void endVisit(functionDeclaration n);

    boolean visit(functionHeader n);
    void endVisit(functionHeader n);

    boolean visit(declarationList n);
    void endVisit(declarationList n);

    boolean visit(declaration n);
    void endVisit(declaration n);

    boolean visit(statementList n);
    void endVisit(statementList n);

    boolean visit(statement n);
    void endVisit(statement n);

    boolean visit(block n);
    void endVisit(block n);

    boolean visit(Type n);
    void endVisit(Type n);

    boolean visit(assignmentStmt n);
    void endVisit(assignmentStmt n);

    boolean visit(whileStmt n);
    void endVisit(whileStmt n);

    boolean visit(returnStmt n);
    void endVisit(returnStmt n);

    boolean visit(functionCall n);
    void endVisit(functionCall n);

    boolean visit(functionStmt n);
    void endVisit(functionStmt n);

    boolean visit(expressionList n);
    void endVisit(expressionList n);

    boolean visit(identifier n);
    void endVisit(identifier n);

    boolean visit(BadAssignment n);
    void endVisit(BadAssignment n);

    boolean visit(declarationStmt0 n);
    void endVisit(declarationStmt0 n);

    boolean visit(declarationStmt1 n);
    void endVisit(declarationStmt1 n);

    boolean visit(primitiveType0 n);
    void endVisit(primitiveType0 n);

    boolean visit(primitiveType1 n);
    void endVisit(primitiveType1 n);

    boolean visit(primitiveType2 n);
    void endVisit(primitiveType2 n);

    boolean visit(ifStmt0 n);
    void endVisit(ifStmt0 n);

    boolean visit(ifStmt1 n);
    void endVisit(ifStmt1 n);

    boolean visit(expression0 n);
    void endVisit(expression0 n);

    boolean visit(expression1 n);
    void endVisit(expression1 n);

    boolean visit(expression2 n);
    void endVisit(expression2 n);

    boolean visit(expression3 n);
    void endVisit(expression3 n);

    boolean visit(expression4 n);
    void endVisit(expression4 n);

    boolean visit(expression5 n);
    void endVisit(expression5 n);

    boolean visit(expression6 n);
    void endVisit(expression6 n);

    boolean visit(expression7 n);
    void endVisit(expression7 n);

    boolean visit(term0 n);
    void endVisit(term0 n);

    boolean visit(term1 n);
    void endVisit(term1 n);

    boolean visit(term2 n);
    void endVisit(term2 n);

    boolean visit(term3 n);
    void endVisit(term3 n);

}


