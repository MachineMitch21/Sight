package sight.image.pixel.operations.basic;

import sight.image.pixel.Pixel;
import sight.image.pixel.operations.PixelOperation;

public class PixelOperationInverse implements PixelOperation {

	@Override
	public Pixel operate(final Pixel p) {
		int r = p.getR();
		int g = p.getG();
		int b = p.getB();
		return new Pixel(255 - r, 255 - g, 255 - b, p.getA(), p.getxCoordinate(), p.getyCoordinate());
	}
	
}
