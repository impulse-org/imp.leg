package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<em>
 *<li>Rule 23:  Type ::= primitiveType
 *</em>
 *<p>
 *<b>
 *<li>Rule 24:  Type ::= void
 *</b>
 */
public class Type extends ASTNodeToken implements IType
{
    public IToken getvoid() { return leftIToken; }

    public Type(IToken token) { super(token); initialize(); }

    public void accept(IAstVisitor v)
    {
        if (! v.preVisit(this)) return;
        enter((Visitor) v);
        v.postVisit(this);
    }

    public void enter(Visitor v)
    {
        v.visit(this);
        v.endVisit(this);
    }
}


