/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package defs;

import java.awt.Color;

/**
 *
 * @author Stephan_2
 */
public class StandardLayout implements GraphLayout {

    private final Color ACTIVECOLOR = new Color(255, 0, 0);
    private final Color VISITEDCOLOR = new Color(0, 0, 255);
    private final Color UNVISITEDCOLOR = new Color(0, 0, 0);
    private final int VERTEX_SIZE = 50;
    private final float EDGE_WIDTH = 2f;

    public StandardLayout() {
       
    }

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
    public int getVertexSize() {
        return VERTEX_SIZE;
    }

    @Override
    public float getEdgeWidth() {
        return EDGE_WIDTH;
    }

}
