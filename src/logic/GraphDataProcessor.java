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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.SysexMessage;
import javax.swing.JOptionPane;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ui.controls.GraphPanel;
import ui.controls.VertexComponent;

public class GraphDataProcessor<V, E> {

    // Constructors
    public GraphDataProcessor() {
    }

    // Methods
    // Public Methods
    public void exportGraph(IncidenceListGraph<V, E> g, String filePath) {

        // Get the String for the export
        String stringToExport = constructStringFromGraph(g);

        // Pfad prüfen
        File f = new File(filePath);
        if (f.exists()) {
           // Export the string into the file specified
            FileWriter fw = null;

            try {   //File schreiben
                fw = new FileWriter(filePath);
                fw.write(stringToExport);  // text ist ein String
                
                //Erfolgsmeldung
                JOptionPane.showMessageDialog(null,
                    "Der Graph wurde erfolgreich gespeichert.",
                    "Erfolg",
                    JOptionPane.WARNING_MESSAGE);
                
                
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
        } else {
            //Fehlermeldung
            JOptionPane.showMessageDialog(null,
                    "Der angegebene Pfad wurde nicht gefunden.",
                    "Fehler",
                    JOptionPane.WARNING_MESSAGE);
        }


    }

    public String constructStringFromGraph(IncidenceListGraph<V, E> g) {

        try {
            //Xml-Parser
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //Global graph attributes

            //if graph has no format -> set default graph format 
            GraphFormat gf = FormatHelper.getFormat(GraphFormat.class, g);
            if (null == gf) {
                gf = new GraphFormat();
            }

            // root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("graph");
            doc.appendChild(rootElement);

            //graph attributes
            Element graphAttributes = doc.createElement("graphAttributes");
            rootElement.appendChild(graphAttributes);

            //isDirected
            Element isDirected = doc.createElement("isDirected");
            String strIsDirected = "false";
            if (gf.isDirected() != false) {
                strIsDirected = "true";
            }
            isDirected.appendChild(doc.createTextNode(strIsDirected));
            graphAttributes.appendChild(isDirected);

            //active color
            Element activeColor = doc.createElement("activeColor");
            String strActiveColor = "-";
            if (gf.getActiveColor() != null) {
                strActiveColor = Integer.toString(gf.getActiveColor().getRed());
                strActiveColor += ";" + Integer.toString(gf.getActiveColor().getGreen());
                strActiveColor += ";" + Integer.toString(gf.getActiveColor().getBlue());
            }
            activeColor.appendChild(doc.createTextNode(strActiveColor));
            graphAttributes.appendChild(activeColor);

            //isWeighted
            Element weighted = doc.createElement("isWeighted");
            String strWeighted = "no";
            if(gf.isWeighted()){
                strWeighted = "true";
            }
            weighted.appendChild(doc.createTextNode(strWeighted));
            graphAttributes.appendChild(weighted);
            
            //visitedColor
            Element visitedColor = doc.createElement("visitedColor");
            String strVisitedColor = "-";
            if(gf.getVisitedColor() != null){
                strVisitedColor = Integer.toString(gf.getVisitedColor().getRed());
                strVisitedColor += ";" + Integer.toString(gf.getVisitedColor().getGreen());
                strVisitedColor += ";" + Integer.toString(gf.getVisitedColor().getBlue());
            }
            visitedColor.appendChild(doc.createTextNode(strVisitedColor));
            graphAttributes.appendChild(visitedColor);
            
            //unvisitedColor
            Element unvisitedColor = doc.createElement("unvisitedColor");
            String strUnvisitedColor = "-";
            if(gf.getUnvisitedColor() != null){
                strUnvisitedColor = Integer.toString(gf.getUnvisitedColor().getRed());
                strUnvisitedColor += ";" + Integer.toString(gf.getUnvisitedColor().getGreen());
                strUnvisitedColor += ";" + Integer.toString(gf.getUnvisitedColor().getBlue());
            }
            unvisitedColor.appendChild(doc.createTextNode(strUnvisitedColor));
            graphAttributes.appendChild(unvisitedColor);
            
            //includedColor
            Element includedColor = doc.createElement("includedColor");
            String strIncludedColor = "-";
            if(gf.getIncludedColor() != null){
                strIncludedColor = Integer.toString(gf.getIncludedColor().getRed());
                strIncludedColor += ";" + Integer.toString(gf.getIncludedColor().getGreen());
                strIncludedColor += ";" + Integer.toString(gf.getIncludedColor().getBlue());
            }
            includedColor.appendChild(doc.createTextNode(strIncludedColor));
            graphAttributes.appendChild(includedColor);
            
            //unincludedColor
            Element unincludedColor = doc.createElement("unincludedColor");
            String strUnincludedColor = "-";
            if(gf.getUnincludedColor() != null){
                strUnincludedColor = Integer.toString(gf.getUnincludedColor().getRed());
                strUnincludedColor += ";" + Integer.toString(gf.getUnincludedColor().getGreen());
                strUnincludedColor += ";" + Integer.toString(gf.getUnincludedColor().getBlue());
            }
            unincludedColor.appendChild(doc.createTextNode(strUnincludedColor));
            graphAttributes.appendChild(unincludedColor);

            //Edges
            Element edges = doc.createElement("edges");
            rootElement.appendChild(edges);

            Iterator<Edge<E>> it2 = g.edges();

            Map<Vertex<V>, Integer> map = new HashMap<Vertex<V>, Integer>();

            int j = 1;      //counter
            int id = -1;    //vertex-id

            while (it2.hasNext()) {
                int sourceV = -1;
                int targetV = -1;

                Edge<E> e = it2.next();
                Vertex<V>[] vStartEnd = g.endVertices(e);
                for (int i = 0; i < vStartEnd.length; i++) {
                    if (!map.containsKey(vStartEnd[i])) {
                        map.put(vStartEnd[i], j);
                        //Vertices in Xml hier hinzufügen!?
                        id = j;
                        j++;
                    } else {
                        id = map.get(vStartEnd[i]);
                    }
                    //check if source or target vertex
                    if (g.isDirected()) {
                        if (g.destination(e).equals(vStartEnd[i])) {
                            targetV = id;
                        } else {
                            sourceV = id;
                        }
                    }
                }

                EdgeFormat ef = null;
                ef = FormatHelper.getFormat(EdgeFormat.class, e);

                Element edge = doc.createElement("edge");
                edges.appendChild(edge);

                //source vertex
                Element sourceVertex = doc.createElement("sourceVertex");
                String strSourceVertex = "-";
                if (sourceV > -1) {
                    strSourceVertex = Integer.toString(sourceV);
                }
                sourceVertex.appendChild(doc.createTextNode(strSourceVertex));
                edge.appendChild(sourceVertex);

                //target vertex
                Element targetVertex = doc.createElement("targetVertex");
                String strTargetVertex = "-";
                if (targetV > -1) {
                    strTargetVertex = Integer.toString(targetV);
                }
                targetVertex.appendChild(doc.createTextNode(strTargetVertex));
                edge.appendChild(targetVertex);

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
//                if (ef != null && ef.getLabel() != null) {
//                    strLabel = ef.getLabel().toString();
//                }
                label.appendChild(doc.createTextNode(strLabel));
                edge.appendChild(label);

//                //fromPoint
//                Element fromPoint = doc.createElement("fromPoint");
//                Element xFromPoint = doc.createElement("x");
//                Element yFromPoint = doc.createElement("y");
//                String fPosX = "-";
//                String fPosY = "-";
//                if (ef != null && ef.getFromPoint() != null) {
//                    
////                    fPosX = Double.toString(ef.getFromPoint().getX());
////                    fPosY = Double.toString(ef.getFromPoint().getY());
//                    
//                    fPosX = "" + ef.getFromPoint().x;
//                    fPosY = "" + ef.getFromPoint().y;
//                    
//                    
//                }
//                xFromPoint.appendChild(doc.createTextNode(fPosX));
//                yFromPoint.appendChild(doc.createTextNode(fPosY));
//                fromPoint.appendChild(xFromPoint);
//                fromPoint.appendChild(yFromPoint);
//
//                //toPoint
//                Element toPoint = doc.createElement("toPoint");
//                Element xToPoint = doc.createElement("x");
//                Element yToPoint = doc.createElement("y");
//
//                String tPosX = "-";
//                String tPosY = "-";
//                if (ef != null && ef.getToPoint() != null) {
//                    tPosX = Double.toString(ef.getToPoint().getX());
//                    tPosY = Double.toString(ef.getToPoint().getY());
//                }
//                xToPoint.appendChild(doc.createTextNode(tPosX));
//                yToPoint.appendChild(doc.createTextNode(tPosY));
//                toPoint.appendChild(xToPoint);
//                toPoint.appendChild(yToPoint);
//
//                //isWeighted?
//                Element weighted = doc.createElement("weighted");
//                String strWeighted = "no";

//                if (ef != null && ef.isWeighted() == true) {
//                    strWeighted = "yes";
//                }
//                weighted.appendChild(doc.createTextNode(strWeighted));
//                edge.appendChild(weighted);
            }

            //todo: evtl. Startknoten "markieren"

            //Vertices (iterate via map)
            Element vertices = doc.createElement("vertices");
            rootElement.appendChild(vertices);

            for (Vertex<V> v : map.keySet()) {
                VertexFormat f = null;

                int vertexID = map.get(v);
                //v = it.next();
                f = FormatHelper.getFormat(VertexFormat.class, v);

                Element vertex = doc.createElement("vertex");
                vertices.appendChild(vertex);

                //vertex-ID
                Element vID = doc.createElement("vertexID");
                String strVertexID = "-";
                if (vertexID > -1) {
                    strVertexID = Integer.toString(vertexID);
                }
                vID.appendChild(doc.createTextNode(strVertexID));
                vertex.appendChild(vID);

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
                    posX = "" + (f.getCenterPoint().x);
                    posY = "" + (f.getCenterPoint().y);
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



            // Transform XML object to string
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.toString();

//          XMLSerializer serializer = new XMLSerializer(System.out, new OutputFormat(doc,"UTF-8", 
//true));
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

        File file = new File(filePath);

        if (!file.canRead() || !file.isFile()) {
            System.exit(0);
        }

        FileReader fr = null;
        int c;
        StringBuffer buff = new StringBuffer();
        try {
            fr = new FileReader(file);
            while ((c = fr.read()) != -1) {
                buff.append((char) c);
            }
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        s = buff.toString();
        //System.out.println(s);

        // Reconstruct the graph
        return reconstructGraphFromString(s);
    }

    public IncidenceListGraph<V, E> reconstructGraphFromString(String s) {


        try {

            //  
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(s.toString());
            Document doc = builder.parse(new InputSource(new ByteArrayInputStream(s.getBytes("utf-8"))));
            // new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8")))

            //Graph attributes
            GraphFormat format = new GraphFormat();

            //read graph-attributes
            NodeList graphAttributes = doc.getElementsByTagName("graphAttributes");
            Element graphAtt = (Element) graphAttributes.item(0);

            //isDirected
            NodeList isDirectedList = graphAtt.getElementsByTagName("isDirected");
            Element isDirectedElement = (Element) isDirectedList.item(0);
            if ("true".equals(isDirectedElement.getTextContent())) {
                format.setDirected(true);
            } else {
                format.setDirected(false);
            }

            //activeColor
            NodeList activeColorList = graphAtt.getElementsByTagName("activeColor");
            Element activeColorElement = (Element) activeColorList.item(0);

            String[] split = activeColorElement.getTextContent().split(";");
            int red = Integer.parseInt(split[0]);
            int green = Integer.parseInt(split[1]);
            int blue = Integer.parseInt(split[2]);
            Color actColor = new Color(red, green, blue);

            format.setActiveColor(actColor);

            //...

            //instantiate and set format
            IncidenceListGraph<V, E> g = new IncidenceListGraph<V, E>(format.isDirected());
            g.set(FormatHelper.FORMAT, format);


            Map<Integer, Vertex<V>> map = new HashMap<Integer, Vertex<V>>();


            //Vertices
            NodeList listOfVertices = doc.getElementsByTagName("vertex");
            int totalVertices = listOfVertices.getLength();

            Vertex<String> vi = null;
            for (int i = 0; i < totalVertices; i++) {

                Node vertexNode = listOfVertices.item(i);
                if (vertexNode.getNodeType() == Node.ELEMENT_NODE) {

                    //add vertex
                    // create object
                    V vElement = null;
                    Vertex<V> vNew = g.insertVertex(vElement);
//                   // vertex format
                    VertexFormat vFi = new VertexFormat();

                    Element vertexElement = (Element) vertexNode;

                    //vertex-ID
                    NodeList vertexIDList = vertexElement.getElementsByTagName("vertexID");
                    Element vertexID = (Element) vertexIDList.item(0);

                    //x-Position
                    NodeList xPositionList = vertexElement.getElementsByTagName("x");
                    Element xElement = (Element) xPositionList.item(0);

                    //y-Position
                    NodeList yPositionList = vertexElement.getElementsByTagName("y");
                    Element yElement = (Element) yPositionList.item(0);

                    vFi.setCenterPoint(Integer.parseInt(xElement.getTextContent()), Integer.parseInt(yElement.getTextContent()));

                    vNew.set(FormatHelper.FORMAT, vFi);

                    map.put(Integer.parseInt(vertexID.getTextContent()), vNew);

//                    //name
//                    NodeList nameList = vertexElement.getElementsByTagName("name");
//                    Element nameElement = (Element) nameList.item(0);
//                    System.out.println(nameElement.getTextContent());
//
//                    //label
//                    NodeList labelList = vertexElement.getElementsByTagName("label");
//                    Element labelElement = (Element) labelList.item(0);

//                    //Visited
//                    NodeList visitedList = vertexElement.getElementsByTagName("visited");
//                    Element visitedElement = (Element) visitedList.item(0);
//                    System.out.println(visitedElement.getTextContent());
//
//                    //Active
//                    NodeList activeList = vertexElement.getElementsByTagName("active");
//                    Element activeElement = (Element) activeList.item(0);
//                    System.out.println(activeElement.getTextContent());

                }
//
            }



            //Edges
            NodeList listOfEdges = doc.getElementsByTagName("edge");
            int totalEdges = listOfEdges.getLength();

            Edge<String> ei = null;

            for (int i = 0; i < totalEdges; i++) {
                Node edgeNode = listOfEdges.item(i);
                if (edgeNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element edgeElement = (Element) edgeNode;

                    E eElement = null;
                    EdgeFormat eF = new EdgeFormat();

                    // eF.setActive(true);

                    //sourcePoint
                    NodeList sourcePointList = edgeElement.getElementsByTagName("sourceVertex");
                    Element sourcePointElement = (Element) sourcePointList.item(0);
                    int sourceP = Integer.parseInt(sourcePointElement.getTextContent());

                    //targetPoint
                    NodeList targetPointList = edgeElement.getElementsByTagName("targetVertex");
                    Element targetPointElement = (Element) targetPointList.item(0);
                    int targetP = Integer.parseInt(targetPointElement.getTextContent());

                    eF.setFromPoint(sourceP, targetP);

                    //Vertex aus Map holen
                    Vertex<V> sourceVertex = null;
                    Vertex<V> targetVertex = null;
                    if (map.containsKey(sourceP)) {
                        sourceVertex = map.get(sourceP);
                    }
                    if (map.containsKey(targetP)) {
                        targetVertex = map.get(targetP);
                    }

                    if (null != sourceVertex && null != targetVertex) {
                        Edge<E> eNew = g.insertEdge(sourceVertex, targetVertex, eElement);
                        eNew.set(FormatHelper.FORMAT, eF);
                    }


//                    //name
//                    NodeList nameList = edgeElement.getElementsByTagName("name");
//                    Element nameElement = (Element) nameList.item(0);
//                    System.out.println(nameElement.getTextContent());
//
//                    //label
//                    NodeList labelList = edgeElement.getElementsByTagName("label");
//                    Element labelElement = (Element) labelList.item(0);




//                    //weighted
//                    NodeList weightedList = edgeElement.getElementsByTagName("weighted");
//                    Element weightedElement = (Element) weightedList.item(0);

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
