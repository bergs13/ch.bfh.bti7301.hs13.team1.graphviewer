package logic;

import defs.EdgeFormat;
import defs.FormatHelper;
import defs.GraphFormat;
import defs.VertexFormat;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import logic.extlib.Edge;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GraphDataProcessor<V, E> {
    // Constructors

    public GraphDataProcessor() {
    }

    // End of Constructors
    // Methods
    // Public Methods
    public void exportGraph(IncidenceListGraph<V, E> g, String filePath) {
        // Get the String for the export
        String stringToExport = constructStringFromGraph(g);

        // Export the string into the file specified
        FileWriter fw = null;

        try {
            fw = new FileWriter(filePath);
            fw.write(stringToExport);  // text ist ein String
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    public String constructStringFromGraph(IncidenceListGraph<V, E> g) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("graph");
            doc.appendChild(rootElement);

            //Global graph attributes
            GraphFormat gf = null;

            //Vertices
            Iterator<Vertex<V>> it = g.vertices();

            while (it.hasNext()) {
                Vertex<V> v = null;
                VertexFormat f = null;

                v = it.next();
                f = FormatHelper.getFormat(VertexFormat.class, v);

                Element vertex = doc.createElement("vertex");
                rootElement.appendChild(vertex);
                vertex.setAttribute("name", v.element().toString());

                //Attributes
                //Label
                Element label = doc.createElement("label");
                String strLabel = "-";
                if (f != null && f.getLabel() != null) {
                    strLabel = f.getLabel().toString();
                }
                label.appendChild(doc.createTextNode(strLabel));
                vertex.appendChild(label);

                //Position
                Element position = doc.createElement("position");
                Element x = doc.createElement("x");
                Element y = doc.createElement("y");

                //x, y coordinates
                String posX = "-";
                String posY = "-";
                if (f != null && f.getCenterPoint() != null) {
                    posX = Double.toString(f.getCenterPoint().getX());
                    posY = Double.toString(f.getCenterPoint().getY());
                }

                x.appendChild(doc.createTextNode(posX));
                y.appendChild(doc.createTextNode(posY));
                position.appendChild(x);
                position.appendChild(y);
                vertex.appendChild(position);

                //Visited?
                Element visited = doc.createElement("visited");
                String vis = "no";

                if (f != null && f.isVisited() == true) {
                    vis = "yes";
                }
                visited.appendChild(doc.createTextNode(vis));
                vertex.appendChild(visited);

                //Active?
                Element active = doc.createElement("active");
                String act = "no";

                if (f != null && f.isActive() == true) {
                    act = "yes";
                }
                active.appendChild(doc.createTextNode(act));
                vertex.appendChild(active);
            }

            //Edges
            Iterator<Edge<E>> it2 = g.edges();

            while (it2.hasNext()) {
                Edge<E> e = null;
                EdgeFormat ef = null;

                e = it2.next();
                ef = FormatHelper.getFormat(EdgeFormat.class, e);

                Element edge = doc.createElement("edge");

                rootElement.appendChild(edge);
                edge.setAttribute("name", e.element().toString());

                //Attributes
                //Label
                Element label = doc.createElement("label");
                String strLabel = "-";
                if (ef != null && ef.getLabel() != null) {
                    strLabel = ef.getLabel().toString();
                }
                label.appendChild(doc.createTextNode(strLabel));
                edge.appendChild(label);

                //fromPoint
                Element fromPoint = doc.createElement("fromPoint");
                Element xFromPoint = doc.createElement("x");
                Element yFromPoint = doc.createElement("y");
                String fPosX = "-";
                String fPosY = "-";
                if (ef != null && ef.getFromPoint() != null) {
                    fPosX = Double.toString(ef.getFromPoint().getX());
                    fPosY = Double.toString(ef.getFromPoint().getY());
                }
                xFromPoint.appendChild(doc.createTextNode(fPosX));
                yFromPoint.appendChild(doc.createTextNode(fPosY));
                fromPoint.appendChild(xFromPoint);
                fromPoint.appendChild(yFromPoint);

                //toPoint
                Element toPoint = doc.createElement("toPoint");
                Element xToPoint = doc.createElement("x");
                Element yToPoint = doc.createElement("y");

                String tPosX = "-";
                String tPosY = "-";
                if (ef != null && ef.getToPoint() != null) {
                    tPosX = Double.toString(ef.getToPoint().getX());
                    tPosY = Double.toString(ef.getToPoint().getY());
                }
                xToPoint.appendChild(doc.createTextNode(tPosX));
                yToPoint.appendChild(doc.createTextNode(tPosY));
                toPoint.appendChild(xToPoint);
                toPoint.appendChild(yToPoint);

                //isWeighted?
                Element weighted = doc.createElement("weighted");
                String strWeighted = "no";

                if (ef != null && ef.isWeighted() == true) {
                    strWeighted = "yes";
                }
                weighted.appendChild(doc.createTextNode(strWeighted));
                edge.appendChild(weighted);
            }

            // Transform XML object to string
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.toString();

            //return string
            return output;

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        return "";
    }

    public IncidenceListGraph<V, E> importGraph(String filePath) {
        // Get the String from the file
        String s = "";
        // Reconstruct the graph
        return reconstructGraphFromString(s);
    }

    public IncidenceListGraph<V, E> reconstructGraphFromString(String s) {
        IncidenceListGraph<V, E> g = new IncidenceListGraph<>(false);
        return g;
    }
    // End of Public Methods
    // End of Methods
}
