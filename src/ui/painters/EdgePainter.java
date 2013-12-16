package ui.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import logic.VisualizationCalculator;
import defs.EdgeFormat;
import defs.GraphFormat;

public class EdgePainter {
	// Members
	private static boolean antiAliasing = true;

	// End of Members

	// methods
	public static void paintEdge(GraphFormat graphFormat,
			EdgeFormat edgeFormat, Graphics2D g2) {
		// TODO: Ev. auch bei VertexComponent
		if (antiAliasing) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Formate müssen vorhanden sein
		if (null == graphFormat || null == edgeFormat) {
			return;
		}

		// Graph format (Für alle Edges gleich)
		boolean directed = graphFormat.isDirected();
		Color activeColor = graphFormat.getActiveColor();
		Color inactiveColor = graphFormat.getUnincludedColor();
		// Edge-Format
		Point fromPoint = edgeFormat.getFromPoint();
		Point toPoint = edgeFormat.getToPoint();
		boolean active = edgeFormat.isActive();
		boolean weighted = edgeFormat.isWeighted();

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
							GraphFormat.ARROWTRIANGLEHEIGHT,
							GraphFormat.ARROWTRIANGLEWIDTH / 2);

			// Daten fï¿½r Pfeil bekannt, Pfeil zeichnen
			int[] arrowX = new int[] { toPoint.x, leftAndRightPoints[0].x,
					leftAndRightPoints[1].x };
			int[] arrowY = new int[] { toPoint.y, leftAndRightPoints[0].y,
					leftAndRightPoints[1].y };
			int arrowN = 3;
			Polygon arrowPolygon = new Polygon(arrowX, arrowY, arrowN);
			g2.fill(arrowPolygon);
		}
		// Gewicht anzeigen, wenn gewichtet
		if (weighted && graphFormat.isLabelVisible()) {
			g2.setColor(graphFormat.getVisitedColor());
			String label = edgeFormat.getLabel();
			if (null == label) {
				label = "";
			}

			// Punkt fï¿½r Gewichtanzeige
			Point labelPointOnStraightLine = VisualizationCalculator
					.getPointOnStraightLine(toPoint, fromPoint,
							VisualizationCalculator.getLineWidth(fromPoint,
									toPoint) / 2);

			// Gewicht String an Punkt anzeigen
			g2.drawString(label,
					(int) Math.round(labelPointOnStraightLine.getX()),
					(int) Math.round(labelPointOnStraightLine.getY()));
		}
	}
	// End of methods
}