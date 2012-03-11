import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;

public interface Drawable {
	Collection<Drawable> dObjs = new ArrayList<Drawable>();

	public void dInit();

	public void render(Graphics2D g2D);
}