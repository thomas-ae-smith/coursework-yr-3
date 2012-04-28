package Core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.UIManager;

import Model.Constants;

//implements a one-dimensional SVM; algorithm as described in http://genomics10.bu.edu/yangsu/rankgene/oned-svm.pdf
public class Evaluator {

	private static final int BUFFER = 40;
	private static final int MAXRATING = 1000*Constants.JUMP_DIST.x/Constants.MAX_VELOCITY;
	
	private LinkedList<Integer> fail, live;
	private Integer failS = 0, liveS = MAXRATING;	//initialise to limits
//	private double L = Double.POSITIVE_INFINITY;
	
	public void addPositive(int rating) {
		System.out.printf("1: %d\n", rating);
		if(live.size() - live.indexOf(liveS) < BUFFER || rating < live.getLast()) sortedAdd(live, rating);		
	}

	public void addNegative(int rating) {
		System.out.printf("0: %d\n", rating);
		if(fail.indexOf(failS) < BUFFER || rating > fail.getLast()) sortedAdd(fail, MAXRATING-rating);		
	}
	
	public Evaluator() {
		fail = new LinkedList<Integer>();
		live = new LinkedList<Integer>();
		fail.add(failS);
		live.add(liveS);
	}
	
	public int getPlane() { return (failS + liveS)/2; }
	
	private void sortedAdd(LinkedList<Integer> list, int rating) {
//		System.out.println("Adding: " + rating);
		int index = list.size(); 	//initialise high
		for (Integer integer : list) {
			if (rating <= integer) {
				index = list.indexOf(integer);
				break;
			}
		}
		list.add(index, rating);
		runSVM();
	}
	
	private void runSVM() {
		//first: positive assumed lower than negative. More difficult obstacles have smaller ratings, so fail are 'positive'
		Iterator<Integer> itp = fail.descendingIterator(); 	//positive errors, high to low
		Iterator<Integer> itq = live.iterator();				//negative errors, low to high
		
		
		int i = 0;							//keep track of i; using an iterator we wouldn't have this
		int c = 1;							//tuning constant. 1 seems to work fine
		int pi, qi;							//coordinate of the ith point
		int dp = 0, dq = 0;					//running totals
		double wi, li;						//ith values
		double L = Double.POSITIVE_INFINITY;
		
		while (itp.hasNext() && itq.hasNext()) {
			pi = itp.next();
			qi = itq.next();
//			System.out.printf("Starting: pi:%d qi:%d\n", pi, qi);
			//if we've run out of p before reaching q
			if (pi > qi && !itp.hasNext()) {
				while (pi > qi && itq.hasNext()) qi = itq.next();	//last-ditch attempt to bring qi into range
				System.err.println("Resorted to unfair tactics");
			}
			//if dp and dq are 'between' pi & qi in this direction
			if (pi < qi) {
				//calculate values
				wi = 2.0/(pi-qi);
				li = wi*wi/2 + c*(2*(i) - wi*(dp - dq));
				//if better than current best
				if (li < L) {
					L = li;
					failS = pi;
					liveS = qi;
//					System.out.printf("Found a better l. li:%f  plane:%f\n", li, (pi+qi)/2.0);
				}
			}
			dp += pi;
			dq += qi;
			i++;
		}
		if (fail.indexOf(failS) > BUFFER) fail.subList(0, fail.indexOf(failS)-BUFFER).clear(); //if there's more than BUFFER at the beginning, trim
		if (live.indexOf(liveS) + BUFFER < live.size()) live.subList(live.indexOf(liveS) + BUFFER, live.size()).clear(); //as above, so below
	}
	
//	public static void main(String[] args) {
//		try {
//			// Attempt to use the system's native look and feel (buttons etc)
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//			System.err.println("Error changing system look and feel.");
//		}
//		new Evaluator();
//	}
}

/*take in callbacks from player death or reparenting (to further parent)*/