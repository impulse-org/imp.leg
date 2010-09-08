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

    boolean visit(declarationStmt__declaration_SEMICOLON n);
    void endVisit(declarationStmt__declaration_SEMICOLON n);

    boolean visit(declarationStmt__declaration_ASSIGN_expression_SEMICOLON n);
    void endVisit(declarationStmt__declaration_ASSIGN_expression_SEMICOLON n);

    boolean visit(primitiveType__boolean n);
    void endVisit(primitiveType__boolean n);

    boolean visit(primitiveType__double n);
    void endVisit(primitiveType__double n);

    boolean visit(primitiveType__int n);
    void endVisit(primitiveType__int n);

    boolean visit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement n);
    void endVisit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement n);

    boolean visit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement n);
    void endVisit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement n);

    boolean visit(expression__expression_PLUS_term n);
    void endVisit(expression__expression_PLUS_term n);

    boolean visit(expression__expression_MINUS_term n);
    void endVisit(expression__expression_MINUS_term n);

    boolean visit(expression__expression_TIMES_term n);
    void endVisit(expression__expression_TIMES_term n);

    boolean visit(expression__expression_DIVIDE_term n);
    void endVisit(expression__expression_DIVIDE_term n);

    boolean visit(expression__expression_GREATER_term n);
    void endVisit(expression__expression_GREATER_term n);

    boolean visit(expression__expression_LESS_term n);
    void endVisit(expression__expression_LESS_term n);

    boolean visit(expression__expression_EQUAL_term n);
    void endVisit(expression__expression_EQUAL_term n);

    boolean visit(expression__expression_NOTEQUAL_term n);
    void endVisit(expression__expression_NOTEQUAL_term n);

    boolean visit(term__NUMBER n);
    void endVisit(term__NUMBER n);

    boolean visit(term__DoubleLiteral n);
    void endVisit(term__DoubleLiteral n);

    boolean visit(term__true n);
    void endVisit(term__true n);

    boolean visit(term__false n);
    void endVisit(term__false n);

}


