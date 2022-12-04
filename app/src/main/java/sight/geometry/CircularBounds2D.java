package sight.geometry;

public class CircularBounds2D extends AbstractBounds2D {

	private double edge;

	public CircularBounds2D(Point2D origin, int radius) {
		super(origin);
		this.edge = Math.pow(radius, 2);
	}

	@Override
	/*
	 * Using math derived from
	 * https://www.mathsisfun.com/algebra/circle-equations.html
	 */
	protected boolean doesContain(Point2D p) {
		double scaledX = Math.pow(p.getX() - ((Point2D) getOrigin()).getX(), 2);
		double scaledY = Math.pow(p.getY() - ((Point2D) getOrigin()).getY(), 2);
		boolean withinBounds = (scaledX + scaledY) < edge;
		return withinBounds;
	}
	
}
