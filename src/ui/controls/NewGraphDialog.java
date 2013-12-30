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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Stephan_2
 */
public class NewGraphDialog extends JDialog{
    private final String question= "Should the new graph be";
    private final JRadioButton directedButton=new JRadioButton();
    private final JRadioButton undirectedButton= new JRadioButton();
    private final ButtonGroup directionButtongroup = new ButtonGroup(); 
    private boolean directed= false;
    private final GraphFormat format;
//consturctors
    //This sets the field directed of GraphFormat of the new Graph  
    public NewGraphDialog(GraphFormat newFormat){
        super();
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.format = newFormat;
        
        //Layout
        this.setLayout(new GridLayout(0, 1));
        this.setMinimumSize(new Dimension(200, 200));
	this.setMaximumSize(new Dimension(200, 200));
        
        this.add(new JLabel(question));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(setButtonPanel(), BorderLayout.NORTH);
                
        JButton okButton = new JButton("OK");
                JPanel okPanel = new JPanel();
                okPanel.add(okButton);buttonPanel.add(okPanel, BorderLayout.SOUTH);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
                                directed = directedButton.isSelected();
                                format.setDirected(directed);
				dispose();
			};
		});
         buttonPanel.add(okPanel, BorderLayout.SOUTH);
         this.add(buttonPanel);
         this.setVisible(true);
    }
//End of constructors            
    
//Methods
    
    public final JPanel setButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,1));
        directedButton.setText("directed");
        undirectedButton.setText("undirected");
        buttonPanel.add(directedButton);
        buttonPanel.add(undirectedButton);
        directionButtongroup.add(directedButton);
        directionButtongroup.add(undirectedButton);
        return buttonPanel;
    }
    
}
