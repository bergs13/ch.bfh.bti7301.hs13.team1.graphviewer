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
    private boolean textVisible;
    private boolean active = false;
    private boolean visited = false;
    private VertexLayout layout;
    
    
    
    public VertexFormat(VertexLayout newlayout) {
        setLayout(newlayout);
    }
    //delayed until StandartLayout has been defined
    //public VertexFormat(){
    //this(StandarLayout);
    //}
    
    /**
     * 
     * @param newLayout 
     */
    public void setLayout(VertexLayout newLayout){
        layout = newLayout;
        activeColor = layout.getActiveColor();
        visitedColor = layout.getVisitedColor();
        unvisitedColor = layout.getUnvisitedColor();
        textVisible= layout.getTextVisible();
        label = layout.getDefaultText();
        
    }
}
