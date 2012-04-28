package Model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

// A pattern is a structure of components, ranging from basic to compound
public abstract class Pattern extends GameCollection<Component>{

	@Override
	public void tweak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int rate() {
		int total = 0;
		for (Component c : elements) {
			total += c.rate();
		}
		total /= elements.size();
		return total; 
	}
	
	//hijack the returned result, and check it to see if we need to reparent the player
	@Override
	public Point collide(Player p) {
		Point result = super.collide(p);
		if (result != null && (result.y*result.y > result.x*result.x && result.y < 0)) p.setParent(this);
		return result;
	}
	
	@Override
	public void render(Graphics2D g2D) {
		super.render(g2D);
		g2D.setColor(Color.BLUE);
		g2D.drawLine(getStartPoint().x, getStartPoint().y-1, getStartPoint().x + Constants.TILE_WIDTH, getStartPoint().y-1);
	}
	
	@Override
	public void debugRender(Graphics2D g2D, int inset) {
		super.debugRender(g2D, inset);
		g2D.setColor(Constants.DEBUG_COLOURS[inset]);
		g2D.drawString(String.format("%s", rate()), this.getStartPoint().x + 20, 40);
	}

	
}