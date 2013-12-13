/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Stack;
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
    private GraphDataProcessor processor;
    private final Stack graphstack= new Stack();
    //End of Members
    
    //Constructors
    public Recorder() {
        processor = new GraphDataProcessor();
    }
    //End of constructors
    
    public void add(Graph graph) {
        String storedgraph = ""; //processor.exportGraph(graph);
        graphstack.push(storedgraph);
    }
    public IncidenceListGraph remove(){
        if (!graphstack.empty()){
        IncidenceListGraph graph = new IncidenceListGraph(true);//processor.importGraph(graphstack.pop());
        return graph;
        }
        else {
        return null;
        }
    } 
  
}
