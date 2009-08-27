package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 49:  functionCall ::= identifier ( expressions )
 *</b>
 */
public class functionCall extends ASTNode implements IfunctionCall
{
    private identifier _identifier;
    private ASTNodeToken _LEFTPAREN;
    private expressionList _expressions;
    private ASTNodeToken _RIGHTPAREN;

    public identifier getidentifier() { return _identifier; }
    public ASTNodeToken getLEFTPAREN() { return _LEFTPAREN; }
    public expressionList getexpressions() { return _expressions; }
    public ASTNodeToken getRIGHTPAREN() { return _RIGHTPAREN; }

    public functionCall(IToken leftIToken, IToken rightIToken,
                        identifier _identifier,
                        ASTNodeToken _LEFTPAREN,
                        expressionList _expressions,
                        ASTNodeToken _RIGHTPAREN)
    {
        super(leftIToken, rightIToken);

        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._LEFTPAREN = _LEFTPAREN;
        ((ASTNode) _LEFTPAREN).setParent(this);
        this._expressions = _expressions;
        ((ASTNode) _expressions).setParent(this);
        this._RIGHTPAREN = _RIGHTPAREN;
        ((ASTNode) _RIGHTPAREN).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_identifier);
        list.add(_LEFTPAREN);
        list.add(_expressions);
        list.add(_RIGHTPAREN);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof functionCall)) return false;
        if (! super.equals(o)) return false;
        functionCall other = (functionCall) o;
        if (! _identifier.equals(other._identifier)) return false;
        if (! _LEFTPAREN.equals(other._LEFTPAREN)) return false;
        if (! _expressions.equals(other._expressions)) return false;
        if (! _RIGHTPAREN.equals(other._RIGHTPAREN)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_identifier.hashCode());
        hash = hash * 31 + (_LEFTPAREN.hashCode());
        hash = hash * 31 + (_expressions.hashCode());
        hash = hash * 31 + (_RIGHTPAREN.hashCode());
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
            _LEFTPAREN.accept(v);
            _expressions.accept(v);
            _RIGHTPAREN.accept(v);
        }
        v.endVisit(this);
    }
}


