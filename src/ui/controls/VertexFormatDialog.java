package ui.controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import defs.VertexFormat;

@SuppressWarnings("serial")
public class VertexFormatDialog extends JDialog {
	VertexFormat format = null;
	// saved when closed?
	boolean saved = false;

	public VertexFormatDialog(final VertexFormat format) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.format = format;

		// Layout
		this.setLayout(new GridLayout(2, 2));
		this.setMinimumSize(new Dimension(60, 200));
		this.setMaximumSize(new Dimension(60, 200));

		// Input fields
		// Label
		this.add(new JLabel("Label:"));
		this.add(new JTextField(format.getLabel()));

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

	public VertexFormat getFormat() {
		return null == this.format ? new VertexFormat() : this.format;
	}
}
