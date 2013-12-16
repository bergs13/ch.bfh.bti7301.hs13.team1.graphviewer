/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package defs;

import java.awt.Point;

/**
 * 
 * @author Stephan_2
 * 
 */
public class EdgeFormat {
	private boolean active;
	private boolean included;
	private String label;
	private Point fromPoint = new Point();
	private Point toPoint = new Point();
	private boolean isWeighted;

	public EdgeFormat() {
	}

	/**
	 * reset this edget to Start State
	 */
	private void reset() {
		active = false;
		included = false;

	}

	// Start Getters and Setters

	public boolean isWeighted() {
		return isWeighted;
	}

	public void setIsWeighted(boolean weighted) {
		this.isWeighted = weighted;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isInMSF() {
		return included;
	}

	public void setInMSF() {
		this.included = true;
		this.active = false;
	}

	public void setNotInMSF() {
		this.reset();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
