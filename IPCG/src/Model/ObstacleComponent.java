package Model;
import java.awt.Point;

public class ObstacleComponent extends Component {
	public ObstacleComponent(Point start) {
		elements.add(new FlatPlatformPart(start, 3));
		FlatPlatformPart obstaclePlatform = new FlatPlatformPart(getLast().getEndPoint(), 3);
		elements.add(new ObstaclePart(obstaclePlatform, true));
		elements.add(obstaclePlatform);
	}
	
	@Override
	public Object clone(boolean placeAtEnd) {
		if (placeAtEnd) return new ObstacleComponent(this.getEndPoint());
		return new ObstacleComponent(this.getStartPoint());
	}
	
}