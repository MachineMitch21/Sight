package sight.image;

public class Pixel {

	// Red Channel
	private int r;
	// Green Channel
	private int g;
	// Blue Channel
	private int b;
	// Alpha Channel
	private int a;

	public Pixel(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Pixel(Pixel copy) {
		this(copy.r, copy.g, copy.b, copy.a);
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
	
}
