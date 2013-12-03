package logic;

import java.util.Observable;
import logic.extlib.Vertex;
import defs.GraphFormat;
import defs.ModelEventConstants;
import defs.VertexFormat;

public class VertexComponentModel<V> extends Observable {
	// Members
	private Vertex<V> vertex = null;
	private GraphFormat graphFormat = null;
	boolean isSelected = false;
	VertexFormat changedVertexFormat = null;

	// End of members

	// Constructors
	public VertexComponentModel(Vertex<V> vertex, GraphFormat graphFormat) {
		if (null == vertex || null == graphFormat) {
			throw new IllegalArgumentException(
					"one or more parameters invalid.");
		}

		this.vertex = vertex;
		this.graphFormat = graphFormat;
	}

	// End of constructors

	// Methods
	public Vertex<V> getVertex() {
		return vertex;
	}

	public GraphFormat getGraphFormat() {
		return graphFormat;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void addVertex() {
		setChanged();
		notifyObservers(ModelEventConstants.ADDVERTEXTOSELECTED);
	}

	public void deleteVertex() {
		setChanged();
		notifyObservers(ModelEventConstants.DELETESELECTEDVERTEX);
	}

	public VertexFormat getChangedVertexFormat() {
		return this.changedVertexFormat;
	}

	public void setChangedVertexFormat(VertexFormat newFormat) {
		this.changedVertexFormat = newFormat;
		setChanged();
		notifyObservers(ModelEventConstants.CHANGESELECTEDVERTEXFORMAT);
	}
	// End of methods
}
