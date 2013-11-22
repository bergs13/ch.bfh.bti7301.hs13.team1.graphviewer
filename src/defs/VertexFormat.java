/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package defs;

import java.awt.Point;
import java.awt.Color;

/**
 *
 * @author Stephan_2
 */
public class VertexFormat {

    // Members
    private Color activeColor;
    private Color visitedColor;
    private Color unvisitedColor;
    private Point centerPoint;
    private static GraphLayout layout;
    private String label;
    private boolean textVisible;
    private boolean active = false;
    private boolean visited = false;

    // End of members
    //Constant Values
    private static final GraphLayout STANDARD_LAYOUT = new StandardLayout();
    private static final int OUTERCIRCLEDIAMETER = 40;
    private static final int INNERCIRCLEDIAMETER = OUTERCIRCLEDIAMETER - 10;
    private static final int LOCATIONCENTERMODIFIER = OUTERCIRCLEDIAMETER / 2;

    // End of constant values
    //Constructors
    /**
     *
     * @param vertex
     * @param newlayout
     */
    public VertexFormat(GraphLayout newlayout) {
        setLayout(newlayout);
    }

    public VertexFormat() {
        setLayout(STANDARD_LAYOUT);
    }
    // End of Constructors

    // Getter and setter methods
    /**
     *
     * @param newLayout
     */
    public void setLayout(GraphLayout newLayout) {
        layout = newLayout;
        activeColor = layout.getActiveColor();
        visitedColor = layout.getVisitedColor();
        unvisitedColor = layout.getUnvisitedColor();
        textVisible = layout.getTextVisible();
        label = layout.getDefaultText();
        //just temorarily set to visited until implementation of traversation of algorithms
        this.setVisited();

    }

    public static int getLOCATIONCENTERMODIFIER() {
        return LOCATIONCENTERMODIFIER;
    }

    public static int getINNERCIRCLEDIAMETER() {
        return INNERCIRCLEDIAMETER;
    }

    public static int getOUTERCIRCLEDIAMETER() {
        return OUTERCIRCLEDIAMETER;
    }

    public static GraphLayout getLayout() {
        return layout;

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

    public boolean isTextVisible() {
        return textVisible;
    }

    public void setTextVisible(boolean textVisible) {
        this.textVisible = textVisible;
    }

    public void setUnvisited() {
        active = false;
        visited = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive() {
        this.active = true;
        this.visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        this.visited = true;
        this.active = false;
    }

    //get the color accroding to the current state
    public Color getColor() {
        if (active) {
            return activeColor;
        } else if (visited) {
            return visitedColor;
        } else {
            return unvisitedColor;
        }
    }

    

	// End of getters and setters
}
