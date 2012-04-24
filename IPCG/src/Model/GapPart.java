package Model;

import java.awt.Graphics2D;
import java.awt.Point;

public class GapPart extends Part {
	
	public GapPart(Point start, int length) {
		this.start = start;
		this.end = new Point(start.x + length * Constants.TILE_WIDTH, start.y + 0);		
	}
	
	public GapPart(Point start, int length, int height) {
		this.start = start;
		this.end = new Point(start.x + length * Constants.TILE_WIDTH, start.y + height);
	}
	
	@Override
	public float rate() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void render(Graphics2D g2D) { } //gaps need no rendering

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