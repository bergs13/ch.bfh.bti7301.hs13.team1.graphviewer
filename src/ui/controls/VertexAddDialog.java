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
import logic.extlib.Vertex;

@SuppressWarnings("serial")
public class VertexAddDialog<V> extends JDialog {
	Vertex<V> sourceVertex = null;
	// saved when closed?
	boolean saved = false;

	public VertexAddDialog(Iterator<Vertex<V>> itV) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		// Layout
		this.setLayout(new GridLayout(3, 2));
		this.setMinimumSize(new Dimension(90, 90));
		this.setMaximumSize(new Dimension(90, 90));

		// Input fields
		// Source vertex
		this.add(new JLabel("Source vertex:"));
		final JComboBox<Vertex<V>> cBV = new JComboBox<Vertex<V>>();
		while (itV.hasNext()) {
			cBV.addItem(itV.next());
		}
		// Default selection
		sourceVertex = (Vertex<V>) cBV.getSelectedItem();
		cBV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sourceVertex = (Vertex<V>) cBV.getSelectedItem();
			};
		});
		this.add(cBV);

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
}
