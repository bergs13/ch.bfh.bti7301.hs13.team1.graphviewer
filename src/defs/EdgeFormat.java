package defs;

import java.awt.Point;

/**
 * 
 * @author Stephan_2
 * 
 */
public class EdgeFormat {
	private boolean active;
	private boolean visited;
	private Point fromPoint = new Point();
	private Point toPoint = new Point();

	public EdgeFormat() {
	}

	// Start Getters and Setters
	public boolean isActive() {
		return active;
	}

	public void setInactive()
	{
		this.active = false;
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

	public void setUnvisited() {
		this.visited = false;
	}

	public Point getFromPoint() {
		return fromPoint;
	}

	public void setFromPoint(int x, int y) {
		this.fromPoint.setLocation(x, y);
	}

	public Point getToPoint() {
		return toPoint;
	}

	public void setToPoint(int x, int y) {
		this.toPoint.setLocation(x, y);
	}
	// End Getters and Setters
}
