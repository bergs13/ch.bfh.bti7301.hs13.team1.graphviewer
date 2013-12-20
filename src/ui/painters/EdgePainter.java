package ui.painters;

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

		g2.setColor(edgeFormat.isActive() ? graphFormat.getActiveColor()
				: graphFormat.getUnincludedColor());

		// Linie immer
		g2.draw(new Line2D.Double(edgeFormat.getFromPoint().x, edgeFormat
				.getFromPoint().y, edgeFormat.getToPoint().x, edgeFormat
				.getToPoint().y));

		// Optionale Shapes
		// Pfeil als Dreieck wenn gerichtet
		if (graphFormat.isDirected()) {
			// Seitliche Ecken
			Point[] leftAndRightPoints = VisualizationCalculator
					.getPointsOnNormalVectorsOfStraightLine(
							edgeFormat.getToPoint(), edgeFormat.getFromPoint(),
							GraphFormat.ARROWTRIANGLEHEIGHT,
							GraphFormat.ARROWTRIANGLEWIDTH / 2);

			// Daten fï¿½r Pfeil bekannt, Pfeil zeichnen
			g2.fill(new Polygon(new int[] { edgeFormat.getToPoint().x,
					leftAndRightPoints[0].x, leftAndRightPoints[1].x },
					new int[] { edgeFormat.getToPoint().y,
							leftAndRightPoints[0].y, leftAndRightPoints[1].y },
					3));
		}
		// Gewicht anzeigen, wenn gewichtet
		if (edgeFormat.isWeighted() && graphFormat.isLabelVisible()) {
			g2.setColor(graphFormat.getVisitedColor());
			String label = edgeFormat.getLabel();
			if (null == label) {
				label = "";
			}

			// Punkt fï¿½r Gewichtanzeige
			Point labelPointOnStraightLine = VisualizationCalculator
					.getPointOnStraightLine(
							edgeFormat.getToPoint(),
							edgeFormat.getFromPoint(),
							VisualizationCalculator.getLineWidth(
									edgeFormat.getFromPoint(),
									edgeFormat.getToPoint()) / 2);

			// Gewicht String an Punkt anzeigen
			g2.drawString(label,
					(int) Math.round(labelPointOnStraightLine.getX()),
					(int) Math.round(labelPointOnStraightLine.getY()));
		}
	}
	// End of methods
}