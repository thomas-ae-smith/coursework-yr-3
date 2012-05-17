package Model;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;


//assuming a desired jump of 3.5w, 7h (224, 112):
//a = (h^2)/56, and u = -a/(448/h)
public abstract class Constants{
	public static final boolean DRAW_DEBUG = true;	// causes object outlines to be drawn

	public static final int TILE_WIDTH = 64;
	public static final int TILE_HEIGHT = 16;
	public static final int WINDOW_HEIGHT = 320;
	public static final int WINDOW_WIDTH = 1024;
	public static final Color[] DEBUG_COLOURS = { Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.BLACK};
//	public static final Point2D.Double MAX_VELOCITY = new Point2D.Double(336,2016); //max velocity in X & Y
	public static final int MAX_VELOCITY = 336; //max velocity in X
	public static final int GRAVITY = 2016; //acceleration in Y
	public static final int PLAYER_OFFSET = 100;	//used for camera and spawning within a pattern
	public static final int PLAYER_RADIUS = 20;
	public static final int OBSTACLE_RADIUS = 14;
	public static final int OBSTACLE_BASE_SPEED = 200;
	public static final Point JUMP_DIST = new Point((int) (TILE_WIDTH*3.5), 7*TILE_HEIGHT);
	public static final int VERSION = 33;
	
	public static enum keycodes{
		JUMP(KeyEvent.VK_UP),
		RIGHT(KeyEvent.VK_RIGHT),
		LEFT(KeyEvent.VK_LEFT),
		RESET(KeyEvent.VK_SPACE),
		MAX(-1);
		
		private int code;
		private keycodes(int code) { this.code = code; }
		public int getkey() { return code;}
	}
	
}