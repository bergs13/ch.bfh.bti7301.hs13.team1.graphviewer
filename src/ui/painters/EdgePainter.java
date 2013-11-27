package ui.painters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import logic.VisualizationCalculator;
import defs.EdgeFormat;

public class EdgePainter {
	// Members
	private static boolean antiAliasing = true;
	// End of Members

	// End of constant values

	public static void paintEdge(EdgeFormat edgeFormat, Graphics2D g2) {
		// TODO: Ev. auch bei VertexComponent
		if (antiAliasing) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Definierbare Sachen aus Edge-Format (Muss vorhanden sein!)
		if (null == edgeFormat) {
			return;
		}
		Point fromPoint = edgeFormat.getFromPoint();
		Point toPoint = edgeFormat.getToPoint();
		Color activeColor = edgeFormat.getActiveColor();
		Color inactiveColor = edgeFormat.getUnincludedColor();
		boolean active = edgeFormat.isActive();
		boolean weighted = edgeFormat.isTextVisible();
		boolean directed = edgeFormat.isIsDirected();

		g2.setColor(active ? activeColor : inactiveColor);

		// Linie immer
		Line2D line = new Line2D.Double(fromPoint.x, fromPoint.y, toPoint.x,
				toPoint.y);
		g2.draw(line);

		// Optionale Shapes
		// Pfeil als Dreieck wenn gerichtet
		if (directed) {
			// Seitliche Ecken
			Point[] leftAndRightPoints = VisualizationCalculator
					.getPointsOnNormalVectorsOfStraightLine(toPoint, fromPoint,
							EdgeFormat.getARROWTRIANGLEHEIGHT(),EdgeFormat.getARROWTRIANGLEWIDTH() / 2);

			// Daten f�r Pfeil bekannt, Pfeil zeichnen
			int[] arrowX = new int[] { toPoint.x, leftAndRightPoints[0].x,
					leftAndRightPoints[1].x };
			int[] arrowY = new int[] { toPoint.y, leftAndRightPoints[0].y,
					leftAndRightPoints[1].y };
			int arrowN = 3;
			Polygon arrowPolygon = new Polygon(arrowX, arrowY, arrowN);
			g2.fill(arrowPolygon);
		}
		// Gewicht wenn gewichtet
		if (weighted) {
			// Gewicht Label an Punkt anzeigen
			JLabel label = new JLabel(edgeFormat.getLabel());
			label.setPreferredSize(new Dimension(100, 100));
			// Punkt f�r Gewichtanzeige
			Point labelPointOnStraightLine = VisualizationCalculator
					.getPointOnStraightLine(toPoint, fromPoint,
							VisualizationCalculator.getLineWidth(fromPoint,
									toPoint) / 2);

			// Verschieben zu Punkt
			label.setLocation(labelPointOnStraightLine);
			label.setBorder(BorderFactory.createLineBorder(Color.red));
		}
	}
}
