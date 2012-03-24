import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Player implements Active {

	Point2D.Double centre;
	Point2D.Double velocity;
	int radius;
	boolean right, left, up, down, stop;
	boolean collided;
	
	public Player(int x, int y) {
		centre = new Point2D.Double(x, y);
		velocity = new Point2D.Double(0, 0);
		radius = 20;
	}
	
	public void render(Graphics2D g2D) {
		g2D.fillOval((int)centre.x - radius, (int)centre.y - radius, radius*2, radius*2);
		g2D.setColor(Color.RED);
		g2D.drawLine((int)centre.x, (int)centre.y, (int)(centre.x + velocity.x*10), (int)(centre.y + velocity.y*10));
	}

	@Override
	public void update(double delta) {
		
		delta /= 10;
		if (collided) {
			if (up) velocity.y = -15*Constants.VELOCITY.y;
		}
		else velocity.y = Math.min(Constants.VELOCITY.y*2, velocity.y + Constants.VELOCITY.y*delta);
		collided = false;
		if (!stop) {
			if (right) velocity.x += Constants.VELOCITY.x;
			if (left) velocity.x -= Constants.VELOCITY.x;
		} else centre = new Point2D.Double(65, 65);
		
		centre.x += velocity.x * delta;
		centre.y += velocity.y * delta;
		velocity.x = 0;
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

	//called as a result of a collision
	public void translate(Point delta) {
		centre.x += delta.x;
		centre.y += delta.y;
		collided = true;
	}
	
	// top-left
	public Point getStartPoint() {
		return new Point((int)centre.x - radius, (int)centre.y - radius);
	}
	
	// bottom-right
	public Point getEndPoint() {
		return new Point((int)centre.x + radius, (int)centre.y + radius);
	}
}