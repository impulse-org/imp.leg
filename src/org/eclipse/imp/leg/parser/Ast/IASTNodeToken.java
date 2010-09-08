package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 * is always implemented by <b>ASTNodeToken</b>. It is also implemented by:
 *<b>
 *<ul>
 *<li>statement
 *<li>block
 *<li>Type
 *<li>assignmentStmt
 *<li>whileStmt
 *<li>returnStmt
 *<li>functionCall
 *<li>functionStmt
 *<li>identifier
 *<li>BadAssignment
 *<li>declarationStmt__declaration_SEMICOLON
 *<li>declarationStmt__declaration_ASSIGN_expression_SEMICOLON
 *<li>primitiveType__boolean
 *<li>primitiveType__double
 *<li>primitiveType__int
 *<li>ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement
 *<li>ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement
 *<li>term__NUMBER
 *<li>term__DoubleLiteral
 *<li>term__true
 *<li>term__false
 *</ul>
 *</b>
 */
public interface IASTNodeToken
{
    public IToken getLeftIToken();
    public IToken getRightIToken();

    void accept(IAstVisitor v);
}


