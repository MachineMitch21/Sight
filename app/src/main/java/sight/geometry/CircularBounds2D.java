package sight.geometry;

public class CircularBounds2D implements Bounds {

	Point2D center;
	int radius;

	public CircularBounds2D(Point2D center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	@Override
	public boolean contains(Point p) {
		int coords[] = p.getCoords();
		boolean result = false;
		if (coords.length < 2) {
			// TODO:  Add logging here to indicate point doesn't fit into this coordinate system
		} else {
			int x = coords[0];
			int y = coords[1];
		}

		return result;
	}
	
}
