package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

public abstract class AbstractVisitor implements Visitor
{
    public abstract void unimplementedVisitor(String s);

    public boolean preVisit(IAst element) { return true; }

    public void postVisit(IAst element) {}

    public boolean visit(ASTNodeToken n) { unimplementedVisitor("visit(ASTNodeToken)"); return true; }
    public void endVisit(ASTNodeToken n) { unimplementedVisitor("endVisit(ASTNodeToken)"); }

    public boolean visit(functionDeclarationList n) { unimplementedVisitor("visit(functionDeclarationList)"); return true; }
    public void endVisit(functionDeclarationList n) { unimplementedVisitor("endVisit(functionDeclarationList)"); }

    public boolean visit(functionDeclaration n) { unimplementedVisitor("visit(functionDeclaration)"); return true; }
    public void endVisit(functionDeclaration n) { unimplementedVisitor("endVisit(functionDeclaration)"); }

    public boolean visit(functionHeader n) { unimplementedVisitor("visit(functionHeader)"); return true; }
    public void endVisit(functionHeader n) { unimplementedVisitor("endVisit(functionHeader)"); }

    public boolean visit(declarationList n) { unimplementedVisitor("visit(declarationList)"); return true; }
    public void endVisit(declarationList n) { unimplementedVisitor("endVisit(declarationList)"); }

    public boolean visit(declaration n) { unimplementedVisitor("visit(declaration)"); return true; }
    public void endVisit(declaration n) { unimplementedVisitor("endVisit(declaration)"); }

    public boolean visit(statementList n) { unimplementedVisitor("visit(statementList)"); return true; }
    public void endVisit(statementList n) { unimplementedVisitor("endVisit(statementList)"); }

    public boolean visit(statement n) { unimplementedVisitor("visit(statement)"); return true; }
    public void endVisit(statement n) { unimplementedVisitor("endVisit(statement)"); }

    public boolean visit(block n) { unimplementedVisitor("visit(block)"); return true; }
    public void endVisit(block n) { unimplementedVisitor("endVisit(block)"); }

    public boolean visit(Type n) { unimplementedVisitor("visit(Type)"); return true; }
    public void endVisit(Type n) { unimplementedVisitor("endVisit(Type)"); }

    public boolean visit(assignmentStmt n) { unimplementedVisitor("visit(assignmentStmt)"); return true; }
    public void endVisit(assignmentStmt n) { unimplementedVisitor("endVisit(assignmentStmt)"); }

    public boolean visit(whileStmt n) { unimplementedVisitor("visit(whileStmt)"); return true; }
    public void endVisit(whileStmt n) { unimplementedVisitor("endVisit(whileStmt)"); }

    public boolean visit(returnStmt n) { unimplementedVisitor("visit(returnStmt)"); return true; }
    public void endVisit(returnStmt n) { unimplementedVisitor("endVisit(returnStmt)"); }

    public boolean visit(functionCall n) { unimplementedVisitor("visit(functionCall)"); return true; }
    public void endVisit(functionCall n) { unimplementedVisitor("endVisit(functionCall)"); }

    public boolean visit(functionStmt n) { unimplementedVisitor("visit(functionStmt)"); return true; }
    public void endVisit(functionStmt n) { unimplementedVisitor("endVisit(functionStmt)"); }

    public boolean visit(expressionList n) { unimplementedVisitor("visit(expressionList)"); return true; }
    public void endVisit(expressionList n) { unimplementedVisitor("endVisit(expressionList)"); }

    public boolean visit(identifier n) { unimplementedVisitor("visit(identifier)"); return true; }
    public void endVisit(identifier n) { unimplementedVisitor("endVisit(identifier)"); }

    public boolean visit(BadAssignment n) { unimplementedVisitor("visit(BadAssignment)"); return true; }
    public void endVisit(BadAssignment n) { unimplementedVisitor("endVisit(BadAssignment)"); }

    public boolean visit(declarationStmt__declaration_SEMICOLON n) { unimplementedVisitor("visit(declarationStmt__declaration_SEMICOLON)"); return true; }
    public void endVisit(declarationStmt__declaration_SEMICOLON n) { unimplementedVisitor("endVisit(declarationStmt__declaration_SEMICOLON)"); }

    public boolean visit(declarationStmt__declaration_ASSIGN_expression_SEMICOLON n) { unimplementedVisitor("visit(declarationStmt__declaration_ASSIGN_expression_SEMICOLON)"); return true; }
    public void endVisit(declarationStmt__declaration_ASSIGN_expression_SEMICOLON n) { unimplementedVisitor("endVisit(declarationStmt__declaration_ASSIGN_expression_SEMICOLON)"); }

    public boolean visit(primitiveType__boolean n) { unimplementedVisitor("visit(primitiveType__boolean)"); return true; }
    public void endVisit(primitiveType__boolean n) { unimplementedVisitor("endVisit(primitiveType__boolean)"); }

    public boolean visit(primitiveType__double n) { unimplementedVisitor("visit(primitiveType__double)"); return true; }
    public void endVisit(primitiveType__double n) { unimplementedVisitor("endVisit(primitiveType__double)"); }

