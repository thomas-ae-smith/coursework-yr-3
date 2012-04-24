import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Model;

public class Controller implements Runnable, KeyListener{
	private Generator generator;
	private Model model;
	private View view;

	public Controller() {
		this.model = new Model();
		this.view = new View();
		this.generator = new Generator();
	}

	@Override
	public void run() {
		// Variables for counting frames per seconds
		long curTime = System.currentTimeMillis();
		long lastTime = curTime;

		while( true ) {
			try {
				// count Frames per second...
				lastTime = curTime;
				curTime = System.currentTimeMillis();

				float delta_t = curTime - lastTime;
				generator.update(delta_t);
				model.update(delta_t);
				view.update(delta_t);


				// Let the OS have a little time...
				Thread.yield();
			} finally {
				view.dispose();
			}
		}
	}


	//refactor to use keys[], possibly
	@Override
	public void keyPressed(KeyEvent e) {

		model.setkey(e.getKeyCode(), true);
		//		for (int i = 0; i < Constants.keycodes.MAX.ordinal(); i++) {
		//			if(e.getKeyCode() == Constants.keycodes.values()[i].getkey() ) model.setkey(i, true);
		//		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		model.setkey(e.getKeyCode(), false);
		//		for (int i = 0; i < Constants.keycodes.MAX.ordinal(); i++) {
		//			if(e.getKeyCode() == Constants.keycodes.values()[i].getkey() ) model.setkey(i, false);
		//		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public View getView() {
		return view;
	}

	public void init() {
		view.init(model);		
		generator.init(model);
	}

}