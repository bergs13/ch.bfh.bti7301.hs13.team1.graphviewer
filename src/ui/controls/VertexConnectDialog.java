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
public class VertexConnectDialog<V> extends JDialog {
	Vertex<V> sourceVertex = null;
	Vertex<V> targetVertex = null;
	// saved when closed?
	boolean saved = false;

	public VertexConnectDialog(Iterator<Vertex<V>> itV) {
		this(itV, false);
	}

	public VertexConnectDialog(Iterator<Vertex<V>> itV,
			boolean sourceVertexGiven) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		// Layout
		this.setLayout(new GridLayout(sourceVertexGiven ? 2 : 3, 2));
		int height = sourceVertexGiven ? 90 : 135;
		this.setMinimumSize(new Dimension(200, height));
		this.setMaximumSize(new Dimension(200, height));

		// Input fields
		if (!sourceVertexGiven) {
			// Source vertex
			this.add(new JLabel("Source vertex:"));
			final JComboBox<Vertex<V>> cBSV = new JComboBox<Vertex<V>>();
			while (itV.hasNext()) {
				cBSV.addItem(itV.next());
			}
			// Default selection
			sourceVertex = (Vertex<V>) cBSV.getSelectedItem();
			cBSV.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					sourceVertex = (Vertex<V>) cBSV.getSelectedItem();
				};
			});
			this.add(cBSV);
		}

		// Target vertex
		this.add(new JLabel("Target vertex:"));
		final JComboBox<Vertex<V>> cBTV = new JComboBox<Vertex<V>>();
		while (itV.hasNext()) {
			cBTV.addItem(itV.next());
		}
		// Default selection
		targetVertex = (Vertex<V>) cBTV.getSelectedItem();
		cBTV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				targetVertex = (Vertex<V>) cBTV.getSelectedItem();
			};
		});
		this.add(cBTV);

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
}
