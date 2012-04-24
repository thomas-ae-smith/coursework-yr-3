package Model;

import java.awt.Point;


public class SimplePattern extends Pattern {
	
	public SimplePattern(Component c) {
		elements.add(c);
		elements.add((Component) c.clone(true));
		
	}

	@Override
	public Object clone(boolean placeAtEnd) {
		if (placeAtEnd) {
			Component first = (Component)(this.elements.get(0).clone(false));
			first.translate(new Point(this.getEndPoint().x - this.getStartPoint().x, this.getEndPoint().y - this.getStartPoint().y));
			return new SimplePattern(first);
		}
		return new SimplePattern((Component)(this.elements.get(0).clone(false)));
	}
	
}