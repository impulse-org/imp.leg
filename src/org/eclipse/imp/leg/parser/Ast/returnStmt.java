package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 33:  returnStmt ::= return expression ;
 *</b>
 */
public class returnStmt extends ASTNode implements IreturnStmt
{
    private ASTNodeToken _return;
    private Iexpression _expression;
    private ASTNodeToken _SEMICOLON;

    public ASTNodeToken getreturn() { return _return; }
    public Iexpression getexpression() { return _expression; }
    public ASTNodeToken getSEMICOLON() { return _SEMICOLON; }

    public returnStmt(IToken leftIToken, IToken rightIToken,
                      ASTNodeToken _return,
                      Iexpression _expression,
                      ASTNodeToken _SEMICOLON)
    {
        super(leftIToken, rightIToken);

        this._return = _return;
        ((ASTNode) _return).setParent(this);
        this._expression = _expression;
        ((ASTNode) _expression).setParent(this);
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
        list.add(_return);
        list.add(_expression);
        list.add(_SEMICOLON);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof returnStmt)) return false;
        if (! super.equals(o)) return false;
        returnStmt other = (returnStmt) o;
        if (! _return.equals(other._return)) return false;
        if (! _expression.equals(other._expression)) return false;
        if (! _SEMICOLON.equals(other._SEMICOLON)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_return.hashCode());
        hash = hash * 31 + (_expression.hashCode());
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
            _return.accept(v);
            _expression.accept(v);
            _SEMICOLON.accept(v);
        }
        v.endVisit(this);
    }
}


