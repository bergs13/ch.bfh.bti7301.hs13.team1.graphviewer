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
    private boolean textVisible;
    
    private String label;
    private Point fromPoint = new Point();
    private Point toPoint = new Point();
    private GraphLayout layout;
    private BasicStroke edgeStyle;
    private Color activeColor;
    private Color includedColor;
    private Color unincludedColor;

    private final float MAX_WIDTH = 20F;
    private final float MIN_WIDTH = 2F;
    private final float DEFAULT_WIDTH = 4F;
    
    //Constant Values
    public static final Object FORMAT = new Object();
    //End of constant values

    
    public EdgeFormat(GraphLayout newLayout) {
        setLayout(newLayout);
    }

    /**
     *
     */
    public EdgeFormat() {
        setLayout(new StandardLayout());
    }

    public final void setLayout(GraphLayout newLayout) {
        layout = newLayout;
        activeColor = layout.getActiveColor();
        includedColor = layout.getVisitedColor();
        unincludedColor = layout.getUnvisitedColor();
        textVisible = layout.getTextVisible();
        //label = layout.getDefaultText();
        edgeStyle = new BasicStroke(this.setWidth(layout.getEdgeWidth()));
        this.reset();
    }
   /**
    * reset this edget to Start State
    */
    private void reset() {
        active = false;
        included = false;

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

    public Color getUnincludedColor() {
        return unincludedColor;
    }

    public void setUnincludedColor(Color unincludedColor) {
        this.unincludedColor = unincludedColor;
    }

    public boolean isTextVisible() {
        return textVisible;
    }

    public void setTextVisible(boolean textVisible) {
        this.textVisible = textVisible;
    }

    private float setWidth(float width) {
        if (width < MIN_WIDTH || width > MAX_WIDTH) {
            return DEFAULT_WIDTH;
        }
        return width;
    }

    //End Getters and Setters
}
