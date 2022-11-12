package sight.image.operations;

import sight.image.Pixel;
import sight.image.PixelOperation;

public class PixelOperationGreyscale implements PixelOperation {
	
	@Override
	public Pixel operate(Pixel p) {
		int greyscale = (p.getR() + p.getG() + p.getB()) / 3;

		return new Pixel(greyscale, greyscale, greyscale, p.getA());
	}

}
