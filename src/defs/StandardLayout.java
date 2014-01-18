package defs;

import java.awt.Color;

/**
 * 
 * @author Stephan_2
 */
public class StandardLayout implements GraphLayout {

	private static final Color ACTIVECOLOR = new Color(255, 0, 0);
	private static final Color VISITEDCOLOR = new Color(0, 0, 255);
	private static final Color UNVISITEDCOLOR = new Color(0, 0, 0);
	private static final boolean lableVisible = true;

	public StandardLayout() {

	}

	@Override
	public Color getActiveColor() {
		return ACTIVECOLOR;
	}

	@Override
	public Color getVisitedColor() {
		return VISITEDCOLOR;
	}

	@Override
	public Color getUnvisitedColor() {
		return UNVISITEDCOLOR;
	}

	@Override
	public boolean getLabelVisible() {
		return lableVisible;
	}

	@Override
	public String getDefaultText() {
		return "default";
	}
}
