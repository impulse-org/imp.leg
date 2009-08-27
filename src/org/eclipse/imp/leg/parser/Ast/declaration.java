package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 9:  declaration ::= primitiveType identifier
 *</b>
 */
public class declaration extends ASTNode implements Ideclaration
{
    private IprimitiveType _primitiveType;
    private identifier _identifier;

    public IprimitiveType getprimitiveType() { return _primitiveType; }
    public identifier getidentifier() { return _identifier; }

    public declaration(IToken leftIToken, IToken rightIToken,
                       IprimitiveType _primitiveType,
                       identifier _identifier)
    {
        super(leftIToken, rightIToken);

        this._primitiveType = _primitiveType;
        ((ASTNode) _primitiveType).setParent(this);
        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_primitiveType);
        list.add(_identifier);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof declaration)) return false;
        if (! super.equals(o)) return false;
        declaration other = (declaration) o;
        if (! _primitiveType.equals(other._primitiveType)) return false;
        if (! _identifier.equals(other._identifier)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_primitiveType.hashCode());
        hash = hash * 31 + (_identifier.hashCode());
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
            _primitiveType.accept(v);
            _identifier.accept(v);
        }
        v.endVisit(this);
    }
}


