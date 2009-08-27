package org.eclipse.imp.leg.parser.Ast;

import lpg.runtime.*;

import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SymbolTable;
import java.util.Stack;

/**
 *<em>
 *<li>Rule 12:  statement ::= declarationStmt
 *<li>Rule 13:  statement ::= assignmentStmt
 *<li>Rule 14:  statement ::= ifStmt
 *<li>Rule 15:  statement ::= returnStmt
 *<li>Rule 16:  statement ::= whileStmt
 *<li>Rule 17:  statement ::= block
 *<li>Rule 18:  statement ::= functionStmt
 *</em>
 *<p>
 *<b>
 *<li>Rule 19:  statement ::= ;
 *</b>
 */
public class statement extends ASTNodeToken implements Istatement
{
    public IToken getSEMICOLON() { return leftIToken; }

    public statement(IToken token) { super(token); initialize(); }

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


