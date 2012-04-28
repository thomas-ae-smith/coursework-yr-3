package Model;
import java.awt.Point;

// A part is the lowest level item in the heirarchy. It is concrete and collideable
public abstract class Part extends GameObject {
	protected Point start, end;
	protected int rating;
	
	static int calculateRating(int x, int y) {
		return 0;
	}
		
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