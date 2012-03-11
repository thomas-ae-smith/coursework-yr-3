import java.awt.Point;

public interface Active {
	public void update(double delta);
	public Point getLoc();
	public int getRadius();
}