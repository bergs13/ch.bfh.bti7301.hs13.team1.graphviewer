/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package defs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Stephan_2
 */
public class EdgeFormat {

    private boolean isDirected;
    private boolean active;
    private boolean included;
    private String label;
    private Point fromPoint;
    private Point toPoint;
    private GraphLayout layout;
    private BasicStroke edgeStyle;
    private Color activeColor;
    private Color includedColor;
    private Color unvisitedColor;
    private boolean textVisible;
    private final int MAX_WIDTH = 20;

    public EdgeFormat(GraphLayout newLayout) {
        setLayout(newLayout);
    }

    /**
     *
     */
    public EdgeFormat() {
        setLayout(new StandardLayout());
    }

    //Start Getters and Setters
    public boolean isIsDirected() {
        return isDirected;
    }

    public void setIsDirected(boolean isDirected) {
        this.isDirected = isDirected;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
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

    public BasicStroke getEdgeStyle() {
        return edgeStyle;
    }

    public void setEdgeStyle(BasicStroke edgeStyle) {
        this.edgeStyle = edgeStyle;
    }

    public Color getActiveColor() {
        return activeColor;
    }

    public void setActiveColor(Color activeColor) {
        this.activeColor = activeColor;
    }

    public Color getIncludedColor() {
        return includedColor;
    }

    public void setIncludedColor(Color includedColor) {
        this.includedColor = includedColor;
    }

    public Color getUnvisitedColor() {
        return unvisitedColor;
    }

    public void setUnvisitedColor(Color unvisitedColor) {
        this.unvisitedColor = unvisitedColor;
    }

    public boolean isTextVisible() {
        return textVisible;
    }

    public void setTextVisible(boolean textVisible) {
        this.textVisible = textVisible;
    }
    //End Getters and Setters

    public final void setLayout(GraphLayout newLayout) {
        layout = newLayout;
        activeColor = layout.getActiveColor();
        includedColor = layout.getVisitedColor();
        unvisitedColor = layout.getUnvisitedColor();
        textVisible = layout.getTextVisible();
        label = layout.getDefaultText();
        edgeStyle = new BasicStroke(layout.getEdgeWidth());
        this.reset();
    }

    private void reset() {
        active = false;
        included = false;

    }

}
