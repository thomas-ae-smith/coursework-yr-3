package Model;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;


public class FinalComponent extends Component {
	
	public FinalComponent(Point start) {
		elements.add(new FinalPlatformPart(start));
		elements.add(new GapPart(this.getEndPoint(), 2 * Constants.WINDOW_WIDTH/Constants.TILE_WIDTH));
		
	}
	
	@Override
	public int rate() {
		return 0;
	}

	@Override
	public Object clone(boolean placeAtEnd) {
		if (placeAtEnd) return new FinalComponent(this.getEndPoint());
		return new FinalComponent(this.getStartPoint());
	}
	
	private class FinalPlatformPart extends FlatPlatformPart {

		private int tick = 0;
		
		public FinalPlatformPart(Point start) {
			super(start, 2);
		}
		
		@Override
		public void render(Graphics2D g2D) {
			super.render(g2D);
//			g2D.setColor(Color.BLUE);
			tick = (tick + 1) % (360*2);
			g2D.setPaint(new GradientPaint(new Point(0, 0), new Color(0, 0, 255, 0), new Point(0, start.y), new Color(.0f, .0f, 1.f, (float) ((Math.sin(Math.toRadians(tick/2))/4) + 0.75f) )));
			g2D.fillRect(start.x, 0, end.x - start.x, start.y);
		}
		
		@Override
		public Point collide(Player p) {
			Point result = super.collide(p);
				if (p.getLoc().x >= this.getStartPoint().x + Constants.TILE_WIDTH) p.setFinished(true);
			return result;			
		}

	}
	
}