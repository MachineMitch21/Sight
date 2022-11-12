package sight.image.operations;

import sight.image.Pixel;
import sight.image.PixelOperation;

public class PixelOperationInverse implements PixelOperation {

	@Override
	public Pixel operate(Pixel p) {
		int r = p.getR();
		int g = p.getG();
		int b = p.getB();
		return new Pixel(255 - r, 255 - g, 255 - b, p.getA());
	}
	
}
