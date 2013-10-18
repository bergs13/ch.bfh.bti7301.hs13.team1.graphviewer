package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import extlib.Edge;

@SuppressWarnings("serial")
public class EdgeComponent<E> extends JComponent {
	// Members
	private Edge<E> edge = null;
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
		
		//Linie immer
		Line2D line = new Line2D.Double(fromPoint.x, fromPoint.y, toPoint.x,
				toPoint.y);
		g2.draw(line);
		
		//Point für Dimension Preferred Size: Linie ohne optionale Shapes
		Point shapeDimensionPoint = new Point(fromPoint.x,toPoint.y);
		
		//Optionale Shapes
		//Steigung der Linie benötigt wenn gerichtet oder gewichtet
		if(directed || weighted)
		{
		int deltaX = fromPoint.x - toPoint.x;
		int deltaY = fromPoint.y - toPoint.y;
		int slope = Integer.MIN_VALUE;
		//Steigung wird nur benötigt wenn kein Spezialfall (waagrecht/senkrecht)
		if(deltaX != 0 && deltaY != 0)
		{
			slope = deltaX/deltaY;
		}	
		//Pfeil als Dreieck wenn gerichtet
		if (directed) {
			Polygon arrowHead = new Polygon();
			//Spitze immer gleich
			arrowHead.addPoint(toPoint.x, toPoint.y);
			
			//Spezialfälle
			//Senkrecht
			if(deltaX == 0)
			{
				//Richtung
				if(toPoint.y > fromPoint.y)
				{
					arrowHead.addPoint(toPoint.x - ARROWTRIANGLEWIDTH/2, toPoint.y - ARROWTRIANGLEHEIGHT);
					arrowHead.addPoint(toPoint.x + ARROWTRIANGLEWIDTH/2, toPoint.y - ARROWTRIANGLEHEIGHT);
					//Point für Dimension Preferred Size anpassen
										
				}
				else
				{
					arrowHead.addPoint(toPoint.x - ARROWTRIANGLEWIDTH/2, toPoint.y + ARROWTRIANGLEHEIGHT);
					arrowHead.addPoint(toPoint.x + ARROWTRIANGLEWIDTH/2, toPoint.y + ARROWTRIANGLEHEIGHT);
				}
			}
			//Waagrecht
			else if(deltaY == 0)
			{
				//Richtung
				if(toPoint.x > fromPoint.x)
				{
					arrowHead.addPoint(toPoint.x - ARROWTRIANGLEHEIGHT, toPoint.y - ARROWTRIANGLEWIDTH/2);
					arrowHead.addPoint(toPoint.x - ARROWTRIANGLEHEIGHT, toPoint.y + ARROWTRIANGLEWIDTH/2);
				}
				else
				{
					arrowHead.addPoint(toPoint.x + ARROWTRIANGLEHEIGHT, toPoint.y - ARROWTRIANGLEWIDTH/2);
					arrowHead.addPoint(toPoint.x + ARROWTRIANGLEHEIGHT, toPoint.y + ARROWTRIANGLEWIDTH/2);
				}
			}
			//Allgemeiner Fall (Abhängig von Steigung)
			else
			{
				
			}
			
			g2.fill(arrowHead);
		}
		
		//Gewicht wenn gewichtet
		if (weighted) {

		}
		}
	
		this.setPreferredSize( new Dimension(shapeDimensionPoint.x,shapeDimensionPoint.y));
		
	
	}
	// End of paintComponent method
}
