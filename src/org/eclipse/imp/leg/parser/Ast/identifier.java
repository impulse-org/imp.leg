package org.eclipse.imp.leg.parser.Ast;

import org.eclipse.imp.leg.parser.*;
import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<b>
 *<li>Rule 55:  identifier ::= IDENTIFIER
 *</b>
 */
public class identifier extends ASTNodeToken implements Iidentifier
{
    private LEGParser environment;
    public LEGParser getEnvironment() { return environment; }

    public IToken getIDENTIFIER() { return leftIToken; }

    public identifier(LEGParser environment, IToken token)    {
        super(token);
        this.environment = environment;
        initialize();
    }

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

    IAst decl;
    public void setDeclaration(IAst decl) { this.decl = decl; }
    public IAst getDeclaration() { return decl; }
}


