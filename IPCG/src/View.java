import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import Model.Constants;
import Model.Model;

public class View extends Canvas {
	private static final long serialVersionUID = 1L;
	private Model model;

	private BufferedImage buffer;
	private BufferStrategy bufferStrategy;
	private Graphics graphics;
	private Graphics2D g2D;
	private Color background;
	int cameraStart = 0;

	int fps = 0;
	int frames = 0;
	double totalTime = 0;

	public View() {
		this.setIgnoreRepaint(true);
		this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

		// Get graphics configuration... not my fault that Java is horrid
		GraphicsConfiguration gc = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		// Create off-screen drawing surface
		buffer = gc.createCompatibleImage(Constants.WINDOW_WIDTH,
				Constants.WINDOW_HEIGHT);

		background = Color.LIGHT_GRAY;
	}

	public void init(Model model) {
		this.createBufferStrategy(2);
		this.bufferStrategy = this.getBufferStrategy();

		this.model = model;
	}

	public void update(double delta_t) {

		totalTime += delta_t;
		if (totalTime > 1.f) {
			totalTime -= 1.f;
			fps = frames;
			frames = 0;
		}
		++frames;

		// clear back buffer...
		g2D = buffer.createGraphics();
		g2D.setColor(background);
		g2D.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		cameraStart = model.getPlayerLoc() - Constants.PLAYER_OFFSET;
		g2D.translate(-cameraStart, 0);
		g2D.setColor(background.brighter());
		for (int i = 0; i < Constants.WINDOW_HEIGHT; i += Constants.TILE_HEIGHT) {
			g2D.drawLine(cameraStart, i, cameraStart + Constants.WINDOW_WIDTH,
					i);
		}
		for (int i = cameraStart - (cameraStart % Constants.TILE_WIDTH); i < cameraStart
				+ Constants.WINDOW_WIDTH; i += Constants.TILE_WIDTH) {
			g2D.drawLine(i, 0, i, Constants.WINDOW_HEIGHT);
		}

		// display frames per second...
		g2D.setFont(new Font("Courier New", Font.PLAIN, 12));
		g2D.setColor(Color.RED);
		g2D.drawString(String.format("FPS: %s\ncamerastart: %s", fps, cameraStart), cameraStart + 20, 20);

		g2D.setColor(Color.DARK_GRAY);

		model.render(g2D); // this is where the meat happens

		// Blit image and flip...
		graphics = bufferStrategy.getDrawGraphics();
		graphics.drawImage(buffer, 0, 0, null);
		if (!bufferStrategy.contentsLost())
			bufferStrategy.show();

	}

	public void dispose() {
		if (graphics != null)
			graphics.dispose();
		if (g2D != null)
			g2D.dispose();
	}
}