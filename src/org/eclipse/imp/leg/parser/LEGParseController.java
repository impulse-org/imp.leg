package org.eclipse.imp.leg.parser;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.leg.Activator;
import org.eclipse.imp.leg.parser.Ast.ASTNode;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.services.ILanguageSyntaxProperties;

/**
 * The LEG implementation of the IParseController IMP interface.
 */
public class LEGParseController extends SimpleLPGParseController implements IParseController {
    public LEGParseController() {
        super(Activator.kLanguageID);
        fLexer= new LEGLexer();
        fParser= new LEGParser();
    }

    public ILanguageSyntaxProperties getSyntaxProperties() {
        return null;
    }

    public Object parse(String contents, IProgressMonitor monitor) {
        super.parse(contents, monitor);

        ((LEGParser) fParser).resolve((ASTNode) fCurrentAst);

        return fCurrentAst;
    }
}
