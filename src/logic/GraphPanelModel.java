package logic;

import defs.FormatHelper;
import defs.VertexFormat;
import logic.extlib.GraphExamples;
import logic.extlib.IncidenceListGraph;
import logic.extlib.Vertex;

public class GraphPanelModel<V, E> extends GraphExamples<V, E> {
	// Members
	private IncidenceListGraph<V, E> graph;
	// End of Members

	// Constructors
	public GraphPanelModel(IncidenceListGraph<V, E> g) {
		this.graph = null != g ? g :  new IncidenceListGraph<V, E>(true);
	}

	// End of Constructors

	// Methods
	public Vertex<V> addVertex(VertexFormat format) {
		if (format == null) {
			format = new VertexFormat();
		}

		// create objects and set format
		V element = null;
		Vertex<V> vNew = this.graph.insertVertex(element);
		vNew.set(FormatHelper.FORMAT, format);

		return vNew;
	}

	public void updateVertex(Vertex<V> vertex, VertexFormat newFormat) {
		// Format updates
		if (null == newFormat) {
			newFormat = FormatHelper.getFormat(VertexFormat.class, vertex);
			if (null == newFormat) {
				newFormat = new VertexFormat();
			}
		}
		vertex.set(FormatHelper.FORMAT, newFormat);
	}

	public IncidenceListGraph<V, E> getGraph() {
		return this.graph;
	}
	// End of Methods

}
