package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import javax.swing.JLabel;
import logic.VisualizationCalculator;
import defs.EdgeFormat;

public class EdgePainter {
	// Members
	private static boolean antiAliasing = true;
	// End of Members
	// Constant values
	private static final int ARROWTRIANGLEWIDTH = 10;
	private static final int ARROWTRIANGLEHEIGHT = 15;

	// End of constant values

	public static void paintEdge(EdgeFormat edgeFormat, Graphics2D g2) {
		// TODO: Ev. auch bei VertexComponent
		if (antiAliasing) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Definierbare Sachen aus Edge-Format (Überschreiben wenn Format
		// vorhanden)

		Point fromPoint;
		Point toPoint;
		if (null != edgeFormat) {
			fromPoint = edgeFormat.getFromPoint();
			toPoint = edgeFormat.getToPoint();
		} else {
			fromPoint = new Point(100, 100);
			toPoint = new Point(0, 0);
		}
		Color inactiveColor = new Color(0, 0, 255);
		Color activeColor = new Color(255, 0, 0);
		boolean active = true;
		boolean weighted = true;
		boolean directed = true;

		g2.setColor(active ? activeColor : inactiveColor);

		// Linie immer
		Line2D line = new Line2D.Double(fromPoint.x, fromPoint.y, toPoint.x,
				toPoint.y);
		g2.draw(line);

		// Optionale Shapes
		// Pfeil als Dreieck wenn gerichtet
		if (directed) {
			// Wird benötigt für Fallunterscheidungen
			int deltaX = Math.abs(fromPoint.x - toPoint.x);
			int deltaY = Math.abs(fromPoint.y - toPoint.y);

			// Seitliche Ecken
			Point sidePointOne = new Point();
			Point sidePointTwo = new Point();

			// Spezialfälle
			// Senkrecht
			if (deltaX == 0) {
				// Richtung
				if (toPoint.y > fromPoint.y) {
					sidePointOne.setLocation(
							toPoint.x - ARROWTRIANGLEWIDTH / 2, toPoint.y
									- ARROWTRIANGLEHEIGHT);
					sidePointTwo.setLocation(
							toPoint.x + ARROWTRIANGLEWIDTH / 2, toPoint.y
									- ARROWTRIANGLEHEIGHT);
				} else {
					sidePointOne.setLocation(
							toPoint.x - ARROWTRIANGLEWIDTH / 2, toPoint.y
									+ ARROWTRIANGLEHEIGHT);
					sidePointTwo.setLocation(
							toPoint.x + ARROWTRIANGLEWIDTH / 2, toPoint.y
									+ ARROWTRIANGLEHEIGHT);
				}
			}
			// Waagrecht
			else if (deltaY == 0) {
				// Richtung
				if (toPoint.x > fromPoint.x) {
					sidePointOne.setLocation(toPoint.x - ARROWTRIANGLEHEIGHT,
							toPoint.y - ARROWTRIANGLEWIDTH / 2);
					sidePointTwo.setLocation(toPoint.x - ARROWTRIANGLEHEIGHT,
							toPoint.y + ARROWTRIANGLEWIDTH / 2);
				} else {
					sidePointOne.setLocation(toPoint.x + ARROWTRIANGLEHEIGHT,
							toPoint.y - ARROWTRIANGLEWIDTH / 2);
					sidePointTwo.setLocation(toPoint.x + ARROWTRIANGLEHEIGHT,
							toPoint.y + ARROWTRIANGLEWIDTH / 2);
				}
			}
			// Allgemeiner Fall
			else {
				Point[] leftAndRightPoints = VisualizationCalculator
						.getPointsOnNormalVectorsOfStraightLine(toPoint,
								fromPoint, ARROWTRIANGLEHEIGHT,
								ARROWTRIANGLEWIDTH / 2);
				sidePointOne = leftAndRightPoints[0];
				sidePointTwo = leftAndRightPoints[1];
			}

			// Daten für Pfeil bekannt, Pfeil zeichnen
			int[] arrowX = new int[] { toPoint.x, sidePointOne.x,
					sidePointTwo.x };
			int[] arrowY = new int[] { toPoint.y, sidePointOne.y,
					sidePointTwo.y };
			int arrowN = 3;
			Polygon arrowPolygon = new Polygon(arrowX, arrowY, arrowN);
			g2.setColor(Color.black);
			g2.fill(arrowPolygon);
		}
		// Gewicht wenn gewichtet
		if (weighted) {
			// Gewicht Label an Punkt anzeigen
			JLabel label = new JLabel("42");

			// Punkt für Gewichtanzeige
			Point labelPointOnStraightLine = VisualizationCalculator
					.getPointOnStraightLine(toPoint, fromPoint,
							VisualizationCalculator.getLineWidth(fromPoint,
									toPoint) / 2);

			// Verschieben zu Punkt
			label.setLocation(labelPointOnStraightLine);
		}
	}
}
