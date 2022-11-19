package sight.image.pixel.operations.basic;

import sight.image.pixel.Pixel;
import sight.image.pixel.operations.PixelOperation;

public class PixelOperationGreyscale implements PixelOperation {
	
	@Override
	public Pixel operate(final Pixel p) {
		int greyscale = (p.getR() + p.getG() + p.getB()) / 3;

		return new Pixel(greyscale, greyscale, greyscale, p.getA(), p.getxCoordinate(), p.getyCoordinate());
	}

}
