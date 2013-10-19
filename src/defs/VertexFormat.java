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
    private Point centerPoint = new Point(100, 100);
    private String label;
    private boolean active = false;
    private boolean visited = false;
    
    //default values
    private static final Color DEFAULT_ACTIVE_COLOR = new Color(255, 0 ,0);
    private static final String DEFAULT_LABEL = "DV";
    
    public VertexFormat() {
        this.setDefault();
    }

    
    
    public void setDefault(){
        label= DEFAULT_LABEL;
        active = false;
        activeColor = DEFAULT_ACTIVE_COLOR;
        
        
    }
}
