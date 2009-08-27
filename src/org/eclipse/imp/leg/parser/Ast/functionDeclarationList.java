package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 1:  compilationUnit ::= $Empty
 *<li>Rule 2:  compilationUnit ::= compilationUnit functionDeclaration
 *</b>
 */
public class functionDeclarationList extends AbstractASTNodeList implements IcompilationUnit
{
    public functionDeclaration getfunctionDeclarationAt(int i) { return (functionDeclaration) getElementAt(i); }

    public functionDeclarationList(IToken leftIToken, IToken rightIToken, boolean leftRecursive)
    {
        super(leftIToken, rightIToken, leftRecursive);
    }

    public functionDeclarationList(functionDeclaration _functionDeclaration, boolean leftRecursive)
    {
        super((ASTNode) _functionDeclaration, leftRecursive);
        ((ASTNode) _functionDeclaration).setParent(this);
    }

    public void add(functionDeclaration _functionDeclaration)
    {
        super.add((ASTNode) _functionDeclaration);
        ((ASTNode) _functionDeclaration).setParent(this);
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (! (o instanceof functionDeclarationList)) return false;
        if (! super.equals(o)) return false;
        functionDeclarationList other = (functionDeclarationList) o;
        if (size() != other.size()) return false;
        for (int i = 0; i < size(); i++)
        {
            functionDeclaration element = getfunctionDeclarationAt(i);
            if (! element.equals(other.getfunctionDeclarationAt(i))) return false;
        }
        return true;
    }

    public int hashCode()
    {
        int hash = super.hashCode();
        for (int i = 0; i < size(); i++)
            hash = hash * 31 + (getfunctionDeclarationAt(i).hashCode());
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
                functionDeclaration element = getfunctionDeclarationAt(i);
                if (! v.preVisit(element)) continue;
                element.enter(v);
                v.postVisit(element);
            }
        }
        v.endVisit(this);
    }
}


