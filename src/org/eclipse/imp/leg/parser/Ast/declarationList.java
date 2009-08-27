package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 5:  parameters ::= $Empty
 *<li>Rule 6:  parameters ::= parameterList
 *<li>Rule 7:  parameterList ::= declaration
 *<li>Rule 8:  parameterList ::= parameterList , declaration
 *</b>
 */
public class declarationList extends AbstractASTNodeList implements Iparameters, IparameterList
{
    public declaration getdeclarationAt(int i) { return (declaration) getElementAt(i); }

    public declarationList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public declarationList(declaration _declaration, boolean leftRecursive)
    {
        super((ASTNode) _declaration, leftRecursive);
        ((ASTNode) _declaration).setParent(this);
    }

    public void add(declaration _declaration)
    {
        super.add((ASTNode) _declaration);
        ((ASTNode) _declaration).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof declarationList)) return false;
        if (! super.equals(o)) return false;
        declarationList other = (declarationList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            declaration element = getdeclarationAt(i);
            if (! element.equals(other.getdeclarationAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getdeclarationAt(i).hashCode());
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
                declaration element = getdeclarationAt(i);
                if (! v.preVisit(element)) continue;
                element.enter(v);
                v.postVisit(element);
            }
        }
        v.endVisit(this);
    }
}


