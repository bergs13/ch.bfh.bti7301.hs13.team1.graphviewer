package logic;

import java.awt.Point;
import defs.VectorR2;

public class VisualizationCalculator {
	public static double getLineWidth(Point from, Point to) {
		return VectorR2.getVectorByStraightLine(from, to).getAbsoluteValue();
	}
	public static Point getPointOnStraightLine(
			Point straightLinePointForVectorFrom,
			Point straightLinePointForVectorTo,
			double distanceFromToOnStraightLine) {

		// vP (vector of point on straight line)
		VectorR2 vP = GetVectorPointOnStraightLine(
				straightLinePointForVectorFrom, straightLinePointForVectorTo,
				distanceFromToOnStraightLine);

		return new Point((int) Math.round(vP.getX()), (int) Math.round(vP
				.getY()));
	}

	public static Point[] getPointsOnNormalVectorsOfStraightLine(
			Point straightLinePointForVectorFrom,
			Point straightLinePointForVectorTo,
			double distanceFromToOnStraightLine, double distanceFromStraightLine) {

		// Projection
		// vX = vP + t * (vS)

		// vP (vector of point for projection on straight line)
		VectorR2 vP = GetVectorPointOnStraightLine(
				straightLinePointForVectorFrom, straightLinePointForVectorTo,
				distanceFromToOnStraightLine);

		// vS 1 and 2 (Normed normalvectors)
		VectorR2[] normalVectors = vP.getNormalVectors();
		VectorR2 vS1 = normalVectors[0].getNormedVector();
		VectorR2 vS2 = normalVectors[1].getNormedVector();

		// t = distanceFromStraightLine
		// vX 1 and 2 (Vectors of the points arrow left and right)
		VectorR2 vX1 = vP.addVector(vS1
				.multiplyConstantValue(distanceFromStraightLine));
		VectorR2 vX2 = vP.addVector(vS2
				.multiplyConstantValue(distanceFromStraightLine));

		// Create points from vectors
		Point[] points = new Point[2];
		points[0] = new Point((int) Math.round(vX1.getX()),
				(int) Math.round(vX1.getY()));
		points[0] = new Point((int) Math.round(vX2.getX()),
				(int) Math.round(vX2.getY()));

		return points;
	}

	// Helper methods
	private static VectorR2 GetVectorPointOnStraightLine(
			Point straightLinePointForVectorFrom,
			Point straightLinePointForVectorTo,
			double distanceFromToOnStraightLine) {
		VectorR2 vP = VectorR2
				.getVectorByStraightLine(straightLinePointForVectorFrom,
						straightLinePointForVectorTo).getNormedVector()
				.multiplyConstantValue(distanceFromToOnStraightLine);
		return vP;
	}
	// End of Helper methods
}
