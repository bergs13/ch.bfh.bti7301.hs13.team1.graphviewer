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

    
    //Constructors
    public Recorder(AlgorithmDataProcessor modelProcessor) {
            this.processor = modelProcessor;
    }
      
    //methods
    public void recordStep(Graph graph){
            if (null == processor){
                throw new RuntimeException("processor not instantiated");
            }
            this.processor.set((IncidenceListGraph)graph);    
    }
    
}
