package logic;

import java.awt.Point;
import java.util.Observable;
import defs.FormatHelper;
import defs.GraphFormat;
import defs.ModelEventConstants;
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
	Vertex<V> changedVertex = null;

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
		if (this.graph.isDirected()) {
			this.graphFormat.setDirected(true);
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
		return this.selectedVertex;
	}

	public void setSelectedVertex(Vertex<V> selectedVertex) {
		if (null == this.selectedVertex
				|| !this.selectedVertex.equals(selectedVertex)) {
			this.selectedVertex = selectedVertex;
			setChanged();
			notifyObservers(ModelEventConstants.VERTEXSELECTION);
		}
	}

	public Vertex<V> getChangedVertex() {
		return this.changedVertex;
	}

	public void setSelectedVertexFormat(VertexFormat newFormat) {
		// Check variables
		if (null == this.selectedVertex || null == newFormat) {
			return;
		}

		// Update Format (Decorable implementation notifies the gui
		this.selectedVertex.set(FormatHelper.FORMAT, newFormat);
	}

	// Graph manipulation Methods
	public void addVertex(Vertex<V> sourceVertex, VertexFormat format) {
		// Check variables
		if (null == sourceVertex) {
			return;
		}

		// Update data
		// create object
		V vElement = null;
		Vertex<V> vNew = this.graph.insertVertex(vElement);
		// set format
		if (null == format) {
			format = new VertexFormat();
		}
		// Place the new vertex under the source vertex
		VertexFormat sourceFormat = FormatHelper.getFormat(VertexFormat.class,
				sourceVertex);
		if (null != sourceFormat) {
			Point sourceCenter = sourceFormat.getCenterPoint();
			if (null != sourceCenter) {
				format.setCenterPoint(sourceCenter.x, sourceCenter.y + 2
						* GraphFormat.OUTERCIRCLEDIAMETER);
			}
		}
		vNew.set(FormatHelper.FORMAT, format);
		// connect via edge if has source (if there is no source, it will be
		// null)
		E eElement = null;
		this.graph.insertEdge(sourceVertex, vNew, eElement);

		// Update UI
		changedVertex = vNew;
		setChanged();
		notifyObservers(ModelEventConstants.VERTEXADDED);
	}

	public void connectVertices(Vertex<V> sourceVertex, Vertex<V> targetVertex) {
		// Check variables
		if (null == sourceVertex || null == targetVertex) {
			return;
		}

		// connect via new Edge
		E eElement = null;
		this.graph.insertEdge(sourceVertex, targetVertex, eElement);

		// Update UI
		changedVertex = sourceVertex; // For the edge recalculations
		setChanged();
		notifyObservers(ModelEventConstants.VERTEXCONNECTED);
	}

	public void deleteSelectedVertex() {
		// Check variables
		if (null == this.selectedVertex) {
			return;
		}

		// Update data
		// Remove Vertex
		this.graph.removeVertex(this.selectedVertex);

		// Update UI
		changedVertex = this.selectedVertex;
		setChanged();
		notifyObservers(ModelEventConstants.VERTEXDELETED);
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
		notifyObservers(ModelEventConstants.GRAPHFORMAT);
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
