package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 37:  expression ::= expression / term
 *</b>
 */
public class expression__expression_DIVIDE_term extends ASTNode implements Iexpression
{
    private Iexpression _expression;
    private ASTNodeToken _DIVIDE;
    private Iterm _term;

    public Iexpression getexpression() { return _expression; }
    public ASTNodeToken getDIVIDE() { return _DIVIDE; }
    public Iterm getterm() { return _term; }

    public expression__expression_DIVIDE_term(IToken leftIToken, IToken rightIToken,
                                              Iexpression _expression,
                                              ASTNodeToken _DIVIDE,
                                              Iterm _term)
    {
        super(leftIToken, rightIToken);

        this._expression = _expression;
        ((ASTNode) _expression).setParent(this);
        this._DIVIDE = _DIVIDE;
        ((ASTNode) _DIVIDE).setParent(this);
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
        list.add(_DIVIDE);
        list.add(_term);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof expression__expression_DIVIDE_term)) return false;
        if (! super.equals(o)) return false;
        expression__expression_DIVIDE_term other = (expression__expression_DIVIDE_term) o;
        if (! _expression.equals(other._expression)) return false;
        if (! _DIVIDE.equals(other._DIVIDE)) return false;
        if (! _term.equals(other._term)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_expression.hashCode());
        hash = hash * 31 + (_DIVIDE.hashCode());
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
            _DIVIDE.accept(v);
            _term.accept(v);
        }
        v.endVisit(this);
    }
}


