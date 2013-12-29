package logic;

import logic.extlib.IncidenceListGraph;

public class MainGUIModel<V, E> {
	GraphPanelModel<V, E> graphPanelModel = null;

	public MainGUIModel(IncidenceListGraph<V, E> g) {
		this.graphPanelModel = new GraphPanelModel<V, E>(g);
	}

	public GraphPanelModel<V, E> getGraphPanelModel() {
		return this.graphPanelModel;
	}
}