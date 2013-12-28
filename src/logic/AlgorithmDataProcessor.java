package logic;

import java.util.ArrayList;
import logic.extlib.IncidenceListGraph;

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
    //Start recording
    public void resetGraphList(){
        graphList.clear();
        graphListIndex = 0;
    }
    
    public void set(IncidenceListGraph graph){
        String graphString = GDProcessor.constructStringFromGraph(graph);
        graphList.add(graphString);
    }
    
    //returns the previous step
    public IncidenceListGraph backward(){
        if (graphListIndex == 0){
            //Warning start of list
        }
        String graphString = graphList.get(--graphListIndex);
        IncidenceListGraph graph = GDProcessor.reconstructGraphFromString(graphString);
        return graph;
    }
    
    //returns the next step
    public IncidenceListGraph forward(){
        if (graphListIndex == graphList.size()-1){
            //warning end of list
        }
        String graphString = graphList.get(++graphListIndex);
        IncidenceListGraph graph = GDProcessor.reconstructGraphFromString(graphString);
        return graph; 
    }
    
    //returns the first step
    public IncidenceListGraph first(){
        graphListIndex = 0;
        String graphString = graphList.get(graphListIndex);
        IncidenceListGraph graph = GDProcessor.reconstructGraphFromString(graphString);
        return graph; 
    }
    
    //returns the last step
    public IncidenceListGraph last(){
        
        graphListIndex = graphList.size()-1;
        String graphString = graphList.get(graphListIndex);
        IncidenceListGraph graph = GDProcessor.reconstructGraphFromString(graphString);
        return graph;
    }
    public ArrayList getGraphList(){
       return graphList;
    }
    public void setGraphList(ArrayList GraphlistToImport){
        this.resetGraphList();
        graphList.addAll(GraphlistToImport);
    }
}
