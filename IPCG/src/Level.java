// needs to own a Canvas, the player and all of the other essentials. possibly be runnable...
import java.awt.Graphics2D;
import java.awt.Point;


public class Level extends GameCollection<Pattern> {

	public Level() {
		elements.add(new SimplePattern());
	}
	
	@Override
	public void tweak() { }

	@Override
	public float rate() { return 0; }

	@Override
	public Point collide(Player p) {
		boolean collided = false;
//		for (ActivePart a : ActivePart.all) {
//			if (collided = (a.collide(p) != null)) break;
//		}
		
		if (collided) return new Point();	// TODO some displacement back to origin
		
		Point delta;
		if ((delta = super.collide(p)) != null) p.translate(delta);
		return null;
	}

	public void update(double delta) {
//		for (ActivePart a : ActivePart.all) {
//			if (collided = (a.collide(p) != null)) break;
//		}
	}

	@Override
	public void render(Graphics2D g2d) {
		super.render(g2d);
		if (Constants.DEBUG) debugRender(g2d, 4);
	}
	
}