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
 *<li>declarationStmt0
 *<li>declarationStmt1
 *<li>primitiveType0
 *<li>primitiveType1
 *<li>primitiveType2
 *<li>ifStmt0
 *<li>ifStmt1
 *<li>term0
 *<li>term1
 *<li>term2
 *<li>term3
 *</ul>
 *</b>
 */
public interface IASTNodeToken
{
    public IToken getLeftIToken();
    public IToken getRightIToken();

    void accept(IAstVisitor v);
}


