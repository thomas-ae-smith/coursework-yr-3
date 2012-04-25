package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;


public class FinalComponent extends Component {
	
	public FinalComponent(Point start) {
		elements.add(new FinalPlatformPart(start));
		elements.add(new FlatPlatformPart(this.getEndPoint(), 2 * Constants.WINDOW_WIDTH/Constants.TILE_WIDTH));
		
	}

	@Override
	public Object clone(boolean placeAtEnd) {
		if (placeAtEnd) return new FinalComponent(this.getEndPoint());
		return new FinalComponent(this.getStartPoint());
	}
	
	private class FinalPlatformPart extends FlatPlatformPart {

		public FinalPlatformPart(Point start) {
			super(start, 2);
		}
		
		@Override
		public void render(Graphics2D g2D) {
			super.render(g2D);
			g2D.setColor(Color.BLUE);
			g2D.fillRect(start.x, 0, end.x - start.x, start.y);
		}
		
		@Override
		public Point collide(Player p) {
			Point result = super.collide(p);
			if (result != null) {
				if (p.getLoc().x >= this.getStartPoint().x + Constants.TILE_WIDTH) p.setFinished(true);
			}
			return result;			
		}

	}
	
}