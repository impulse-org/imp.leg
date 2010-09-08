package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 44:  term ::= DoubleLiteral
 *</b>
 */
public class term__DoubleLiteral extends ASTNodeToken implements Iterm
{
    public IToken getDoubleLiteral() { return leftIToken; }

    public term__DoubleLiteral(IToken token) { super(token); initialize(); }

    public void accept(IAstVisitor v)
    {
        if (! v.preVisit(this)) return;
        enter((Visitor) v);
        v.postVisit(this);
    }

    public void enter(Visitor v)
    {
        v.visit(this);
        v.endVisit(this);
    }
}


