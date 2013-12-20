/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import java.util.ArrayList;
import logic.extlib.Graph;

/**
 *
 * @author Stephan_2
 */
public class AlgorithmDataProcessor {
    //
    private int graphListIndex;
    private final ArrayList<String> graphList;
    private GraphDataProcessor GDProcessor;
    
    public AlgorithmDataProcessor(){
        GDProcessor = new GraphDataProcessor();
        graphList = new ArrayList<>();
        
    }
    public void startRecord(){
        graphList.clear();
        graphListIndex = 0;
    }
    
    public void set(Graph graph){
        String graphString = "no implemented"; //=GDProcessor.exportGraph(graph);
        graphList.add(graphString);
        graphListIndex++;
    }
    //returns the previous graph
    public Graph backward(){
        //GDProcessor.importGraph(null);
        Graph graph = null;//waitin for GraphDataPrcessor
        String graphString = graphList.get(--graphListIndex);
        //Graph graph = GDProcessor.importGraph(graphString);
        return graph;
    }
    
    //returns the next graph
    public Graph forward(){
        Graph graph = null;
        String graphString = graphList.get(++graphListIndex);
        //Graph graph = GDProcessor.importGraph(graphString);
        return graph; 
    }
    
    //returns the first step
    public Graph first(){
        Graph graph = null;//waitin for GraphDataPrcessor
        graphListIndex = 0;
        String graphString = graphList.get(graphListIndex);
        //Graph graph = GDProcessor.importGraph(graphString);
        return graph; 
    }
    
    //returns the last step
    public Graph last(){
        Graph graph = null;//waitin for GraphDataPrcessor
        graphListIndex = graphList.size()-1;
        String graphString = graphList.get(graphListIndex);
        //Graph graph = GDProcessor.importGraph(graphString);
        return graph;
    }
}
