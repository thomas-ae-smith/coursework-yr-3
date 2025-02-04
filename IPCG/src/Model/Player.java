package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import Core.Evaluator;

public class Player extends ActivePart {

	Point2D.Double velocity;
	Point resetLoc;
	public boolean right;
	public boolean left;
	public boolean up;
	public boolean down;
	public boolean stop;
	boolean collided;
	private boolean finished;
	private boolean lastCollided;
	private Evaluator eval;
	private Point2D.Double deathVel = null;
	private float alpha = 0.5f;

	public Player(Pattern parent, Evaluator eval) {
		this.setParent(parent);
		centre = new Point2D.Double(resetLoc.x, resetLoc.y);
		velocity = new Point2D.Double(0, 0);
		radius = Constants.PLAYER_RADIUS;
		start = new Point(resetLoc.x - radius, resetLoc.y - radius);
		end = new Point(resetLoc.x + radius, resetLoc.y + radius);
		this.eval = eval;
	}

	@Override
	public void render(Graphics2D g2D) {
		g2D.setColor(new Color(0, 0, 1, alpha));
		g2D.fillOval(resetLoc.x - radius/2, resetLoc.y - radius/2,
				radius, radius);
		if (deathVel != null) {
			g2D.drawLine(resetLoc.x, resetLoc.y, (int)centre.x, (int)centre.y);
			g2D.fillOval((int) centre.x - radius - 1, (int) centre.y - radius - 1, radius * 2 + 2, radius * 2 + 2);
		}
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
		if (deathVel != null) {
			centre.x += deathVel.x * delta_t;
			centre.y += deathVel.y * delta_t;
			if ((resetLoc.y - centre.y) * deathVel.y < 0) deathVel = null;
			return;
		}
		if (getStartPoint().y > Constants.WINDOW_HEIGHT) { // fallen off the
			// bottom of the
			// screen
			reset();
			return;
		}
//		delta_t *= 100;
		if (collided && up)
			velocity.y = -112f * Constants.GRAVITY/(Constants.MAX_VELOCITY); // jump if on ground, see Constants for maths
		else if (!collided)
			velocity.y += Constants.GRAVITY * delta_t;
		else
			velocity.y = 0;
		collided = false;
//		if (right)
			velocity.x += Constants.MAX_VELOCITY;
//		if (left)
//			velocity.x -= Constants.MAX_VELOCITY;

		centre.x += velocity.x * delta_t;
		centre.y += velocity.y * delta_t;
		velocity.x = 0;
		stop = false;
	}

	// called as a result of a collision
	@Override
	public void translate(Point delta_l) {
		if (deathVel != null) return;
		super.translate(delta_l);
		if(lastCollided || (delta_l.y*delta_l.y > delta_l.x*delta_l.x && delta_l.y < 0))
			collided = true;
	}

	@Override
	public void reset() {
		if (deathVel == null) {
			eval.addNegative(parent.rate());
//					this.centre = new Point2D.Double(resetLoc.x, resetLoc.y);
			deathVel = new Point2D.Double(2*(resetLoc.x - centre.x), 2*(resetLoc.y - centre.y));
			velocity = new Point2D.Double();
			collided = false;
		}
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
	public int rate() { return 0; }
	
	@Override
	public void setParent(GameObject parent) {
		if (this.parent == null || (this.centre.x - Constants.TILE_WIDTH/2 > parent.getStartPoint().x && parent.getStartPoint().x > this.parent.getStartPoint().x)) {	//only reparent if further right
			if (this.parent != null) eval.addPositive(this.parent.rate());
			super.setParent(parent);
			resetLoc = new Point(parent.getStartPoint().x
					+ Constants.TILE_WIDTH / 2, parent.getStartPoint().y
					- Constants.PLAYER_RADIUS);		
		}
	}

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