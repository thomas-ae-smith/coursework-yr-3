package Model;
// needs to own a Canvas, the player and all of the other essentials. possibly be runnable...

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Iterator;



public class Level extends GameCollection<Pattern> {

	public Level() {
		elements.add(new SinglePattern(new FlatComponent(new Point(0, Constants.TILE_HEIGHT*7))));
	}
	
	@Override
	public void tweak() { }

	@Override
	public double rate() { 
		double total = 0;
		for (Pattern p : elements) {
			total += p.rate();
		}
		total /= elements.size();
		return total; 
	}

	@Override
	public Point collide(Player p) {
		boolean collided = false;
		for (ObstaclePart a : ObstaclePart.all) {
			if (collided = (a.collide(p) != null)) break;
		}
		
		if (collided) p.reset();
		
		Point delta_l;
		if ((delta_l = super.collide(p)) != null) p.translate(delta_l);
		return null;
	}

	public void update(double delta_t) {
		for (ObstaclePart o : ObstaclePart.all) {
			o.update(delta_t);
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		super.render(g2d);
		if (Constants.DRAW_DEBUG) debugRender(g2d, 4);
	}

	@Override
	public Object clone(boolean b) {
		// Level should be uncloneable
		return null;
	}
	
}