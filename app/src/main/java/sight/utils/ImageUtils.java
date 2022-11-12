package sight.utils;

import sight.image.Pixel;
import sight.image.PixelOperation;
import sight.image.operations.PixelOperationGreyscale;
import sight.image.operations.PixelOperationInverse;
import sight.image.operations.PixelOperationMultiStep;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collection;

public class ImageUtils {
	
	public static Pixel getPixelAtPosition(BufferedImage img, int x, int y) {
		int pixel = img.getRGB(x, y);

		int a = (pixel >> 24) & 0xff;
		int r = (pixel >> 16) & 0xff;
		int g = (pixel >> 8) & 0xff;
		int b = pixel & 0xff;

		return new Pixel(r, g, b, a);
	}

	public static void setPixelAtPosition(BufferedImage img, int x, int y, Pixel pixel) {
		int p = (pixel.getA() << 24) | (pixel.getR() << 16) | (pixel.getG() << 8) | pixel.getB();

		img.setRGB(x, y, p);
	}

	public static BufferedImage from(BufferedImage img) {

		if (img == null) {
			return null;
		}

		return new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
	}

	public static BufferedImage performOperation(BufferedImage buff, int xStart, int xEnd, int yStart, int yEnd, PixelOperation operation) {
		
		BufferedImage newBuff = null;

		// We'll default to returning null if buff is null
		if (buff != null) {
			// If operation is null, then just return buff as new BufferedImage
			// NOTE:  Don't know why this would be useful...  Just adding in case somebody does something weird
			// I assume, for whatever reason, if operation supplied is null, then decent behavior is to return original buff *shrugs*
			if (operation != null) {
				newBuff = from(buff);
				for (int x = xStart; x < xEnd; x++) {
					for (int y = yStart; y <br yEnd; y++) {
						Pixel p = getPixelAtPosition(buff, x, y);
						p = operation.operate(p);
						setPixelAtPosition(newBuff, x, y, p);
					}
				}
			} else {
				newBuff = buff;
			}
		}

		return newBuff;
	}

	public static BufferedImage performFullOperation(BufferedImage buff, PixelOperation operation) {
		return performOperation(buff, 0, buff.getWidth(), 0, buff.getHeight(), operation);
	}

	public static BufferedImage convertToGreyScale(BufferedImage img) {
		return performFullOperation(img, new PixelOperationGreyscale());
	}

	public static BufferedImage inverse(BufferedImage img) {
		return performFullOperation(img, new PixelOperationInverse());
	}

	/**
	 * Utilizes {@link PixelOperationMultiStep} to run {@link PixelOperationInverse}, then {@link PixelOperationGreyscale} </br>
	 * back to back per pixel.
	 * 
	 * @param img
	 * @return An image that represent the greyscale inverse of the given {@link BufferedImage}.
	 */
	public static BufferedImage inverseGreyscale(BufferedImage img) {
		return performFullOperation(img, new PixelOperationMultiStep(Arrays.asList(
			new PixelOperationInverse(),
			new PixelOperationGreyscale()
		)));
	}
}
