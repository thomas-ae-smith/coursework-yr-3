package Model;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;

public class FlatPlatformPart extends Part {
	
	public FlatPlatformPart(Point start, int length) {
		this.start = start;
		this.end = new Point(start.x + length * Constants.TILE_WIDTH, start.y);
	}
	
	@Override
	public double rate() { return 0; }
	
	@Override
	public void render(Graphics2D g2D) {
//		g2D.setColor(Color.BLACK);
//		g2D.drawLine(start.x, start.y, end.x, end.y);
//		g2D.setColor(Color.BLACK);
//		g2D.drawLine(start.x, start.y+1, end.x, end.y+1);
//		g2D.setColor(Color.DARK_GRAY);
//		g2D.drawLine(start.x, start.y+2, end.x, end.y+2);
//		g2D.setColor(Color.DARK_GRAY);
//		g2D.drawLine(start.x, start.y+3, end.x, end.y+3);
		g2D.setPaint(new GradientPaint(start, Color.BLACK, new Point(start.x, start.y+5), Color.RED));
		g2D.fillRect(start.x, start.y, end.x-start.x, 5);
	}

	@Override
	public Point collide(Player p) {
		Point aLoc = p.getLoc();
//		System.out.println("aloc: " + aLoc);
		int aRad = p.getRadius();
		Point vec = new Point(aLoc.x - start.x, aLoc.y - start.y);
		if(aLoc.x < start.x && vec.x*vec.x + vec.y*vec.y < aRad*aRad ){
			//magnitude of vec
			double mag = Math.sqrt(vec.x*vec.x + vec.y*vec.y);
			double scale = aRad/mag - 1;  // |--a--|-b-|, a + ax = c, a(1+x) = c, x = c/a - 1
			return new Point((int)(vec.x * scale), (int)(vec.y * scale));
		}
//		System.out.println("aloc.x: " + aLoc.x + " start.x " + start.x + " end.x " + end.x);
		if(aLoc.x >= start.x && aLoc.x <= end.x) {
//			System.out.println("aloc.y: " + aLoc.y + " start.y " + start.y);
			if(start.y - aLoc.y < aRad && start.y - aLoc.y >= 0) return new Point(0, start.y - aLoc.y - aRad);
			if(aLoc.y - start.y < aRad && aLoc.y - start.y > 0) return new Point(0, start.y + aRad - aLoc.y);
		}
		vec = new Point(aLoc.x - end.x, aLoc.y - end.y);
		if(aLoc.x > end.x && vec.x*vec.x + vec.y*vec.y < aRad*aRad ){
			//magnitude of vec
			double mag = Math.sqrt(vec.x*vec.x + vec.y*vec.y);
			double scale = aRad/mag - 1;  // |--a--|-b-|, a + ax = c, a(1+x) = c, x = c/a - 1
			return new Point((int)(vec.x * scale), (int)(vec.y * scale));
		}
		return null;
	}

	@Override
	public void tweak() {
		// TODO 
		
	}

	@Override
	public Object clone(boolean placeAtEnd) {
		int length = this.getEndPoint().x - this.getStartPoint().x;
		if (placeAtEnd)	return new FlatPlatformPart(this.getEndPoint(), length);
		return new FlatPlatformPart(this.getStartPoint(), length);
	}
	
}