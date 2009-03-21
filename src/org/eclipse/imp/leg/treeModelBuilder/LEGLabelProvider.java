package org.eclipse.imp.leg.treeModelBuilder;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.imp.editor.ModelTreeNode;
import org.eclipse.imp.services.ILabelProvider;
import org.eclipse.imp.leg.Activator;
import org.eclipse.imp.leg.ILEGResources;
import org.eclipse.imp.utils.MarkerUtils;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import org.eclipse.imp.leg.parser.Ast.*;

public class LEGLabelProvider implements ILabelProvider {
    private Set<ILabelProviderListener> fListeners= new HashSet<ILabelProviderListener>();

    private static ImageRegistry sImageRegistry= Activator.getInstance().getImageRegistry();

    private static Image DEFAULT_IMAGE= sImageRegistry.get(ILEGResources.LEG_DEFAULT_IMAGE);
    private static Image FILE_IMAGE= sImageRegistry.get(ILEGResources.LEG_FILE);
    private static Image FILE_WITH_WARNING_IMAGE= sImageRegistry.get(ILEGResources.LEG_FILE_WARNING);
    private static Image FILE_WITH_ERROR_IMAGE= sImageRegistry.get(ILEGResources.LEG_FILE_ERROR);
    private static Image FUNC_DECL_IMAGE= sImageRegistry.get(ILEGResources.FUNC_DECL);
    private static Image MAIN_DECL_IMAGE= sImageRegistry.get(ILEGResources.MAIN_DECL);

    public Image getImage(Object element) {
        if (element instanceof IFile) {
            // TODO:  rewrite to provide more appropriate images
            IFile file= (IFile) element;
            int sev= MarkerUtils.getMaxProblemMarkerSeverity(file,
                    IResource.DEPTH_ONE);

            switch (sev) {
            case IMarker.SEVERITY_ERROR:
                return FILE_WITH_ERROR_IMAGE;
            case IMarker.SEVERITY_WARNING:
                return FILE_WITH_WARNING_IMAGE;
            default:
                return FILE_IMAGE;
            }
        }
        ASTNode n= (element instanceof ModelTreeNode) ? (ASTNode) ((ModelTreeNode) element)
                .getASTNode()
                : (ASTNode) element;
        return getImageFor(n);
    }

    public static Image getImageFor(ASTNode n) {
        if (n instanceof functionDeclaration) {
            functionDeclaration fd= (functionDeclaration) n;
            if (fd.getfunctionHeader().getidentifier().toString().equals("main"))
                return MAIN_DECL_IMAGE;
            else
                return FUNC_DECL_IMAGE;
        }
        return DEFAULT_IMAGE;
    }

    public String getText(Object element) {
        ASTNode n= (element instanceof ModelTreeNode) ? (ASTNode) ((ModelTreeNode) element)
                .getASTNode()
                : (ASTNode) element;

        return getLabelFor(n);
    }

    public static String getLabelFor(ASTNode n) {
        // START_HERE
        if (n instanceof IcompilationUnit)
            return "Compilation unit";
        if (n instanceof block)
            return "Block";
        if (n instanceof assignmentStmt) {
            assignmentStmt stmt= (assignmentStmt) n;
            return stmt.getidentifier().toString() + "="
                    + stmt.getexpression().toString();
        }
        if (n instanceof declarationStmt0) {
            declaration decl= (declaration) ((declarationStmt0) n)
                    .getdeclaration();
            return decl.getprimitiveType() + " "
                    + decl.getidentifier().toString();
        }
        if (n instanceof declarationStmt1) {
            declaration decl= (declaration) ((declarationStmt1) n)
                    .getdeclaration();
            return decl.getprimitiveType() + " "
                    + decl.getidentifier().toString();
        }
        if (n instanceof functionDeclaration) {
            functionHeader hdr= (functionHeader) ((functionDeclaration) n)
                    .getfunctionHeader();
            StringBuilder sb= new StringBuilder();
            sb.append(hdr.getType());
            sb.append(" ");
            sb.append(hdr.getidentifier().toString());
            sb.append("(");
            for(int i=0; i < hdr.getparameters().size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(hdr.getparameters().getdeclarationAt(i).getprimitiveType());
            }
            sb.append(")");
            return sb.toString();
        }
        return "<???>";
    }

    public void addListener(ILabelProviderListener listener) {
        fListeners.add(listener);
    }

    public void dispose() {
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
        fListeners.remove(listener);
    }
}
