package ui.controls;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import defs.VertexFormat;

@SuppressWarnings("serial")
public class VertexFormatDialog extends JDialog {
	VertexFormat format = null;
	String label = null;
	// saved when closed?
	boolean saved = false;

	public VertexFormatDialog(final VertexFormat format) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.format = format;
		this.label = format.getLabel();
		if (null == this.label) {
			this.label = "";
		}

		// Layout
		this.setLayout(new GridLayout(2, 2));
		this.setMinimumSize(new Dimension(200, 90));
		this.setMaximumSize(new Dimension(200, 90));

		// Input fields
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
				format.setLabel(label);
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

	public VertexFormat getFormat() {
		return null == this.format ? new VertexFormat() : this.format;
	}
}
