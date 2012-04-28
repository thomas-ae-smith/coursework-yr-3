package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class GapPart extends Part {
	int dist;
	
	public GapPart(Point start, int length) {
		this(start, length, 0);
	}
	
	public GapPart(Point start, int length, int height) {
		this.start = start;
		this.end = new Point(start.x + length * Constants.TILE_WIDTH, start.y + height * Constants.TILE_HEIGHT);
		rating = GapPart.calculateRating(length, height);
		dist = GapPart.calculateDist(length, height);
	}
	
	@Override
	public int rate() {
		return rating;
	}
	
	private static int calculateDist(int length, int height) {
		double t1 = 112f/Constants.MAX_VELOCITY;		//time to apex
		double t2 = Math.sqrt(2f*(height*Constants.TILE_HEIGHT+Constants.JUMP_DIST.y)/Constants.GRAVITY);	//t = (2s/a)^(1/2) time from apex to platform
		int dist = (int)((t1+t2)*Constants.MAX_VELOCITY)-(length * Constants.TILE_WIDTH);	//distance a player can press jump in and still clear the gap
		return Math.min(dist, Constants.JUMP_DIST.x);									//limit dist to at most a singel jump's worth before the launch point
	}
	public static int calculateRating(int length, int height) {
		return calculateDist(length, height)*1000/Constants.MAX_VELOCITY;								//converted to time in milliseconds
	}
	
	@Override
	public void render(Graphics2D g2D) { } //gaps need no rendering
	
	@Override
	public void debugRender(Graphics2D g2D, int inset) {
		super.debugRender(g2D, inset);
		g2D.setColor(Color.YELLOW);
		g2D.drawLine(start.x, start.y, start.x - dist, start.y);
	}

	@Override
	public Point collide(Player p) {
		return null;						//can't collide with a gap
	}

	@Override
	public void tweak() {
		// TODO 
		
	}

	@Override
	public Object clone(boolean placeAtEnd) {
		int length = this.getEndPoint().x - this.getStartPoint().x;
		int height = this.getEndPoint().y - this.getStartPoint().y;
		if (placeAtEnd)	return new GapPart(this.getEndPoint(), length, height);
		return new GapPart(this.getStartPoint(), length, height);
	}
	
}