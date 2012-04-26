package Model;
import java.awt.Point;

// A pattern is a structure of components, ranging from basic to compound
public abstract class Pattern extends GameCollection<Component>{

	@Override
	public void tweak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double rate() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//hijack the returned result, and check it to see if we need to reparent the player
	@Override
	public Point collide(Player p) {
		Point result = super.collide(p);
		if (result != null) p.setParent(this);
		return result;
	}
	
}