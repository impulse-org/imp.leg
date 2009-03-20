package org.eclipse.imp.leg.preferences;

import java.util.List;
import java.util.ArrayList;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.imp.preferences.*;
import org.eclipse.imp.preferences.fields.*;
import org.osgi.service.prefs.Preferences;


/**
 * The instance level preferences tab.
 */
public class LEGInstanceTab extends InstancePreferencesTab {

	public LEGInstanceTab(IPreferencesService prefService) {
		super(prefService, false);
	}

	/**
	 * Creates specific preference fields with settings appropriate to
	 * the instance preferences level.
	 *
	 * Overrides an unimplemented method in PreferencesTab.
	 *
	 * @return    An array that contains the created preference fields
	 *
	 */
	protected FieldEditor[] createFields(TabbedPreferencesPage page, Composite parent)
	{
		List<FieldEditor> fields = new ArrayList<FieldEditor>();

		BooleanFieldEditor UseDefaultIncludePath = fPrefUtils.makeNewBooleanField(
			page, this, fPrefService,
			"instance", "UseDefaultIncludePath", "Use default include path",
			"",
			parent,
			true, true,
			false, false,
			false, false,
			true);
		fields.add(UseDefaultIncludePath);

		Link UseDefaultIncludePathDetailsLink = fPrefUtils.createDetailsLink(parent, UseDefaultIncludePath, UseDefaultIncludePath.getChangeControl().getParent(), "Details ...");

		UseDefaultIncludePathDetailsLink.setEnabled(true);
		fDetailsLinks.add(UseDefaultIncludePathDetailsLink);


		DirectoryListFieldEditor IncludePathToUse = fPrefUtils.makeNewDirectoryListField(
			page, this, fPrefService,
			"instance", "IncludePathToUse", "Include path to use",
			"A semicolon-separated list of folders to search for include files",
			parent,
			true, true,
			false, "Unspecified",
			true, "",
			true);
		fields.add(IncludePathToUse);

		Link IncludePathToUseDetailsLink = fPrefUtils.createDetailsLink(parent, IncludePathToUse, IncludePathToUse.getTextControl().getParent(), "Details ...");

		IncludePathToUseDetailsLink.setEnabled(true);
		fDetailsLinks.add(IncludePathToUseDetailsLink);


		fPrefUtils.createToggleFieldListener(UseDefaultIncludePath, IncludePathToUse, false);
		boolean isEnabledIncludePathToUse = !UseDefaultIncludePath.getBooleanValue();
		IncludePathToUse.getTextControl().setEditable(isEnabledIncludePathToUse);
		IncludePathToUse.getTextControl().setEnabled(isEnabledIncludePathToUse);
		IncludePathToUse.setEnabled(isEnabledIncludePathToUse, IncludePathToUse.getParent());


		StringFieldEditor SourceFileExtensions = fPrefUtils.makeNewStringField(
			page, this, fPrefService,
			"instance", "SourceFileExtensions", "Source file extensions",
			"A comma-separated list of file name extensions identifying the source files to process",
			parent,
			true, true,
			false, "Unspecified",
			true, "",
			true);
		fields.add(SourceFileExtensions);

		Link SourceFileExtensionsDetailsLink = fPrefUtils.createDetailsLink(parent, SourceFileExtensions, SourceFileExtensions.getTextControl().getParent(), "Details ...");

		SourceFileExtensionsDetailsLink.setEnabled(true);
		fDetailsLinks.add(SourceFileExtensionsDetailsLink);


		BooleanFieldEditor GenerateLog = fPrefUtils.makeNewBooleanField(
			page, this, fPrefService,
			"instance", "GenerateLog", "Generate log",
			"If true, place detailed information from the build process in a log file",
			parent,
			true, true,
			false, false,
			false, false,
			true);
		fields.add(GenerateLog);

		Link GenerateLogDetailsLink = fPrefUtils.createDetailsLink(parent, GenerateLog, GenerateLog.getChangeControl().getParent(), "Details ...");

		GenerateLogDetailsLink.setEnabled(true);
		fDetailsLinks.add(GenerateLogDetailsLink);


		IntegerFieldEditor MaxLogEntries = fPrefUtils.makeNewIntegerField(
			page, this, fPrefService,
			"instance", "MaxLogEntries", "Maximum # of log entries",
			"",
			parent,
			true, true,
			false, String.valueOf(0),
			false, "0",
			true);
		fields.add(MaxLogEntries);

		Link MaxLogEntriesDetailsLink = fPrefUtils.createDetailsLink(parent, MaxLogEntries, MaxLogEntries.getTextControl().getParent(), "Details ...");

		MaxLogEntriesDetailsLink.setEnabled(true);
		fDetailsLinks.add(MaxLogEntriesDetailsLink);


		fPrefUtils.createToggleFieldListener(GenerateLog, MaxLogEntries, true);
		boolean isEnabledMaxLogEntries = GenerateLog.getBooleanValue();
		return fields.toArray(new FieldEditor[fields.size()]);
	}
}
