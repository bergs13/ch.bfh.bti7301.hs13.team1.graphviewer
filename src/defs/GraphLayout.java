package defs;

import java.awt.Color;

/**
 * 
 * @author Stephan_2
 */
public interface GraphLayout {
	Color getActiveColor();

	Color getVisitedColor();

	Color getUnvisitedColor();

	boolean getLabelVisible();

	String getDefaultText();
}
