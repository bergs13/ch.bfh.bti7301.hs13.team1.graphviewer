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
 * 
 */
public class VertexFormat {

    // Members
   
    private Point centerPoint;
    private String label;
    private boolean active;
    private boolean visited;   
    
    // End of members

   

    //Constructors
    /**
     *
     * 
     * 
     */

    public VertexFormat() {
        
    }
    // End of Constructors
    
    // Getter and setter methods
    /**
     *
     * 
     */
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
    //get the color according to the current state
   
}


