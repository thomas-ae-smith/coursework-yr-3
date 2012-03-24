//refactor into acivepart class, with internal list of members
import java.awt.Point;

public interface Active {
	public void update(double delta);
	public Point getLoc();
	public int getRadius();
}