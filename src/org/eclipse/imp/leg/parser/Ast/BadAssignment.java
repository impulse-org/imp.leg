package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 56:  BadAssignment ::= identifier = MissingExpression
 *</b>
 */
public class BadAssignment extends ASTNode implements IBadAssignment
{
    private identifier _identifier;
    private ASTNodeToken _ASSIGN;
    private ASTNodeToken _MissingExpression;

    public identifier getidentifier() { return _identifier; }
    public ASTNodeToken getASSIGN() { return _ASSIGN; }
    public ASTNodeToken getMissingExpression() { return _MissingExpression; }

    public BadAssignment(IToken leftIToken, IToken rightIToken,
                         identifier _identifier,
                         ASTNodeToken _ASSIGN,
                         ASTNodeToken _MissingExpression)
    {
        super(leftIToken, rightIToken);

        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._ASSIGN = _ASSIGN;
        ((ASTNode) _ASSIGN).setParent(this);
        this._MissingExpression = _MissingExpression;
        ((ASTNode) _MissingExpression).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_identifier);
        list.add(_ASSIGN);
        list.add(_MissingExpression);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof BadAssignment)) return false;
        if (! super.equals(o)) return false;
        BadAssignment other = (BadAssignment) o;
        if (! _identifier.equals(other._identifier)) return false;
        if (! _ASSIGN.equals(other._ASSIGN)) return false;
        if (! _MissingExpression.equals(other._MissingExpression)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_identifier.hashCode());
        hash = hash * 31 + (_ASSIGN.hashCode());
        hash = hash * 31 + (_MissingExpression.hashCode());
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
            _identifier.accept(v);
            _ASSIGN.accept(v);
            _MissingExpression.accept(v);
        }
        v.endVisit(this);
    }
}


