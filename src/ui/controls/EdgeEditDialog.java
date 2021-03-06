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

		// Dynamic contents set in events
		final JTextField weightField = new JTextField();
		final JComboBox<CustomComboBoxItem> cBTV = new JComboBox<CustomComboBoxItem>();

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
			// only vertices with incident edges
			if (graph.incidentEdges(key).hasNext()) {
				formatForValue = FormatHelper
						.getFormat(VertexFormat.class, key);
				value = "";
				if (null != formatForValue && null != formatForValue.getLabel()) {
					value = formatForValue.getLabel();
				}
				cBV.addItem(new CustomComboBoxItem(key, value));
			}
		}
		cBV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sourceVertex = (Vertex<V>) ((CustomComboBoxItem) cBV
						.getSelectedItem()).getKey();
				adjustTargetVertices(sourceVertex, graph, cBTV);
				if (showWeight) {
					initEdgeWeightInTextField(graph, sourceVertex,
							targetVertex, weightField);
				}
			}
		});
		// Default selection (After event because of the target list when
		// starting the editor)
		if (cBV.getItemCount() > 0) {
			cBV.setSelectedIndex(0);
			sourceVertex = (Vertex<V>) cBV.getItemAt(0).getKey();
		}
		this.add(cBV);

		// Target vertex
		this.add(new JLabel("Target vertex:"));
		cBTV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (null != (CustomComboBoxItem) cBTV.getSelectedItem()) {
					targetVertex = (Vertex<V>) ((CustomComboBoxItem) cBTV
							.getSelectedItem()).getKey();
					if (showWeight) {
						initEdgeWeightInTextField(graph, sourceVertex,
								targetVertex, weightField);
					}
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
							try {
								weight = Double.parseDouble(weightField
										.getText());
							} catch (NumberFormatException nfe) {
							}
						}

						@Override
						public void removeUpdate(DocumentEvent e) {
							// text was deleted
							try {
								weight = Double.parseDouble(weightField
										.getText());
							} catch (NumberFormatException nfe) {
							}
						}

						@Override
						public void insertUpdate(DocumentEvent e) {
							try {
								weight = Double.parseDouble(weightField
										.getText());
							} catch (NumberFormatException nfe) {
							}
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

	// Helper methods
	private void adjustTargetVertices(Vertex<V> sourceV,
			IncidenceListGraph<V, E> graph,
			JComboBox<CustomComboBoxItem> targetVCombo) {
		if (null != sourceV && null != graph && null != targetVCombo) {
			targetVCombo.removeAllItems();
			Iterator<Edge<E>> itE = graph.incidentEdges(sourceV);
			Vertex<V> key = null;
			Edge<E> edge = null;
			VertexFormat formatForValue = null;
			String value = "";
			while (itE.hasNext()) {
				edge = itE.next();
				key = graph.opposite(edge, sourceV);
				if (null != key) {
					formatForValue = FormatHelper.getFormat(VertexFormat.class,
							key);
					value = "";
					if (null != formatForValue
							&& null != formatForValue.getLabel()) {
						value = formatForValue.getLabel();
					}
					targetVCombo.addItem(new CustomComboBoxItem(key, value));
				}
			}
			// Default selection
			if (targetVCombo.getItemCount() > 0) {
				targetVCombo.setSelectedIndex(0);
				targetVertex = (Vertex<V>) targetVCombo.getItemAt(0).getKey();
			}
		}
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
	}
	// End of Helper methods
}
