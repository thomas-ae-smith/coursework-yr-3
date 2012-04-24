import java.util.Random;

import Model.Component;
import Model.Constants;
import Model.FlatComponent;
import Model.GapComponent;
import Model.Model;
import Model.ObstacleComponent;
import Model.SimplePattern;
import Model.SinglePattern;


public class Generator {
	private Model model;
	private Random rndNumGen;

	public Generator() {
		rndNumGen = new Random();
	}
	
	public void init(Model model) {
		this.model = model;
	}
	
	public void update(double delta_t) {
		if (model.getPlayerLoc() + Constants.WINDOW_WIDTH > model.getLevelEndPoint().x) {
			Component next;
			switch(rndNumGen.nextInt(2)) {
			case 0:
				next = new GapComponent(model.getLevelEndPoint());
				break;
			case 1:
				next = new ObstacleComponent(model.getLevelEndPoint());
				break;
			default:
				next = new FlatComponent(model.getLevelEndPoint());
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
	
	public void dispose() {
	}
}