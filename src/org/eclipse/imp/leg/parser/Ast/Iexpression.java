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
 *<li>expression0
 *<li>expression1
 *<li>expression2
 *<li>expression3
 *<li>expression4
 *<li>expression5
 *<li>expression6
 *<li>expression7
 *<li>term0
 *<li>term1
 *<li>term2
 *<li>term3
 *</ul>
 *</b>
 */
public interface Iexpression
{
    public IToken getLeftIToken();
    public IToken getRightIToken();

    void accept(IAstVisitor v);
}


