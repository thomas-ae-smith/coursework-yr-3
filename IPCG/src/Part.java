import java.awt.Point;

// A part is the lowest level item in the heirarchy. It is concrete and collideable
public abstract class Part extends GameObject {
	protected Point start, end;
	
	@Override
	public void translate(Point delta) {
		start.x += delta.x;
		start.y += delta.y;
		end.x += delta.x;
		end.y += delta.y;
	}
	
	@Override
	public Point getStartPoint() { return start; }

	@Override
	public Point getEndPoint() { return end; }
	
}