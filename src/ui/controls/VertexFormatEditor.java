package ui.controls;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import defs.VertexFormat;
import logic.extlib.Vertex;

@SuppressWarnings("serial")
public class VertexFormatEditor<V> extends JDialog {
	ArrayList<Vertex<V>> sourceVertices = null;
	Vertex<V> sourceVertex = null;
	Vertex<V> vertex = null;
	VertexFormat format = null;

	public VertexFormatEditor(ArrayList<Vertex<V>> sourceVertices,
			Vertex<V> vertex, final VertexFormat format) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.vertex = vertex;
		this.format = format;

		// Layout
		this.setLayout(new GridLayout(6, 2));

		// Input fields
		// Source vertex
		this.add(new JLabel("Source vertex:"));
		JComboBox<Vertex<V>> cBV = new JComboBox<Vertex<V>>();
		for (Vertex<V> sourceVertex : sourceVertices) {
			cBV.addItem(sourceVertex);
		}
		this.add(cBV);

		// Unvisited color
		this.add(new JLabel("Unvisited Color:"));
		final JButton bUnvisited = new JButton("Change");
		bUnvisited.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color newC = JColorChooser.showDialog(null, "Choose color",
						format.getUnvisitedColor());
				format.setUnvisitedColor(newC);
				bUnvisited.setBackground(newC);
			};
		});
		this.add(bUnvisited);

		// Visited color
		this.add(new JLabel("Visited Color:"));
		final JButton bVisited = new JButton("Change");
		bVisited.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color newC = JColorChooser.showDialog(null, "Choose color",
						format.getVisitedColor());
				format.setVisitedColor(newC);
				bVisited.setBackground(newC);
			};
		});
		this.add(bVisited);

		// Active color
		this.add(new JLabel("Active Color:"));
		final JButton bActive = new JButton("Change");
		bActive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color newC = JColorChooser.showDialog(null, "Choose color",
						format.getActiveColor());
				format.setActiveColor(newC);
				bActive.setBackground(newC);
			};
		});
		this.add(bActive);

		// Label visible
		this.add(new JLabel("Label visible:"));
		this.add(new JComboBox<Boolean>(new Boolean[] { true, false }));

		// Label
		this.add(new JLabel("Label:"));
		this.add(new JTextField(format.getLabel()));
	}

	public Vertex<V> getSourceVertex() {
		return this.sourceVertex;
	}

	public Vertex<V> getVertex() {
		return this.vertex;
	}

	public VertexFormat getFormat() {
		return null == this.format ? new VertexFormat() : this.format;
	}
}
