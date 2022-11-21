package sight.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OperationBatchHandler {

	private static final ExecutorService BATCH_EXECUTOR = Executors.newFixedThreadPool(8);

	public OperationBatchJobDTO batchOperations(OperationBatchJobDTO jobDto, int numBatches) {
		OperationBatchJobDTO result = new OperationBatchJobDTO(jobDto);

		int[] pixels 	= result.getPixels();
		int width 		= result.getW();
		int height 		= result.getH();
		int xStart 		= result.getX();
		int yStart 		= result.getY();

		final List<Future<OperationBatchJobDTO>> futures = new ArrayList<Future<OperationBatchJobDTO>>();

		final int xBatchSize = width / numBatches;
		final int yBatchSize = height / numBatches;

		int actualBatches = (xBatchSize * yBatchSize) % numBatches == 0 ? numBatches : numBatches + 1;

		for (int i = 0; i < actualBatches; i++) {
			final int start = (xStart + (i * xBatchSize)) * (yStart + (i * yBatchSize));
			final int end = Math.min(pixels.length, (xStart + ((i + 1) * xBatchSize)) * (yStart + ((i + 1) * yBatchSize)));
			final int[] pixelsBatch = Arrays.copyOfRange(pixels, start, end);

			futures.add(BATCH_EXECUTOR.submit(new OperationBatchJob(
				new OperationBatchJobDTO(
					pixelsBatch,
					start,
					end,
					xStart, 
					yStart, 
					xBatchSize, 
					yBatchSize, 
					jobDto.getOperation()
				)
			)));
		}

		for (Future<OperationBatchJobDTO> f : futures) {
			if (f != null) {
				try {
					OperationBatchJobDTO currentDto = f.get();
					int copyLen = currentDto.getOriginalDataEnd() - currentDto.getOriginalDataStart();
					System.arraycopy(currentDto.getPixels(), 0, pixels, currentDto.getOriginalDataStart(), copyLen);
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

}
