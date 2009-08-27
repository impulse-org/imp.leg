package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 31:  ifStmt ::= if ( expression ) statement else statement
 *</b>
 */
public class ifStmt1 extends ASTNode implements IifStmt
{
    private ASTNodeToken _if;
    private ASTNodeToken _LEFTPAREN;
    private Iexpression _expression;
    private ASTNodeToken _RIGHTPAREN;
    private Istatement _statement;
    private ASTNodeToken _else;
    private Istatement _statement7;

    public ASTNodeToken getif() { return _if; }
    public ASTNodeToken getLEFTPAREN() { return _LEFTPAREN; }
    public Iexpression getexpression() { return _expression; }
    public ASTNodeToken getRIGHTPAREN() { return _RIGHTPAREN; }
    public Istatement getstatement() { return _statement; }
    public ASTNodeToken getelse() { return _else; }
    public Istatement getstatement7() { return _statement7; }

    public ifStmt1(IToken leftIToken, IToken rightIToken,
                   ASTNodeToken _if,
                   ASTNodeToken _LEFTPAREN,
                   Iexpression _expression,
                   ASTNodeToken _RIGHTPAREN,
                   Istatement _statement,
                   ASTNodeToken _else,
                   Istatement _statement7)
    {
        super(leftIToken, rightIToken);

        this._if = _if;
        ((ASTNode) _if).setParent(this);
        this._LEFTPAREN = _LEFTPAREN;
        ((ASTNode) _LEFTPAREN).setParent(this);
        this._expression = _expression;
        ((ASTNode) _expression).setParent(this);
        this._RIGHTPAREN = _RIGHTPAREN;
        ((ASTNode) _RIGHTPAREN).setParent(this);
        this._statement = _statement;
        ((ASTNode) _statement).setParent(this);
        this._else = _else;
        ((ASTNode) _else).setParent(this);
        this._statement7 = _statement7;
        ((ASTNode) _statement7).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_if);
        list.add(_LEFTPAREN);
        list.add(_expression);
        list.add(_RIGHTPAREN);
        list.add(_statement);
        list.add(_else);
        list.add(_statement7);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof ifStmt1)) return false;
        if (! super.equals(o)) return false;
        ifStmt1 other = (ifStmt1) o;
        if (! _if.equals(other._if)) return false;
        if (! _LEFTPAREN.equals(other._LEFTPAREN)) return false;
        if (! _expression.equals(other._expression)) return false;
        if (! _RIGHTPAREN.equals(other._RIGHTPAREN)) return false;
        if (! _statement.equals(other._statement)) return false;
        if (! _else.equals(other._else)) return false;
        if (! _statement7.equals(other._statement7)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_if.hashCode());
        hash = hash * 31 + (_LEFTPAREN.hashCode());
        hash = hash * 31 + (_expression.hashCode());
        hash = hash * 31 + (_RIGHTPAREN.hashCode());
        hash = hash * 31 + (_statement.hashCode());
        hash = hash * 31 + (_else.hashCode());
        hash = hash * 31 + (_statement7.hashCode());
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
            _if.accept(v);
            _LEFTPAREN.accept(v);
            _expression.accept(v);
            _RIGHTPAREN.accept(v);
            _statement.accept(v);
            _else.accept(v);
            _statement7.accept(v);
        }
        v.endVisit(this);
    }
}


