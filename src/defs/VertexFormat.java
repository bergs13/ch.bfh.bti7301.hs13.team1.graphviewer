package defs;

import java.awt.Point;

/**
 * 
 * @author Stephan_2
 * 
 */
public class VertexFormat {

	// Members

	private Point centerPoint = new Point();
	private String label;
	private boolean active;
	private boolean visited;
	private boolean hasDistance;
	private Object distance;

	// End of members

	// Constructors
	/**
     *
     * 
     * 
     */

	public VertexFormat() {

	}

	// End of Constructors

	// Getter and setter methods
	/**
     *
     * 
     */
	public Point getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(int x, int y) {
		this.centerPoint.setLocation(x, y);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setUnvisited() {
		visited = false;
	}

	public void setInactive() {
		this.active = false;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive() {
		this.active = true;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited() {
		this.visited = true;
	}

	public boolean hasDistance() {
		return this.hasDistance;
	}

	public void setDistance(Object distance) {
		this.hasDistance = null != distance;
		this.distance = distance;
	}

	public Object getDistance() {
		return this.distance;
	}
	// get the color according to the current state

}
