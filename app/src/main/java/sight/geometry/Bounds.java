package sight.geometry;

/*
 * An interface that would represent some area in coordinate space. </br>
 */
public interface Bounds {
	
	/**
	 * The point in space this Bounds originates from.
	 */
	public Point getOrigin();
	
	/**
	 * Will check if the given {@link Point} is contained in this {@link Bounds}.
	 * 
	 * @param p The {@link Point} that is being evaluated against the area of the {@link Bounds}. </br>
	 * @return True if the {@link Point} is within the area of the {@link Bounds}.
	 * 					False if the {@link Point} is <b>NOT</b> within the area of the {@link Bounds}.
	 */
	public boolean contains(Point p);

}
