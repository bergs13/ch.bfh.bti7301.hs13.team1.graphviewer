package logic;

import logic.extlib.IncidenceListGraph;

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
	}

	public String constructStringFromGraph(IncidenceListGraph<V, E> g) {
		return "";
	}

	public IncidenceListGraph<V, E> importGraph(String filePath) {
		// Get the String from the file
		String s = "";
		// Reconstruct the graph
		return reconstructGraphFromString(s);
	}

	public IncidenceListGraph<V, E> reconstructGraphFromString(String s) {
		IncidenceListGraph<V, E> g = new IncidenceListGraph<>(false);
		return g;
	}
	// End of Public Methods

	// End of Methods

}
