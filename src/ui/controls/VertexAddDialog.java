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
import logic.extlib.Vertex;

@SuppressWarnings("serial")
public class VertexAddDialog<V> extends JDialog {
	Vertex<V> sourceVertex = null;
	String label = "";
	// saved when closed?
	boolean saved = false;

	public VertexAddDialog(Iterator<Vertex<V>> itV) {
		this(itV, false);
	}

	public VertexAddDialog(Iterator<Vertex<V>> itV, boolean sourceVertexGiven) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		// Layout
		this.setLayout(new GridLayout(sourceVertexGiven ? 2 : 3, 2));
		int height = sourceVertexGiven ? 90 : 135;
		this.setMinimumSize(new Dimension(200, height));
		this.setMaximumSize(new Dimension(200, height));

		// Input fields
		// Source vertex
		if (!sourceVertexGiven) {
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
			sourceVertex = key;
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
			public void changedUpdate(DocumentEvent e) {
				// text was changed
				label = labelField.getText();
			}

			public void removeUpdate(DocumentEvent e) {
				// text was deleted
				label = labelField.getText();
			}

			public void insertUpdate(DocumentEvent e) {
				// text was inserted
				label = labelField.getText();
			}
		});
		this.add(labelField);

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

	public String getLabel() {
		return this.label;
	}
}
