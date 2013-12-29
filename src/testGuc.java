
import defs.EdgeFormat;
import defs.FormatHelper;
import defs.VertexFormat;
import demo.GraphDataProcessorGuc;
import ui.controls.GraphImportExportDialog;
import logic.GraphDataProcessor;
import logic.extlib.Edge;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Christian
 */
public class testGuc {

    public static void main(String args[]) {

        final IncidenceListGraph<String, String> testGraph = new IncidenceListGraph<String, String>(true);

        //Graph Format

        // Vertices
        // Vertex 1
        Vertex<String> vA = testGraph.insertVertex("A");
        // Format Vertex 1
        VertexFormat vF1 = new VertexFormat();
        vF1.setLabel("Eckpunkt A");
        vA.set(FormatHelper.FORMAT, vF1);

        // Vertex 2
        Vertex<String> vB = testGraph.insertVertex("B");
        VertexFormat vF2 = new VertexFormat();
        vF2.setLabel("Eckpunkt B");
        vB.set(FormatHelper.FORMAT, vF2);
        //Vertex 3
        Vertex<String> vC = testGraph.insertVertex("C");

        // Edges
        // Edge 1
        Edge<String> eAB = testGraph.insertEdge(vA, vB, "AB");
        // Format Edge 1
        EdgeFormat eF1 = new EdgeFormat();
        //eF1.set(true);
        eF1.setLabel("AB");
        //eF1.setTextVisible(true);
        eAB.set(FormatHelper.FORMAT, eF1);
        // Edge 2
        Edge<String> eBC = testGraph.insertEdge(vB, vC, "BC");
        // Format Edge 2
        EdgeFormat eF2 = new EdgeFormat();
        //eF2.setTextVisible(true);
        eBC.set(FormatHelper.FORMAT, eF2);


//       GraphDataProcessor g1 = new GraphDataProcessor();
//       // System.out.print(g1.constructStringFromGraph(testGraph));
//       g1.exportGraph(testGraph, "\\C:\\Users\\Christian\\Desktop\\testgraph2.xml");

        GraphImportExportDialog gie = new GraphImportExportDialog();
        gie.chooseFile();




    }
}
