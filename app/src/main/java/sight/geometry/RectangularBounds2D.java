package sight.geometry;

public class RectangularBounds2D extends AbstractBounds2D {

	Point2D endPoint;
	int width;
	int height;

	public RectangularBounds2D(Point2D origin, Point2D endPoint) {
		super(origin);
		this.endPoint = endPoint;
		this.width = endPoint.getX() - origin.getX();
		this.height = endPoint.getY() - origin.getY();
	}

	@Override
	protected boolean doesContain(Point2D p) {
		boolean withinX = p.getX() > origin.getX() && p.getX() < endPoint.getX();
		boolean withinY = p.getY() > origin.getY() && p.getY() < endPoint.getY();
		return withinX && withinY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
