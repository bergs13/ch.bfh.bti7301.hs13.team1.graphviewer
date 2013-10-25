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
public interface VertexLayout {
    Color getActiveColor();
    Color getVisitedColor();
    Color getUnvisitedColor();
    boolean getTextVisible();
    String getDefaultText();
    int getSize();
    
}