    public boolean visit(primitiveType__int n) { unimplementedVisitor("visit(primitiveType__int)"); return true; }
    public void endVisit(primitiveType__int n) { unimplementedVisitor("endVisit(primitiveType__int)"); }

    public boolean visit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement n) { unimplementedVisitor("visit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement)"); return true; }
    public void endVisit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement n) { unimplementedVisitor("endVisit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement)"); }

    public boolean visit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement n) { unimplementedVisitor("visit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement)"); return true; }
    public void endVisit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement n) { unimplementedVisitor("endVisit(ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement)"); }

    public boolean visit(expression__expression_PLUS_term n) { unimplementedVisitor("visit(expression__expression_PLUS_term)"); return true; }
    public void endVisit(expression__expression_PLUS_term n) { unimplementedVisitor("endVisit(expression__expression_PLUS_term)"); }

    public boolean visit(expression__expression_MINUS_term n) { unimplementedVisitor("visit(expression__expression_MINUS_term)"); return true; }
    public void endVisit(expression__expression_MINUS_term n) { unimplementedVisitor("endVisit(expression__expression_MINUS_term)"); }

    public boolean visit(expression__expression_TIMES_term n) { unimplementedVisitor("visit(expression__expression_TIMES_term)"); return true; }
    public void endVisit(expression__expression_TIMES_term n) { unimplementedVisitor("endVisit(expression__expression_TIMES_term)"); }

    public boolean visit(expression__expression_DIVIDE_term n) { unimplementedVisitor("visit(expression__expression_DIVIDE_term)"); return true; }
    public void endVisit(expression__expression_DIVIDE_term n) { unimplementedVisitor("endVisit(expression__expression_DIVIDE_term)"); }

    public boolean visit(expression__expression_GREATER_term n) { unimplementedVisitor("visit(expression__expression_GREATER_term)"); return true; }
    public void endVisit(expression__expression_GREATER_term n) { unimplementedVisitor("endVisit(expression__expression_GREATER_term)"); }

    public boolean visit(expression__expression_LESS_term n) { unimplementedVisitor("visit(expression__expression_LESS_term)"); return true; }
    public void endVisit(expression__expression_LESS_term n) { unimplementedVisitor("endVisit(expression__expression_LESS_term)"); }

    public boolean visit(expression__expression_EQUAL_term n) { unimplementedVisitor("visit(expression__expression_EQUAL_term)"); return true; }
    public void endVisit(expression__expression_EQUAL_term n) { unimplementedVisitor("endVisit(expression__expression_EQUAL_term)"); }

    public boolean visit(expression__expression_NOTEQUAL_term n) { unimplementedVisitor("visit(expression__expression_NOTEQUAL_term)"); return true; }
    public void endVisit(expression__expression_NOTEQUAL_term n) { unimplementedVisitor("endVisit(expression__expression_NOTEQUAL_term)"); }

    public boolean visit(term__NUMBER n) { unimplementedVisitor("visit(term__NUMBER)"); return true; }
    public void endVisit(term__NUMBER n) { unimplementedVisitor("endVisit(term__NUMBER)"); }

    public boolean visit(term__DoubleLiteral n) { unimplementedVisitor("visit(term__DoubleLiteral)"); return true; }
    public void endVisit(term__DoubleLiteral n) { unimplementedVisitor("endVisit(term__DoubleLiteral)"); }

    public boolean visit(term__true n) { unimplementedVisitor("visit(term__true)"); return true; }
    public void endVisit(term__true n) { unimplementedVisitor("endVisit(term__true)"); }

    public boolean visit(term__false n) { unimplementedVisitor("visit(term__false)"); return true; }
    public void endVisit(term__false n) { unimplementedVisitor("endVisit(term__false)"); }


    public boolean visit(ASTNode n)
    {
        if (n instanceof ASTNodeToken) return visit((ASTNodeToken) n);
        else if (n instanceof functionDeclarationList) return visit((functionDeclarationList) n);
        else if (n instanceof functionDeclaration) return visit((functionDeclaration) n);
        else if (n instanceof functionHeader) return visit((functionHeader) n);
        else if (n instanceof declarationList) return visit((declarationList) n);
        else if (n instanceof declaration) return visit((declaration) n);
        else if (n instanceof statementList) return visit((statementList) n);
        else if (n instanceof statement) return visit((statement) n);
        else if (n instanceof block) return visit((block) n);
        else if (n instanceof Type) return visit((Type) n);
        else if (n instanceof assignmentStmt) return visit((assignmentStmt) n);
        else if (n instanceof whileStmt) return visit((whileStmt) n);
        else if (n instanceof returnStmt) return visit((returnStmt) n);
        else if (n instanceof functionCall) return visit((functionCall) n);
        else if (n instanceof functionStmt) return visit((functionStmt) n);
        else if (n instanceof expressionList) return visit((expressionList) n);
        else if (n instanceof identifier) return visit((identifier) n);
        else if (n instanceof BadAssignment) return visit((BadAssignment) n);
        else if (n instanceof declarationStmt__declaration_SEMICOLON) return visit((declarationStmt__declaration_SEMICOLON) n);
        else if (n instanceof declarationStmt__declaration_ASSIGN_expression_SEMICOLON) return visit((declarationStmt__declaration_ASSIGN_expression_SEMICOLON) n);
        else if (n instanceof primitiveType__boolean) return visit((primitiveType__boolean) n);
        else if (n instanceof primitiveType__double) return visit((primitiveType__double) n);
        else if (n instanceof primitiveType__int) return visit((primitiveType__int) n);
        else if (n instanceof ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement) return visit((ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement) n);
        else if (n instanceof ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement) return visit((ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement) n);
        else if (n instanceof expression__expression_PLUS_term) return visit((expression__expression_PLUS_term) n);
        else if (n instanceof expression__expression_MINUS_term) return visit((expression__expression_MINUS_term) n);
        else if (n instanceof expression__expression_TIMES_term) return visit((expression__expression_TIMES_term) n);
        else if (n instanceof expression__expression_DIVIDE_term) return visit((expression__expression_DIVIDE_term) n);
        else if (n instanceof expression__expression_GREATER_term) return visit((expression__expression_GREATER_term) n);
        else if (n instanceof expression__expression_LESS_term) return visit((expression__expression_LESS_term) n);
        else if (n instanceof expression__expression_EQUAL_term) return visit((expression__expression_EQUAL_term) n);
        else if (n instanceof expression__expression_NOTEQUAL_term) return visit((expression__expression_NOTEQUAL_term) n);
        else if (n instanceof term__NUMBER) return visit((term__NUMBER) n);
        else if (n instanceof term__DoubleLiteral) return visit((term__DoubleLiteral) n);
        else if (n instanceof term__true) return visit((term__true) n);
        else if (n instanceof term__false) return visit((term__false) n);
        throw new UnsupportedOperationException("visit(" + n.getClass().toString() + ")");
    }
    public void endVisit(ASTNode n)
    {
        if (n instanceof ASTNodeToken) endVisit((ASTNodeToken) n);
        else if (n instanceof functionDeclarationList) endVisit((functionDeclarationList) n);
        else if (n instanceof functionDeclaration) endVisit((functionDeclaration) n);
        else if (n instanceof functionHeader) endVisit((functionHeader) n);
        else if (n instanceof declarationList) endVisit((declarationList) n);
        else if (n instanceof declaration) endVisit((declaration) n);
        else if (n instanceof statementList) endVisit((statementList) n);
        else if (n instanceof statement) endVisit((statement) n);
        else if (n instanceof block) endVisit((block) n);
        else if (n instanceof Type) endVisit((Type) n);
        else if (n instanceof assignmentStmt) endVisit((assignmentStmt) n);
        else if (n instanceof whileStmt) endVisit((whileStmt) n);
        else if (n instanceof returnStmt) endVisit((returnStmt) n);
        else if (n instanceof functionCall) endVisit((functionCall) n);
        else if (n instanceof functionStmt) endVisit((functionStmt) n);
        else if (n instanceof expressionList) endVisit((expressionList) n);
        else if (n instanceof identifier) endVisit((identifier) n);
        else if (n instanceof BadAssignment) endVisit((BadAssignment) n);
        else if (n instanceof declarationStmt__declaration_SEMICOLON) endVisit((declarationStmt__declaration_SEMICOLON) n);
        else if (n instanceof declarationStmt__declaration_ASSIGN_expression_SEMICOLON) endVisit((declarationStmt__declaration_ASSIGN_expression_SEMICOLON) n);
        else if (n instanceof primitiveType__boolean) endVisit((primitiveType__boolean) n);
        else if (n instanceof primitiveType__double) endVisit((primitiveType__double) n);
        else if (n instanceof primitiveType__int) endVisit((primitiveType__int) n);
        else if (n instanceof ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement) endVisit((ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement) n);
        else if (n instanceof ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement) endVisit((ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement) n);
        else if (n instanceof expression__expression_PLUS_term) endVisit((expression__expression_PLUS_term) n);
        else if (n instanceof expression__expression_MINUS_term) endVisit((expression__expression_MINUS_term) n);
        else if (n instanceof expression__expression_TIMES_term) endVisit((expression__expression_TIMES_term) n);
        else if (n instanceof expression__expression_DIVIDE_term) endVisit((expression__expression_DIVIDE_term) n);
        else if (n instanceof expression__expression_GREATER_term) endVisit((expression__expression_GREATER_term) n);
        else if (n instanceof expression__expression_LESS_term) endVisit((expression__expression_LESS_term) n);
        else if (n instanceof expression__expression_EQUAL_term) endVisit((expression__expression_EQUAL_term) n);
        else if (n instanceof expression__expression_NOTEQUAL_term) endVisit((expression__expression_NOTEQUAL_term) n);
        else if (n instanceof term__NUMBER) endVisit((term__NUMBER) n);
        else if (n instanceof term__DoubleLiteral) endVisit((term__DoubleLiteral) n);
        else if (n instanceof term__true) endVisit((term__true) n);
        else if (n instanceof term__false) endVisit((term__false) n);
        throw new UnsupportedOperationException("visit(" + n.getClass().toString() + ")");
    }
}

