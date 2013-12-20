package logic;

import java.util.Observable;
import logic.extlib.Vertex;
import defs.FormatHelper;
import defs.GraphFormat;
import defs.ModelEventConstants;
import defs.VertexFormat;

public class VertexComponentModel<V> extends Observable {
	// Members
	private Vertex<V> vertex = null;
	private GraphFormat graphFormat = null;
	boolean isSelected = false;

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

	public void updateGraphFormat(GraphFormat graphFormat)
	{
		this.graphFormat = graphFormat;
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

	public void connectVertices() {
		setChanged();
		notifyObservers(ModelEventConstants.CONNECTVERTEXTOSELECTED);
	}

	public void deleteVertex() {
		setChanged();
		notifyObservers(ModelEventConstants.DELETESELECTEDVERTEX);
	}

	public VertexFormat getFormat() {
		VertexFormat f = FormatHelper
				.getFormat(VertexFormat.class, this.vertex);
		if (null == f) {
			// Default-Format
			f = new VertexFormat();
		}
		return f;
	}

	public void updateFormat(VertexFormat newFormat) {
		// observable implementation notifies the gui
		this.vertex.set(FormatHelper.FORMAT, newFormat);
	}
	// End of methods
}
