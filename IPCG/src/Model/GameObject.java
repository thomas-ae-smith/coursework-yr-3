package Model;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class GameObject {
	public abstract void render(Graphics2D g2D);

	public void debugRender(Graphics2D g2D, int inset) {
		g2D.setStroke(new BasicStroke(2 * inset));
		g2D.setColor(Constants.DEBUG_COLOURS[inset]);
		g2D.drawRect(getStartPoint().x, 0, getEndPoint().x - getStartPoint().x,
				Constants.WINDOW_HEIGHT);
	}

	public abstract Point collide(Player p);

	public abstract void tweak();

	public abstract void translate(Point delta_l);

	public abstract Object clone(boolean placeAtEnd);

	public abstract double rate();

	public abstract Point getStartPoint();

	public abstract Point getEndPoint();
}