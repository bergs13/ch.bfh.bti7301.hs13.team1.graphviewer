/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephan_2
 */
public class AlgorithmRunner implements Runnable{
    
    private AlgorithmDataProcessor algorithmDataProcessor;
    private boolean stepByStep; 
    
    public AlgorithmRunner(AlgorithmDataProcessor processor){
        if (null == processor) {
			throw new IllegalArgumentException("no AlgoritDataProcessor set for AlgorithmRunner");
		}
        algorithmDataProcessor = processor;
    }
    
    @Override
    public void run() {
        this.algorithmDataProcessor.first();
        if (true == stepByStep){
            
            for(int k = 0; k<=algorithmDataProcessor.getGraphList().size(); k++ ){
                try {
                Thread.sleep(3000);
                algorithmDataProcessor.forward();
            } catch (InterruptedException ex) {
                Logger.getLogger(AlgorithmRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
        
        else {
            
        }
    }
    
}
    
