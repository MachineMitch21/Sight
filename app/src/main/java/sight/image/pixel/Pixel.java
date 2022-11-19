package sight.image.pixel;

public class Pixel {

	// The X Coordinate of this pixel in the original image
	private int xCoordinate;
	// The Y Coordinate of this pixel in the original image
	private int yCoordinate;

	// Red Channel
	private int r;
	// Green Channel
	private int g;
	// Blue Channel
	private int b;
	// Alpha Channel
	private int a;

	/**
	 * 
	 * Prefer to use {@code Pixel(int r, int g, int b, int a, int xCoordinate, int yCoordinate)} to track pixel position.
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	@Deprecated
	public Pixel(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Pixel(int r, int g, int b, int a, int xCoordinate, int yCoordinate) {
		this(r, g, b, a);
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public Pixel(Pixel copy) {
		this(copy.r, copy.g, copy.b, copy.a, copy.xCoordinate, copy.yCoordinate);
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
}
