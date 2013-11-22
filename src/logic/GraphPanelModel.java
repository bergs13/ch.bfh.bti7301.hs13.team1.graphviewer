package logic;

import java.util.Observable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import defs.FormatHelper;
import defs.VertexFormat;
import logic.extlib.GraphExamples;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;

public class GraphPanelModel<V, E> extends Observable {
	// Members
	private IncidenceListGraph<V, E> graph;
	private GraphExamples<V, E> graphExamples;

	// End of Members

	// Constructors
	public GraphPanelModel(IncidenceListGraph<V, E> g) {
		this.graphExamples = new GraphExamples<V, E>();

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

	// Graph manipulation Methods
	public void addVertex(VertexFormat format) {
		// Update data
		// Check format
		if (format == null) {
			format = new VertexFormat();
		}
		// create object and set format
		V element = null;
		Vertex<V> vNew = this.graph.insertVertex(element);
		vNew.set(FormatHelper.FORMAT, format);

		// Update UI
		setChanged();
		notifyObservers(vNew);
	}

	public void updateVertex(Vertex<V> vertex, VertexFormat newFormat) {
		// Update data
		// Check format
		if (null == newFormat) {
			newFormat = FormatHelper.getFormat(VertexFormat.class, vertex);
			if (null == newFormat) {
				newFormat = new VertexFormat();
			}
		}
		// Update format
		vertex.set(FormatHelper.FORMAT, newFormat);

		// Update UI
		setChanged();
		notifyObservers(vertex);
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

	// Simulation methods
	public void applyAlgorithm(String key) {
		throw new NotImplementedException();
	}
	// End of simulation methods

	// End of Methods

}
