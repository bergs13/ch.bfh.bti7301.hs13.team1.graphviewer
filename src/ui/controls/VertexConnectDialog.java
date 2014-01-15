package ui.controls;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import defs.CustomComboBoxItem;
import defs.FormatHelper;
import defs.VertexFormat;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;

@SuppressWarnings("serial")
public class VertexConnectDialog<V, E> extends JDialog {
	Vertex<V> sourceVertex = null;
	Vertex<V> targetVertex = null;
	double weight = Double.NEGATIVE_INFINITY;
	// saved when closed?
	boolean saved = false;

	public VertexConnectDialog(IncidenceListGraph<V, E> graph,
			boolean showWeight) {
		this(graph, showWeight, false);
	}

	public VertexConnectDialog(IncidenceListGraph<V, E> graph,
			boolean showWeight, boolean showSourceVertex) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		// Layout
		// adjust rows and height
		// rows
		int rows = showSourceVertex ? 4 : 3;
		rows = !showWeight ? rows - 1 : rows;
		// height
		int height = showSourceVertex ? 180 : 135;
		height = !showWeight ? height - 45 : height;
		// Set layout
		this.setLayout(new GridLayout(rows, 2));
		this.setMinimumSize(new Dimension(200, height));
		this.setMaximumSize(new Dimension(200, height));

		// Input fields
		Vertex<V> key = null;
		VertexFormat formatForValue;
		String value;
		Iterator<Vertex<V>> itV;
		if (showSourceVertex) {
			// Source vertex
			this.add(new JLabel("Source vertex:"));

			final JComboBox<CustomComboBoxItem> cBSV = new JComboBox<CustomComboBoxItem>();
			itV = graph.vertices();
			while (itV.hasNext()) {
				key = itV.next();
				formatForValue = FormatHelper
						.getFormat(VertexFormat.class, key);
				value = "";
				if (null != formatForValue && null != formatForValue.getLabel()) {
					value = formatForValue.getLabel();
				}
				cBSV.addItem(new CustomComboBoxItem(key, value));
			}
			// Default selection
			if (cBSV.getItemCount() > 0) {
				cBSV.setSelectedIndex(0);
				sourceVertex = (Vertex<V>) cBSV.getItemAt(0).getKey();
			}
			cBSV.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					sourceVertex = (Vertex<V>) ((CustomComboBoxItem) cBSV
							.getSelectedItem()).getKey();
				};
			});
			this.add(cBSV);
		}

		// Target vertex
		this.add(new JLabel("Target vertex:"));
		final JComboBox<CustomComboBoxItem> cBTV = new JComboBox<CustomComboBoxItem>();
		key = null;
		itV = graph.vertices();
		while (itV.hasNext()) {
			key = itV.next();
			formatForValue = FormatHelper.getFormat(VertexFormat.class, key);
			value = "";
			if (null != formatForValue && null != formatForValue.getLabel()) {
				value = formatForValue.getLabel();
			}
			cBTV.addItem(new CustomComboBoxItem(key, value));
		}
		// Default selection
		if (cBTV.getItemCount() > 0) {
			cBTV.setSelectedIndex(0);
			targetVertex = (Vertex<V>) cBTV.getItemAt(0).getKey();
		}
		cBTV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				targetVertex = (Vertex<V>) ((CustomComboBoxItem) cBTV
						.getSelectedItem()).getKey();
			};
		});
		this.add(cBTV);

		if (showWeight) {
			// Weight of Edge
			this.add(new JLabel("Weight of Edge:"));
			final JTextField weightField = new JTextField();
			weightField.getDocument().addDocumentListener(
					new DocumentListener() {
						@Override
						public void changedUpdate(DocumentEvent e) {
							// text was changed
							weight = Double.parseDouble(weightField.getText());
						}

						@Override
						public void removeUpdate(DocumentEvent e) {
							// text was deleted
							weight = Double.parseDouble(weightField.getText());
						}

						@Override
						public void insertUpdate(DocumentEvent e) {
							// text was inserted
							weight = Double.parseDouble(weightField.getText());
						}
					});
			this.add(weightField);
		}

		// OK/Cancel Buttons
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

	public boolean getSaved() {
		return this.saved;
	}

	public Vertex<V> getSourceVertex() {
		return this.sourceVertex;
	}

	public Vertex<V> getTargetVertex() {
		return this.targetVertex;
	}

	public double getWeight() {
		return this.weight;
	}
}
