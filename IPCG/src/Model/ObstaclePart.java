package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ObstaclePart extends ActivePart {

	public static ArrayList<ObstaclePart> all = new ArrayList<ObstaclePart>();
	private int dir = 1;
	private boolean vertical;
	int dist;

	public ObstaclePart(GameObject parent, int height, boolean vertical) {
		all.add(this);
		this.setParent(parent);
		this.radius = Constants.OBSTACLE_RADIUS;
		this.vertical = vertical;
		
		rating = ObstaclePart.calculateRating(0, height);
		dist = ObstaclePart.calculateDist(0, height);
		
		Point midPoint = new Point(
				(parent.getStartPoint().x + parent.getEndPoint().x) / 2,
				(parent.getStartPoint().y + parent.getEndPoint().y) / 2);
		
//		if (vertical) {
//			start = new Point(midPoint.x, midPoint.y
//					- height*Constants.TILE_HEIGHT);
//			end = new Point(midPoint.x, midPoint.y - Constants.TILE_HEIGHT);
//		} else {
//			start = new Point(parent.getStartPoint().x + radius, midPoint.y
//					- radius - height*Constants.TILE_HEIGHT);
//			end = new Point(parent.getEndPoint().x - radius, midPoint.y
//					- radius - height*Constants.TILE_HEIGHT);
//		}
//		centre = new Point2D.Double(end.x, end.y);
		
		centre = new Point2D.Double(midPoint.x, midPoint.y + height*Constants.TILE_HEIGHT);
	}

	@Override
	public void update(double delta_t) {
//		if (vertical) {
//			centre.y -= dir * delta_t * Constants.OBSTACLE_BASE_SPEED;
//			if (centre.y <= start.y) dir = -1;
//			if (centre.y >= end.y) dir = 1;
//		} else {
//			centre.x -= dir * delta_t * Constants.OBSTACLE_BASE_SPEED;
//			if (centre.x <= start.x) dir = -1;
//			if (centre.x >= end.x) dir = 1;
//		}
	}

	@Override
	public void render(Graphics2D g2D) {
		g2D.setColor(Color.RED);
		super.render(g2D);
	}

	@Override
	public void debugRender(Graphics2D g2D, int inset) {
		g2D.setColor(Color.RED);
//		g2D.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public Point collide(Player p) {
		Point pLoc = p.getLoc();
		int pRad = p.getRadius();
		if (pLoc.x - pRad - radius < centre.x
				&& pLoc.x + pRad + radius > centre.x) { // if there is a
														// potential collision
			if (Math.sqrt(Math.pow(pLoc.x - centre.x, 2)
					+ Math.pow(pLoc.y - centre.y, 2)) < pRad + radius) {
				return new Point(1, 1); // this should be the necessary
										// displacement, but as contact results
										// in reset...
			}
		}
		return null;
	}

	@Override
	public void tweak() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone(boolean placeAtEnd) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int rate() {
		return rating;
	}

	private static int calculateDist(int length, int height) {
		double t2 = Math.sqrt(2f*(Constants.JUMP_DIST.y + height*Constants.TILE_HEIGHT - Constants.OBSTACLE_RADIUS)/Constants.GRAVITY);	//t = (2s/a)^(1/2) time spent above obstacle
//		System.out.println(t2);
		int dist = (int)((t2+t2)*Constants.MAX_VELOCITY);	//distance a player can press jump in and still clear the gap
//		System.out.println(dist);
		return Math.min(dist, Constants.JUMP_DIST.x);									//limit dist to at most a singel jump's worth before the launch point
	}
	
	public static int calculateRating(int length, int height) {
		return calculateDist(length, height)*1000/Constants.MAX_VELOCITY - 15*length;								//converted to time in milliseconds
	}

	@Override
	public void reset() {
		centre = new Point2D.Double(end.x, end.y);
		dir = 1;
	}
}