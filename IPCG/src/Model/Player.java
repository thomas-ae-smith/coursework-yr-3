package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Player extends ActivePart {

	Point2D.Double velocity;
	boolean right, left, up, down, stop;
	boolean collided;
	private boolean finished;
	private boolean lastCollided;

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
					(int) (centre.x + velocity.x / 10),
					(int) (centre.y + velocity.y / 10));
		}
	}

	@Override
	public void update(double delta_t) {
		lastCollided = collided;
		if (getStartPoint().y > Constants.WINDOW_HEIGHT) { // fallen off the
			// bottom of the
			// screen
			reset();
			return;
		}
//		delta_t *= 100;
		if (collided && up)
			velocity.y = -112f * Constants.MAX_VELOCITY.y/(Constants.MAX_VELOCITY.x); // jump if on ground, see Constants for maths
		else if (!collided)
			velocity.y += Constants.MAX_VELOCITY.y * delta_t;
		else
			velocity.y = 0;
		collided = false;
		if (right)
			velocity.x += Constants.MAX_VELOCITY.x;
		if (left)
			velocity.x -= Constants.MAX_VELOCITY.x;

		centre.x += velocity.x * delta_t;
		centre.y += velocity.y * delta_t;
		velocity.x = 0;
		stop = false;
	}

	// called as a result of a collision
	@Override
	public void translate(Point delta_l) {
		super.translate(delta_l);
		if(lastCollided || (delta_l.y*delta_l.y > delta_l.x*delta_l.x && delta_l.y < 0))
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
	public double rate() { return 0;	}

	public void setFinished(boolean b) {
		this.finished = b;
		//starttimer
		
	}
	public boolean getFinished() { return finished; }
}

/* getResetLoc() return parent.loc + offset
on kill, set deathVelocity() to 0.5sec movement
isDead() return (deathVelocity!=null)
render: draw blue dot, lifeline if isDead()
don't collide or translate if dead
only set parent if new is further right. on set parent, callback evaluator*/