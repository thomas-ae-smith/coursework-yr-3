package Model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;


public abstract class ActivePart extends Part {

	protected GameObject parent;
	protected Point2D.Double centre;
	protected int radius;
	
	public abstract void update(double delta);

	public Point getLoc() {
		return new Point(getStartPoint().x + radius, getStartPoint().y + radius);
	}

	public int getRadius() {
		return radius;
	}
	
	@Override
	public void translate(Point delta_l) {
		super.translate(delta_l);
		centre.x += delta_l.x;
		centre.y += delta_l.y;
	}
	

	@Override
	public void render(Graphics2D g2D) {
	     Point2D.Double focus = new Point2D.Double(centre.x, centre.y - radius/2);
	     float[] dist = {0.0f, 1.0f};
	     Color[] colors = {g2D.getColor(), g2D.getColor().darker()};
	  
	     g2D.setPaint(new RadialGradientPaint(centre, radius, focus,
	                                 dist, colors,
	                                 CycleMethod.NO_CYCLE));
		g2D.fillOval((int) centre.x - radius, (int) centre.y - radius,
				radius * 2, radius * 2);
//		g2D.setColor(g2D.getColor().darker());
//		g2D.drawOval((int) centre.x - radius, (int) centre.y - radius,
//				radius * 2, radius * 2);
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}

	public abstract void reset();


}