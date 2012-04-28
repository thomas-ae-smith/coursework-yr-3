package Model;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public abstract class GameCollection<T extends GameObject> extends GameObject {

	ArrayList<T> elements;
	
	public GameCollection() {
		elements = new ArrayList<T>();
	}
	
	public void add(T t) {
		elements.add(t);
	}
	
	public T getLast() {
		return elements.get(elements.size()-1);
	}
	
	@Override
	public void render(Graphics2D g2d) {
		// call render on all children
		for (T element : elements) {
			element.render(g2d);
		}
	}
	
	@Override
	public void debugRender(Graphics2D g2d, int inset) {
		super.debugRender(g2d, inset);
		for (T element : elements) {
			element.debugRender(g2d, inset-1);
		}
	}

	@Override
	public Point collide(Player p) {
		if (p.getEndPoint().x >= getStartPoint().x && p.getStartPoint().x <= getEndPoint().x) {
			Point delta = null, test = null;
			for (T element : elements) {
				test = element.collide(p);
				//if the new collision moves the player higher it is 'safer' to accept
				if (delta == null || (test != null && test.y < delta.y)) delta = test; 
			}
			return delta;
		}
		else return null;
	}

	@Override
	public void translate(Point delta) {
		// call translate on all children
		for (T element : elements) {
			element.translate(delta);
		}
	}

//	@Override
//	public Object clone() {
//		// TODO
//		return null;
//	}

	@Override
	public Point getStartPoint() {
		if (!elements.isEmpty()) {
			return elements.get(0).getStartPoint();
		} else return new Point();
	}

	@Override
	public Point getEndPoint() {
		if (!elements.isEmpty()) {
			return elements.get(elements.size()-1).getEndPoint();
		} else return new Point();
	}
	
}