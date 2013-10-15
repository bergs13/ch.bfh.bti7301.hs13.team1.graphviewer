package demo;

import java.awt.Point;

public class DemoVertexFormat 
{
	private Point centerPos = new Point(100,100);
	private String label = "";
	private boolean active = false;
	
	public DemoVertexFormat()
	{
	}
	public DemoVertexFormat(Point centerPos, String label, boolean active)
	{
		this.setCenterPos(centerPos);
		this.setLabel(label);
		this.setActive(active);		
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Point getCenterPos() {
		return centerPos;
	}
	public void setCenterPos(Point centerPos) {
		this.centerPos = centerPos;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
