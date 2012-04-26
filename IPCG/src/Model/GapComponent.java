package Model;
import java.awt.Point;

public class GapComponent extends Component {
	private int xDiff, yDiff;
	
	private GapComponent(Point start, int xDiff, int yDiff) {
		this.xDiff = xDiff;
		this.yDiff = yDiff;
		elements.add(new FlatPlatformPart(start, 3));
		elements.add(new GapPart(this.getEndPoint(), xDiff, yDiff));
	}
	
	@Override
	public Object clone(boolean placeAtEnd) {
		if (placeAtEnd) return new GapComponent(this.getEndPoint(), xDiff, yDiff);
		return new GapComponent(this.getStartPoint(), xDiff, yDiff);
	}
	
	public static GapComponent step(Point start) {
		return new GapComponent(start, 0, 0);
	}

	public static GapComponent hop(Point start) {
		return new GapComponent(start, 1, 0);
	}
	
	public static GapComponent skip(Point start) {
		return new GapComponent(start, 2, 0);
	}
	
	public static GapComponent jump(Point start) {
		return new GapComponent(start, 3, 0);
	}
	
	public static GapComponent leap(Point start) {
		return new GapComponent(start, 4, 5);
	}
}