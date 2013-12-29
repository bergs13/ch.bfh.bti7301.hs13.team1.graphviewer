/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.ActionEvent;
import logic.MainGUIModel;
import ui.controls.GraphPanel;

/**
 * 
 * @author Stephan_2
 */
@SuppressWarnings("serial")
public class MainGUI<V, E> extends javax.swing.JFrame {

	private GraphPanel<V, E> graphPanel;

	/**
	 * Creates new form MainGUI
	 */
	public MainGUI(MainGUIModel<V,E> model) {
		initComponents();
		graphPanel = new GraphPanel<V, E>(model.getGraphPanelModel());
		graphPanel.setBorder(javax.swing.BorderFactory
				.createLineBorder(new java.awt.Color(0, 0, 0)));

		javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(
				graphPanel);
		graphPanel.setLayout(graphPanelLayout);
		graphPanelLayout.setHorizontalGroup(graphPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 649, Short.MAX_VALUE));
		graphPanelLayout.setVerticalGroup(graphPanelLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0,
				Short.MAX_VALUE));
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(0, 0, 0)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		graphPanel,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		informationPanel1,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))
												.addComponent(
														jPanel1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														informationPanel1,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														graphPanel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap()));

		// .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        runButton = new javax.swing.JButton();
        backwardButton = new javax.swing.JButton();
        forwardButton = new javax.swing.JButton();
        directedRadioButton = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        editCheckBox = new javax.swing.JCheckBox();
        informationPanel1 = new ui.controls.InformationPanel();
        mainMenuBar = new javax.swing.JMenuBar();
        Menu1 = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        GraphMenu = new javax.swing.JMenu();
        newGraphMenuItem = new javax.swing.JMenuItem();
        loadGraphjMenuItem = new javax.swing.JMenuItem();
        saveGraphMenuItem = new javax.swing.JMenuItem();
        algorithmMenu = new javax.swing.JMenu();
        dijkstraMenuItem = new javax.swing.JMenuItem();
        kruskalMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        backwardButton.setText("Backward");
        backwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backwardButtonActionPerformed(evt);
            }
        });

        forwardButton.setText("Forward");
        forwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButtonActionPerformed(evt);
            }
        });

        directedRadioButton.setText("directed");

        jRadioButton1.setText("weighted");

        editCheckBox.setText("Edit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(editCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(directedRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 341, Short.MAX_VALUE)
                .addComponent(forwardButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backwardButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(runButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(runButton)
                    .addComponent(backwardButton)
                    .addComponent(forwardButton)
                    .addComponent(jRadioButton1)
                    .addComponent(directedRadioButton)
                    .addComponent(editCheckBox))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        Menu1.setText("Datei");

        exitMenuItem.setText("Beenden");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        Menu1.add(exitMenuItem);

        mainMenuBar.add(Menu1);

        GraphMenu.setText("Graph");

        newGraphMenuItem.setText("New Graph");
        newGraphMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGraphMenuItemActionPerformed(evt);
            }
        });
        GraphMenu.add(newGraphMenuItem);

        loadGraphjMenuItem.setText("Load Graph");
        loadGraphjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadGraphjMenuItemActionPerformed(evt);
            }
        });
        GraphMenu.add(loadGraphjMenuItem);

        saveGraphMenuItem.setText("Save Graph");
        saveGraphMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGraphMenuItemActionPerformed(evt);
            }
        });
        GraphMenu.add(saveGraphMenuItem);

        mainMenuBar.add(GraphMenu);

        algorithmMenu.setText("Algorithm");

        dijkstraMenuItem.setText("Dijkstra");
        dijkstraMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dijkstraMenuItemActionPerformed(evt);
            }
        });
        algorithmMenu.add(dijkstraMenuItem);

        kruskalMenuItem.setText("Kruskal");
        kruskalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kruskalMenuItemActionPerformed(evt);
            }
        });
        algorithmMenu.add(kruskalMenuItem);

        mainMenuBar.add(algorithmMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(informationPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(informationPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void newGraphMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGraphMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newGraphMenuItemActionPerformed

    private void loadGraphjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadGraphjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loadGraphjMenuItemActionPerformed

    private void saveGraphMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveGraphMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveGraphMenuItemActionPerformed

    private void dijkstraMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dijkstraMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dijkstraMenuItemActionPerformed

    private void kruskalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kruskalMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kruskalMenuItemActionPerformed

    private void forwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_forwardButtonActionPerformed

    private void backwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backwardButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_backwardButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_runButtonActionPerformed

	

	/**
	 * @param args
	 *            the command line arguments
	 */
	// public static void main(String args[]) {
	// /* Set the Nimbus look and feel */
	// //<editor-fold defaultstate="collapsed"
	// desc=" Look and feel setting code (optional) ">
	// /* If Nimbus (introduced in Java SE 6) is not available, stay with the
	// default look and feel.
	// * For details see
	// http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
	// */
	// try {
	// for (javax.swing.UIManager.LookAndFeelInfo info :
	// javax.swing.UIManager.getInstalledLookAndFeels()) {
	// if ("Nimbus".equals(info.getName())) {
	// javax.swing.UIManager.setLookAndFeel(info.getClassName());
	// break;
	// }
	// }
	// } catch (ClassNotFoundException ex) {
	// java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE,
	// null, ex);
	// } catch (InstantiationException ex) {
	// java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE,
	// null, ex);
	// } catch (IllegalAccessException ex) {
	// java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE,
	// null, ex);
	// } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	// java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE,
	// null, ex);
	// }
	// //</editor-fold>
	//
	// /* Create and display the form */
	// java.awt.EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// new MainGUI().setVisible(true);
	// }
	// });
	// }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu GraphMenu;
    private javax.swing.JMenu Menu1;
    private javax.swing.JMenu algorithmMenu;
    private javax.swing.JButton backwardButton;
    private javax.swing.JMenuItem dijkstraMenuItem;
    private javax.swing.JRadioButton directedRadioButton;
    private javax.swing.JCheckBox editCheckBox;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JButton forwardButton;
    private ui.controls.InformationPanel informationPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JMenuItem kruskalMenuItem;
    private javax.swing.JMenuItem loadGraphjMenuItem;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JMenuItem newGraphMenuItem;
    private javax.swing.JButton runButton;
    private javax.swing.JMenuItem saveGraphMenuItem;
    // End of variables declaration//GEN-END:variables

}
