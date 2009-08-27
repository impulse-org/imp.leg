package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 21:  declarationStmt ::= declaration ;
 *</b>
 */
public class declarationStmt0 extends ASTNode implements IdeclarationStmt
{
    private declaration _declaration;
    private ASTNodeToken _SEMICOLON;

    public declaration getdeclaration() { return _declaration; }
    public ASTNodeToken getSEMICOLON() { return _SEMICOLON; }

    public declarationStmt0(IToken leftIToken, IToken rightIToken,
                            declaration _declaration,
                            ASTNodeToken _SEMICOLON)
    {
        super(leftIToken, rightIToken);

        this._declaration = _declaration;
        ((ASTNode) _declaration).setParent(this);
        this._SEMICOLON = _SEMICOLON;
        ((ASTNode) _SEMICOLON).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_declaration);
        list.add(_SEMICOLON);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof declarationStmt0)) return false;
        if (! super.equals(o)) return false;
        declarationStmt0 other = (declarationStmt0) o;
        if (! _declaration.equals(other._declaration)) return false;
        if (! _SEMICOLON.equals(other._SEMICOLON)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_declaration.hashCode());
        hash = hash * 31 + (_SEMICOLON.hashCode());
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
            _declaration.accept(v);
            _SEMICOLON.accept(v);
        }
        v.endVisit(this);
    }
}


