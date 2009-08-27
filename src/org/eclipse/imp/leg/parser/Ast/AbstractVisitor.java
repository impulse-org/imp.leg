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

    public boolean visit(declarationStmt0 n) { unimplementedVisitor("visit(declarationStmt0)"); return true; }
    public void endVisit(declarationStmt0 n) { unimplementedVisitor("endVisit(declarationStmt0)"); }

    public boolean visit(declarationStmt1 n) { unimplementedVisitor("visit(declarationStmt1)"); return true; }
    public void endVisit(declarationStmt1 n) { unimplementedVisitor("endVisit(declarationStmt1)"); }

    public boolean visit(primitiveType0 n) { unimplementedVisitor("visit(primitiveType0)"); return true; }
    public void endVisit(primitiveType0 n) { unimplementedVisitor("endVisit(primitiveType0)"); }

    public boolean visit(primitiveType1 n) { unimplementedVisitor("visit(primitiveType1)"); return true; }
    public void endVisit(primitiveType1 n) { unimplementedVisitor("endVisit(primitiveType1)"); }

    public boolean visit(primitiveType2 n) { unimplementedVisitor("visit(primitiveType2)"); return true; }
    public void endVisit(primitiveType2 n) { unimplementedVisitor("endVisit(primitiveType2)"); }

    public boolean visit(ifStmt0 n) { unimplementedVisitor("visit(ifStmt0)"); return true; }
    public void endVisit(ifStmt0 n) { unimplementedVisitor("endVisit(ifStmt0)"); }

    public boolean visit(ifStmt1 n) { unimplementedVisitor("visit(ifStmt1)"); return true; }
    public void endVisit(ifStmt1 n) { unimplementedVisitor("endVisit(ifStmt1)"); }

    public boolean visit(expression0 n) { unimplementedVisitor("visit(expression0)"); return true; }
    public void endVisit(expression0 n) { unimplementedVisitor("endVisit(expression0)"); }

    public boolean visit(expression1 n) { unimplementedVisitor("visit(expression1)"); return true; }
    public void endVisit(expression1 n) { unimplementedVisitor("endVisit(expression1)"); }

    public boolean visit(expression2 n) { unimplementedVisitor("visit(expression2)"); return true; }
    public void endVisit(expression2 n) { unimplementedVisitor("endVisit(expression2)"); }

    public boolean visit(expression3 n) { unimplementedVisitor("visit(expression3)"); return true; }
    public void endVisit(expression3 n) { unimplementedVisitor("endVisit(expression3)"); }

    public boolean visit(expression4 n) { unimplementedVisitor("visit(expression4)"); return true; }
    public void endVisit(expression4 n) { unimplementedVisitor("endVisit(expression4)"); }

    public boolean visit(expression5 n) { unimplementedVisitor("visit(expression5)"); return true; }
    public void endVisit(expression5 n) { unimplementedVisitor("endVisit(expression5)"); }

    public boolean visit(expression6 n) { unimplementedVisitor("visit(expression6)"); return true; }
    public void endVisit(expression6 n) { unimplementedVisitor("endVisit(expression6)"); }

    public boolean visit(expression7 n) { unimplementedVisitor("visit(expression7)"); return true; }
    public void endVisit(expression7 n) { unimplementedVisitor("endVisit(expression7)"); }

    public boolean visit(term0 n) { unimplementedVisitor("visit(term0)"); return true; }
    public void endVisit(term0 n) { unimplementedVisitor("endVisit(term0)"); }

    public boolean visit(term1 n) { unimplementedVisitor("visit(term1)"); return true; }
    public void endVisit(term1 n) { unimplementedVisitor("endVisit(term1)"); }

    public boolean visit(term2 n) { unimplementedVisitor("visit(term2)"); return true; }
    public void endVisit(term2 n) { unimplementedVisitor("endVisit(term2)"); }

    public boolean visit(term3 n) { unimplementedVisitor("visit(term3)"); return true; }
    public void endVisit(term3 n) { unimplementedVisitor("endVisit(term3)"); }


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
        else if (n instanceof declarationStmt0) return visit((declarationStmt0) n);
        else if (n instanceof declarationStmt1) return visit((declarationStmt1) n);
        else if (n instanceof primitiveType0) return visit((primitiveType0) n);
        else if (n instanceof primitiveType1) return visit((primitiveType1) n);
        else if (n instanceof primitiveType2) return visit((primitiveType2) n);
        else if (n instanceof ifStmt0) return visit((ifStmt0) n);
        else if (n instanceof ifStmt1) return visit((ifStmt1) n);
        else if (n instanceof expression0) return visit((expression0) n);
        else if (n instanceof expression1) return visit((expression1) n);
        else if (n instanceof expression2) return visit((expression2) n);
        else if (n instanceof expression3) return visit((expression3) n);
        else if (n instanceof expression4) return visit((expression4) n);
        else if (n instanceof expression5) return visit((expression5) n);
        else if (n instanceof expression6) return visit((expression6) n);
        else if (n instanceof expression7) return visit((expression7) n);
        else if (n instanceof term0) return visit((term0) n);
        else if (n instanceof term1) return visit((term1) n);
        else if (n instanceof term2) return visit((term2) n);
        else if (n instanceof term3) return visit((term3) n);
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
        else if (n instanceof declarationStmt0) endVisit((declarationStmt0) n);
        else if (n instanceof declarationStmt1) endVisit((declarationStmt1) n);
        else if (n instanceof primitiveType0) endVisit((primitiveType0) n);
        else if (n instanceof primitiveType1) endVisit((primitiveType1) n);
        else if (n instanceof primitiveType2) endVisit((primitiveType2) n);
        else if (n instanceof ifStmt0) endVisit((ifStmt0) n);
        else if (n instanceof ifStmt1) endVisit((ifStmt1) n);
        else if (n instanceof expression0) endVisit((expression0) n);
        else if (n instanceof expression1) endVisit((expression1) n);
        else if (n instanceof expression2) endVisit((expression2) n);
        else if (n instanceof expression3) endVisit((expression3) n);
        else if (n instanceof expression4) endVisit((expression4) n);
        else if (n instanceof expression5) endVisit((expression5) n);
        else if (n instanceof expression6) endVisit((expression6) n);
        else if (n instanceof expression7) endVisit((expression7) n);
        else if (n instanceof term0) endVisit((term0) n);
        else if (n instanceof term1) endVisit((term1) n);
        else if (n instanceof term2) endVisit((term2) n);
        else if (n instanceof term3) endVisit((term3) n);
        throw new UnsupportedOperationException("visit(" + n.getClass().toString() + ")");
    }
}

