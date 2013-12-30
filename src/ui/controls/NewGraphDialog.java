/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controls;

import defs.GraphFormat;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Stephan_2
 */
public class NewGraphDialog extends JDialog {

    //members
    private final String question = "Should the new graph be";
    private final GraphFormat format;

    //end of members
//constructors
    //This sets the field directed of GraphFormat of the new Graph  
    public NewGraphDialog(GraphFormat graphFormat) {
        super();
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.format = graphFormat;

        //Layout
        this.setLayout(new GridLayout(0, 1));
        this.setMinimumSize(new Dimension(200, 100));
        this.setMaximumSize(new Dimension(200, 100));
        
        //Fields
        this.add(new JLabel(question));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 0));
        
        //Buttons
        JButton directedButton = new JButton("directed");
        directedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
                format.setDirected(true);
                dispose();
            }
        ;
        });
        JButton undirectedButton = new JButton("undirected");
        undirectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
                format.setDirected(false);
                dispose();
            }
        ;
        });       
        
        buttonPanel.add(directedButton);
        buttonPanel.add(undirectedButton);
        this.add(buttonPanel);
        this.setVisible(true);
    }
//End of constructors            

}
