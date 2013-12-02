package logic;

import java.util.Observable;
import defs.FormatHelper;
import defs.GraphFormat;
import defs.VertexFormat;
import logic.extlib.GraphExamples;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;

public class GraphPanelModel<V, E> extends Observable {
	// Members
	private IncidenceListGraph<V, E> graph;
	private GraphExamples<V, E> graphExamples;
	private GraphFormat graphFormat;
	Vertex<V> selectedVertex = null;

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

	public GraphFormat getGraphFormat() {
		return this.graphFormat;
	}

	public Vertex<V> getSelectedVertex() {
		return selectedVertex;
	}

	public void setSelectedVertex(Vertex<V> selectedVertex) {
		this.selectedVertex = selectedVertex;
		setChanged();
		notifyObservers("selection");
	}

	public VertexFormat getSelectedVertexFormat() {
		VertexFormat f = null;
		if (null != this.selectedVertex) {
			f = FormatHelper.getFormat(VertexFormat.class, this.selectedVertex);
		}
		if (null == f) {
			f = new VertexFormat();
		}
		return f;
	}

	public void setSelectedVertexFormat(VertexFormat newFormat) {
		if (null != this.selectedVertex && null != newFormat) {
			this.selectedVertex.set(FormatHelper.FORMAT, newFormat);

			// Update UI
			setChanged();
			notifyObservers(this.selectedVertex);
		}
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

	public void deleteSelectedVertex() {
		if (null != this.selectedVertex) {
			// Update data
			// Remove Vertex
			this.graph.removeVertex(this.selectedVertex);
			this.selectedVertex = null;

			// Update UI
			setChanged();
			notifyObservers(this.selectedVertex);
		}
	}

	// End of graph manipulation methods

	// Format updates
	public void updateGraphFormat(GraphFormat newFormat) {
		// Check and update format
		if (null == newFormat) {
			newFormat = this.graphFormat;
			if (null == newFormat) {
				newFormat = new GraphFormat();
			}
		}
		this.graphFormat = newFormat;

		// Update UI
		setChanged();
		notifyObservers(GraphFormat.class);
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
