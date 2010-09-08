package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 * is implemented by:
 *<b>
 *<ul>
 *<li>functionCall
 *<li>identifier
 *<li>expression__expression_PLUS_term
 *<li>expression__expression_MINUS_term
 *<li>expression__expression_TIMES_term
 *<li>expression__expression_DIVIDE_term
 *<li>expression__expression_GREATER_term
 *<li>expression__expression_LESS_term
 *<li>expression__expression_EQUAL_term
 *<li>expression__expression_NOTEQUAL_term
 *<li>term__NUMBER
 *<li>term__DoubleLiteral
 *<li>term__true
 *<li>term__false
 *</ul>
 *</b>
 */
public interface Iexpression
{
    public IToken getLeftIToken();
    public IToken getRightIToken();

    void accept(IAstVisitor v);
}


