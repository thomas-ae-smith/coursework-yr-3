package Core;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import Model.Level;
import Model.Pattern;
import Model.Player;

public class Model {
	Level level;
	Player player;
	private boolean skip;

	public Model(Evaluator eval) {
		level = new Level();
		player = new Player(level.getLast(), eval);
	}

	public void update(double delta_t) {
		level.update(delta_t);
		player.update(delta_t);
		level.collide(player);
	}

	public void render(Graphics2D g2D){
		level.render(g2D);
		player.render(g2D);
	}

	public void setkey(int keycode, boolean value) {
		switch (keycode) {
		case KeyEvent.VK_UP:		player.up    = value;  	break;
		case KeyEvent.VK_DOWN: 		player.down  = value;	break;
		case KeyEvent.VK_LEFT:		player.left  = value;	break;
		case KeyEvent.VK_RIGHT:		player.right = value;	break;
		case KeyEvent.VK_SPACE:		player.up	 = value; 	break;
		case KeyEvent.VK_ESCAPE:	skip		 = value; 	break;
		}
		return;
	}
	
	public int getPlayerLoc() { return player.getStartPoint().x; }
	public Point getLevelEndPoint() { return level.getEndPoint(); }

	public void add(Pattern pattern) {
		level.add(pattern);
	}

	public int getRating() {
		return level.rate();
	}
	public boolean getFinished() {
		return player.getFinished() || skip;
	}
	
}