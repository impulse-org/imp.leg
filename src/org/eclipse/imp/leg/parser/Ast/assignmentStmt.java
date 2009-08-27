package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<em>
 *<li>Rule 29:  assignmentStmt ::= BadAssignment
 *</em>
 *<p>
 *<b>
 *<li>Rule 28:  assignmentStmt ::= identifier = expression ;
 *</b>
 */
public class assignmentStmt extends ASTNode implements IassignmentStmt
{
    private identifier _identifier;
    private ASTNodeToken _ASSIGN;
    private Iexpression _expression;
    private ASTNodeToken _SEMICOLON;

    public identifier getidentifier() { return _identifier; }
    public ASTNodeToken getASSIGN() { return _ASSIGN; }
    public Iexpression getexpression() { return _expression; }
    public ASTNodeToken getSEMICOLON() { return _SEMICOLON; }

    public assignmentStmt(IToken leftIToken, IToken rightIToken,
                          identifier _identifier,
                          ASTNodeToken _ASSIGN,
                          Iexpression _expression,
                          ASTNodeToken _SEMICOLON)
    {
        super(leftIToken, rightIToken);

        this._identifier = _identifier;
        ((ASTNode) _identifier).setParent(this);
        this._ASSIGN = _ASSIGN;
        ((ASTNode) _ASSIGN).setParent(this);
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
        list.add(_identifier);
        list.add(_ASSIGN);
        list.add(_expression);
        list.add(_SEMICOLON);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof assignmentStmt)) return false;
        if (! super.equals(o)) return false;
        assignmentStmt other = (assignmentStmt) o;
        if (! _identifier.equals(other._identifier)) return false;
        if (! _ASSIGN.equals(other._ASSIGN)) return false;
        if (! _expression.equals(other._expression)) return false;
        if (! _SEMICOLON.equals(other._SEMICOLON)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_identifier.hashCode());
        hash = hash * 31 + (_ASSIGN.hashCode());
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
            _identifier.accept(v);
            _ASSIGN.accept(v);
            _expression.accept(v);
            _SEMICOLON.accept(v);
        }
        v.endVisit(this);
    }
}


