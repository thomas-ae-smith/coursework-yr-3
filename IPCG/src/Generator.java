import java.awt.Point;
import java.util.Random;

import Model.Component;
import Model.Constants;
import Model.FlatComponent;
import Model.GapComponent;
import Model.Model;
import Model.ObstacleComponent;
import Model.FinalComponent;
import Model.SimplePattern;
import Model.SinglePattern;


public class Generator {
	private Model model;
	private Random rndNumGen;
	private int chances[] =		 {	2,		//step
									2,		//hop
									2,		//skip
									2,		//jump
									2,		//leap
									20,		//horizontal obstacle
									2,		//final
	};
	
	public Generator() {
		rndNumGen = new Random();
	}

	public void init(Model model) {
		this.model = model;
	}

	public void update(double delta_t) {
		if (model.getPlayerLoc() + Constants.WINDOW_WIDTH > model.getLevelEndPoint().x) {
			Component next;
			if (model.getLevelEndPoint().x > 5000) {	//TODO: make this dependant
				model.add(new SinglePattern(new FinalComponent(model.getLevelEndPoint())));
			} else {
				Point start = model.getLevelEndPoint();
				switch(getIndex()) {
				case 0:
					next = GapComponent.step(start);
					break;
				case 1:
					next = GapComponent.hop(start);
					break;
				case 2:
					next = GapComponent.skip(start);
					break;
				case 3:
					next = GapComponent.jump(start);
					break;
				case 4:
					next = GapComponent.leap(start);
					break;
				case 5:
					next = new ObstacleComponent(start);
					break;
				case 6:
					next = new FinalComponent(start);
					break;
				default:
					next = new FlatComponent(start);
					break;
				}


				switch (rndNumGen.nextInt(2)) {
				case 0:
					model.add(new SinglePattern(next));
					break;
				case 1:
					model.add(new SimplePattern(next));
				default:
					break;
				}
			}

		}
	}

	private int getIndex() {
		int pick = rndNumGen.nextInt(sum());
		int i = 0;
		for (; i < chances.length; i++) {
			pick -= chances[i];
			if (pick <= 0) break;
		}
		return i;
	}

	private int sum() {
		int total = 0;
		for (int i = 0; i < chances.length; i++) {
			total += chances[i];
		}
		return total;
	}

	public void dispose() {
	}
}

/* use treemap, with difficulties as keys
 * need a switch to gap, horizontal obs, vertical obs, (gap obs?)
 * pick using rndNumGen.nextGaussian()*stdDeviation + desiredAvg
 * stdDeviation = dist from desiredDAvg to nearest edge, / 2
 * TODO ensure that I don't need to square it and use variation insted?
 * need a getLowerValid(int) and getHigherValid(int)
 */