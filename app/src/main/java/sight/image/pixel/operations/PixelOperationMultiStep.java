package sight.image.pixel.operations;

import java.util.Collection;

import sight.image.pixel.Pixel;

/**
 * Additive operation that manipulates the same pixel with multiple operations one after the other. </br>
 * The pixel given to {@code operate(Pixel)} will be left untouched, and a new {@link Pixel} instance will be returned.
 */
public class PixelOperationMultiStep implements PixelOperation {

	Collection<PixelOperation> operationsToPerform;

	public PixelOperationMultiStep(Collection<PixelOperation> operationsToPerform) {
		this.operationsToPerform = operationsToPerform;
	}

	@Override
	public Pixel operate(Pixel p) {
		Pixel pixel = new Pixel(p);
		if (operationsToPerform != null && !operationsToPerform.isEmpty()) {
			for (PixelOperation operation : operationsToPerform) {
				pixel = operation.operate(pixel);
			}
		}
		return pixel;
	}
	
}
