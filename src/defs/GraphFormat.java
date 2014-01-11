package defs;

import java.awt.Color;

/**
 * 
 * @author Stephan_2
 */
public class GraphFormat {

	private boolean isLabelVisible;
	private boolean isDirected;
	private boolean isWeighted;
	private Color activeColor;
	private Color visitedColor;
	private Color unvisitedColor;

	// size of Vertex
	public static final int INNERCIRCLEDIAMETER = 30;
	public static final int OUTERCIRCLEDIAMETER = 40;
	public static final int LOCATIONCENTERMODIFIER = 20;
	public static final int SELECTEDVERTEXBORDERTHICKNESS = 2;
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

	public boolean isWeighted() {
		return isWeighted;
	}

	public void setWeighted(boolean weighted) {
		this.isWeighted = weighted;
	}

	public Color getColor(VertexFormat format) {
		// get the color according to the current state
		if (format.isActive()) {
			return getActiveColor();
		} else if (format.isVisited()) {
			return getVisitedColor();
		} else {
			return getUnvisitedColor();
		}
	}

	public Color getColor(EdgeFormat format) {
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
