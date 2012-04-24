package Model;
import java.awt.Point;

public class GapComponent extends Component {
	public GapComponent(Point start) {
		elements.add(new FlatPlatformPart(start, 3));
		elements.add(new GapPart(this.getEndPoint(), 2));
	}
	
	@Override
	public Object clone(boolean placeAtEnd) {
		if (placeAtEnd) return new GapComponent(this.getEndPoint());
		return new GapComponent(this.getStartPoint());
	}
	
}