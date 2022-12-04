package sight.geometry;

import java.util.ArrayList;
import java.util.List;

public class Point2D implements Point {
	
	private final List<Integer> coords;

	public Point2D(int x, int y) {
		coords = new ArrayList<Integer>(2);
		coords.add(x);
		coords.add(y);
	}

	public int getX() {
		return coords.get(0);
	}

	public void setX(int x) {
		coords.set(0, x);
	}

	public int getY() {
		return coords.get(1);
	}

	public void setY(int y) {
		coords.set(1, y);
	}

	@Override
	public final List<Integer> coords() {
		return coords;
	}
	
}
