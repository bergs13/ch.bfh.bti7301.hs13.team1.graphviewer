
package defs;

import java.awt.Color;

/**
 *
 * @author Stephan_2
 */
public class GraphFormat {

    public Color getInMSFColor() {
        return inMSFColor;
    }

    public void setInMSFColor(Color inMSFColor) {
        this.inMSFColor = inMSFColor;
    }
    
    private Color activeColor;
    private Color visitedColor;
    private Color unvisitedColor;
    private Color inMSFColor;
    private boolean labelVisible;
    
//size of Vertex 
    public static final int INNERCIRCLEDIAMETER = 30;
    public static final int OUTERCIRCLEDIAMETER = 40;
    public static final int LOCATIONCENTERMODIFIER = 20;
    
    public static final int ARROWTRIANGLEWIDTH = 20;
    public static final int ARROWTRIANGLEHEIGHT = 30;
    
    public GraphFormat(GraphLayout aLayout){
        setLayout(aLayout);
    }
    
    public GraphFormat(){
        setLayout(new StandardLayout());
    }
    
    public final void setLayout(GraphLayout aLayout){
        this.activeColor = aLayout.getActiveColor();
        this.visitedColor = aLayout.getVisitedColor();
        this.unvisitedColor= aLayout.getUnvisitedColor();
        this.labelVisible = aLayout.getLabelVisible();
        //this.inMFSColor = aLayout.ge
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

    public boolean isLabelVisible() {
        return labelVisible;
    }

    public void setLabelVisible(boolean labelVisible) {
        this.labelVisible = labelVisible;
    }
    
}
