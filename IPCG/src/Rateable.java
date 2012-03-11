import java.awt.Point;

public interface Rateable {
	//all rateable objects have a rating, even if just composed from children
	public float getDifficultyRating();
	
	//rateable objects must have a start and end
	public Point getStartPoint();
	public Point getEndPoint();
}