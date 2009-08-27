package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 32:  whileStmt ::= while ( expression ) statement
 *</b>
 */
public class whileStmt extends ASTNode implements IwhileStmt
{
    private ASTNodeToken _while;
    private ASTNodeToken _LEFTPAREN;
    private Iexpression _expression;
    private ASTNodeToken _RIGHTPAREN;
    private Istatement _statement;

    public ASTNodeToken getwhile() { return _while; }
    public ASTNodeToken getLEFTPAREN() { return _LEFTPAREN; }
    public Iexpression getexpression() { return _expression; }
    public ASTNodeToken getRIGHTPAREN() { return _RIGHTPAREN; }
    public Istatement getstatement() { return _statement; }

    public whileStmt(IToken leftIToken, IToken rightIToken,
                     ASTNodeToken _while,
                     ASTNodeToken _LEFTPAREN,
                     Iexpression _expression,
                     ASTNodeToken _RIGHTPAREN,
                     Istatement _statement)
    {
        super(leftIToken, rightIToken);

        this._while = _while;
        ((ASTNode) _while).setParent(this);
        this._LEFTPAREN = _LEFTPAREN;
        ((ASTNode) _LEFTPAREN).setParent(this);
        this._expression = _expression;
        ((ASTNode) _expression).setParent(this);
        this._RIGHTPAREN = _RIGHTPAREN;
        ((ASTNode) _RIGHTPAREN).setParent(this);
        this._statement = _statement;
        ((ASTNode) _statement).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_while);
        list.add(_LEFTPAREN);
        list.add(_expression);
        list.add(_RIGHTPAREN);
        list.add(_statement);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof whileStmt)) return false;
        if (! super.equals(o)) return false;
        whileStmt other = (whileStmt) o;
        if (! _while.equals(other._while)) return false;
        if (! _LEFTPAREN.equals(other._LEFTPAREN)) return false;
        if (! _expression.equals(other._expression)) return false;
        if (! _RIGHTPAREN.equals(other._RIGHTPAREN)) return false;
        if (! _statement.equals(other._statement)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_while.hashCode());
        hash = hash * 31 + (_LEFTPAREN.hashCode());
        hash = hash * 31 + (_expression.hashCode());
        hash = hash * 31 + (_RIGHTPAREN.hashCode());
        hash = hash * 31 + (_statement.hashCode());
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
            _while.accept(v);
            _LEFTPAREN.accept(v);
            _expression.accept(v);
            _RIGHTPAREN.accept(v);
            _statement.accept(v);
        }
        v.endVisit(this);
    }
}


