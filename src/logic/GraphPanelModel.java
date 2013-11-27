package logic;

import java.util.Iterator;
import java.util.Observable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
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
	private VertexFormat vertexFormat;
	private EdgeFormat edgeFormat;

	// End of Members

	// Constructors
	public GraphPanelModel(IncidenceListGraph<V, E> g) {
		this.graphExamples = new GraphExamples<V, E>();

		// input graph?
		if (null != g) {
			this.graph = g;
			if (g.vertices().hasNext()) {
				this.vertexFormat = FormatHelper.getFormat(VertexFormat.class,
						g.vertices().next());
				if (null == this.vertexFormat) {
					this.vertexFormat = new VertexFormat();
				}
			}
			if (g.edges().hasNext()) {
				this.edgeFormat = FormatHelper.getFormat(EdgeFormat.class, g
						.edges().next());
				if (null == this.edgeFormat) {
					this.edgeFormat = new EdgeFormat();
				}
			}
		} else {
			this.graph = new IncidenceListGraph<V, E>(true);
			this.vertexFormat = new VertexFormat();
			this.edgeFormat = new EdgeFormat();
		}
	}

	// End of Constructors

	// Methods
	public IncidenceListGraph<V, E> getGraph() {
		return this.graph;
	}
	public VertexFormat getVertexFormat()
	{
		return this.vertexFormat;
	}
	public EdgeFormat getEdgeFormat()
	{
		return this.edgeFormat;
	}
	
	// Graph manipulation Methods
	public void addVertex(Vertex<V> sourceVertex) {
		// Update data
		// create object
		V vElement = null;
		Vertex<V> vNew = this.graph.insertVertex(vElement);
		// set format
		vNew.set(FormatHelper.FORMAT, this.vertexFormat);
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

	public void updateVertex(Vertex<V> vertex) {

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

	// Format updates
	public void updateVertexFormat(VertexFormat newFormat) {
		// Check and update format
		if (null == newFormat) {
			newFormat = this.vertexFormat;
			if (null == newFormat) {
				newFormat = new VertexFormat();
			}
		}
		this.vertexFormat = newFormat;
		Iterator<Vertex<V>> itV = this.graph.vertices();
		while(itV.hasNext())
		{
			itV.next().set(FormatHelper.FORMAT, this.vertexFormat);
		}
		
		// Update UI
		setChanged();
		notifyObservers(VertexFormat.class);
	}

	public void updateEdgeFormat(EdgeFormat newFormat) {
		// Check and update format
		if (null == newFormat) {
			newFormat = this.edgeFormat;
			if (null == newFormat) {
				newFormat = new EdgeFormat();
			}
		}
		this.edgeFormat = newFormat;
		Iterator<Edge<E>> itE = this.graph.edges();
		while(itE.hasNext())
		{
			itE.next().set(FormatHelper.FORMAT, this.edgeFormat);
		}
		
		// Update UI
		setChanged();
		notifyObservers(EdgeFormat.class);
	}

	// End of graph manipulation methods

	// Simulation methods
	public void applyAlgorithm(String key) {
		throw new NotImplementedException();
	}
	// End of simulation methods

	// End of Methods

}
