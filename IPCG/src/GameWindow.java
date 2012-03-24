import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


public class GameWindow extends JFrame implements KeyListener {

	private static final long serialVersionUID = -8255319694373975038L;
	private Canvas canvas;
	private BufferedImage buffer;
	private BufferStrategy bufferStrategy;
	private Graphics graphics;
	private Graphics2D g2D;
	private Color background;
	
	FlatPlatformPart plat, plat2;
	Player player;
	Level level;

	public GameWindow() {
	

		this.setTitle("IPCG");
		this.setIgnoreRepaint(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		this.canvas = new Canvas();
		this.canvas.setIgnoreRepaint(true);
		this.canvas.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		this.add(this.canvas);
		this.setFocusable(true);
		this.pack();
	    this.setLocationRelativeTo(null);
	    this.addKeyListener(this);
		this.setVisible(true);
		
		this.canvas.createBufferStrategy(2);
		bufferStrategy = this.canvas.getBufferStrategy();
		
		
		
		// Get graphics configuration... not my fault that Java is horrid
	    GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

	    // Create off-screen drawing surface
	    buffer = gc.createCompatibleImage( Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );

	    background = Color.LIGHT_GRAY;
	    
	    
	    player = new Player(65, 65);
	    level = new Level();
	}
	
	public void run() {
	    // Variables for counting frames per seconds
	    int fps = 0;
	    int frames = 0;
	    long totalTime = 0;
	    long curTime = System.currentTimeMillis();
	    long lastTime = curTime;

	    while( true ) {
	    	try {
	    		// count Frames per second...
	    		lastTime = curTime;
	    		curTime = System.currentTimeMillis();
	    		totalTime += curTime - lastTime;
	    		if( totalTime > 1000 ) {
	    			totalTime -= 1000;
	    			fps = frames;
	    			frames = 0;
	    		} 
	    		++frames;

	    		// clear back buffer...
	    		g2D = buffer.createGraphics();
	    		g2D.setColor( background );
	    		g2D.fillRect( 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );


	    		// display frames per second...
	    		g2D.setFont( new Font( "Courier New", Font.PLAIN, 12 ) );
	    		g2D.setColor( Color.GREEN );
	    		g2D.drawString( String.format( "FPS: %s", fps ), 20, 20 );

	    		g2D.setColor( Color.DARK_GRAY );
	    		//	    			g2D.fillOval(20, 20, 40, 40);
	    		
	    		float delta = curTime - lastTime;
//	    		player.update(delta);
//	    		for (Active a : Active.aObjs) {
//	    			a.update(delta);
//	    		}
	    		level.update(delta);
	    		player.update(delta);
	    		level.collide(player);
	    		level.render(g2D);
	    		player.render(g2D);

	    		// Blit image and flip...
	    		graphics = bufferStrategy.getDrawGraphics();
	    		graphics.drawImage( buffer, 0, 0, null );
	    		if( !bufferStrategy.contentsLost() )
	    			bufferStrategy.show();

	    		// Let the OS have a little time...
	    		Thread.yield();
	    	} finally {
	    		// release resources
	    		if( graphics != null ) 
	    			graphics.dispose();
	    		if( g2D != null ) 
	    			g2D.dispose();
	    	}
	    }
	}

	//refactor to use keys[], possibly
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ) player.right = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT ) player.left = true;
		if(e.getKeyCode() == KeyEvent.VK_UP ) player.up = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN ) player.down = true;
		if(e.getKeyCode() == KeyEvent.VK_SPACE ) player.stop = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ) player.right = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT ) player.left = false;
		if(e.getKeyCode() == KeyEvent.VK_UP ) player.up = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN ) player.down = false;
		if(e.getKeyCode() == KeyEvent.VK_SPACE ) player.stop = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
