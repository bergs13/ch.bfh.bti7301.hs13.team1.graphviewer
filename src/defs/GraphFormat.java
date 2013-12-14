package defs;

import java.awt.Color;

/**
 * 
 * @author Stephan_2
 */
public class GraphFormat {

	private boolean isLabelVisible;
	private boolean isDirected;
	private Color activeColor;
	private Color visitedColor;
	private Color unvisitedColor;
	private Color includedColor;
	private Color unincludedColor;

	// size of Vertex
	public static final int INNERCIRCLEDIAMETER = 30;
	public static final int OUTERCIRCLEDIAMETER = 40;
	public static final int LOCATIONCENTERMODIFIER = 20;

	public static final int ARROWTRIANGLEWIDTH = 20;
	public static final int ARROWTRIANGLEHEIGHT = 30;

	public GraphFormat(GraphLayout aLayout) {
		setLayout(aLayout);
	}

	public GraphFormat() {
		setLayout(new StandardLayout());
	}

	public final void setLayout(GraphLayout aLayout) {
		this.activeColor = aLayout.getActiveColor();
		this.visitedColor = aLayout.getVisitedColor();
		this.unvisitedColor = aLayout.getUnvisitedColor();
		this.isLabelVisible = aLayout.getLabelVisible();
		this.includedColor = aLayout.getIncludedEdgeColor();
		this.unincludedColor = aLayout.getUnincludedColor();
	}

	public Color getActiveColor() {
		return activeColor;
	}

	public void setActiveColor(Color activeColor) {
		this.activeColor = activeColor;
	}

	public Color getVisitedColor() {
		return visitedColor;
	}

	public void setVisitedColor(Color visitedColor) {
		this.visitedColor = visitedColor;
	}

	public Color getUnvisitedColor() {
		return unvisitedColor;
	}

	public void setUnvisitedColor(Color unvisitedColor) {
		this.unvisitedColor = unvisitedColor;
	}

	public Color getIncludedColor() {
		return includedColor;
	}

	public void setIncludedColor(Color includedColor) {
		this.includedColor = includedColor;
	}

	public boolean isLabelVisible() {
		return isLabelVisible;
	}

	public void setLabelVisible(boolean labelVisible) {
		this.isLabelVisible = labelVisible;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public void setDirected(boolean directed) {
		this.isDirected = directed;
	}

	public Color getUnincludedColor() {
		return unincludedColor;
	}

	public void setUnincludedColor(Color unincludedColor) {
		this.unincludedColor = unincludedColor;
	}

	public Color getVertexColor(VertexFormat format) {
		// get the color according to the current state
		if (format.isActive()) {
			return getActiveColor();
		} else if (format.isVisited()) {
			return getVisitedColor();
		} else {
			return getUnvisitedColor();
		}
	}
}
