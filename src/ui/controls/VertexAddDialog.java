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
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import defs.CustomComboBoxItem;
import defs.FormatHelper;
import defs.VertexFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import logic.extlib.Vertex;

@SuppressWarnings("serial")
public class VertexAddDialog<V> extends JDialog {
	Vertex<V> sourceVertex = null;
	String label = "";
	double weight = Double.NEGATIVE_INFINITY;
	// saved when closed?
	boolean saved = false;

	public VertexAddDialog(Iterator<Vertex<V>> itV, boolean showWeight) {
		this(itV, showWeight, true);
	}

	public VertexAddDialog(Iterator<Vertex<V>> itV, boolean showWeight,
			boolean showSourceVertex) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		// Layout
		// no weight if first vertex
		boolean isFirstVertex = !itV.hasNext();
		if (isFirstVertex) {
			showWeight = false;
		}
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
		// Source vertex
		if (showSourceVertex && !isFirstVertex) {
			this.add(new JLabel("Source vertex:"));
			final JComboBox<CustomComboBoxItem> cBV = new JComboBox<CustomComboBoxItem>();
			Vertex<V> key = null;
			VertexFormat formatForValue;
			String value;
			while (itV.hasNext()) {
				key = itV.next();
				formatForValue = FormatHelper
						.getFormat(VertexFormat.class, key);
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
				};
			});
			this.add(cBV);
		}

		// Label
		this.add(new JLabel("Label:"));
		final JTextField labelField = new JTextField(this.label);
		// Listen for changes in the text
		labelField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				// text was changed
				label = labelField.getText();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// text was deleted
				label = labelField.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// text was inserted
				label = labelField.getText();
			}
		});
		this.add(labelField);

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
		final JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				saved = true;
				dispose();
			};
		});
                okButton.addKeyListener(new KeyAdapter(){
                
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

	public String getLabel() {
		return this.label;
	}

	public double getWeight() {
		return this.weight;
	}
}
