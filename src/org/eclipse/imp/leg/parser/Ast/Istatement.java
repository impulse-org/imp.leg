package org.eclipse.imp.leg.parser.Ast;

/**
 * is implemented by:
 *<b>
 *<ul>
 *<li>statement
 *<li>block
 *<li>assignmentStmt
 *<li>whileStmt
 *<li>returnStmt
 *<li>functionStmt
 *<li>BadAssignment
 *<li>declarationStmt__declaration_SEMICOLON
 *<li>declarationStmt__declaration_ASSIGN_expression_SEMICOLON
 *<li>ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement
 *<li>ifStmt__if_LEFTPAREN_expression_RIGHTPAREN_statement_else_statement
 *</ul>
 *</b>
 */
public interface Istatement extends IASTNodeToken {}


