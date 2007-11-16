package org.eclipse.imp.leg.parser;

import lpg.runtime.IMessageHandler;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.leg.parser.Ast.ASTNode;
import org.eclipse.imp.leg.parser.Ast.functionDeclarationList;
import org.eclipse.imp.model.ISourceProject;
import org.eclipse.imp.parser.IASTNodeLocator;
import org.eclipse.imp.parser.ILexer;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.IParser;
import org.eclipse.imp.parser.SimpleLPGParseController;

/**
 * @author Stan Sutton (suttons@us.ibm.com) (for the following modifications)
 * @since May 1, 2007 Addition of marker types
 * @since May 10, 2007 Conversion IProject -> ISourceProject
 * @since May 31, 2007 Adapted to extend SimpleLPGParseController
 */
public class LEGParseController extends SimpleLPGParseController implements
		IParseController {
	private LEGParser parser;

	private LEGLexer lexer;

	private char keywords[][];

	private boolean isKeyword[];

	/**
	 * @param filePath
	 *            Absolute path of file
	 * @param project
	 *            Project that contains the file
	 * @param handler
	 *            A message handler to receive error messages (or any others)
	 *            from the parser
	 */
	public void initialize(IPath filePath, ISourceProject project,
			IMessageHandler handler) {
		super.initialize(filePath, project, handler);

		IPath pathToUse;
		IPath projLoc = project.getRawProject().getLocation();

		if (!filePath.isAbsolute()) {
			pathToUse = projLoc.append(filePath);
		} else {
			pathToUse = filePath;
		}

		createLexerAndParser(pathToUse);

		parser.getParseStream().resetTokenStream();
		parser.getParseStream().setMessageHandler(handler);
	}

	public IParser getParser() {
		return parser;
	}

	public ILexer getLexer() {
		return lexer;
	}
	

	public IASTNodeLocator getNodeLocator() {
		return new LEGASTNodeLocator();
	} // return new AstLocator(); }

	public LEGParseController() {
	}

	private void createLexerAndParser(IPath filePath) {
		lexer = new LEGLexer();
		parser = new LEGParser(lexer);
	}

	/**
	 * setFilePath() should be called before calling this method.
	 */
	public Object parse(String contents, boolean scanOnly,
			IProgressMonitor monitor) {
		PMMonitor my_monitor = new PMMonitor(monitor);
		char[] contentsArray = contents.toCharArray();

		//createLexerAndParser(fFilePath); // todo remove this after Phil has fixed LPG
		lexer = new LEGLexer();
		parser.reset(lexer.getLexStream());
		
		lexer.initialize(contentsArray, fFilePath.toPortableString());
		parser.getParseStream().resetTokenStream();
		parser.getParseStream().setMessageHandler(handler);

		lexer.lexer(my_monitor, parser.getParseStream()); // Lex the stream to
															// produce the token
															// stream
		if (my_monitor.isCancelled())
			return fCurrentAst; // TODO fCurrentAst might (probably will) be
								// inconsistent wrt the lex stream now

		fCurrentAst = (ASTNode) parser.parser(my_monitor, 0);

//		if (fCurrentAst instanceof functionDeclarationList) {
//			parser.resolve((ASTNode) fCurrentAst);
//		}

		cacheKeywordsOnce();

		Object result = fCurrentAst;
		return result;
	}

}
