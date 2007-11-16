package org.eclipse.imp.leg.parser;

import lpg.runtime.IAst;
import lpg.runtime.IToken;

import org.eclipse.imp.language.ILanguageService;
import org.eclipse.imp.leg.parser.Ast.ASTNode;
import org.eclipse.imp.leg.parser.Ast.ASTNodeToken;
import org.eclipse.imp.leg.parser.Ast.AbstractASTNodeList;
import org.eclipse.imp.xform.pattern.parser.ASTAdapterBase;

public class LEGASTAdapter extends ASTAdapterBase implements ILanguageService,
		LEGParsersym {
	
	public boolean isList(Object astNode) {
		return astNode instanceof AbstractASTNodeList;
	}
	
	@Override
	public Object[] getChildren(Object astNode) {
		return ((IAst) astNode).getChildren().toArray();
	}

	@Override
	public int getOffset(Object astNode) {
		return ((IAst) astNode).getLeftIToken().getStartOffset();
	}

	@Override
	public int getLength(Object astNode) {
		IAst ast = (IAst) astNode;
		IToken left = ast.getLeftIToken();
		IToken right = ast.getRightIToken();

		// special case for epsilon trees
		if (left.getTokenIndex() > right.getTokenIndex()) {
			return 0;
		} else {
			int start = left.getStartOffset();
			int end = right.getEndOffset();
			return end - start + 1;
		}
	}

	@Override
	public String getTypeOf(Object astNode) {
		return astNode.getClass().getName();
	}

	@Override
	public boolean isMetaVariable(Object astNode) {
		IAst ast = (IAst) astNode;
		if (ast.getChildren().size() == 0) {
			switch (ast.getLeftIToken().getKind()) {
			case TK_METAVARIABLE_expression:
			case TK_METAVARIABLE_expressions:
			case TK_METAVARIABLE_functionDeclaration:
			case TK_METAVARIABLE_identifier:
			case TK_METAVARIABLE_parameters:
			case TK_METAVARIABLE_statement:
			case TK_METAVARIABLE_statements:
			case TK_METAVARIABLE_term:
			case TK_METAVARIABLE_Type:
			case TK_METAVARIABLE_declaration:
			case TK_META_VARIABLE_parameterList:
				return true;
			default:
				return false;
			}
		}

		return false;
	}

}
