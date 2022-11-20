package sight.utils;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import sight.image.pixel.operations.PixelOperation;

/**
 * <pre>
 * Data Transfer Object for passing around data correlating with a batched pixel data mutation. 
 * 
 * Let us observe an example of the intentions of this data object... 
 * 
 * The following represents a matrix of pixels with the numbers correlating with their respective indices in the flat array (only 4 x 4 for simplicity) 
 * 
 * [ 	0  	1  	2  	3 	] 
 * [ 	4  	5  	6  	7 	] 
 * [ 	8  	9  	10 	11 	] 
 * [	12	13	14	15	]
 * 
 * --------------------------------------------------x--y--w--h--------------------------
 * Now, let us say we want to operate on the subset (1, 1, 2, 2) of the given pixel data.
 * 
 * The batch of pixels we are now operating on is as follows (represented still by the indices in the flat array).
 * 
 * (NOTE:  This is displaying weirdly in vscode when the tabs make it look proper in the editor...  No idea why)
 * [	5	6	]
 * [	9	10	]
 * 
 * --------------------------w---h----------------------------------------------------x--y-
 * We are now operating on a 2 x 2 chunk of the original matrix starting at position (1, 1)
 * 
 * It is also important to note that, once we create a batch DTO, we can no longer mutate any data for the batch (at least currently)
 * except for the {@link PixelOperation} we are using.  I made this design choice to begin with because the pixel data we are operating on
 * is already a subset of the original.  Any further subsets may be harder to track.
 * 
 * </pre>
 */
public class OperationBatchJobDTO {
	/**
	 *  Pixel Data
	 */
	private int[] pixels;
	/**
	 *  The start X Coordinate for this batch job
	 */
	private int x;
	/** 
	 * The start Y Coordinate for this batch job
	 */
	private int y;
	/**
	 * The end X Coordinate for this batch job [W for Width]
	 */
	private int w;
	/**
	 * The end Y Coordinate for this batch job [H for Height]
	 */
	private int h; 
	/**
	 * The Pixel Operation to perform on this batch
	 */
	private PixelOperation operation;

	/**
	 * 
	 * Default constructor to use for batch jobs which will consist of the entire image data. 
	 * 
	 * @param img The image buffer to pull data from.
	 * @param operation The operation to perform on each pixel.
	 */
	public OperationBatchJobDTO(BufferedImage img, PixelOperation operation) {
		pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(),null,  0, img.getWidth());
		this.x = 0;
		this.y = 0;
		this.w = img.getWidth();
		this.h = img.getHeight();
		this.operation = operation;		
	}

	/**
	 * 
	 * Use this constructor if you wish to operate only only a subset of pixels in the original image data. 
	 * 
	 * @param img The image buffer to pull data from.
	 * @param x The X Coordinate to start pulling data from.
	 * @param y The Y Coordinate to start pulling data from.
	 * @param w The Width of pixels to pull from the image data.
	 * @param h The height of pixels to pull from the image data.
	 * @param operation The operation to perform on each pixel.
	 */
	public OperationBatchJobDTO(BufferedImage img, int x, int y, int w, int h, PixelOperation operation) {
		pixels = img.getRGB(x, y, w, h, null, 0, w);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.operation = operation;
	}

	/**
	 * 
	 * <pre>
	 * Copy Constructor.
	 * 
	 * Just creates a copy of the original DTO.
	 * 
	 * Optionally, however, you may assign a new pixels array given pixels mutations happened outside of the DTO.
	 * If {@code pixels} is <b><i>null</i></b>, then the pixels within {@code original} will be copied.
	 * </pre>
	 * @param original The {@link OperationBatchJobDTO} to copy.
	 */
	public OperationBatchJobDTO(OperationBatchJobDTO original) {
		this.pixels = Arrays.copyOf(original.pixels, original.pixels.length);
		this.x = original.x;
		this.y = original.y;
		this.w = original.w;
		this.h = original.h;
		this.operation = original.operation;
	}

	public int[] getPixels() {
		return pixels;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public PixelOperation getOperation() {
		return operation;
	}

	public void setOperation(PixelOperation operation) {
		this.operation = operation;
	}

}
