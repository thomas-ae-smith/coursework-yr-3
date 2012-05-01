package Model;
import java.awt.Point;

public class ObstacleComponent extends Component {
	
	public ObstacleComponent(Point start, int xDiff, int yDiff) {
		this.xDiff = xDiff;
		this.yDiff = yDiff;
		elements.add(new FlatPlatformPart(start, 3));
		FlatPlatformPart obstaclePlatform = new FlatPlatformPart(getLast().getEndPoint(), xDiff);
		for (; yDiff <= 0; yDiff += 2) {
			elements.add(new ObstaclePart(obstaclePlatform, yDiff, true));
		}
		elements.add(obstaclePlatform);
	}
	
	@Override
	public Object clone(boolean placeAtEnd) {
		if (placeAtEnd) return new ObstacleComponent(this.getEndPoint(), xDiff, yDiff);
		return new ObstacleComponent(this.getStartPoint(), xDiff, yDiff);
	}
	
	@Override
	public int rate() {
		int rating = 0;
		for (Part element : elements) {
			rating = Math.min(element.rate(), rating);
		}
		return rating;
	}
	
}