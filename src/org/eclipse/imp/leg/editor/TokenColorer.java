package org.eclipse.imp.leg.editor;

import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.services.ITokenColorer;
import org.eclipse.imp.services.base.TokenColorerBase;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import org.eclipse.imp.leg.parser.LEGParseController;
import org.eclipse.imp.leg.parser.LEGParsersym;

import lpg.runtime.IToken;

public class TokenColorer extends TokenColorerBase implements LEGParsersym,
		ITokenColorer {

	TextAttribute commentAttribute, keywordAttribute, stringAttribute,
			numberAttribute, doubleAttribute, identifierAttribute;

	public TextAttribute getColoring(IParseController controller, Object o) {
	        IToken token= (IToken) o;
                if (token.getKind() == TK_EOF_TOKEN)
                    return null;
		switch (token.getKind()) {
		// START_HERE
		case TK_IDENTIFIER:
		    return identifierAttribute;
		case TK_NUMBER:
		    return numberAttribute;
		case TK_DoubleLiteral:
		    return doubleAttribute;
//              case TK_StringLiteral:
//                  return stringAttribute;
		default:
		    if (((LEGParseController) controller).isKeyword(token.getKind()))
			return keywordAttribute;
			//else return null;
		    return super.getColoring(controller, token);
		}
	}

	public TokenColorer() {
		super();
		// TODO:  Define text attributes for the various
		// token types that will have their text colored
		Display display = Display.getDefault();
		commentAttribute = new TextAttribute(display
				.getSystemColor(SWT.COLOR_DARK_RED), null, SWT.ITALIC);
		stringAttribute = new TextAttribute(display
				.getSystemColor(SWT.COLOR_DARK_BLUE), null, SWT.BOLD);
		identifierAttribute = new TextAttribute(display
				.getSystemColor(SWT.COLOR_BLACK), null, SWT.NORMAL);
		doubleAttribute = new TextAttribute(display
				.getSystemColor(SWT.COLOR_DARK_GREEN), null, SWT.BOLD);
		numberAttribute = new TextAttribute(display
				.getSystemColor(SWT.COLOR_DARK_YELLOW), null, SWT.BOLD);
		keywordAttribute = new TextAttribute(display
				.getSystemColor(SWT.COLOR_DARK_MAGENTA), null, SWT.BOLD);
	}

    public IRegion calculateDamageExtent(IRegion seed) {
        return seed;
    }
}
