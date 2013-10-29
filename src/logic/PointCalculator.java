package logic;

import java.awt.Point;
import defs.VectorR2;

public class PointCalculator {
	public PointCalculator() {
	}

	public static Point getPointOnStraightLine(Point fromPoint,
			Point toPoint, double distance) {
		VectorR2 vector = VectorR2.getVectorByStraightLine(fromPoint,toPoint).getNormedVector().multiplyConstantValue(distance);
		return new Point();
	}
}
