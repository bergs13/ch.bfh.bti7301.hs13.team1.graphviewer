package ui.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	VertexFormat format = null;
	Vertex<V> sourceVertex = null;
	//saved when closed?
	boolean saved = false;

	public VertexFormatEditor(final VertexFormat format,ArrayList<Vertex<V>> sourceVertices) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.format = format;

		// Layout
		this.setLayout(new GridLayout(7, 2));
		this.setMinimumSize(new Dimension(210,210));
		this.setMaximumSize(new Dimension(210,210));
		
		// Input fields
		// Source vertex
		this.add(new JLabel("Source vertex:"));
		final JComboBox<Vertex<V>> cBV = new JComboBox<Vertex<V>>();
		for (Vertex<V> sourceVertex : sourceVertices) {
			cBV.addItem(sourceVertex);
		}
		//Default selection
		sourceVertex = (Vertex<V>)cBV.getSelectedItem();
		cBV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sourceVertex = (Vertex<V>)cBV.getSelectedItem();
			};
		});
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
		final JComboBox<Boolean> cBLVisible = new JComboBox<Boolean>(new Boolean[] { true, false }); 
		cBLVisible.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				format.setTextVisible((boolean)cBLVisible.getSelectedItem());
			};
		});
		this.add(cBLVisible);

		// Label
		this.add(new JLabel("Label:"));
		this.add(new JTextField(format.getLabel()));
		
		//OK/Cancel Buttons
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				saved = true;
				dispose();
			};
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			};
		});
		this.add(okButton);
		this.add(cancelButton);
	}
	public boolean getSaved()
	{
		return this.saved;
	}
	public Vertex<V> getSourceVertex() {
		return this.sourceVertex;
	}
	public VertexFormat getFormat() {
		return null == this.format ? new VertexFormat() : this.format;
	}
}
