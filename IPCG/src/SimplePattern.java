import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class SimplePattern implements Pattern {
	private ArrayList<Component> components;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getDifficultyRating() {
		float total = 0;
		for (Component c : components) {
			total += c.getDifficultyRating();
		}
		// TODO correct this caclulation
		return total;
	}

	@Override
	public Point getStartPoint() {
		return components.get(0).getStartPoint();
	}

	@Override
	public Point getEndPoint() {
		return components.get(components.size()).getEndPoint();
	}
	
}