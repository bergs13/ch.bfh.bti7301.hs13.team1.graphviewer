/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import logic.GraphPanelModel;
import logic.extlib.IncidenceListGraph;

/**
 *
 * @author Stephan_2
 */
public class VertexUpdaterTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      TestData testData = new TestData();  
    IncidenceListGraph<String, String> graph = testData.getTestGraph();
    
    graph.insertVertex("A");
    GraphPanelModel model = new GraphPanelModel(graph);
    }
    
}
