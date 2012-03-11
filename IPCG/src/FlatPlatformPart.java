import java.awt.Graphics2D;
import java.awt.Point;

public class FlatPlatformPart implements Part {

	Point start, end;
	
	public FlatPlatformPart(int start, int length, int height) {
		this.start = new Point(start, height);
		this.end = new Point(start + length, height);
		cInit();
		dInit();
	}
	
	@Override
	public float getDifficultyRating() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void render(Graphics2D g2D) {
		g2D.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public Point collide(Active a) {
		Point aLoc = a.getLoc();
//		System.out.println("aloc: " + aLoc);
		int aRad = a.getRadius();
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
	public void cInit() { cObjs.add(this); }

	@Override
	public void dInit() { dObjs.add(this); }

	@Override
	public Point getStartPoint() { return start; }

	@Override
	public Point getEndPoint() { return end; }
	
}