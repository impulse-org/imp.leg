package org.eclipse.imp.leg.preferences;

import org.eclipse.imp.preferences.PreferencesInitializer;
import org.eclipse.imp.preferences.IPreferencesService;
import org.eclipse.imp.leg.Activator;

/**
 * Initializations of default values for preferences.
 */
public class LEGInitializer extends PreferencesInitializer {
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferencesService service = Activator.getInstance().getPreferencesService();

		service.setBooleanPreference(IPreferencesService.DEFAULT_LEVEL, LEGConstants.P_USEDEFAULTINCLUDEPATH, true);
		service.setStringPreference(IPreferencesService.DEFAULT_LEVEL, LEGConstants.P_INCLUDEPATHTOUSE, ".;..;${pluginResource:org.eclipse.imp.leg/include}");
		service.setStringPreference(IPreferencesService.DEFAULT_LEVEL, LEGConstants.P_SOURCEFILEEXTENSIONS, "leg");
		service.setBooleanPreference(IPreferencesService.DEFAULT_LEVEL, LEGConstants.P_GENERATELOG, true);
		service.setIntPreference(IPreferencesService.DEFAULT_LEVEL, LEGConstants.P_MAXLOGENTRIES, 100);
	}

	/*
	 * Clear (remove) any preferences set on the given level.
	 */
	public void clearPreferencesOnLevel(String level) {
		IPreferencesService service = Activator.getInstance().getPreferencesService();
		service.clearPreferencesAtLevel(level);

	}
}
