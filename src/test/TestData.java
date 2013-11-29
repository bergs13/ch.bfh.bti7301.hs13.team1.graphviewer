

package test;

import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;

/**
 *
 * @author Stephan_2
 */
public class TestData {
    
    private IncidenceListGraph<String, String> testGraph;
    
    
    public TestData(){
        createTestGraph();
        
    }
    private void createTestGraph(){
        testGraph = new IncidenceListGraph(true);
        Vertex<String> vA = testGraph.insertVertex("A");
	Vertex<String> vB = testGraph.insertVertex("B");
	Vertex<String> vC = testGraph.insertVertex("C");
	Vertex<String> vD = testGraph.insertVertex("D");
	Vertex<String> vE = testGraph.insertVertex("E");
        testGraph.insertEdge(vA, vB, "AB");
        testGraph.insertEdge(vA, vC, "AC");
        testGraph.insertEdge(vC, vD,"CD");
        testGraph.insertEdge(vB, vE, "BE");
    }
    public IncidenceListGraph getTestGraph(){
        if (testGraph == null){
            createTestGraph();
        }
        return testGraph;
    }
}
