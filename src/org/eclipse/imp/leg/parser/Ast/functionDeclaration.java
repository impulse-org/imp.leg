package org.eclipse.imp.leg.parser.Ast;

import org.eclipse.imp.leg.parser.*;
import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 3:  functionDeclaration ::= functionHeader block
 *</b>
 */
public class functionDeclaration extends ASTNode implements IfunctionDeclaration
{
    private LEGParser environment;
    public LEGParser getEnvironment() { return environment; }

    private functionHeader _functionHeader;
    private block _block;

    public functionHeader getfunctionHeader() { return _functionHeader; }
    public block getblock() { return _block; }

    public functionDeclaration(LEGParser environment, IToken leftIToken, IToken rightIToken,
                               functionHeader _functionHeader,
                               block _block)
    {
        super(leftIToken, rightIToken);

        this.environment = environment;
        this._functionHeader = _functionHeader;
        ((ASTNode) _functionHeader).setParent(this);
        this._block = _block;
        ((ASTNode) _block).setParent(this);
        initialize();
    }

    /**
     * A list of all children of this node, including the null ones.
     */
    public java.util.ArrayList getAllChildren()
    {
        java.util.ArrayList list = new java.util.ArrayList();
        list.add(_functionHeader);
        list.add(_block);
        return list;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof functionDeclaration)) return false;
        if (! super.equals(o)) return false;
        functionDeclaration other = (functionDeclaration) o;
        if (! _functionHeader.equals(other._functionHeader)) return false;
        if (! _block.equals(other._block)) return false;
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        hash = hash * 31 + (_functionHeader.hashCode());
        hash = hash * 31 + (_block.hashCode());
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
            _functionHeader.accept(v);
            _block.accept(v);
        }
        v.endVisit(this);
    }

    SymbolTable<IAst> symbolTable;
    public void setSymbolTable(SymbolTable<IAst> symbolTable) { this.symbolTable = symbolTable; }
    public SymbolTable<IAst> getSymbolTable() { return symbolTable; }
}


