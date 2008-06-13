package org.eclipse.imp.leg.actions;

import org.eclipse.imp.editor.UniversalEditor;
import org.eclipse.imp.runtime.RuntimePlugin;
import org.eclipse.imp.services.ILanguageActionsContributor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class EditorActions implements ILanguageActionsContributor {
	

	/**
	 * This example adds an action to the editor popup menu, in a submenu
	 */
	public void contributeToEditorMenu(final UniversalEditor editor,
			IMenuManager menuManager) {
		IMenuManager languageMenu = new MenuManager("Generate");
		menuManager.add(languageMenu);
		languageMenu.add(new Action("Factorial Function") {
			@Override
			public void run() {
				IEditorInput input = editor.getEditorInput();
				IDocumentProvider provider = editor.getDocumentProvider();
				IDocument doc = provider.getDocument(input);
				Point selection = editor.getSelection();
				
				try {
					doc.replace(selection.y, 0, "int fac(int n) {\n  if (n == 0) {\n    return 1;\n  }\n  else {\n    return fac(n - 1);\n  }\n}");
				} catch (BadLocationException e) {
					RuntimePlugin.getInstance().logException("could not insert text", e);
				}
			}
		});
	}

	public void contributeToMenuBar(UniversalEditor editor, IMenuManager menu) {
		// TODO Auto-generated method stub

	}

	public void contributeToStatusLine(final UniversalEditor editor,
			IStatusLineManager statusLineManager) {
		ControlContribution control = new ControlContribution("leg-example") {
			@Override
			protected Control createControl(Composite parent) {
				Label advertisement = new Label(parent, SWT.NONE);
				advertisement.setText("LEG is COOL!");
				return advertisement;
			}
		};
		
		statusLineManager.add(control);
	}

	public void contributeToToolBar(UniversalEditor editor,
			IToolBarManager toolbarManager) {
		// TODO Auto-generated method stub

	}

}
