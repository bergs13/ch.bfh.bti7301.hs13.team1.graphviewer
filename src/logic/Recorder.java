/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.Iterator;
import logic.extlib.Edge;
import logic.extlib.Graph;
import logic.extlib.Vertex;

/**
 *
 * @author Stephan_2
 * @param <V>
 * @param <E>
 */
public class Recorder<V, E> {

    //Members
    private final Graph<V, E> graph;
   // private ArrayList<Memento> graphs;
    //End of Members
    
    //Constructors
    public Recorder(Graph g) {
        graph = g;
        

    }
    //End of constructors
    
    public void breakPoint(Object element) {

        //record state or

    }
}
