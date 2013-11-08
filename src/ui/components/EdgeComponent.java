package ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import logic.VisualizationCalculator;
import logic.extlib.Edge;

@SuppressWarnings("serial")
public class EdgeComponent<E> extends JComponent {
	// Members
	private Edge<E> edge = null;
	// Members for the custom component shape
	private Shape shape;
	private boolean antiAliasing = true; // "better rendering"
	// End Members for the custom component shape
	// End of members

	// Constant values
	private static final int ARROWTRIANGLEWIDTH = 10;
	private static final int ARROWTRIANGLEHEIGHT = 5;
	private static final int SHAPEBORDER = 1;

	// End of constant values

	// Constructors
	public EdgeComponent(Edge<E> edge) {
		this.edge = edge;
	}

	// End of constructors

	// PaintComponent method
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // Cast g to Graphics2D

		// TODO: Noch mal schauen ob wirklich ben�tigt und wenn ja, ev. auch bei
		// VertexComponent
		if (antiAliasing) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Definierbare Sachen aus Edge-Format (�berschreiben wenn Format
		// vorhanden)
		Point fromPoint = new Point(0, 0);
		Point toPoint = new Point(100, 100);
		Color inactiveColor = new Color(0, 0, 255);
		Color activeColor = new Color(255, 0, 0);
		boolean active = true;
		boolean weighted = true;
		boolean directed = true;

		g2.setColor(active ? activeColor : inactiveColor);

		// Polygon f�r Shape der Component
		Polygon shapePolygon = new Polygon();

		// Linie immer
		Line2D line = new Line2D.Double(fromPoint.x, fromPoint.y, toPoint.x,
				toPoint.y);
		g2.draw(line);

		// Optionale Shapes
		// Pfeil als Dreieck wenn gerichtet
		if (directed) {
			// Wird ben�tigt f�r Fallunterscheidungen
			int deltaX = fromPoint.x - toPoint.x;
			int deltaY = fromPoint.y - toPoint.y;

			// Seitliche Ecken
			Point sidePointOne = new Point();
			Point sidePointTwo = new Point();

			// Spezialf�lle
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

			// Daten f�r Pfeil bekannt, Pfeil zeichnen
			Polygon arrowPolygon = new Polygon();
			// Spitze immer gleich
			arrowPolygon.addPoint(toPoint.x, toPoint.y);
			arrowPolygon.addPoint(sidePointOne.x, sidePointOne.y);
			arrowPolygon.addPoint(sidePointTwo.x, sidePointTwo.y);
			// Noch einmal die Spitze zum abschliesen des Polygons
			arrowPolygon.addPoint(toPoint.x, toPoint.y);
			g2.fill(arrowPolygon);
		}
		// Gewicht wenn gewichtet
		if (weighted) {
			// Punkt f�r Gewichtanzeige
			Point labelPointOnStraightLine = VisualizationCalculator
					.getPointOnStraightLine(toPoint, fromPoint,
							VisualizationCalculator.getLineWidth(fromPoint,
									toPoint) / 2);

			// Gewicht Label an Punkt anzeigen
			JLabel l = new JLabel("42");
			l.setLocation(labelPointOnStraightLine);
		}
		if (!directed && !weighted) {
			// Shape f�r Linie
			
		}
		// Shape setzen
		this.shape = shapePolygon;
		
		this.setBorder(BorderFactory.createLineBorder(Color.red));
	}

	// End of paintComponent method

	// Size overrides for the custom component shape
	@Override
	public Dimension getPreferredSize() {
		Rectangle bounds = shape.getBounds();
		return new Dimension(bounds.width, bounds.height);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	// End of size overrides for the custom component shape
}
