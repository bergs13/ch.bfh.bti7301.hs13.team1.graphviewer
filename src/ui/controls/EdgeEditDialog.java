package ui.controls;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import defs.CustomComboBoxItem;
import defs.DecorableConstants;
import defs.FormatHelper;
import defs.VertexFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import logic.extlib.Edge;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;

@SuppressWarnings("serial")
public class EdgeEditDialog<V, E> extends JDialog {
	Vertex<V> sourceVertex = null;
	Vertex<V> targetVertex = null;
	double weight = Double.NEGATIVE_INFINITY;
	boolean clearEdge = false;
	// saved when closed?
	boolean saved = false;

	public EdgeEditDialog(final IncidenceListGraph<V, E> graph,
			final boolean showWeight) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		final JTextField weightField = new JTextField();

		// Layout
		// adjust rows and height
		// rows
		int rows = 5;
		rows = !showWeight ? rows - 1 : rows;
		// height
		int height = 225;
		height = !showWeight ? height - 45 : height;
		// Set layout
		this.setLayout(new GridLayout(rows, 2));
		this.setMinimumSize(new Dimension(200, height));
		this.setMaximumSize(new Dimension(200, height));

		// Input fields
		// Source vertex
		this.add(new JLabel("Source vertex:"));
		final JComboBox<CustomComboBoxItem> cBV = new JComboBox<CustomComboBoxItem>();
		Vertex<V> key = null;
		VertexFormat formatForValue;
		String value;
		Iterator<Vertex<V>> itV = graph.vertices();
		while (itV.hasNext()) {
			key = itV.next();
			formatForValue = FormatHelper.getFormat(VertexFormat.class, key);
			value = "";
			if (null != formatForValue && null != formatForValue.getLabel()) {
				value = formatForValue.getLabel();
			}
			cBV.addItem(new CustomComboBoxItem(key, value));
		}
		// Default selection
		if (cBV.getItemCount() > 0) {
			cBV.setSelectedIndex(0);
			sourceVertex = (Vertex<V>) cBV.getItemAt(0).getKey();
		}
		cBV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sourceVertex = (Vertex<V>) ((CustomComboBoxItem) cBV
						.getSelectedItem()).getKey();
				if (showWeight) {
					initEdgeWeightInTextField(graph, sourceVertex,
							targetVertex, weightField);
				}
			};
		});
		this.add(cBV);

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
				if (showWeight) {
					initEdgeWeightInTextField(graph, sourceVertex,
							targetVertex, weightField);
				}
			}
		});
		this.add(cBTV);

		if (showWeight) {
			// Weight of Edge
			this.add(new JLabel("Weight of Edge:"));
			initEdgeWeightInTextField(graph, sourceVertex, targetVertex,
					weightField);
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

		// Delete edge?
		this.add(new JLabel("Delete edge?:"));
		final JCheckBox cb = new JCheckBox();
		cb.setSelected(this.clearEdge);
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearEdge = cb.isSelected();
			};
		});
		this.add(cb);

		// OK/Cancel Buttons
		final JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				saved = true;
				dispose();
			};
		});
		okButton.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					okButton.doClick();
				}
			}
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
		getRootPane().setDefaultButton(okButton);
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

	public boolean getClearEdge() {
		return this.clearEdge;
	}

	public double getWeight() {
		return this.weight;
	}

	private void initEdgeWeightInTextField(IncidenceListGraph<V, E> graph,
			Vertex<V> sourceV, Vertex<V> targetV, JTextField weightField) {
		if (null != graph && null != sourceV && null != targetV
				&& null != weightField) {
			Iterator<Edge<E>> itE = graph.incidentEdges(sourceV);
			Edge<E> e = null;
			while (itE.hasNext()) {
				e = itE.next();
				if (graph.opposite(e, sourceV).equals(targetV)) {
					if (e.has(DecorableConstants.WEIGHT)) {
						Object oWeight = e.get(DecorableConstants.WEIGHT);
						if (null != oWeight && Double.class.isInstance(oWeight)) {
							weight = (double) oWeight;
							weightField.setText("" + weight);
						}
					}
				}
			}
		}
	};
}
