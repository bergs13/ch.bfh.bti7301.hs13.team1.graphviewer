package logic;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import defs.EdgeFormat;
import defs.FormatHelper;
import defs.GraphFormat;
import defs.ModelEventConstants;
import defs.VertexFormat;

import java.awt.Color;
import java.awt.Point;
import java.io.*;
import java.text.Format;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.SysexMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import logic.extlib.Edge;
import logic.extlib.IGLDecorable;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
            
            //STEFAN
            GraphFormat gf = FormatHelper.getFormat(GraphFormat.class,g);
            if(null == gf)
            {
            	gf = new GraphFormat();
            }
            //STEFAN
            
            //todo
            


            //Vertices
            Element vertices = doc.createElement("vertices");
            rootElement.appendChild(vertices);

            Iterator<Vertex<V>> it = g.vertices();

            while (it.hasNext()) {
                Vertex<V> v = null;
                VertexFormat f = null;

                v = it.next();
                f = FormatHelper.getFormat(VertexFormat.class, v);

                Element vertex = doc.createElement("vertex");
                vertices.appendChild(vertex);
                //vertex.setAttribute("name", v.element().toString());

                //Attributes
                //Name
                Element name = doc.createElement("name");
                String strName = "-";
                if (v.element() != null) {
                    strName = v.element().toString();
                }
                name.appendChild(doc.createTextNode(strName));
                vertex.appendChild(name);

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
            Element edges = doc.createElement("edges");
            rootElement.appendChild(edges);

            Iterator<Edge<E>> it2 = g.edges();  
            
            //STEFAN
            Vertex<V>[] vStartEnd = g.endVertices(it2.next());
//STEFAN

            
            while (it2.hasNext()) {
                Edge<E> e = null;
                EdgeFormat ef = null;

                e = it2.next();
                ef = FormatHelper.getFormat(EdgeFormat.class, e);

                Element edge = doc.createElement("edge");
                edges.appendChild(edge);

                //edge.setAttribute("name", e.element().toString());

                //Attributes
                //Name
                Element name = doc.createElement("name");
                String strName = "-";
                if (e.element() != null) {
                    strName = e.element().toString();
                }
                name.appendChild(doc.createTextNode(strName));
                edge.appendChild(name);

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

//          XMLSerializer serializer = new XMLSerializer(System.out, new OutputFormat(doc,"UTF-8", true));
//          serializer.serialize(doc);


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
        return reconstructGraphFromString(filePath);
    }

    public IncidenceListGraph<V, E> reconstructGraphFromString(String s) {

    	//STEFAN
    	  GraphFormat format = new GraphFormat();
    	  //Xml to format
    	  format.setDirected(true);
    	  format.setActiveColor(Color.red);
    	  //instantiate and set format
        IncidenceListGraph<V, E> g = new IncidenceListGraph<V,E>(format.isDirected());  
		g.set(FormatHelper.FORMAT, format);
        //STEFAN
        
        try {

            //Create xml-object  
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(s);

            //Graph attributes
            //todo
            


            //Vertices
            NodeList listOfVertices = doc.getElementsByTagName("vertex");
            int totalVertices = listOfVertices.getLength();

            Vertex<String> vi = null;

            for (int i = 0; i < totalVertices; i++) {

                Node vertexNode = listOfVertices.item(i);
                if (vertexNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element vertexElement = (Element) vertexNode;

                    //name
                    NodeList nameList = vertexElement.getElementsByTagName("name");
                    Element nameElement = (Element) nameList.item(0);
                    System.out.println(nameElement.getTextContent());

                    //label
                    NodeList labelList = vertexElement.getElementsByTagName("label");
                    Element labelElement = (Element) labelList.item(0);

                    //x-Position
                    NodeList xPositionList = vertexElement.getElementsByTagName("x");
                    Element xElement = (Element) xPositionList.item(0);
                    //Element yElement = (Element) positionList.item(1);
                    System.out.println(xElement.getTextContent());

                    //y-Position
                    NodeList yPositionList = vertexElement.getElementsByTagName("y");
                    Element yElement = (Element) yPositionList.item(0);
                    System.out.println(yElement.getTextContent());

                    //Visited
                    NodeList visitedList = vertexElement.getElementsByTagName("visited");
                    Element visitedElement = (Element) visitedList.item(0);
                    System.out.println(visitedElement.getTextContent());

                    //Active
                    NodeList activeList = vertexElement.getElementsByTagName("active");
                    Element activeElement = (Element) activeList.item(0);
                    System.out.println(activeElement.getTextContent());

                }

            }

            //Edges
            NodeList listOfEdges = doc.getElementsByTagName("edges");
            int totalEdges = listOfEdges.getLength();

            Edge<String> ei = null;

            for (int i = 0; i < totalEdges; i++) {

                Node edgeNode = listOfEdges.item(i);
                if (edgeNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element edgeElement = (Element) edgeNode;

                    //name
                    NodeList nameList = edgeElement.getElementsByTagName("name");
                    Element nameElement = (Element) nameList.item(0);
                    System.out.println(nameElement.getTextContent());

                    //label
                    NodeList labelList = edgeElement.getElementsByTagName("label");
                    Element labelElement = (Element) labelList.item(0);

                    //fromPoint
                    NodeList fromPointList = edgeElement.getElementsByTagName("fromPoint");
                    if (fromPointList.getLength() > 0) {
                        NodeList fromPoints = fromPointList.item(0).getChildNodes();
                        Element xFromPointElement = (Element) fromPoints.item(0);   //x-Position
                        Element yFromPointElement = (Element) fromPoints.item(1);   //y-Position
                        System.out.println(xFromPointElement.getTextContent());
                        System.out.println(yFromPointElement.getTextContent());
                    }

                    //toPoint
                    NodeList toPointList = edgeElement.getElementsByTagName("toPoint");
                    if (toPointList.getLength() > 0) {
                        NodeList toPoints = toPointList.item(0).getChildNodes();
                        Element xToPointElement = (Element) toPoints.item(0);   //x-Position
                        Element yToPointElement = (Element) toPoints.item(1);   //y-Position
                    }

                    //weighted
                    NodeList weightedList = edgeElement.getElementsByTagName("weighted");
                    Element weightedElement = (Element) weightedList.item(0);

                }
            }

            return g;




        } catch (SAXException ex) {
            Logger.getLogger(GraphDataProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GraphDataProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(GraphDataProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
//STEFAN
////Graph manipulation Methods
//	public void addVertex(Vertex<V> sourceVertex, VertexFormat format) {
//		// Check variables
//		if (null == sourceVertex) {
//			return;
//		}
//
//		// Update data
//		// create object
//		V vElement = null;
//		Vertex<V> vNew = this.graph.insertVertex(vElement);
//		// set format
//		if (null == format) {
//			format = new VertexFormat();
//		}
//		// Place the new vertex under the source vertex
//		VertexFormat sourceFormat = FormatHelper.getFormat(VertexFormat.class,
//				sourceVertex);
//		if (null != sourceFormat) {
//			Point sourceCenter = sourceFormat.getCenterPoint();
//			if (null != sourceCenter) {
//				format.setCenterPoint(sourceCenter.x, sourceCenter.y + 2
//						* GraphFormat.OUTERCIRCLEDIAMETER);
//			}
//		}
//		vNew.set(FormatHelper.FORMAT, format);
//		// connect via edge if has source (if there is no source, it will be
//		// null)
//		E eElement = null;
//		this.graph.insertEdge(sourceVertex, vNew, eElement);
//
//	}
//STEFAN
