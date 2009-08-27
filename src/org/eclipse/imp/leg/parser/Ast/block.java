package org.eclipse.imp.leg.parser.Ast;

import org.eclipse.imp.leg.parser.*;
import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 20:  block ::= { stmtList }
 *</b>
 */
public class block extends ASTNode implements Iblock
{
    private LEGParser environment;
    public LEGParser getEnvironment() { return environment; }

    private ASTNodeToken _LEFTBRACE;
    private statementList _stmtList;
    private ASTNodeToken _RIGHTBRACE;

    public ASTNodeToken getLEFTBRACE() { return _LEFTBRACE; }
    public statementList getstmtList() { return _stmtList; }
    public ASTNodeToken getRIGHTBRACE() { return _RIGHTBRACE; }

    public block(LEGParser environment, IToken leftIToken, IToken rightIToken,
                 ASTNodeToken _LEFTBRACE,
                 statementList _stmtList,
                 ASTNodeToken _RIGHTBRACE)
    {
        super(leftIToken, rightIToken);

        this.environment = environment;
        this._LEFTBRACE = _LEFTBRACE;
        ((ASTNode) _LEFTBRACE).setParent(this);
        this._stmtList = _stmtList;
        ((ASTNode) _stmtList).setParent(this);
        this._RIGHTBRACE = _RIGHTBRACE;
        ((ASTNode) _RIGHTBRACE).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_LEFTBRACE);
        list.add(_stmtList);
        list.add(_RIGHTBRACE);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof block)) return false;
        if (! super.equals(o)) return false;
        block other = (block) o;
        if (! _LEFTBRACE.equals(other._LEFTBRACE)) return false;
        if (! _stmtList.equals(other._stmtList)) return false;
        if (! _RIGHTBRACE.equals(other._RIGHTBRACE)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_LEFTBRACE.hashCode());
        hash = hash * 31 + (_stmtList.hashCode());
        hash = hash * 31 + (_RIGHTBRACE.hashCode());
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
            _LEFTBRACE.accept(v);
            _stmtList.accept(v);
            _RIGHTBRACE.accept(v);
        }
        v.endVisit(this);
    }

    SymbolTable<IAst> symbolTable;
    public void setSymbolTable(SymbolTable<IAst> symbolTable) { this.symbolTable = symbolTable; }
    public SymbolTable<IAst> getSymbolTable() { return symbolTable; }
}


