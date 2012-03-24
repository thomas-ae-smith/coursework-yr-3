import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class GameObject {
	public abstract void render(Graphics2D g2d);
	
	public void debugRender(Graphics2D g2d, int inset) {
		g2d.setStroke(new BasicStroke(2*inset));
		g2d.setColor(Constants.DEBUG_COLOURS[inset]);
		g2d.drawRect(getStartPoint().x, 0, getEndPoint().x-getStartPoint().x, Constants.WINDOW_HEIGHT);
	}
	
	public abstract Point collide(Player p);
	
	public abstract void tweak();
	
	public abstract void translate(Point delta);
	
	public abstract Object clone();
	
	public abstract float rate();
	
	public abstract Point getStartPoint();
	
	public abstract Point getEndPoint();
}