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
import defs.GraphFormat;

@SuppressWarnings("serial")
public class GraphFormatDialog extends JDialog {
	GraphFormat format = null;
	Color unvisitedColor = null;
	Color visitedColor = null;
	Color activeColor = null;
	boolean isLabelVisible = false;
	boolean isDirected = false;
	// saved when closed?
	boolean saved = false;

	public GraphFormatDialog(final GraphFormat format) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.format = format;
		this.unvisitedColor = format.getUnvisitedColor();
		this.visitedColor = format.getVisitedColor();
		this.activeColor = format.getActiveColor();
		this.isLabelVisible = format.isLabelVisible();
		this.isDirected = format.isDirected();
		// Layout
		this.setLayout(new GridLayout(6, 2));
		this.setMinimumSize(new Dimension(200, 270));
		this.setMaximumSize(new Dimension(200, 270));

		// Input fields
		// Unvisited color
		this.add(new JLabel("Unvisited Color:"));
		final JButton bUnvisited = new JButton("Change");
		bUnvisited.setBackground(this.unvisitedColor);
		bUnvisited.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color newC = JColorChooser.showDialog(null, "Choose color",
						unvisitedColor);
				unvisitedColor = newC;
				bUnvisited.setBackground(unvisitedColor);
			};
		});
		this.add(bUnvisited);

		// Visited color
		this.add(new JLabel("Visited Color:"));
		final JButton bVisited = new JButton("Change");
		bVisited.setBackground(this.visitedColor);
		bVisited.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color newC = JColorChooser.showDialog(null, "Choose color",
						visitedColor);
				visitedColor = newC;
				bVisited.setBackground(visitedColor);
			};
		});
		this.add(bVisited);

		// Active color
		this.add(new JLabel("Active Color:"));
		final JButton bActive = new JButton("Change");
		bActive.setBackground(this.activeColor);
		bActive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Color newC = JColorChooser.showDialog(null, "Choose color",
						activeColor);
				activeColor = newC;
				bActive.setBackground(activeColor);
			};
		});
		this.add(bActive);

		// Label visible
		this.add(new JLabel("Label visible:"));
		final JComboBox<Boolean> cBLVisible = new JComboBox<Boolean>(
				new Boolean[] { true, false });
		cBLVisible.setSelectedItem(this.isLabelVisible);
		cBLVisible.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isLabelVisible = (boolean) cBLVisible.getSelectedItem();
			};
		});
		this.add(cBLVisible);

		// Directed
		this.add(new JLabel("Directed?:"));
		final JComboBox<Boolean> cBDirected = new JComboBox<Boolean>(
				new Boolean[] { true, false });
		cBDirected.setSelectedItem(this.isDirected);
		cBDirected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isDirected = (boolean) cBDirected.getSelectedItem();
			};
		});
		this.add(cBDirected);

		// OK/Cancel Buttons
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				format.setUnvisitedColor(unvisitedColor);
				format.setVisitedColor(visitedColor);
				format.setActiveColor(activeColor);
				format.setLabelVisible(isLabelVisible);
				format.setDirected(isDirected);
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

	public GraphFormat getFormat() {
		return null == this.format ? new GraphFormat() : this.format;
	}
}
