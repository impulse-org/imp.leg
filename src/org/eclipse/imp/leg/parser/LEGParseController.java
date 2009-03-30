package org.eclipse.imp.leg.parser;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.leg.Activator;
import org.eclipse.imp.leg.parser.Ast.ASTNode;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.parser.IMessageHandler;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.MessageHandlerAdapter;
import org.eclipse.imp.parser.SimpleLPGParseController;
import org.eclipse.imp.services.ILanguageSyntaxProperties;

/**
 * The LEG implementation of the IParseController IMP interface.
 */
public class LEGParseController extends SimpleLPGParseController implements
        IParseController {
    public LEGParseController() {
        super(Activator.kLanguageID);
    }

    public ILanguageSyntaxProperties getSyntaxProperties() {
        return null;
    }

    /**
     * setFilePath() should be called before calling this method.
     */
    public Object parse(String contents, IProgressMonitor monitor) {
        PMMonitor my_monitor= new PMMonitor(monitor);
        char[] contentsArray= contents.toCharArray();

        if (fLexer == null) {
            fLexer= new LEGLexer();
        }
        fLexer.reset(contentsArray, fFilePath.toPortableString());

        if (fParser == null) {
            fParser= new LEGParser(fLexer.getILexStream());
        }
        fParser.reset(fLexer.getILexStream());
        fParser.getIPrsStream().setMessageHandler(new MessageHandlerAdapter(handler));

        fLexer.lexer(my_monitor, fParser.getIPrsStream()); // Lex the stream to produce the token stream
        if (my_monitor.isCancelled())
            return fCurrentAst; // TODO fCurrentAst might (probably will) be inconsistent wrt the lex stream now

        fCurrentAst= fParser.parser(my_monitor, 0);

        // SMS 18 Dec 2007:  functionDeclarationList is a LEG-specific type
        // not suitable for other languages; try replacing with ASTNode
        //		if (fCurrentAst instanceof functionDeclarationList) {
        //			parser.resolve((ASTNode) fCurrentAst);
        //		}
        if (fCurrentAst instanceof ASTNode) {
            ((LEGParser) fParser).resolve((ASTNode) fCurrentAst);
        }

        cacheKeywordsOnce();

        Object result= fCurrentAst;
        return result;
    }
}
