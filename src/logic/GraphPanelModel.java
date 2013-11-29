package logic;

import java.util.Iterator;
import java.util.Observable;
import defs.EdgeFormat;
import defs.FormatHelper;
import defs.VertexFormat;
import logic.extlib.Edge;
import logic.extlib.GraphExamples;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;

public class GraphPanelModel<V, E> extends Observable {
	// Members
	private IncidenceListGraph<V, E> graph;
	private GraphExamples<V, E> graphExamples;
	private static EdgePainter edgePainter;
	private GraphFormat graphFormat;

	// End of Members

	// Constructors
	public GraphPanelModel(IncidenceListGraph<V, E> g) {
		this.graphExamples = new GraphExamples<V, E>();
		this.graphFormat = new GraphFormat();
		
		// input graph?
		if (null != g) {
			this.graph = g;
		} else {
			this.graph = new IncidenceListGraph<V, E>(true);
		}
	}

	// End of Constructors

	// Methods
	public IncidenceListGraph<V, E> getGraph() {
		return this.graph;
	}

	public VertexFormat getGraphFormat() {
		return this.graphFormat;
	}

	// Graph manipulation Methods
	public void addVertex(Vertex<V> sourceVertex, VertexFormat format) {
		// Update data
		// create object
		V vElement = null;
		Vertex<V> vNew = this.graph.insertVertex(vElement);
		// set format
		if (null == format) {
			format = new VertexFormat();
		}
		vNew.set(FormatHelper.FORMAT, format);
		// connect via edge if has source (if there is no source, it will be
		// null)
		if (null != sourceVertex) {
			E eElement = null;
			this.graph.insertEdge(sourceVertex, vNew, eElement);
		}

		// Update UI
		setChanged();
		notifyObservers(vNew);
	}

	public void deleteVertex(Vertex<V> vertex) {
		// Update data
		// Remove Vertex
		this.graph.removeVertex(vertex);
		vertex = null;

		// Update UI
		setChanged();
		notifyObservers(vertex);
	}

	// End of graph manipulation methods

	// Format updates
	public void updateGraphFormat(GraphFormat newFormat) {
		// Check and update format
		if (null == newFormat) {
			newFormat = this.vertexFormat;
			if (null == newFormat) {
				newFormat = new VertexFormat();
			}
		}
		this.vertexFormat = newFormat;
		Iterator<Vertex<V>> itV = this.graph.vertices();
		while (itV.hasNext()) {
			itV.next().set(FormatHelper.FORMAT, this.vertexFormat);
		}

		// Update UI
		setChanged();
		notifyObservers(VertexFormat.class);
	}

	// End of format updates

	// Simulation methods
	public void applyAlgorithm(String key) {
		if (key.equals("DUMMY")) {
			this.graphExamples.dijkstra(this.graph, null);
		}
	}
	// End of simulation methods

	// End of Methods

}
