import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public abstract class Constants{
	public static final int TILE_WIDTH = 64;
	public static final int TILE_HEIGHT = 16;
	public static final int WINDOW_HEIGHT = 320;
	public static final int WINDOW_WIDTH = 1024;
	public static final Color[] DEBUG_COLOURS = { Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.BLACK};
	public static final boolean DEBUG = false;
	public static final Point2D.Double VELOCITY = new Point2D.Double(3,1);
	
}