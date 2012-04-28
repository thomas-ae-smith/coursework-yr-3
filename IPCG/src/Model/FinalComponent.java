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
	
}