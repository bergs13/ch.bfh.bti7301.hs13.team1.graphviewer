package logic;

import java.awt.Point;
import java.awt.geom.Point2D;

public class PointCalculator {
	public PointCalculator() {
	}

	public static Point getPointOnStraightLine(Point sourcePoint,
			Point targetPoint, double distance) {
		int deltaX = Math.abs(sourcePoint.x - targetPoint.x);
		int deltaY = Math.abs(sourcePoint.y - targetPoint.y);
		if (deltaX == 0 || deltaY == 0) {
			throw new IllegalArgumentException(
					"Points who lead to horizontal and vertical straight lines are not allowed!");
		}

		//Ermitteln mit Vektoren (R2)
		//Vx : Vp + k * Vu
		
		
//		// R2-Gleichungen-Weg (Eher schwerer als Vektor-Weg
//		// Parameter Geradengleichung y = mx + b ermitteln
//		// m ermitteln: m = deltay/deltax
//		double m = deltaY / deltaX;
//		// b ermitteln (Punkt einsetzen): b = y - mx
//		double b = targetPoint.y - m * targetPoint.x;
//		// Punkt auf Gerade ausgehend von Source-Point ermitteln
//		// d = sqrt((y2 - y1)^2 + (x2 - x1)^2)
//		// Höhe (toPoint gegeben, zweiter Punkt auf Gerade von toPoint aus
//		// gesucht)
		
		return new Point();
	}
}
