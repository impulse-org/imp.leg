package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 4:  functionHeader ::= Type identifier ( parameters )
 *</b>
 */
public class functionHeader extends ASTNode implements IfunctionHeader
{
    private IType _Type;
    private identifier _identifier;
    private ASTNodeToken _LEFTPAREN;
    private declarationList _parameters;
    private ASTNodeToken _RIGHTPAREN;

    public IType getType() { return _Type; }
    public identifier getidentifier() { return _identifier; }
    public ASTNodeToken getLEFTPAREN() { return _LEFTPAREN; }
    public declarationList getparameters() { return _parameters; }
    public ASTNodeToken getRIGHTPAREN() { return _RIGHTPAREN; }

    public functionHeader(IToken leftIToken, IToken rightIToken,
                          IType _Type,
                          identifier _identifier,
                          ASTNodeToken _LEFTPAREN,
                          declarationList _parameters,
                          ASTNodeToken _RIGHTPAREN)
    {
        super(leftIToken, rightIToken);

        this._Type = _Type;
        ((ASTNode) _Type).setParent(this);
        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._LEFTPAREN = _LEFTPAREN;
        ((ASTNode) _LEFTPAREN).setParent(this);
        this._parameters = _parameters;
        ((ASTNode) _parameters).setParent(this);
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
        list.add(_Type);
        list.add(_identifier);
        list.add(_LEFTPAREN);
        list.add(_parameters);
        list.add(_RIGHTPAREN);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof functionHeader)) return false;
        if (! super.equals(o)) return false;
        functionHeader other = (functionHeader) o;
        if (! _Type.equals(other._Type)) return false;
        if (! _identifier.equals(other._identifier)) return false;
        if (! _LEFTPAREN.equals(other._LEFTPAREN)) return false;
        if (! _parameters.equals(other._parameters)) return false;
        if (! _RIGHTPAREN.equals(other._RIGHTPAREN)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_Type.hashCode());
        hash = hash * 31 + (_identifier.hashCode());
        hash = hash * 31 + (_LEFTPAREN.hashCode());
        hash = hash * 31 + (_parameters.hashCode());
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
            _Type.accept(v);
            _identifier.accept(v);
            _LEFTPAREN.accept(v);
            _parameters.accept(v);
            _RIGHTPAREN.accept(v);
        }
        v.endVisit(this);
    }
}


