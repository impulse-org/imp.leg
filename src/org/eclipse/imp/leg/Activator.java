package org.eclipse.imp.leg;

import org.eclipse.imp.runtime.PluginBase;
import org.osgi.framework.BundleContext;

/*
 * SMS 27 Mar 2007:  Deleted creation of preferences cache (now obsolete)
 * SMS 28 Mar 2007:
 * 	- Plugin class name now totally parameterized
 *  - Plugin package made a separate parameter
 * SMS 19 Jun 2007:
 * 	- Added kLanguageName (may be used by later updates to the template)
 * 	- Added field and method related to new preferences service; deleted
 *	  code for initializing preference store from start(..) method
 */

public class Activator extends PluginBase {

    public static final String kPluginID= "org.eclipse.imp.leg";

    public static final String kLanguageID= "LEG";

    /**
     * The unique instance of this plugin class
     */
    protected static Activator sPlugin;

    public static Activator getInstance() {
        // SMS 11 Jul 2007
        // Added conditional call to constructor in case the plugin
        // class has not been auto-started
        if (sPlugin == null)
            new Activator();
        return sPlugin;
    }

    public Activator() {
        super();
        sPlugin= this;
    }

    public void start(BundleContext context) throws Exception {
        super.start(context);

    }

    public String getID() {
        return kPluginID;
    }

    @Override
    public String getLanguageID() {
        return kLanguageID;
    }

    // Definitions for image management

    public static final org.eclipse.core.runtime.IPath ICONS_PATH= new org.eclipse.core.runtime.Path(
            "icons/"); //$NON-NLS-1$("icons/"); //$NON-NLS-1$

    protected void initializeImageRegistry(
            org.eclipse.jface.resource.ImageRegistry reg) {
        org.eclipse.core.runtime.IPath path= ICONS_PATH
                .append("leg_default_image.gif");//$NON-NLS-1$
        org.eclipse.jface.resource.ImageDescriptor imageDescriptor= createImageDescriptor(
                getInstance().getBundle(), path);
        reg.put(ILEGResources.LEG_DEFAULT_IMAGE, imageDescriptor);

        path= ICONS_PATH.append("leg_default_outline_item.gif");//$NON-NLS-1$
        imageDescriptor= createImageDescriptor(getInstance().getBundle(), path);
        reg.put(ILEGResources.LEG_DEFAULT_OUTLINE_ITEM, imageDescriptor);

        path= ICONS_PATH.append("leg_file.gif");//$NON-NLS-1$
        imageDescriptor= createImageDescriptor(getInstance().getBundle(), path);
        reg.put(ILEGResources.LEG_FILE, imageDescriptor);

        path= ICONS_PATH.append("leg_file_warning.gif");//$NON-NLS-1$
        imageDescriptor= createImageDescriptor(getInstance().getBundle(), path);
        reg.put(ILEGResources.LEG_FILE_WARNING, imageDescriptor);

        path= ICONS_PATH.append("leg_file_error.gif");//$NON-NLS-1$
        imageDescriptor= createImageDescriptor(getInstance().getBundle(), path);
        reg.put(ILEGResources.LEG_FILE_ERROR, imageDescriptor);
    }

    public static org.eclipse.jface.resource.ImageDescriptor createImageDescriptor(
            org.osgi.framework.Bundle bundle,
            org.eclipse.core.runtime.IPath path) {
        java.net.URL url= org.eclipse.core.runtime.FileLocator.find(bundle,
                path, null);
        if (url != null) {
            return org.eclipse.jface.resource.ImageDescriptor
                    .createFromURL(url);
        }
        return null;
    }

    // Definitions for image management end

}
