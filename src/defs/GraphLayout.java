/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    boolean getTextVisible();
    String getDefaultText();
    int getVertexSize();
    float getEdgeWidth();
    int getBorderWidth();
    
}
