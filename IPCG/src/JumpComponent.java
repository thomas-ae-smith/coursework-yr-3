import java.awt.Point;

public class JumpComponent extends Component {
	public JumpComponent(Point start) {
		elements.add(new FlatPlatformPart(start, 400));
		elements.add(new FlatPlatformPart(new Point(start.x + 400, start.y), 400));
	}
}