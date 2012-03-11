import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

public interface Collideable {
	Collection<Collideable> cObjs = new ArrayList<Collideable>();
	
	public void cInit();
	//return penetration vector for collision (overloaded Point as 2D vector)
	public Point collide(Active a);
}