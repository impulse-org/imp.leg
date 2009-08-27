package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 10:  stmtList ::= $Empty
 *<li>Rule 11:  stmtList ::= stmtList statement
 *</b>
 */
public class statementList extends AbstractASTNodeList implements IstmtList
{
    public Istatement getstatementAt(int i) { return (Istatement) getElementAt(i); }

    public statementList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public statementList(Istatement _statement, boolean leftRecursive)
    {
        super((ASTNode) _statement, leftRecursive);
        ((ASTNode) _statement).setParent(this);
    }

    public void add(Istatement _statement)
    {
        super.add((ASTNode) _statement);
        ((ASTNode) _statement).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof statementList)) return false;
        if (! super.equals(o)) return false;
        statementList other = (statementList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            Istatement element = getstatementAt(i);
            if (! element.equals(other.getstatementAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getstatementAt(i).hashCode());
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
            for (int i = 0; i < size(); i++)
            {
                Istatement element = getstatementAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


