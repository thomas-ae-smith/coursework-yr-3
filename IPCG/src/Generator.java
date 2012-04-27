import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.UIManager;

import Model.Component;
import Model.Constants;
import Model.FlatComponent;
import Model.GapComponent;
import Model.GapPart;
import Model.Model;
import Model.SimplePattern;
import Model.SinglePattern;


public class Generator {
	private static final int RATING = 0;
	private static final int TYPE = 0;
	private static final int X = 0;
	private static final int Y = 0;
	private enum componentType{
		GAP(-7, 10, 0, 4),
		HOBS(0, 0, 3, 3),
		VOBS(6, 6, 3, 3),
		MAX_KINDS(0,0,0,0);

		private final int minY, maxY, minX, maxX;
		private componentType(int minY, int maxY, int minX, int maxX) {
			this.minY = minY;
			this.maxY = maxY;
			this.minX = minX;
			this.maxX = maxX;
		}
		
		public static componentType get(int i) {
			for (componentType c : values()) {
				if (c.ordinal() == i) return c;
			}
			return null;
		}

	};

	private TreeSet<int[]> ratings;
	private Model model;
	private Random rndNumGen;


	public Generator() {
		rndNumGen = new Random();
		calculateRatings();
		//printRatings();
	}

	public static void main(String[] args) {

		// Applet IPCGsystem = new Applet();
		try {
			// Attempt to use the system's native look and feel (buttons etc)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Error changing system look and feel.");
		}
		new Generator();
	}

	public void printRatings() {
		for (Iterator<int[]> iterator = ratings.iterator(); iterator.hasNext();) {
			int[] rating = (int[]) iterator.next();
			System.err.println("R: "+ rating[RATING] + "    \tt: "+rating[TYPE] + " \tx: "+rating[X] + "\ty: "+rating[Y]);
		}
	}

	public void init(Model model) {
		this.model = model;
	}

	public void update(int desiredRating) {
		if (model.getPlayerLoc() + Constants.WINDOW_WIDTH > model.getLevelEndPoint().x) {
			//calculate desired standard deviation:
			//half of the distance to the nearest 'edge' of the obstacle distribution
			int stdDev = Math.min(desiredRating - ratings.first()[RATING], ratings.last()[RATING] - desiredRating) / 2;
			//pick a random location within the distribution
			int target = (int)(rndNumGen.nextGaussian() * stdDev + desiredRating);
			//get the closest object lower
			int[] lowerRating = getLower(target);
			//get the closest object higher
			int[] higherRating = getHigher(target);
			
			if (lowerRating[RATING] == 0) {
				addComponent(higherRating);
				return;
			} else if (higherRating[RATING] == 0) {
				addComponent(lowerRating);
				return;
			}
			
			if(rndNumGen.nextInt(higherRating[RATING]-lowerRating[RATING]) > target - lowerRating[RATING]) {
				addComponent(lowerRating);
				return;
			} else {
				addComponent(higherRating);
				return;				
			}
		}
	}

	private void addComponent(int[] data) {
		Component next;
		Point start = model.getLevelEndPoint();
		System.err.println(data);
		switch (componentType.get(data[TYPE])) {
		case GAP:
			next = new GapComponent(start, data[X], data[Y]);
		default:
			next = new FlatComponent(start);
		}
		switch (rndNumGen.nextInt(2)) {
		case 0:
			model.add(new SinglePattern(next));
		case 1:
			model.add(new SimplePattern(next));
		}
		
	}

	private int[] getLower(int target) {
		if (target < ratings.first()[0]) return new int[]{0,0,0,0};		//if the destination is off the bottom, fake it
		//set up variables
		ArrayList<int[]> list = new ArrayList<int[]>();
		Iterator<int[]> it = ratings.descendingIterator();
		int[] test;
		//run until test contains the first with rating equal to or less than target 
		while((test = it.next()) != null) {
			if (test[0] <= target) break;
		};
		
		target = test[0];	//update target to be the actual rating
		if (isValid(test)) list.add(test);	//if test is valid to be placed
		//while remaining tests have the same rating
		while((test = it.next())[0] == target) {
			if (isValid(test)) list.add(test);	//add to the list if valid
		}
		Collections.shuffle(list);
		return list.get(0);
	}
	
	private int[] getHigher(int target) {
		if (target > ratings.last()[0]) return new int[]{0,0,0,0};		//if the destination is off the top, fake it
		//set up variables
		ArrayList<int[]> list = new ArrayList<int[]>();
		Iterator<int[]> it = ratings.iterator();
		int[] test;
		//run until test contains the first with rating equal to or less than target 
		while((test = it.next()) != null) {
			if (test[0] >= target) break;
		};
		
		target = test[0];	//update target to be the actual rating
		if (isValid(test)) list.add(test);	//if test is valid to be placed
		//while remaining tests have the same rating
		while((test = it.next())[0] == target) {
			if (isValid(test)) list.add(test);	//add to the list if valid
		}
		Collections.shuffle(list);
		return list.get(0);
	}
	
	private boolean isValid(int[] test) {
		return true; 		//FIXME TODO implement this
	}
	
	private void calculateRatings() {
		int rating;
		ratings = new TreeSet<int[]>(new Comparator<int[]>() {
				public int compare(int[] obj1, int[] obj2) { 
						for (int i = 0; i < 4; i++) {
							if (obj1[i] != obj2[i])	return obj1[i] - obj2[i]; 
						}
						return 1;
					} 
			}); 
		for (componentType c : componentType.values()) {
			for (int x = c.minX; x <= c.maxX; x++) {
				for (int y = c.minY; y <= c.maxY; y++) {
					rating = getRating(c, x, y);
					if (rating > 0) ratings.add(new int[]{rating, c.ordinal(), x, y});
				}
			}
		}
	}

	private int getRating(componentType type, int x, int y) {
		switch (type) {
		case GAP:
			return GapPart.calculateRating(x, y);
		default:
			return 0;
		}
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