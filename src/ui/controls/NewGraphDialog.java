/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRadioButton;

/**
 *
 * @author Stephan_2
 */
public class NewGraphDialog extends JDialog{
    private String question= "Should the new Graph be";
    private final JRadioButton directedButton=new JRadioButton();
    private final JRadioButton undirectedButton= new JRadioButton();
    private final ButtonGroup buttongroup = new ButtonGroup(); 
    private boolean directed= false;
//consturctors
    public NewGraphDialog(){
        super();
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        
        this.add(directedButton);
        this.add(undirectedButton);
        
        JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
                                directed = directedButton.isSelected();
				dispose();
			};
		});
         this.add(okButton);
    }
//End of constructors            
    
//Methods
    private void setRadioButtons(){
        directedButton.setText("directed");
        undirectedButton.setText("undirected");
        this.buttongroup.add(directedButton);
        this.buttongroup.add(undirectedButton);
    }
    public boolean getSaved() {
		return this.directed;
    }
}
