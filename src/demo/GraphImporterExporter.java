/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Christian
 */
public class GraphImporterExporter {

    public static void chooseFile(String path) {

        if (path == null) {
            path = "c:/";
        }

        // Create a file chooser
        final JFileChooser fc = new JFileChooser(path);
        // File filter
        FileFilter filter = new FileNameExtensionFilter("XML-File", "xml");
        // Add filer to file chooser
        fc.setFileFilter(filter);
        // Show the open dialog
        int rückgabewert = fc.showOpenDialog(null);

        //Abfrage, ob auf Öffnen geklickt wurde
        if (rückgabewert == JFileChooser.APPROVE_OPTION) {
            //System.out.println(fc.getSelectedFile().getName()); //Ausgabe des Dateinamens
            //reader()  todo
        }

    }

    public static void saveFile(String path) {

        if (path == null) {
            path = "c:/";
        }
        JFileChooser saveFile = new JFileChooser(path);
        saveFile.setDialogType(JFileChooser.SAVE_DIALOG);

        int rückgabewert = saveFile.showSaveDialog(null);

        if (rückgabewert == JFileChooser.APPROVE_OPTION) {
            //System.out.println(); //Save file
            //writer()  todo
        }
    }
}
