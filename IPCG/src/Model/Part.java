package Model;
import java.awt.Point;

// A part is the lowest level item in the heirarchy. It is concrete and collideable
public abstract class Part extends GameObject {
	protected Point start, end;
	protected double rating;
	
	@Override
	public void translate(Point delta_l) {
		start.x += delta_l.x;
		start.y += delta_l.y;
		end.x += delta_l.x;
		end.y += delta_l.y;
	}
	
	@Override
	public Point getStartPoint() { return start; }

	@Override
	public Point getEndPoint() { return end; }
	
}