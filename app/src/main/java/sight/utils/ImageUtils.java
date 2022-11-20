package sight.utils;

import sight.image.pixel.Pixel;
import sight.image.pixel.operations.PixelOperation;
import sight.image.pixel.operations.PixelOperationEvent;
import sight.image.pixel.operations.PixelOperationListener;
import sight.image.pixel.operations.PixelOperationMultiStep;
import sight.image.pixel.operations.basic.PixelOperationGreyscale;
import sight.image.pixel.operations.basic.PixelOperationInverse;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageUtils {

	private static final ExecutorService IMG_EXECUTOR = Executors.newFixedThreadPool(8);
	public static final int BATCH_COUNT = 8;
	private static final Object IMG_LOCK = new Object();
	
	public static Pixel getPixelAtPosition(BufferedImage img, int x, int y) {
		Pixel p = null;
		synchronized (IMG_LOCK) {
			int pixel = img.getRGB(x, y);
	
			p = getPixelFrom32BitInt(pixel);
			p.setxCoordinate(x);
			p.setyCoordinate(y);
		}
		return p;
	}

	public static Pixel getPixelFrom32BitInt(int pixel) {
		int a = (pixel >> 24) & 0xff;
		int r = (pixel >> 16) & 0xff;
		int g = (pixel >> 8) & 0xff;
		int b = pixel & 0xff;

		// Using deprecated Pixel Constructor because we don't know position merely from having the 32 bit integer
		// TODO:  Figure out a different way to instantiate pixels??
		return new Pixel(r, g, b, a);
	}

	public static void setPixelAtPosition(BufferedImage img, int x, int y, Pixel pixel) {
		synchronized (IMG_LOCK) {
			int p = convertPixelTo32BitInt(pixel);
	
			img.setRGB(x, y, p);
		}
	}

	public static int convertPixelTo32BitInt(Pixel pixel) {
		return (pixel.getA() << 24) | (pixel.getR() << 16) | (pixel.getG() << 8) | pixel.getB();
	}

	public static BufferedImage from(final BufferedImage img) {

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

	public static BufferedImage performOperation(final BufferedImage buff, int xStart, int xEnd, int yStart, int yEnd, PixelOperation operation) {
		
		BufferedImage newBuff = null;

		// We'll default to returning null if buff is null
		if (buff != null) {
			// If operation is null, then just return buff as new BufferedImage
			// NOTE:  Don't know why this would be useful...  Just adding in case somebody does something weird
			// I assume, for whatever reason, if operation supplied is null, then decent behavior is to return original buff *shrugs*
			if (operation != null) {
				// TODO:  This isn't the best approach.  If we're doing work in separate threads, this is dumb and breaks...
				newBuff = from(buff);
				OperationBatchJobDTO jobDto = new OperationBatchJobDTO(newBuff, xStart, yStart, xEnd, yEnd, operation);
				OperationBatchJob batchJob = new OperationBatchJob(jobDto);
				try {
					jobDto = batchJob.call();
					newBuff.setRGB(jobDto.getX(), jobDto.getY(), jobDto.getW(), jobDto.getH(), jobDto.getPixels(), 0, jobDto.getW());
				} catch (Exception e) {
					e.printStackTrace();
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
		
		IMG_EXECUTOR.submit(new Callable<BufferedImage>() {

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
