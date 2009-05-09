package org.eclipse.imp.leg.debug;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.imp.leg.Activator;
import org.eclipse.imp.services.IToggleBreakpointsHandler;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.jdt.internal.debug.core.breakpoints.JavaLineBreakpoint;

public class JavaBreakpointHandler implements IToggleBreakpointsHandler {
    private static final String JDT_DEBUG_PLUGIN_ID= "org.eclipse.jdt.debug";

    public void setLineBreakpoint(IFile file, int lineNumber, IMarker marker) {
        String srcFileName= file.getName();
        String typeName= srcFileName.substring(0, srcFileName.lastIndexOf('.'));
        Map<String,String> bkptAttributes= new HashMap<String, String>();
        bkptAttributes.put("org.eclipse.jdt.debug.core.sourceName", srcFileName);
        bkptAttributes.put("org.eclipse.jdt.debug.core.typeName", typeName);

        try {
            IBreakpoint bkpt= JDIDebugModel.createStratumBreakpoint(file , "leg", srcFileName, file.getFullPath().toString(), null, lineNumber, -1, -1, 0, true, bkptAttributes);
            System.out.println("bkpt creation succeeded");
        } catch (CoreException e) {
            Activator.getInstance().logException("Unable to set stratum breakpoint on file " + srcFileName, e);
        }
    }

    public void clearLineBreakpoint(IFile file, int lineNumber, IMarker marker) {
        String srcFileName= file.getName();
        String typeName= srcFileName.substring(0, srcFileName.lastIndexOf('.'));
        try {
            IJavaLineBreakpoint lineBkpt= stratumBreakpointExists(file, typeName, lineNumber);

            if (lineBkpt != null) {
                lineBkpt.delete();
            }
        } catch (CoreException e) {
            Activator.getInstance().logException("Unable to clear line breakpoint on file " + srcFileName, e);
        }
    }

    public void disableLineBreakpoint(IFile file, int lineNumber, IMarker marker) {
        String srcFileName= file.getName();
        String typeName= srcFileName.substring(0, srcFileName.lastIndexOf('.'));
        try {
            IJavaLineBreakpoint lineBkpt= stratumBreakpointExists(file, typeName, lineNumber);

            if (lineBkpt != null) {
                lineBkpt.setEnabled(false);
            }
        } catch (CoreException e) {
            Activator.getInstance().logException("Unable to disable line breakpoint on file " + srcFileName, e);
        }
    }

    public void enableLineBreakpoint(IFile file, int lineNumber, IMarker marker) {
        String srcFileName= file.getName();
        String typeName= srcFileName.substring(0, srcFileName.lastIndexOf('.'));
        try {
            IJavaLineBreakpoint lineBkpt= stratumBreakpointExists(file, typeName, lineNumber);

            if (lineBkpt != null) {
                lineBkpt.setEnabled(true);
            }
        } catch (CoreException e) {
            Activator.getInstance().logException("Unable to enable line breakpoint on file " + srcFileName, e);
        }
    }
    /**
     * Returns a Java line breakpoint that is already registered with the breakpoint
     * manager for a type with the given name at the given line number.
     * 
     * @param typeName fully qualified type name
     * @param lineNumber line number
     * @return a Java line breakpoint that is already registered with the breakpoint
     *  manager for a type with the given name at the given line number or <code>null</code>
     * if no such breakpoint is registered
     * @exception CoreException if unable to retrieve the associated marker
     *  attributes (line number).
     */
    public static IJavaLineBreakpoint stratumBreakpointExists(IResource resource, String typeName, int lineNumber) throws CoreException {
        String modelId= JDT_DEBUG_PLUGIN_ID;
        String markerType= "org.eclipse.jdt.debug.javaStratumLineBreakpointMarker";
        IBreakpointManager manager= DebugPlugin.getDefault().getBreakpointManager();
        IBreakpoint[] breakpoints= manager.getBreakpoints(modelId);
        for (int i = 0; i < breakpoints.length; i++) {
            if (!(breakpoints[i] instanceof IJavaLineBreakpoint)) {
                continue;
            }
            IJavaLineBreakpoint breakpoint = (IJavaLineBreakpoint) breakpoints[i];
            IMarker marker = breakpoint.getMarker();
            if (marker != null && marker.exists() && marker.getType().equals(markerType)) {
                String breakpointTypeName = breakpoint.getTypeName();
                if ((breakpointTypeName.equals(typeName) || breakpointTypeName.startsWith(typeName + '$')) &&
                    breakpoint.getLineNumber() == lineNumber &&
                    resource.equals(marker.getResource())) {
                        return breakpoint;
                }
            }
        }
        return null;
    }
}
