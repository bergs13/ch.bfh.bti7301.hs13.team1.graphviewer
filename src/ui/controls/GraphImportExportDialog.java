/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controls;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GraphImportExportDialog {
	private String path = "";

	public GraphImportExportDialog(boolean load) {
		JFileChooser fc = new JFileChooser();
		int rueckgabewert;
		if (load) {
			// File open dialog
			// File filter
			FileFilter filter = new FileNameExtensionFilter("XML-File", "xml");
			// Add filer to file chooser
			fc.setFileFilter(filter);
			// Show the open dialog
			rueckgabewert = fc.showOpenDialog(null);
		} else {
			// save dialog
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			rueckgabewert = fc.showSaveDialog(null);
		}
		if (rueckgabewert == JFileChooser.APPROVE_OPTION) { // beim Klicken
			// auf
			// 'Speichern'
			this.path = fc.getSelectedFile().getPath();
		}
	}

	public String getPath() {
		return this.path;
	}
}
