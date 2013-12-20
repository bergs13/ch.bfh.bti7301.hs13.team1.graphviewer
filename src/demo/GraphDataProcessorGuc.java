/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;


import defs.FormatHelper;
import defs.GraphFormat;
import defs.VertexFormat;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import logic.extlib.Graph;
import logic.extlib.Vertex;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Christian
 */
public class GraphDataProcessorGuc<V, E> {
    
     public GraphDataProcessorGuc() {
    }

    public void exportGraph(Graph g)  {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Graph");
            doc.appendChild(rootElement);

            //Vertices
            Iterator<Vertex<V>> it = g.vertices();

            while (it.hasNext()) {
                Vertex<V> v = null;
                VertexFormat f = null;

                v = it.next();
                f = FormatHelper.getFormat(VertexFormat.class, v);

                Element vertex = doc.createElement("Vertex");
                rootElement.appendChild(vertex);
                vertex.setAttribute("name", v.element().toString());

                //Attributes
                //Label
                Element label = doc.createElement("label");
                String strLabel;
                if (f != null && f.getLabel() != null) {
                    strLabel = f.getLabel().toString();
                } else {
                    strLabel = "-";
                }
                label.appendChild(doc.createTextNode(strLabel));
                vertex.appendChild(label);

                //Position
                Element position = doc.createElement("position");
                Element x = doc.createElement("x");
                Element y = doc.createElement("y");
                String strPosition;
                String posX = "-";
                String posY = "-";
                if (f != null && f.getCenterPoint() != null) {
                    posX = Double.toString(f.getCenterPoint().getX());
                    posY = Double.toString(f.getCenterPoint().getY());
                    //strPosition = f.getCenterPoint().toString();
                }
                //position.appendChild(doc.createTextNode("sdf"));
                //vertex.appendChild(position);
                
                x.appendChild(doc.createTextNode(posX));
                y.appendChild(doc.createTextNode(posY));
                position.appendChild(x);
                position.appendChild(y);
                vertex.appendChild(position);
                
//              position.appendChild(x.appendChild(doc.createTextNode(posX)));
//              position.appendChild(y.appendChild(doc.createTextNode(posY)));
                

            }





            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            //StreamResult result = new StreamResult(new File(System.out));

            // Output to console for testing
            StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        //Graph attributes
//        Iterator<Vertex<V>> it = g.vertices();
//        Vertex<V> v = null;
//        VertexFormat f = null;
//
//        //Vertices
//        while (it.hasNext()) {
//            v = it.next();
//            System.out.println("<Vertex>" + v.element() + "</Vertex>");
//
//            f = FormatHelper.getFormat(VertexFormat.class, v);
//            System.out.println(f.getLabel());
//
//            //f = (VertexFormat) FormatHelper.getFormat(v.getClass(), v);
//            //System.out.println(f.getColor());
//        }
//        //System.out.println("</Vertices>");
//
//        Iterator<Edge<E>> edgeIt = g.edges();
//        Edge<E> e = null;
//        EdgeFormat eFormat = null;
//
//        //Edges
//        while (edgeIt.hasNext()) {
//            e = edgeIt.next();
//
//        }

    }

    private String getGraphFormat(Graph g) {

        String activeColor = null;
        String visitedColor = null;

        GraphFormat gFormat = null;

        return gFormat.getActiveColor().toString();


    }

    public void importGraph(String filePath) {
    }
//
////Vertices
//		Iterator<Vertex<String>> it =  g.vertices();
//		Vertex<String> v = null;
//		VertexFormat f = null;
//		while(it.hasNext())
//		{
//			v = it.next();
//			f = FormatHelper.getFormat(v.getClass(), v);
//			
//		}
//		//Edges
//		g.incidentEdges(v);
}
    
    

