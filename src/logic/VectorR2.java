package logic;

import java.awt.Point;

public class VectorR2 {
	private double x;
	private double y;

	public VectorR2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public static VectorR2 getVectorByStraightLine(Point fromPoint, Point toPoint) {
		return new VectorR2(toPoint.x - fromPoint.x, toPoint.y - fromPoint.y);
	}

	public VectorR2 addVector(VectorR2 otherVector) {
		return new VectorR2(this.x + otherVector.getX(), this.y
				+ otherVector.getY());
	}

	public VectorR2 subtractVector(VectorR2 otherVector) {
		return new VectorR2(this.x - otherVector.getX(), this.y
				- otherVector.getY());
	}

	public double scalarProduct(VectorR2 otherVector) {
		return this.x* otherVector.getX() + this.y * otherVector.getY();
	}

	public VectorR2 multiplyConstantValue(double constantValue) {
		return new VectorR2(this.x * constantValue, this.y * constantValue);
	}

	public double getAbsoluteValue() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}

	public VectorR2 getNormalVector() {
		return null;
	}
	public VectorR2 getNormedVector()
	{
		return this.multiplyConstantValue(1/this.getAbsoluteValue());
	}
}
