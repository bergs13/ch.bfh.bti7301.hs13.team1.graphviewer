package logic;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import defs.VectorR2;

public class VisualizationCalculator 
{
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

	public static Point[] getCircularAlignedPoints(int circleCount,
			int circleDiameter, int gap) {
		if (circleCount < 0 || circleDiameter < 0 || gap < 0) {
			throw new IllegalArgumentException(
					"one or more invalid parameters.");
		}

		// calculate main circle data
		// radius by circumferrence (c: n * diameter + n * gap) => c = 2r*PI
		// => r = c/PI/2
		double mainCircleRadius = (circleCount * circleDiameter + circleCount
				* gap)
				/ Math.PI / 2;
		// center position (base of the new coordinate system)
		Point mainCircleCenter = new Point(
				(int) Math.round(mainCircleRadius + circleDiameter + 10/* inset */),
				(int) Math
						.round(mainCircleRadius + circleDiameter + 10/* inset */));

		// calculate points on main circle
		// prepare variables
		Point[] points = new Point[circleCount];
		int pointsAdded = 0;
		double totalAngleChange = 0;
		double angleChangePerCircle = 360 / circleCount;
		double angleForCalculation;
		while (pointsAdded < circleCount) {
			if (0 == pointsAdded) {
				points[pointsAdded] = new Point(
						(int) Math.round(mainCircleCenter.x + /* deltaX */0),
						(int) Math.round(mainCircleCenter.y
								+ /* deltaY */-mainCircleRadius));
			} else {
				totalAngleChange = pointsAdded * angleChangePerCircle;
				if (totalAngleChange < 90) {
					angleForCalculation = 90 - totalAngleChange;
					points[pointsAdded] = new Point(
							(int) Math.round(mainCircleCenter.x
									+ /* deltaX */Math.sin(angleForCalculation)
									* mainCircleRadius),
							(int) Math.round(mainCircleCenter.y
									- /* deltaY */Math.cos(angleForCalculation)
									* mainCircleRadius));
				} else if (totalAngleChange < 180) {
					angleForCalculation = totalAngleChange - 90;
					points[pointsAdded] = new Point(
							(int) Math.round(mainCircleCenter.x
									+ /* deltaX */Math.sin(angleForCalculation)
									* mainCircleRadius),
							(int) Math.round(mainCircleCenter.y
									- /* -deltaY */(-1)
									* Math.cos(angleForCalculation)
									* mainCircleRadius));
				} else if (totalAngleChange < 270) {
					angleForCalculation = 270 - totalAngleChange;
					points[pointsAdded] = new Point(
							(int) Math.round(mainCircleCenter.x
									+ /* -deltaX */(-1)
									* Math.sin(angleForCalculation)
									* mainCircleRadius),
							(int) Math.round(mainCircleCenter.y
									- /* -deltaY */(-1)
									* Math.cos(angleForCalculation)
									* mainCircleRadius));
				} else /* < 360 */{
					angleForCalculation = totalAngleChange - 270;
					points[pointsAdded] = new Point(
							(int) Math.round(mainCircleCenter.x
									+ /* -deltaX */(-1)
									* Math.sin(angleForCalculation)
									* mainCircleRadius),
							(int) Math.round(mainCircleCenter.y
									- /* deltaY */Math.cos(angleForCalculation)
									* mainCircleRadius));
				}
			}
			pointsAdded++;
		}
		return points;
	}

	public static double getStringWidth(Graphics2D g2, Font f, String s) {
		// Find the size of string s in the font of the Graphics context "page"
		FontMetrics fm = g2.getFontMetrics(f);
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(s, g2);
		return rect.getWidth();
	}

	public static double getStringHeight(Graphics2D g2, Font f, String s) {
		// Find the size of string s in the font of the Graphics context "page"
		FontMetrics fm = g2.getFontMetrics(f);
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(s, g2);
		return rect.getHeight();
	}
}
