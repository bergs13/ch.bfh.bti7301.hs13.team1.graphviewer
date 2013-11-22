package logic;

import java.awt.Point;
import defs.VectorR2;

public class VisualizationCalculator {
	public static double getLineWidth(Point from, Point to) {
		return VectorR2.getVectorByStraightLine(from, to).getAbsoluteValue();
	}

	public static Point getPointOnStraightLine(Point straightLinePointFrom,
			Point straightLinePointTo, double distanceFromToOnStraightLine) {
		// Projection
		// vX = vP + t * (vS)
		VectorR2 vP = new VectorR2(straightLinePointFrom.x,
				straightLinePointFrom.y);
		VectorR2 tVS = VectorR2
				.getVectorByStraightLine(straightLinePointFrom,
						straightLinePointTo).getNormedVector()
				.multiplyConstantValue(distanceFromToOnStraightLine);
		VectorR2 vX = vP.addVector(tVS);

		return new Point((int) Math.round(vX.getX()), (int) Math.round(vX
				.getY()));
	}

	public static Point[] getPointsOnNormalVectorsOfStraightLine(
			Point straightLinePointFrom, Point straightLinePointTo,
			double distanceFromToOnStraightLine, double distanceFromStraightLine) {
		// Projection of the straightline point
		// vP1 = vP0 + t * (vS)
		VectorR2 vP0 = new VectorR2(straightLinePointFrom.x,
				straightLinePointFrom.y);
		VectorR2 straightLineTVS = VectorR2
				.getVectorByStraightLine(straightLinePointFrom,
						straightLinePointTo).getNormedVector()
				.multiplyConstantValue(distanceFromToOnStraightLine);
		VectorR2 vP1 = vP0.addVector(straightLineTVS);

		// Projection of the sidepoints
		// vX = vP1 + t * (vS)
		VectorR2[] normalVectors = straightLineTVS.getNormalVectors();
		VectorR2 normalVector1TVS = normalVectors[0].getNormedVector()
				.multiplyConstantValue(distanceFromStraightLine);
		VectorR2 normalVector2TVS = normalVectors[1].getNormedVector()
				.multiplyConstantValue(distanceFromStraightLine);
		VectorR2 vX1 = vP1.addVector(normalVector1TVS);
		VectorR2 vX2 = vP1.addVector(normalVector2TVS);

		// Create points from vectors
		Point[] points = new Point[2];
		points[0] = new Point((int) Math.round(vX1.getX()),
				(int) Math.round(vX1.getY()));
		points[1] = new Point((int) Math.round(vX2.getX()),
				(int) Math.round(vX2.getY()));

		return points;
	}
}
