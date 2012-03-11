import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;

public class Player implements  Drawable, Active {

	static final double ACCEL = 0.5;
	static final double MAX_VEL = 4;
	Point2D.Double centre;
	Point2D.Double velocity;
	int radius;
	boolean right, left, up, down, stop;
	boolean collided;
	
	Collection<Collideable> collideables; //temp
	
	public Player(Collection<Collideable> collideables) {
		centre = new Point2D.Double(65, 65);
		velocity = new Point2D.Double(0, 0);
		radius = 20;
		this.collideables = collideables;
		dInit();
	}
	
	@Override
	public void render(Graphics2D g2D) {
		g2D.fillOval((int)centre.x - radius, (int)centre.y - radius, radius*2, radius*2);
		g2D.setColor(Color.RED);
		g2D.drawLine((int)centre.x, (int)centre.y, (int)(centre.x + velocity.x*10), (int)(centre.y + velocity.y*10));
	}

	@Override
	public void update(double delta) {
		
//System.out.println(delta);
		delta /= 10;
//System.out.println(delta);
		if (right) velocity.x += delta * ACCEL;
		if (left) velocity.x -= delta * ACCEL;
//		if (up) velocity.y -= delta * ACCEL;
//		if (down) velocity.y += delta * ACCEL;
		if (stop && velocity.x == 0 && velocity.y == 0) centre = new Point2D.Double(65, 65);
		if (stop) velocity = new Point2D.Double(0,0);

		for (Iterator<Collideable> iterator = collideables.iterator(); iterator.hasNext();) {
			Collideable collide = iterator.next();

			Point bounce = collide.collide(this);
			if (bounce != null) {
				System.out.println(bounce);
				centre.x += bounce.x;
				centre.y += bounce.y;
				if (bounce.x != 0) velocity.x *= -0.8;
				if (bounce.y != 0) velocity.y *= -0;
			}
		}
		velocity.y += delta * ACCEL * 0.2;
		if (stop) velocity.y -= delta * ACCEL * 15;

		double mag = velocity.x*velocity.x + velocity.y*velocity.y;
		if (mag > MAX_VEL * MAX_VEL) {
			mag = Math.sqrt(mag);
			velocity.x *= MAX_VEL / mag;
			velocity.y *= MAX_VEL / mag;
		}
		
		centre.x += velocity.x * delta;
		centre.y += velocity.y * delta;
		stop = false;
	}

	@Override
	public Point getLoc() {
		return new Point((int)centre.x, (int)centre.y);
	}

	@Override
	public int getRadius() {
		return radius;
	}

	@Override
	public void dInit() { dObjs.add(this); }
	
}