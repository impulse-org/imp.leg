package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 36:  expression ::= expression * term
 *</b>
 */
public class expression2 extends ASTNode implements Iexpression
{
    private Iexpression _expression;
    private ASTNodeToken _TIMES;
    private Iterm _term;

    public Iexpression getexpression() { return _expression; }
    public ASTNodeToken getTIMES() { return _TIMES; }
    public Iterm getterm() { return _term; }

    public expression2(IToken leftIToken, IToken rightIToken,
                       Iexpression _expression,
                       ASTNodeToken _TIMES,
                       Iterm _term)
    {
        super(leftIToken, rightIToken);

        this._expression = _expression;
        ((ASTNode) _expression).setParent(this);
        this._TIMES = _TIMES;
        ((ASTNode) _TIMES).setParent(this);
        this._term = _term;
        ((ASTNode) _term).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_expression);
        list.add(_TIMES);
        list.add(_term);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof expression2)) return false;
        if (! super.equals(o)) return false;
        expression2 other = (expression2) o;
        if (! _expression.equals(other._expression)) return false;
        if (! _TIMES.equals(other._TIMES)) return false;
        if (! _term.equals(other._term)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_expression.hashCode());
        hash = hash * 31 + (_TIMES.hashCode());
        hash = hash * 31 + (_term.hashCode());
        return hash;
    }

    public void accept(IAstVisitor v)
    {
        if (! v.preVisit(this)) return;
        enter((Visitor) v);
        v.postVisit(this);
    }

    public void enter(Visitor v)
    {
        boolean checkChildren = v.visit(this);
        if (checkChildren)
        {
            _expression.accept(v);
            _TIMES.accept(v);
            _term.accept(v);
        }
        v.endVisit(this);
    }
}


