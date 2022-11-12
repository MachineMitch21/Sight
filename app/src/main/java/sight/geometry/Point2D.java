package sight.geometry;

public class Point2D implements Point {
	
	int x;
	int y;

	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int[] getCoords() {
		return new int[] {x, y};
	}

	
}
