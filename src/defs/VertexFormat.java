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

    //variabels
    private Color activeColor;
    private Color visitedColor;
    private Color unvisitedColor;
    private Point centerPoint;
    private String label;
    private boolean textVisible;
    private boolean active = false;
    private boolean visited = false;
    private VertexLayout layout;
    private int size;
    private final int DEFAULT_SIZE = 40;
    private final int MAX_SIZE = 100;
    private final int MIN_SIZE = 10;

    /**
     *
     * @param newlayout
     */
    public VertexFormat(VertexLayout newlayout) {
        setLayout(newlayout);
    }

    public VertexFormat() {
        setLayout(new StandardLayout());
    }

    /**
     *
     * @param newLayout
     */
    public void setLayout(VertexLayout newLayout) {
        layout = newLayout;
        activeColor = layout.getActiveColor();
        visitedColor = layout.getVisitedColor();
        unvisitedColor = layout.getUnvisitedColor();
        textVisible = layout.getTextVisible();
        label = layout.getDefaultText();
        this.setSize(layout.getSize());

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
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

    public void setSize(int aSize) {
        if (aSize % 2 == 1) {
            aSize--;
        }
        if (aSize < MIN_SIZE || aSize > MAX_SIZE) {
            aSize = DEFAULT_SIZE;
        }
        this.size = aSize;

    }

    public int getSize() {
        return size;
    }

    //End of getters and setters
    public void reset() {
        active = false;
        visited = false;
    }

    private class StandardLayout implements VertexLayout {

        private final Color ACTIVECOLOR = new Color(255, 0, 0);
        private final Color VISITEDCOLOR = new Color(0, 0, 255);
        private final Color UNVISITEDCOLOR = new Color(0,0,0);
        private final int SIZE = 50;

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
        public boolean getTextVisible() {
            return true;
        }

        @Override
        public String getDefaultText() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getSize() {
            return size;
        }

    }
}
