package demo;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.controls.GraphPanel;
import logic.extlib.GraphExamples;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;

public class GraphPanelDemo extends GraphExamples<String, String> {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Graph zum Testen
		IncidenceListGraph<String, String> g = new IncidenceListGraph<String, String>(
				true);
		// Vertices
		Vertex<String> vA = g.insertVertex("A");
		Vertex<String> vB = g.insertVertex("B");
		Vertex<String> vC = g.insertVertex("C");
		// Edges
		g.insertEdge(vA, vB, "AB");
		g.insertEdge(vB, vC, "BC");

		// GUI aus Graph
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setTitle("A Graph Component Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 1));
		JPanel graphPanel = new GraphPanel<String, String>(g);
		frame.add(graphPanel);
		frame.setVisible(true);
	}
}
