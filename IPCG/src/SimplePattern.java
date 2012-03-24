import java.awt.Point;

public class SimplePattern extends Pattern {
	
	public SimplePattern() {
		elements.add(new JumpComponent(new Point(0, Constants.WINDOW_HEIGHT/2)));
		
	}
	
}