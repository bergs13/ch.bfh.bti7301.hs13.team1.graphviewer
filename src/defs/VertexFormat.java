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
@SuppressWarnings("serial")
public class VertexFormat {

    //variabels
    private Color activeColor;
    private Color visitedColor;
    private Color unvisitedColor;
    private Point centerPoint;
    private String label;
    private boolean textVisible;
    private boolean active;
    private boolean visited;
    private GraphLayout layout;
    private int size;
    private final int DEFAULT_SIZE = 40;
    private final int MAX_SIZE = 80;
    private final int MIN_SIZE = 20;

    /**
     *
     * @param newlayout
     */
    public VertexFormat(GraphLayout newlayout) {
        setLayout(newlayout);
    }

    public VertexFormat() {
        setLayout(new StandardLayout());
    }

    /**
     *
     * @param newLayout
     */
    public final void setLayout(GraphLayout newLayout) {
        layout = newLayout;
        activeColor = layout.getActiveColor();
        visitedColor = layout.getVisitedColor();
        unvisitedColor = layout.getUnvisitedColor();
        textVisible = layout.getTextVisible();
        label = layout.getDefaultText();
        this.setSize();
        this.setUnvisited();

    }

    //Getter and setter methods
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

    public Color getColor() {
        if (active) {
            return activeColor;
        } else if (visited) {
            return visitedColor;
        } else {
            return unvisitedColor;
        }
    }

    public void setSize() {
        this.size = this.layout.getVertexSize();
        if (size % 2 == 1) {
            size++;
        }
        
        if (size < MIN_SIZE || size > MAX_SIZE) {
            this.size = DEFAULT_SIZE;
        }
        

    }

    public int getSize() {
        return size;
    }

    //End of getters and setters
}
