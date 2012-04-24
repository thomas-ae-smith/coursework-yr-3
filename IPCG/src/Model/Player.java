package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Player extends ActivePart {

	Point2D.Double velocity;
	boolean right, left, up, down, stop;
	boolean collided;

	public Player(int x, int y) {
		centre = new Point2D.Double(x, y);
		velocity = new Point2D.Double(0, 0);
		radius = Constants.PLAYER_RADIUS;
		start = new Point(x - radius, y - radius);
		end = new Point(x + radius, y + radius);
	}

	@Override
	public void render(Graphics2D g2D) {
		g2D.setColor(Color.GREEN);
		super.render(g2D);
		if (Constants.DRAW_DEBUG) {
			g2D.setColor(Color.RED);
			g2D.drawLine((int) centre.x, (int) centre.y,
					(int) (centre.x + velocity.x * 10),
					(int) (centre.y + velocity.y * 10));
		}
	}

	@Override
	public void update(double delta_t) {
		if (getStartPoint().y > Constants.WINDOW_HEIGHT) { // fallen off the
			// bottom of the
			// screen
			reset();
			return;
		}
		delta_t /= 10;
		if (collided && up)
			velocity.y = -15 * Constants.MAX_VELOCITY.y; // jump if on ground
		else
			velocity.y = Math.min(Constants.MAX_VELOCITY.y * 2, velocity.y
					+ Constants.MAX_VELOCITY.y * delta_t);
		collided = false;
		if (!stop) {
			if (right)
				velocity.x += Constants.MAX_VELOCITY.x;
			if (left)
				velocity.x -= Constants.MAX_VELOCITY.x;
		} else
			centre = new Point2D.Double(65, 65);

		centre.x += velocity.x * delta_t;
		centre.y += velocity.y * delta_t;
		velocity.x = 0;
		stop = false;
	}

	// called as a result of a collision
	@Override
	public void translate(Point delta_l) {
		super.translate(delta_l);
		collided = true;
	}

	@Override
	public void reset() {
		this.centre = new Point2D.Double(parent.getStartPoint().x
				+ Constants.TILE_WIDTH / 2, parent.getStartPoint().y
				- Constants.PLAYER_RADIUS);
		velocity = new Point2D.Double();
		collided = false;
	}

	// top-left
	@Override
	public Point getStartPoint() {
		return new Point((int) centre.x - radius, (int) centre.y - radius);
	}

	// bottom-right
	@Override
	public Point getEndPoint() {
		return new Point((int) centre.x + radius, (int) centre.y + radius);
	}

	@Override
	public Point collide(Player p) { return null; }

	@Override
	public void tweak() { }

	@Override
	public Object clone(boolean placeAtEnd) { return null; }

	@Override
	public float rate() { return 0;	}
}