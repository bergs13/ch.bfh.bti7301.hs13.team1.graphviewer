/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import logic.extlib.Graph;
import logic.extlib.IncidenceListGraph;


/**
 *
 * @author Stephan_2
 * @param <V>
 * @param <E>
 */
public class Recorder<V, E> {

    //Members
   private  AlgorithmDataProcessor processor;
    //End of Members
    
    //Constructors
    public Recorder(AlgorithmDataProcessor processor) {
        
    }
    //End of constructors
    
    //methods
    public void breakPoint(Graph graph){
        if (null == processor){
            throw new RuntimeException("processor not instantiated");
        }
        this.processor.set((IncidenceListGraph)graph);
    }
    
    
}
