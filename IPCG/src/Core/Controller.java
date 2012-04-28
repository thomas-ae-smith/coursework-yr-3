package Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.ActivePart;
import Model.ObstaclePart;
import UI.Screen;

public class Controller implements Runnable, KeyListener, Screen{
	private Evaluator eval;
	private Generator generator;
	private Model model;
	private View view;
	private ActionListener listener; //hijack the action system
	private int minRating = 0;
	private int maxRating = Integer.MAX_VALUE;

	public Controller(ActionListener listener, Generator gen, Evaluator eval, int minRating, int maxRating) {
		this(listener, gen, eval);
		this.minRating = minRating;
		this.maxRating = maxRating;
	}

	public Controller(ActionListener listener, Generator gen, Evaluator eval) {
		this.eval = eval;
		this.model = new Model(eval);
		this.view = new View();
		this.generator = gen;
		this.listener = listener;
	}

	@Override
	public void run() {
		// Variables for counting frames per seconds
		long curTime = System.currentTimeMillis();
		long startTime = curTime;
		long lastTime = curTime;

		while( true ) {
			try {
				// count Frames per second...
				lastTime = curTime;
				curTime = System.currentTimeMillis();

				double delta_t = (curTime - lastTime)/1000f;
				
//				System.err.println("c: " + curTime + " \tl: " + lastTime + "\tdiff: " + (curTime-lastTime) + " \td: " + delta_t);
				if (minRating == maxRating)	generator.update(maxRating);
				else generator.update(Math.min(Math.max(eval.getPlane(), minRating), maxRating));
				model.update(delta_t);
				view.update(delta_t);
				if(model.getFinished()) break;
				// Let the OS have a little time...
				Thread.yield();
			} finally {
				view.dispose();
			}
		}
		listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "t: " + (System.currentTimeMillis()-startTime) + "\n"));
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
		ObstaclePart.clear();
		Thread thread = new Thread(this);
		thread.start();
	}

}