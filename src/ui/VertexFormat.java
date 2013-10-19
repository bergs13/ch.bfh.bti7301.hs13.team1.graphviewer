/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Point;

/**
 *
 * @author Stephan_2
 */
public class VertexFormat {

    private Point centerPoint = new Point(100, 100);
    private String label;
    private boolean active = false;
    private static final String DEFAUL_LABEL = "V";

    public VertexFormat() {
    }

    public VertexFormat(Point center, String label) {
        this.label = label;
        this.centerPoint = center;

    }
}
