package Model;
import java.awt.Point;


public class SinglePattern extends Pattern {
	
	public SinglePattern(Component c) {
		elements.add(c);
	}

	@Override
	public Object clone(boolean placeAtEnd) {
		if (placeAtEnd) {
			Component first = (Component)(this.elements.get(0).clone(false));
			first.translate(new Point(this.getEndPoint().x - this.getStartPoint().x, this.getEndPoint().y - this.getStartPoint().y));
			return new SinglePattern(first);
		}
		return new SinglePattern((Component)(this.elements.get(0).clone(false)));
	}
	
}