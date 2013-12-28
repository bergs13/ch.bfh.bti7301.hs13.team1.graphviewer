/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controls;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import logic.GraphDataProcessor;
import logic.extlib.IncidenceListGraph;


public class GraphImportExportDialog {

    public static void chooseFile() {
        //File open dialog
        
        // Create a file chooser
        final JFileChooser fc = new JFileChooser();
        // File filter
        FileFilter filter = new FileNameExtensionFilter("XML-File", "xml");
        // Add filer to file chooser
        fc.setFileFilter(filter);
        // Show the open dialog
        int rueckgabewert = fc.showOpenDialog(null);

        //Abfrage, ob auf Öffnen geklickt wurde
        if (rueckgabewert == JFileChooser.APPROVE_OPTION) {
            //System.out.println(fc.getSelectedFile().getName()); //Ausgabe des Dateinamens
            String file;
            file = fc.getSelectedFile().getPath();
            
            GraphDataProcessor g = new GraphDataProcessor();
            g.importGraph(file);
        }

    }

    public static void saveFile() {

        //save dialog
        JFileChooser saveFile = new JFileChooser();
        saveFile.setDialogType(JFileChooser.SAVE_DIALOG);

        int rueckgabewert = saveFile.showSaveDialog(null);

        if (rueckgabewert == JFileChooser.APPROVE_OPTION) {   //beim Klicken auf 'Speichern'         
            
            //Wie aktueller Graph ermitteln??:
            IncidenceListGraph<String, String> graph = new IncidenceListGraph(true);
                       
            
            String path = saveFile.getSelectedFile().getPath();

            GraphDataProcessor g = new GraphDataProcessor();
            g.exportGraph(graph, path);

        }
    }
}
