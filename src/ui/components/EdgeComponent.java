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
import javax.swing.JComponent;
import logic.PointCalculator;
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

	// End of constant values

	// Constructors
	public EdgeComponent(Edge<E> edge) {
		this.edge = edge;
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
		Point fromPoint = new Point(0, 0);
		Point toPoint = new Point(100, 100);
		Color inactiveColor = new Color(0, 0, 255);
		Color activeColor = new Color(255, 0, 0);
		boolean active = true;
		boolean weighted = true;
		boolean directed = true;

		g2.setColor(active ? activeColor : inactiveColor);

		// Polygon für Shape der Component
		Polygon shapePolygon = new Polygon();

		// Linie immer
		Line2D line = new Line2D.Double(fromPoint.x, fromPoint.y, toPoint.x,
				toPoint.y);
		g2.draw(line);

		// Optionale Shapes
		if (directed || weighted) {
			//Wird benötigt für Fallunterscheidungen
			int deltaX = fromPoint.x - toPoint.x;
			int deltaY = fromPoint.y - toPoint.y;
			
			// Pfeil als Dreieck wenn gerichtet
			if (directed) {
				Polygon arrowHead = new Polygon();
				// Spitze immer gleich
				arrowHead.addPoint(toPoint.x, toPoint.y);
				// Spitze links und rechts
				Point arrowLeft = new Point();
				Point arrowRight = new Point();

				// Spezialfälle
				// Senkrecht
				if (deltaX == 0) {
					// Richtung
					if (toPoint.y > fromPoint.y) {
						arrowLeft.setLocation(toPoint.x - ARROWTRIANGLEWIDTH
								/ 2, toPoint.y - ARROWTRIANGLEHEIGHT);
						arrowRight.setLocation(toPoint.x + ARROWTRIANGLEWIDTH
								/ 2, toPoint.y - ARROWTRIANGLEHEIGHT);
					} else {
						arrowLeft.setLocation(toPoint.x - ARROWTRIANGLEWIDTH
								/ 2, toPoint.y + ARROWTRIANGLEHEIGHT);
						arrowRight.setLocation(toPoint.x + ARROWTRIANGLEWIDTH
								/ 2, toPoint.y + ARROWTRIANGLEHEIGHT);
					}
				}
				// Waagrecht
				else if (deltaY == 0) {
					// Richtung
					if (toPoint.x > fromPoint.x) {
						arrowLeft.setLocation(toPoint.x - ARROWTRIANGLEHEIGHT,
								toPoint.y - ARROWTRIANGLEWIDTH / 2);
						arrowRight.setLocation(toPoint.x - ARROWTRIANGLEHEIGHT,
								toPoint.y + ARROWTRIANGLEWIDTH / 2);
					} else {
						arrowLeft.setLocation(toPoint.x + ARROWTRIANGLEHEIGHT,
								toPoint.y - ARROWTRIANGLEWIDTH / 2);
						arrowRight.setLocation(toPoint.x + ARROWTRIANGLEHEIGHT,
								toPoint.y + ARROWTRIANGLEWIDTH / 2);
					}
				}
				// Allgemeiner Fall
				else {
					
					Point p = PointCalculator.getPointOnStraightLine(toPoint,
							fromPoint, ARROWTRIANGLEHEIGHT);
				}

				// Daten für Pfeil bekannt, Pfeil zeichnen
				arrowHead.addPoint(arrowLeft.x, arrowLeft.y);
				arrowHead.addPoint(arrowRight.x, arrowRight.y);
				// Noch einmal die Spitze zum abschliesen des Polygons
				arrowHead.addPoint(toPoint.x, toPoint.y);
				g2.fill(arrowHead);
			}
			
			// Gewicht wenn gewichtet
			if (weighted) {
				// Senkrecht
				if (deltaX == 0) {

				}
				// Waagrecht
				else if (deltaY == 0) {

				}
				// Allgemeiner Fall (Abhängig von Steigung)
				else {

				}
			}

			// Shape setzen
			this.shape = shapePolygon;
		}
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
