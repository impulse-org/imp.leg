package org.eclipse.imp.leg.treeModelBuilder;

import org.eclipse.imp.services.base.TreeModelBuilderBase;

import org.eclipse.imp.leg.parser.Ast.*;

public class LEGTreeModelBuilder extends TreeModelBuilderBase {
    @Override
    public void visitTree(Object root) {
        if (root == null)
            return;
        ASTNode rootNode= (ASTNode) root;
        LEGModelVisitor visitor= new LEGModelVisitor();

        rootNode.accept(visitor);
    }

    private class LEGModelVisitor extends AbstractVisitor {
        @Override
        public void unimplementedVisitor(String s) {
        }

        public boolean visit(block n) {
            pushSubItem(n);
            return true;
        }

        public void endVisit(block n) {
            popSubItem();
        }

        public boolean visit(declarationStmt0 n) {
            createSubItem(n);
            return true;
        }

        public boolean visit(declarationStmt1 n) {
            createSubItem(n);
            return true;
        }

        public boolean visit(assignmentStmt n) {
            createSubItem(n);
            return true;
        }

        // START_HERE
        @Override
        public boolean visit(functionDeclaration n) {
            pushSubItem(n);
            return true;
        }
        @Override
        public void endVisit(functionDeclaration n) {
            popSubItem();
        }
    }
}
