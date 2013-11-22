package defs;

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

	public static VectorR2 getVectorByStraightLine(Point fromPoint,
			Point toPoint) {
		// v = (tx - fx, ty - fy) => Endpunkt - Anfangspunkt
		return new VectorR2(toPoint.x - fromPoint.x, toPoint.y - fromPoint.y);
	}

	public VectorR2 addVector(VectorR2 otherVector) {
		// v2 = (v0x + v1x, v0y + v1y)
		return new VectorR2(this.x + otherVector.getX(), this.y
				+ otherVector.getY());
	}

	public VectorR2 subtractVector(VectorR2 otherVector) {
		// v2 = (v0x - v1x, v0y - v1y)
		return new VectorR2(this.x - otherVector.getX(), this.y
				- otherVector.getY());
	}

	public double scalarProduct(VectorR2 otherVector) {
		// scalarValue = v0x * v1x + v0y * v1y
		return this.x * otherVector.getX() + this.y * otherVector.getY();
	}

	public VectorR2 multiplyConstantValue(double constantValue) {
		// (vOutx, vOuty) = (v0x * c, v0y * c)
		return new VectorR2(this.x * constantValue, this.y * constantValue);
	}

	public double getAbsoluteValue() {
		// |v0| = Wurzel(v0x^2 + v0y^2)
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}

	public VectorR2[] getNormalVectors() {
		// vNormal((v0x/v0y)) = (-v0x/v0y) && (v0x/-v0y)
		VectorR2[] normalVectors = new VectorR2[2];
		normalVectors[0] = new VectorR2(this.y, -this.x);
		normalVectors[1] = new VectorR2(-this.y, this.x);
		return normalVectors;
	}

	public VectorR2 getNormedVector() {
		// vNormed = 1/|v0| * v0
		return this.multiplyConstantValue(1 / this.getAbsoluteValue());
	}
}
