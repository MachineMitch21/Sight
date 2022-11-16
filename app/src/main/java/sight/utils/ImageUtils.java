package sight.utils;

import sight.image.Pixel;
import sight.image.operations.PixelOperation;
import sight.image.operations.PixelOperationEvent;
import sight.image.operations.PixelOperationGreyscale;
import sight.image.operations.PixelOperationInverse;
import sight.image.operations.PixelOperationListener;
import sight.image.operations.PixelOperationMultiStep;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageUtils {

	private static ExecutorService imgExecutor = Executors.newFixedThreadPool(4);
	private static final Object imgLock = new Object();
	
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

		BufferedImage copy = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				Pixel p = getPixelAtPosition(img, x, y);
				setPixelAtPosition(copy, x, y, p);
			}
		}

		return copy;
	}

	public static BufferedImage performOperation(BufferedImage buff, int xStart, int xEnd, int yStart, int yEnd, PixelOperation operation) {
		
		BufferedImage newBuff = null;

		// We'll default to returning null if buff is null
		if (buff != null) {
			// If operation is null, then just return buff as new BufferedImage
			// NOTE:  Don't know why this would be useful...  Just adding in case somebody does something weird
			// I assume, for whatever reason, if operation supplied is null, then decent behavior is to return original buff *shrugs*
			if (operation != null) {
				// TODO:  This isn't the best approach.  If we're doing work in separate threads, this is dumb and breaks...
				newBuff = from(buff);
				for (int x = xStart; x < xEnd; x++) {
					for (int y = yStart; y < yEnd; y++) {
						synchronized (imgLock) {
							Pixel p = getPixelAtPosition(buff, x, y);
							p = operation.operate(p);
							setPixelAtPosition(newBuff, x, y, p);
						}
					}
				}
			} else {
				newBuff = buff;
			}
		}

		return newBuff;
	}

	// TODO:  Uhh, this doesn't work LOL
	public static void performLapsedOperation(
		BufferedImage buff,
		int xStart, 
		int xEnd, 
		int yStart, 
		int yEnd,
		long batchDelay,
		PixelOperation operation,
		PixelOperationListener opListener
	) {
		
		imgExecutor.submit(new Callable<BufferedImage>() {

			@Override
			public BufferedImage call() throws Exception {
				BufferedImage bi = from(buff);

				for (int i = 0; i < buff.getHeight(); i++) {
					Long start = System.currentTimeMillis();
					bi = ImageUtils.performOperation(bi, 0, bi.getWidth(), i, i + 1, operation);
					opListener.handlePostOperation(new PixelOperationEvent(this, bi));
					Long elapsed = System.currentTimeMillis() - start;
					// Thread.sleep(Math.max(batchDelay - elapsed, 0));
				}

				return bi;
			}
			
		});

	}

	public static BufferedImage performFullOperation(BufferedImage buff, PixelOperation operation) {
		return performOperation(buff, 0, buff.getWidth(), 0, buff.getHeight(), operation);
	}

	public static Image performFullOperation(Image img, PixelOperation operation) {
		BufferedImage buff = SwingFXUtils.fromFXImage(img, null);
		buff = performFullOperation(buff, operation);
		return SwingFXUtils.toFXImage(buff, null);
	}

	public static BufferedImage greyscale(BufferedImage img) {
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

	public static BufferedImage greyscaleInverse(BufferedImage img) {
		return performFullOperation(img, new PixelOperationMultiStep(Arrays.asList(
			new PixelOperationGreyscale(),
			new PixelOperationInverse()
		)));
	}
}
