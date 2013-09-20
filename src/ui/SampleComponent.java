package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class SampleComponent extends JComponent {
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // Cast g to Graphics2D

		int xStart = 50;
		int yStart = 50;
		for (int i = 0; i < 2; i++) {
			xStart = (i + 1) * xStart;
			yStart = (i + 1) * yStart;
			// Vertex-Test
			Ellipse2D inner = new Ellipse2D.Double(xStart, yStart, 50, 50);
			boolean active = true;
			if (active) {
				Ellipse2D outer = new Ellipse2D.Double(inner.getCenterX() - 30,
						inner.getCenterX() - 30, 60, 60);
				g2.setColor(new Color(0, 0, 255));
				g2.fill(outer);
			}
			g2.setColor(new Color(255, 0, 0));
			g2.fill(inner);
		}
	}
}
