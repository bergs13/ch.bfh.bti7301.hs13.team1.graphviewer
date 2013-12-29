package logic;

import java.awt.Point;
import java.util.Observable;
import defs.GUICommandConstants;
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
	Vertex<V> selectedVertex = null;
	Vertex<V> changedVertex = null;
	private GraphDataProcessor<V, E> graphDataProcessor = new GraphDataProcessor<V, E>();
        private final AlgorithmDataProcessor algorithmDataProcessor = new AlgorithmDataProcessor();

	// End of Members

	// Constructors
	public GraphPanelModel(IncidenceListGraph<V, E> g) {
		this.graphExamples = new GraphExamples<V, E>();
                this.graphExamples.setRecorder(algorithmDataProcessor);
		// input graph?
		if (null != g) {
			setExternalGraph(g);
		} else {
			setNewGraph();
		}

	}

	// End of Constructors

	private void setNewGraph() {
		GraphFormat format = new GraphFormat();
		this.graph = new IncidenceListGraph<V, E>(format.isDirected());
		this.graph.set(FormatHelper.FORMAT, format);
	}

	private void setExternalGraph(IncidenceListGraph<V, E> g) {
		this.graph = g;
		GraphFormat format = FormatHelper.getFormat(GraphFormat.class,
				this.graph);
		if (null == format) {
			format = new GraphFormat();
		}
		format.setDirected(this.graph.isDirected());
		this.graph.set(FormatHelper.FORMAT, format);
	}

	// Methods
	public IncidenceListGraph<V, E> getGraph() {
		return this.graph;
	}

	public GraphFormat getGraphFormat() {
		GraphFormat f = FormatHelper.getFormat(GraphFormat.class, this.graph);
		if (null == f) {
			f = new GraphFormat();
		}
		return f;
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

	public void updateFormat(GraphFormat newFormat) {
		if (null == newFormat) {
			newFormat = FormatHelper.getFormat(GraphFormat.class, this.graph);
			if (null == newFormat) {
				newFormat = new GraphFormat();
			}
		}
		// observable implementation notifies the gui
		this.graph.set(FormatHelper.FORMAT, newFormat);
	}

	// End of graph manipulation methods

	// Main GUI handlers
	public void handleMainGUICommand(String gUICommandConstant, Object param) {
		// apply algorithms
		if (gUICommandConstant.equals(GUICommandConstants.DIJKSTRA)) {
                        this.algorithmDataProcessor.resetGraphList();
			this.graphExamples.dijkstra(this.graph, null);
		}
		// load/save/clear graph
		else if (gUICommandConstant.equals(GUICommandConstants.NEWGRAPH)) {
			setNewGraph();
		} else if (gUICommandConstant.equals(GUICommandConstants.LOADGRAPH)) {
			if (String.class.isInstance(param)) {
				IncidenceListGraph<V, E> g = graphDataProcessor
						.importGraph((String) param);
				if (null != g) {
					setExternalGraph(g);
				}
			}
		} else if (gUICommandConstant.equals(GUICommandConstants.SAVEGRAPH)) {
			if (String.class.isInstance(param)) {
				graphDataProcessor.exportGraph(this.graph, (String) param);
			}
		}
	}
	// Main GUI handlers

	// End of Methods

}
