package sight.utils;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.awt.image.BufferedImage;

import sight.image.pixel.Pixel;
import sight.image.pixel.operations.PixelOperation;

public class OperationBatchJob implements Callable<OperationBatchJobDTO> {

	private OperationBatchJobDTO jobData;

	public OperationBatchJob(OperationBatchJobDTO jobData) {
		this.jobData = jobData;
	}

	@Override
	public OperationBatchJobDTO call() throws Exception {
		OperationBatchJobDTO resultDto = new OperationBatchJobDTO(jobData);
		int[] pixels = resultDto.getPixels();
		for (int y = 0; y < resultDto.getH(); y++) {
			for (int x = 0; x < resultDto.getW(); x++) {
				int currentPixel = x + (y * resultDto.getW());
				// There's currently a bug where the last batch in OperationBatchHandler causes this to go out of bounds once...
				// Not sure why...
				if (currentPixel < pixels.length) {
					Pixel p = ImageUtils.getPixelFrom32BitInt(pixels[currentPixel]);
					p = resultDto.getOperation().operate(p);
					pixels[currentPixel] = ImageUtils.convertPixelTo32BitInt(p);
				} else {
					System.out.println("Uh oh, we went outside of the range.");
					break;
				}
			}
		}
		return resultDto;
	}
	
}
