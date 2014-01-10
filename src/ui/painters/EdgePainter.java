package ui.painters;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import logic.VisualizationCalculator;
import logic.extlib.Edge;
import defs.DecorableConstants;
import defs.EdgeFormat;
import defs.FormatHelper;
import defs.GraphFormat;

public class EdgePainter {
	// Members
	private static boolean antiAliasing = true;

	// End of Members

	// methods
	public static <E> void paintEdge(GraphFormat graphFormat, Edge<E> edge,
			Graphics2D g2) {
		// Ohne Edge geht gar nix ;-)
		if (null == edge) {
			return;
		}

		// TODO: Ev. auch bei VertexComponent
		if (antiAliasing) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Formate müssen vorhanden sein
		EdgeFormat edgeFormat = FormatHelper.getFormat(EdgeFormat.class, edge);
		if (null == graphFormat || null == edgeFormat) {
			return;
		}

		// edge color
		g2.setColor(graphFormat.getColor(edgeFormat));

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
		if (graphFormat.isWeighted() && graphFormat.isLabelVisible()) {
			g2.setColor(graphFormat.getVisitedColor());
			String label = "";
			if (edge.has(DecorableConstants.WEIGHT)) {
				Object weight = edge.get(DecorableConstants.WEIGHT);
				if (null != weight) {
					label = "" + weight;
				}
			}

			// Punkt für Gewichtanzeige
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