package org.eclipse.imp.leg.contentProposer;

import org.eclipse.imp.leg.parser.*;
import org.eclipse.imp.leg.parser.Ast.*;

import lpg.runtime.*;

import java.util.*;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.imp.services.IContentProposer;
import org.eclipse.imp.editor.SourceProposal;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.SimpleLPGParseController;

public class LEGContentProposer implements IContentProposer {
    private Map<String,IAst> getVisibleVariables(LEGParser parser, ASTNode n) {
        Map<String,IAst> map= new HashMap<String,IAst>();
        for(LEGParser.SymbolTable s= parser.getEnclosingSymbolTable(n); s != null; s= s.getParent())
            for(Enumeration<String> e= s.keys(); e.hasMoreElements();) {
                String key= e.nextElement();
                if (!map.containsKey(key))
                    map.put(key, s.get(key));
            }

        return map;
    }

    private String getDeclName(IAst decl) {
        if (decl instanceof declaration0)
            return ((declaration0) decl).getidentifier().toString();
        else if (decl instanceof functionDeclaration0)
            return ((functionDeclaration0) decl).getfunctionHeader().getidentifier().toString();
        return "";
    }

    private String getVariableProposal(IAst decl) {
        String string= "";
        if (decl instanceof declaration0) {
            string= ((declaration0) decl).getprimitiveType().toString() + " " + ((declaration0) decl).getidentifier().toString();
        } else if (decl instanceof functionDeclaration0) {
            functionDeclaration0 funcDecl= (functionDeclaration0) decl;
            functionHeader funcHeader= funcDecl.getfunctionHeader();
            declarationList parameters= funcHeader.getparameters();
            string= funcHeader.getType().toString() + " " + funcHeader.getidentifier().toString() + "(";
            for(int i= 0; i < parameters.size(); i++)
                string+= ((declaration0) parameters.getdeclarationAt(i)).getprimitiveType() + (i < parameters.size() - 1 ? ", " : "");
            string+= ")";
        }
        return string;
    }

    private List<IAst> filterSymbols(Map<String,IAst> in_symbols, String prefix) {
        ArrayList<IAst> symbols= new ArrayList<IAst>();
        for(Iterator<IAst> i= in_symbols.values().iterator(); i.hasNext();) {
            IAst decl= i.next();
            String name= getDeclName(decl);
            if (name.length() >= prefix.length() && prefix.equals(name.substring(0, prefix.length())))
                symbols.add(decl);
        }

        return symbols;
    }

    private IToken getToken(IParseController controller, int offset) {
        PrsStream stream= ((SimpleLPGParseController) controller).getParser().getParseStream();
        int index= stream.getTokenIndexAtCharacter(offset), token_index= (index < 0 ? -(index - 1) : index), previous_index= stream.getPrevious(token_index);
        return stream
                .getIToken(((stream.getKind(previous_index) == LEGParsersym.TK_IDENTIFIER || ((LEGParseController) controller).isKeyword(stream.getKind(previous_index))) && offset == stream
                        .getEndOffset(previous_index) + 1) ? previous_index : token_index);
    }

    private String getPrefix(IToken token, int offset) {
        if (token.getKind() == LEGParsersym.TK_IDENTIFIER)
            if (offset >= token.getStartOffset() && offset <= token.getEndOffset() + 1)
                return token.toString().substring(0, offset - token.getStartOffset());
        return "";
    }

    /**
     * Returns an array of content proposals applicable relative to the AST of the given
     * parse controller at the given position.
     * 
     * (The provided ITextViewer is not used in the default implementation provided here
     * but but is stipulated by the IContentProposer interface for purposes such as accessing
     * the IDocument for which content proposals are sought.)
     * 
     * @param controller	A parse controller from which the AST of the document being edited
     * 						can be obtained
     * @param int			The offset for which content proposals are sought
     * @param viewer		The viewer in which the document represented by the AST in the given
     * 						parse controller is being displayed (may be null for some implementations)
     * @return				An array of completion proposals applicable relative to the AST of the given
     * 						parse controller at the given position
     */
    public ICompletionProposal[] getContentProposals(IParseController controller, int offset, ITextViewer viewer) {
        // START_HERE           
        ArrayList<ICompletionProposal> list= new ArrayList<ICompletionProposal>();
        if (controller.getCurrentAst() != null) {
            IToken token= getToken(controller, offset);
            String prefix= getPrefix(token, offset);

            LEGASTNodeLocator locator= new LEGASTNodeLocator();
            ASTNode node= (ASTNode) locator.findNode(controller.getCurrentAst(), token.getStartOffset(), token.getEndOffset());
            if (node != null
                    && (node.getParent() instanceof Iexpression || node.getParent() instanceof assignmentStmt || node.getParent() instanceof BadAssignment)) {
                Map<String,IAst> symbols= getVisibleVariables((LEGParser) ((SimpleLPGParseController) controller).getParser(), node);
                List<IAst> vars= filterSymbols(symbols, prefix);
                for(int i= 0; i < vars.size(); i++) {
                    IAst decl= vars.get(i);
                    boolean isFunc= decl instanceof functionDeclaration0;
                    boolean hasArgs= isFunc && ((functionDeclaration0) decl).getfunctionHeader().getparameters().size() > 0;
                    final String proposal= getVariableProposal(decl);
                    final String newText= getDeclName(decl) + (isFunc ? "()" : "");

                    if (hasArgs) {
                        int cursorLoc= offset + (newText.length() - prefix.length()) - 1;
                        list.add(new SourceProposal(proposal, newText, prefix, offset, cursorLoc));
                    } else {
                        list.add(new SourceProposal(proposal, newText, prefix, offset));
                    }
                }
            } else
                list.add(new SourceProposal("No completion exists for that prefix", "", offset));
        } else
            list.add(new SourceProposal("No info available due to Syntax error(s)", "", offset));

        return list.toArray(new ICompletionProposal[list.size()]);
    }
}
