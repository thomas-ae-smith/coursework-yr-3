import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Level implements Drawable, Collideable {

	private ArrayList<Pattern> patterns;
	
	@Override
	public void cInit() { cObjs.add(this); }

	@Override
	public Point collide(Active a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dInit() { dObjs.add(this); }

	@Override
	public void render(Graphics2D g2d) { 
		for (Pattern p : patterns) {
			p.render(g2d);
		}
	}
	
}