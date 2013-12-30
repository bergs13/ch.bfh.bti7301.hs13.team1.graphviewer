/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controls;

import defs.CustomComboBoxItem;
import defs.FormatHelper;
import defs.VertexFormat;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logic.extlib.Vertex;

/**
 *
 * @author Stephan_2
 */
public class ChooseStartVertexDialog<V> extends JDialog {

    //Members
    Vertex<V> startVertex = null;

    // saved when closed?
    boolean saved = false;

//constructor
    public ChooseStartVertexDialog(Iterator<Vertex<V>> itV) {
        super();
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        // Layout
        this.setLayout(new GridLayout(2, 0));
        this.setMinimumSize(new Dimension(200, 135));
        this.setMaximumSize(new Dimension(200, 135));

        // Input fields
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
            startVertex = (Vertex<V>) cBV.getItemAt(0).getKey();
        }
        cBV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                startVertex = (Vertex<V>) ((CustomComboBoxItem) cBV
                        .getSelectedItem()).getKey();
            }
        ;
        });
	
         JPanel vertexPanel = new JPanel();
         vertexPanel.setLayout(new GridLayout(0,2));
         vertexPanel.add(new JLabel("Start vertex:"));
         vertexPanel.add(cBV);
         this.add(vertexPanel);
        // OK/Cancel Buttons
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
                saved = true;
                dispose();
            }
        ;
        });
		JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
                dispose();
            }
        ;
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,2));
	buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        this.add(buttonPanel);
        this.setVisible(true);
    }

    public boolean getSaved() {
        return this.saved;
    }

    public Vertex<V> getStartVertex() {
        return this.startVertex;
    }
}
