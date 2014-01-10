package logic;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import logic.extlib.IncidenceListGraph;

/**
 *
 * @author Stephan_2
 */
public class AlgorithmDataProcessor {

    //
    private int graphListIndex;
    private final ArrayList<String> graphList;
    private final GraphDataProcessor GDProcessor;

    public AlgorithmDataProcessor() {
        GDProcessor = new GraphDataProcessor();
        graphList = new ArrayList<String>();

    }

    //reset graphlist at start of recording
    public void resetGraphList() {
        graphList.clear();
        graphListIndex = 0;
    }

    //save a grap to the GrapList as a string
    public void set(IncidenceListGraph graph) {
        try {
            String graphString = GDProcessor.constructStringFromGraph(graph);
            graphList.add(graphString);
            System.out.println(graphString);
        } catch (IllegalArgumentException err) {
            System.err.println("No Graph");
        }

    }

    //returns the previous step
    public IncidenceListGraph backward() {
        if (graphListIndex == 0) {
            JOptionPane.showMessageDialog(null, "Das ist der Startzustand.", "Anfang", JOptionPane.PLAIN_MESSAGE);
            return first();
        } else {
            String graphString = graphList.get(--graphListIndex);
            IncidenceListGraph graph = GDProcessor.reconstructGraphFromString(graphString);
            return graph;
        }
    }

    //returns the next step
    public IncidenceListGraph forward() {
        if (graphListIndex == graphList.size() - 1) {
            JOptionPane.showMessageDialog(null, "Das ist der Endzustand.", "Ende", JOptionPane.PLAIN_MESSAGE);
            return last();
        } else {
            String graphString = graphList.get(++graphListIndex);
            IncidenceListGraph graph = GDProcessor.reconstructGraphFromString(graphString);
            return graph;
        }
    }

    //returns the first step
    public IncidenceListGraph first() {
        graphListIndex = 0;
        String graphString = graphList.get(graphListIndex);
        IncidenceListGraph graph = GDProcessor.reconstructGraphFromString(graphString);
        return graph;
    }

    //returns the last step
    public IncidenceListGraph last() {

        graphListIndex = graphList.size() - 1;
        String graphString = graphList.get(graphListIndex);
        IncidenceListGraph graph = GDProcessor.reconstructGraphFromString(graphString);
        return graph;
    }
    //export the Graphlist
    public ArrayList<String> getGraphList() {
        return graphList;
    }
    //import a Graphlist
    public void setGraphList(ArrayList<String> graphlistToImport) {
        this.resetGraphList();
        graphList.addAll(graphlistToImport);
    }
}
