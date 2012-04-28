package Model;
import java.awt.Point;

public class FlatComponent extends Component {
	public FlatComponent(Point start) {
		elements.add(new FlatPlatformPart(start, 3));
	}
	
	@Override
	public Object clone(boolean placeAtEnd) {
		if (placeAtEnd) return new FlatComponent(this.getEndPoint());
		return new FlatComponent(this.getStartPoint());
	}
	
	@Override
	public int rate() { return 1000*Constants.JUMP_DIST.x/Constants.MAX_VELOCITY; } //'null' rating: as difficult as staying still
	
}