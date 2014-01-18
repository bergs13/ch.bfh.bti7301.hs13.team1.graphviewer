package ui.controls;

import defs.CustomComboBoxItem;
import defs.FormatHelper;
import defs.VertexFormat;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import logic.extlib.Vertex;

/**
 * 
 * @author Stephan_2
 */
@SuppressWarnings("serial")
public class ChooseStartVertexDialog<V> extends JDialog {

	// Members
	Vertex<V> startVertex = null;

	// saved when closed?
	boolean saved = false;

	// constructor
	public ChooseStartVertexDialog(Iterator<Vertex<V>> itV) {
		super();
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		// Layout
		this.setLayout(new GridLayout(2, 2));
		this.setMinimumSize(new Dimension(200, 90));
		this.setMaximumSize(new Dimension(200, 90));

		// Input fields
		final JComboBox<CustomComboBoxItem> cBV = new JComboBox<CustomComboBoxItem>();
		Vertex<V> key = null;
		VertexFormat formatForValue;
		String value;
		while (itV.hasNext()) {
			key = itV.next();
			formatForValue = FormatHelper.getFormat(VertexFormat.class, key);
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
			};
		});
		this.add(new JLabel("Start vertex:"));
		this.add(cBV);

		// OK/Cancel Buttons
		final JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				saved = true;
				dispose();
			};
		});
                okButton.addKeyListener(new KeyAdapter(){
                
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                            okButton.doClick();
                        }
                    }        
                });
		final JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			};
		});
                cancelButton.addKeyListener(new KeyAdapter(){
                    
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                            getRootPane().setDefaultButton(cancelButton);
                            cancelButton.doClick();
            
                        }
                    }
                });
		this.add(okButton);
		this.add(cancelButton);
                getRootPane().setDefaultButton(okButton);
                this.setVisible(true);
	}

	public boolean getSaved() {
		return this.saved;
	}

	public Vertex<V> getStartVertex() {
		return this.startVertex;
	}
}
