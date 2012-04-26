package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class GapPart extends Part {
	
	public GapPart(Point start, int length) {
		this.start = start;
		this.end = new Point(start.x + length * Constants.TILE_WIDTH, start.y + 0);	
		this.calculateRating();
	}
	
	public GapPart(Point start, int length, int height) {
		this.start = start;
		this.end = new Point(start.x + length * Constants.TILE_WIDTH, start.y + height * Constants.TILE_HEIGHT);
		this.calculateRating();
	}
	
	@Override
	public double rate() {
		return rating;
	}
	
	private void calculateRating() {
		double t1 = 112/Constants.MAX_VELOCITY.x;		//time to apex
		int apex = start.y - 112;			//112 = 7*tileheight
		double t2 = Math.sqrt(2*(end.y - apex)/Constants.MAX_VELOCITY.y);	//t = (2s/a)^(1/2) time from apex to platform
		int dist = (int)((t1+t2)*Constants.MAX_VELOCITY.x)-(end.x - start.x);
		rating = dist;
	}
	@Override
	public void render(Graphics2D g2D) { } //gaps need no rendering
	
	@Override
	public void debugRender(Graphics2D g2D, int inset) {
		super.debugRender(g2D, inset);
		g2D.setColor(Color.YELLOW);
		g2D.drawLine(start.x, start.y, start.x - (int)rating, start.y);
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