package org.eclipse.imp.leg.preferences;

import org.eclipse.swt.widgets.TabFolder;import org.eclipse.imp.preferences.IPreferencesService;import org.eclipse.imp.preferences.PreferencesInitializer;import org.eclipse.imp.preferences.PreferencesTab;import org.eclipse.imp.preferences.TabbedPreferencesPage;import org.eclipse.imp.leg.Activator;

/**
 * A preference page class.
 */


public class LEGPreferencePage extends TabbedPreferencesPage {
	public LEGPreferencePage() {
		super();
		prefService = Activator.getInstance().getPreferencesService();
	}

	protected PreferencesTab[] createTabs(IPreferencesService prefService,
		TabbedPreferencesPage page, TabFolder tabFolder) {
		PreferencesTab[] tabs = new PreferencesTab[1];

		LEGInstanceTab instanceTab = new LEGInstanceTab(prefService);
		instanceTab.createTabContents(page, tabFolder);
		tabs[0] = instanceTab;

		return tabs;
	}

	public PreferencesInitializer getPreferenceInitializer() {
		return new LEGInitializer();
	}
}
