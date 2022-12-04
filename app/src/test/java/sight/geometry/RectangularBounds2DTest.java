package sight.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class RectangularBounds2DTest {

	private static Logger log = LogManager.getLogger("Calc");
	
	@Test
	public void testContainsPoint() {
		Point2D origin = new Point2D(10, 10);
		Point2D endPoint = new Point2D(20, 20);

		RectangularBounds2D bounds = new RectangularBounds2D(origin, endPoint);
		assertTrue(bounds.getWidth() == 10);
		assertTrue(bounds.getHeight() == 10);

		log.info("Width: " + bounds.getWidth() + ", Height: " + bounds.getHeight());

		Point2D outside = new Point2D(9, 10);
		Point2D inside = new Point2D(15, 15);

		boolean contains = bounds.contains(outside);
		assertFalse(contains);
		contains = bounds.contains(inside);
		assertTrue(contains);
	}
}
