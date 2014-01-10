/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controls;

/**
 *
 * @author Stephan_2
 */
@SuppressWarnings("serial")
public class InformationPanel extends javax.swing.JPanel {
    private boolean weighted;
    private boolean directed;
    private boolean connected;
    private String graphInformations = "";
    /**
     * Creates new form InformationPanel
     */
    public InformationPanel() {
        initComponents();
        this.setGraphinformationText();
        this.informationLabel.setText(graphInformations);
        
    }
    private String isWeighted(){
        if (weighted){
            return "weigthed, ";
        }
        else {
            return "unweighted";
        }
    }
    private String isDirected(){
        if (directed){
            return "is directet";
        }
        else {
            return " is undirected";
        }
    }
    private String isConnected(){
        if (connected){
            return "is connected";
        }
        else {
            return "not connected";
        }
    }
    private void setGraphinformationText(){
        this.graphInformations =  "<html><p>is "+this.isWeighted()+ " and "+this.isDirected()+"</p>"+"<p>It is "+this.isConnected()+"</p>"+
                "<p>and has";
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlLabel = new javax.swing.JLabel();
        informationLabel = new javax.swing.JLabel();

        setToolTipText("");

        titlLabel.setText("This Graph:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(informationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(informationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(199, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel informationLabel;
    private javax.swing.JLabel titlLabel;
    // End of variables declaration//GEN-END:variables


}
