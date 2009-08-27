package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 50:  functionStmt ::= functionCall ;
 *</b>
 */
public class functionStmt extends ASTNode implements IfunctionStmt
{
    private functionCall _functionCall;
    private ASTNodeToken _SEMICOLON;

    public functionCall getfunctionCall() { return _functionCall; }
    public ASTNodeToken getSEMICOLON() { return _SEMICOLON; }

    public functionStmt(IToken leftIToken, IToken rightIToken,
                        functionCall _functionCall,
                        ASTNodeToken _SEMICOLON)
    {
        super(leftIToken, rightIToken);

        this._functionCall = _functionCall;
        ((ASTNode) _functionCall).setParent(this);
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
        list.add(_functionCall);
        list.add(_SEMICOLON);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof functionStmt)) return false;
        if (! super.equals(o)) return false;
        functionStmt other = (functionStmt) o;
        if (! _functionCall.equals(other._functionCall)) return false;
        if (! _SEMICOLON.equals(other._SEMICOLON)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_functionCall.hashCode());
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
            _functionCall.accept(v);
            _SEMICOLON.accept(v);
        }
        v.endVisit(this);
    }
}


