import defs.EdgeFormat;
import defs.FormatHelper;
import defs.VertexFormat;
import logic.extlib.Edge;
import logic.extlib.IncidenceListGraph;
import ui.MainGUI;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		final IncidenceListGraph<String, String> gr = new IncidenceListGraph<String, String>(
				true);

		// Vertices
		// Vertex 1
		Vertex<String> vA = gr.insertVertex("A");
		// Format Vertex 1
		VertexFormat vF1 = new VertexFormat();
		vF1.setLabel("A");
		vA.set(FormatHelper.FORMAT, vF1);
		// Vertex 2
		Vertex<String> vB = gr.insertVertex("B");
		// Vertex 3
		Vertex<String> vC = gr.insertVertex("C");

		// Edges
		// Edge 1
		Edge<String> eAB = gr.insertEdge(vA, vB, "AB");
		// Format Edge 1
		EdgeFormat eF1 = new EdgeFormat();
		eF1.setIsDirected(true);
		eF1.setLabel("AB");
		eF1.setTextVisible(true);
		eAB.set(FormatHelper.FORMAT, eF1);
		// Edge 2
		Edge<String> eBC = gr.insertEdge(vB, vC, "BC");
		// Format Edge 2
		EdgeFormat eF2 = new EdgeFormat();
		eF2.setTextVisible(true);
		eBC.set(FormatHelper.FORMAT, eF2);

		// // Edges
		// gr.insertEdge(vA, vB, "AB");
		// gr.insertEdge(vB, vC, "BC");
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				new MainGUI<String, String>(gr).setVisible(true);
			}
		});
	}
}
