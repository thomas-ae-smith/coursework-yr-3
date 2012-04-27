import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Model;

public class Controller implements Runnable, KeyListener, Screen{
	private Generator generator;
	private Model model;
	private View view;
	private ActionListener listener; //hijack the action system

	public Controller(ActionListener listener) {
		this.model = new Model();
		this.view = new View();
		this.generator = new Generator();
		this.listener = listener;
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

				double delta_t = (curTime - lastTime)/1000f;
				
//				System.err.println("c: " + curTime + " \tl: " + lastTime + "\tdiff: " + (curTime-lastTime) + " \td: " + delta_t);
				generator.update(333);
				model.update(delta_t);
				view.update(delta_t);
				if(model.getFinished()) break;

				// Let the OS have a little time...
				Thread.yield();
			} finally {
				view.dispose();
			}
		}
		listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "++level event log++\n"));
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
		Thread thread = new Thread(this);
		thread.start();
	}

}