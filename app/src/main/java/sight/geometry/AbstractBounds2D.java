package sight.geometry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractBounds2D implements Bounds {

	protected Point2D origin;

	private static Logger log = LogManager.getLogger("Calc");

	public AbstractBounds2D(Point2D origin) {
		this.origin = origin;
	}

	@Override
	public Point getOrigin() {
		return origin;
	}

	@Override 
	public boolean contains(Point p) {
		boolean doesContain = false;
		if (p instanceof Point2D) {
			doesContain = doesContain((Point2D) p);
		} else {
			log.warn(p + " is not of type <" + Point2D.class +">");
		}
		return doesContain;
	}

	protected abstract boolean doesContain(Point2D p);
}
