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
import defs.EdgeFormat;
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
	private static final int ARROWTRIANGLEHEIGHT = 15;

	// End of constant values

	// Constructors
	public EdgeComponent(Edge<E> edge) {
		this.edge = edge;	
		this.shape = new Rectangle(300,300);
	}

	// End of constructors

	// PaintComponent method
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // Cast g to Graphics2D

		// TODO: Noch mal schauen ob wirklich benötigt und wenn ja, ev. auch bei
		// VertexComponent
		if (antiAliasing) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Definierbare Sachen aus Edge-Format (Überschreiben wenn Format
		// vorhanden)
		Object o = edge.get(EdgeFormat.FORMAT);
		EdgeFormat edgeFormat = EdgeFormat.class.isInstance(o) ? EdgeFormat.class.cast(o) : null;
		Point fromPoint;
		Point toPoint;
		if(null != edgeFormat)
		{
			fromPoint = edgeFormat.getFromPoint();
			toPoint = edgeFormat.getToPoint();
		}
		else
		{
			 fromPoint = new Point(100,100);
			 toPoint = new Point(0,0);
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
		if (directed || weighted) {
			// Polygon für Shape der Component
			Polygon shapePolygon = new Polygon();
			shapePolygon.addPoint(fromPoint.x, fromPoint.y);
			shapePolygon.addPoint(toPoint.x, toPoint.y);
			shapePolygon.npoints = 2;

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
						sidePointOne.setLocation(toPoint.x - ARROWTRIANGLEWIDTH
								/ 2, toPoint.y - ARROWTRIANGLEHEIGHT);
						sidePointTwo.setLocation(toPoint.x + ARROWTRIANGLEWIDTH
								/ 2, toPoint.y - ARROWTRIANGLEHEIGHT);
					} else {
						sidePointOne.setLocation(toPoint.x - ARROWTRIANGLEWIDTH
								/ 2, toPoint.y + ARROWTRIANGLEHEIGHT);
						sidePointTwo.setLocation(toPoint.x + ARROWTRIANGLEWIDTH
								/ 2, toPoint.y + ARROWTRIANGLEHEIGHT);
					}
				}
				// Waagrecht
				else if (deltaY == 0) {
					// Richtung
					if (toPoint.x > fromPoint.x) {
						sidePointOne.setLocation(toPoint.x
								- ARROWTRIANGLEHEIGHT, toPoint.y
								- ARROWTRIANGLEWIDTH / 2);
						sidePointTwo.setLocation(toPoint.x
								- ARROWTRIANGLEHEIGHT, toPoint.y
								+ ARROWTRIANGLEWIDTH / 2);
					} else {
						sidePointOne.setLocation(toPoint.x
								+ ARROWTRIANGLEHEIGHT, toPoint.y
								- ARROWTRIANGLEWIDTH / 2);
						sidePointTwo.setLocation(toPoint.x
								+ ARROWTRIANGLEHEIGHT, toPoint.y
								+ ARROWTRIANGLEWIDTH / 2);
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

				// Shape nachziehen: Seite 1 und Seite 2 Dreieck
				shapePolygon.addPoint(sidePointOne.x, sidePointOne.y);
				shapePolygon.addPoint(sidePointTwo.x, sidePointTwo.y);
				shapePolygon.npoints += 2;
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

				// Shape nachziehen: Bounds von Label-Rechteck
				Rectangle labelBounds = label.getBounds();
				shapePolygon.addPoint(labelBounds.x, labelBounds.y);
				shapePolygon.addPoint(labelBounds.x, labelBounds.y
						+ labelBounds.height);
				shapePolygon.addPoint(labelBounds.x + labelBounds.width,
						labelBounds.y);
				shapePolygon.addPoint(labelBounds.x + labelBounds.width,
						labelBounds.y + labelBounds.height);
				shapePolygon.npoints += 4;
			}

			// Shape aus berechnetem Polygon
			this.shape = shapePolygon;
		} else {
			// Shape für Linie immer gleich
			this.shape = line.getBounds();
		}

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
