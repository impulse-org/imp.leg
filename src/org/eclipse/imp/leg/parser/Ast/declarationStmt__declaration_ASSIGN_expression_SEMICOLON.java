package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 22:  declarationStmt ::= declaration = expression ;
 *</b>
 */
public class declarationStmt__declaration_ASSIGN_expression_SEMICOLON extends ASTNode implements IdeclarationStmt
{
    private declaration _declaration;
    private ASTNodeToken _ASSIGN;
    private Iexpression _expression;
    private ASTNodeToken _SEMICOLON;

    public declaration getdeclaration() { return _declaration; }
    public ASTNodeToken getASSIGN() { return _ASSIGN; }
    public Iexpression getexpression() { return _expression; }
    public ASTNodeToken getSEMICOLON() { return _SEMICOLON; }

    public declarationStmt__declaration_ASSIGN_expression_SEMICOLON(IToken leftIToken, IToken rightIToken,
                                                                    declaration _declaration,
                                                                    ASTNodeToken _ASSIGN,
                                                                    Iexpression _expression,
                                                                    ASTNodeToken _SEMICOLON)
    {
        super(leftIToken, rightIToken);

        this._declaration = _declaration;
        ((ASTNode) _declaration).setParent(this);
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
        list.add(_declaration);
        list.add(_ASSIGN);
        list.add(_expression);
        list.add(_SEMICOLON);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof declarationStmt__declaration_ASSIGN_expression_SEMICOLON)) return false;
        if (! super.equals(o)) return false;
        declarationStmt__declaration_ASSIGN_expression_SEMICOLON other = (declarationStmt__declaration_ASSIGN_expression_SEMICOLON) o;
        if (! _declaration.equals(other._declaration)) return false;
        if (! _ASSIGN.equals(other._ASSIGN)) return false;
        if (! _expression.equals(other._expression)) return false;
        if (! _SEMICOLON.equals(other._SEMICOLON)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_declaration.hashCode());
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
            _declaration.accept(v);
            _ASSIGN.accept(v);
            _expression.accept(v);
            _SEMICOLON.accept(v);
        }
        v.endVisit(this);
    }
}


