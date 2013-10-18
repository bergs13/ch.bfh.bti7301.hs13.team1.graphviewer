import extlib.IncidenceListGraph;
import extlib.Vertex;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.MainGUI;

import ui.GraphPanel;

public class Program {

	/**
	 * @param args
	 */
	 public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
           final IncidenceListGraph<String, String> gr = new IncidenceListGraph<>(true);
        // Vertices
		Vertex<String> vA = gr.insertVertex("A");
		Vertex<String> vB = gr.insertVertex("B");
		Vertex<String> vC = gr.insertVertex("C");
		// Edges
		gr.insertEdge(vA, vB, "AB");
		gr.insertEdge(vB, vC, "BC");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new MainGUI<String, String>(gr).setVisible(true);
            }
        });
	}
}
