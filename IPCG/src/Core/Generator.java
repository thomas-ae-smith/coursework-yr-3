package Core;

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
import Model.FinalComponent;
import Model.FlatComponent;
import Model.GapComponent;
import Model.ObstacleComponent;
import Model.GapPart;
import Model.ObstaclePart;
import Model.Pattern;
import Model.SimplePattern;
import Model.SinglePattern;


public class Generator {
	private static final int RATING = 0;
	private static final int TYPE = 1;
	private static final int X = 2;
	private static final int Y = 3;
	private enum componentType{
		GAP(-7, 10, 0, 4),
		HOBS(0, 0, 3, 3),
		VOBS(4, 7, 1, 1),
		STOBS(-6, 0, 1, 3),
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
		printRatings();
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
			if (model.getLevelEndPoint().x > 10240) {
				model.add(new SinglePattern(new FinalComponent(model.getLevelEndPoint())));
				return;
			}
//			System.err.println("New pattern needed. Desired rating: " + desiredRating);
			//calculate desired standard deviation:
			//quarter of the distance to the nearest 'edge' of the obstacle distribution. Varies between 1/8 to 0 of the range
			int stdDev = Math.min(desiredRating - ratings.first()[RATING], ratings.last()[RATING] - desiredRating) / 4;
			stdDev += (ratings.first()[RATING] - ratings.last()[RATING]) / 8; //add another 1/8 to give healthy minimum
			//pick a random location within the distribution
			int target = (int)(rndNumGen.nextGaussian() * stdDev + desiredRating);
//			System.err.println("Target chosen: " + target);
			//get the closest object lower
			int[] lowerRating = getLower(target);
			//get the closest object higher
			int[] higherRating = getHigher(target);
//			System.err.println("Lower obstacle: R: " + lowerRating[RATING] + "    \tt: " + lowerRating[TYPE] + " \tx: " + lowerRating[X] + "\ty: " + lowerRating[Y]);
//			System.err.println("Highr obstacle: R: " + higherRating[RATING] + "    \tt: " + higherRating[TYPE] + " \tx: " + higherRating[X] + "\ty: " + higherRating[Y]);
			
			if (lowerRating[RATING] == higherRating[RATING] || lowerRating[RATING] == 0) {
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
//		System.err.println("\nChosen obstacle: R: " + data[RATING] + "    \tt: " + data[TYPE] + " \tx: " + data[X] + "\ty: " + data[Y]);
		
		Component next;
		Pattern p = null;
		Point start = model.getLevelEndPoint();
		switch (componentType.get(data[TYPE])) {
		case GAP:
//			System.err.println("Making a GAP with X:" + data[X] + " and Y:" + data[Y]);
			next = new GapComponent(start, data[X], data[Y]);
			break;
//		case HOBS:
//			next = new ObstacleComponent(start, data[X], data[Y]);
//			break;
//		case VOBS:
//			next = new ObstacleComponent(start, data[X], data[Y]);
//			break;
		case STOBS:
			next = new ObstacleComponent(start, data[X], data[Y]);
			break;
		default:
			System.err.println("Making a default FlatComponent");
			next = new FlatComponent(start);
		}
		int i = 0;
		while (p == null) {
			i++;
			switch (rndNumGen.nextInt(2)) {
			case 0:
				p = new SinglePattern(next);
				break;
			case 1:
				if (isValid(2*data[Y]))
					p = new SimplePattern(next);
			}
			if (i >= 12) p = new SinglePattern(next);
		}
		model.add(p);
//		System.err.println("Eventually got " + p.rate());
	}

	private int[] getLower(int target) {
		if (target < ratings.first()[RATING]) return new int[]{0,0,0,0};		//if the destination is off the bottom, fake it
		//set up variables
		ArrayList<int[]> list = new ArrayList<int[]>();
		Iterator<int[]> it = ratings.descendingIterator();
		int[] test;
		//run until test contains the first with rating equal to or less than target 
		while((test = it.next()) != null) {
			if (test[RATING] <= target) break;
		};
//		System.err.println("\nChosen obstacle: R: " + test[RATING] + "    \tt: " + test[TYPE] + " \tx: " + test[X] + "\ty: " + test[Y]);

		while (!isValid(test[Y])) {
			if (!it.hasNext()) return new int[]{0,0,0,0};		//if there are no valid ones before the end, fake it
			test = it.next();
		}
		target = test[RATING];	//update target to be the actual rating
		do {
			list.add(test);	//if test is valid to be placed
		//while remaining tests have the same rating
		} while (it.hasNext() && (test = it.next())[RATING] == target && isValid(test[Y]));
		
		Collections.shuffle(list);
		return list.get(0);
	}
	
	private int[] getHigher(int target) {
		if (target > ratings.last()[RATING]) return new int[]{0,0,0,0};		//if the destination is off the top, fake it
		//set up variables
		ArrayList<int[]> list = new ArrayList<int[]>();
		Iterator<int[]> it = ratings.iterator();
		int[] test;
		//run until test contains the first with rating equal to or less than target 
		while((test = it.next()) != null) {
			if (test[RATING] >= target) break;
		};
//		System.err.println("\nChosen obstacle: R: " + test[RATING] + "    \tt: " + test[TYPE] + " \tx: " + test[X] + "\ty: " + test[Y]);

		while (!isValid(test[Y])) {
			if (!it.hasNext()) return new int[]{0,0,0,0};		//if there are no valid ones before the end, fake it
			test = it.next();
		}
		target = test[RATING];	//update target to be the actual rating
		do {
			list.add(test);	//if test is valid to be placed
		//while remaining tests have the same rating
		} while (it.hasNext() && (test = it.next())[RATING] == target && isValid(test[Y]));
		
		Collections.shuffle(list);
		return list.get(0);
	}
	
	private boolean isValid(int test) {
		int tile = model.getLevelEndPoint().y/Constants.TILE_HEIGHT + test;
		if (tile < 5 || tile > 18) {
//			System.out.println("Point: " + model.getLevelEndPoint().y/Constants.TILE_HEIGHT + " diff: " + test + " result: " + tile);
			return false;
		}
		return true; 		//FIXME TODO implement this - might already work
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
		case STOBS:
			return ObstaclePart.calculateRating(x, y);
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