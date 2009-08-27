package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 51:  expressions ::= $Empty
 *<li>Rule 52:  expressions ::= expressionList
 *<li>Rule 53:  expressionList ::= expression
 *<li>Rule 54:  expressionList ::= expressionList , expression
 *</b>
 */
public class expressionList extends AbstractASTNodeList implements Iexpressions, IexpressionList
{
    public Iexpression getexpressionAt(int i) { return (Iexpression) getElementAt(i); }

    public expressionList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public expressionList(Iexpression _expression, boolean leftRecursive)
    {
        super((ASTNode) _expression, leftRecursive);
        ((ASTNode) _expression).setParent(this);
    }

    public void add(Iexpression _expression)
    {
        super.add((ASTNode) _expression);
        ((ASTNode) _expression).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof expressionList)) return false;
        if (! super.equals(o)) return false;
        expressionList other = (expressionList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            Iexpression element = getexpressionAt(i);
            if (! element.equals(other.getexpressionAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getexpressionAt(i).hashCode());
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
                Iexpression element = getexpressionAt(i);
                element.accept(v);
            }
        }
        v.endVisit(this);
    }
}


