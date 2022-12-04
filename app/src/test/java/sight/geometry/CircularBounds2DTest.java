package sight.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CircularBounds2DTest {
	
	@Test
	public void testWithinBounds() {
		Point2D origin = new Point2D(5, 5);
		int radius = 5;
		CircularBounds2D bounds = new CircularBounds2D(origin, radius);

		Point2D outside = new Point2D(1, 1);
		Point2D inside = new Point2D(4, 4);

		boolean contains = bounds.contains(outside);
		assertFalse(contains);
		contains = bounds.contains(inside);
		assertTrue(contains);
	}

}
