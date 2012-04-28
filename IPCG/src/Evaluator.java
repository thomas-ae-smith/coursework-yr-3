import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.UIManager;

//implements a one-dimensional SVM; algorithm as described in http://genomics10.bu.edu/yangsu/rankgene/oned-svm.pdf
public class Evaluator {

	private LinkedList<Integer> fail;
	private LinkedList<Integer> live;
	
	public void addPositive(int rating) {
		System.out.printf("1: %d\n", rating);
		live.add(rating);
	}

	public void addNegative(int rating) {
		System.out.printf("0: %d\n", rating);
		fail.add(rating);
	}
	
	public Evaluator() {
		fail = new LinkedList<Integer>();
		live = new LinkedList<Integer>();
//		fail.add(1);
		live.add(2);
//		fail.add(5);
		live.add(7);
		fail.add(9);
//		fail.add(12);//
		live.add(14);
//		fail.add(18);
		//plane
		live.add(23);
		live.add(28);//
//		fail.add(32);
		live.add(34);
//		fail.add(36);
		live.add(39);
		live.add(42);
		live.add(45);
		live.add(48);
//		fail.add(50);
		System.err.println(findPlane());
	}
	
	public void test() {
		fail.add(1);
		live.add(2);
		fail.add(5);
		live.add(7);
		fail.add(9);
		fail.add(12);//
		live.add(14);
		fail.add(18);
		//plane
		live.add(23);
		live.add(28);//
		fail.add(32);
		live.add(34);
		fail.add(36);
		live.add(39);
		live.add(42);
		live.add(45);
		live.add(48);
		fail.add(50);
		System.err.println(findPlane());
	}
	
	public int findPlane() {
		if (fail.isEmpty()) return avg(live);
		if (live.isEmpty()) return avg(fail);
		
		//first: positive assumed lower than negative. More difficult obstacles have smaller ratings, so fail are 'positive'
		Iterator<Integer> itp = fail.descendingIterator(); 	//positive examples, high to low
		Iterator<Integer> itq = live.iterator();				//negative examples, low to high
		
		int i = 0;							//keep track of i; using an iterator we wouldn't have this
		int c = 1;							//tuning constant. 1 seems to work fine
		int pi, qi;							//coordinate of the ith point
		double l = Double.POSITIVE_INFINITY;						//keep the running best value here
		double plane = 0;						//result
		int dp = 0, dq = 0;					//running totals
		double wi, li;						//ith values
		
		while (itp.hasNext() && itq.hasNext()) {
			pi = itp.next();
			qi = itq.next();
			//if we've run out of p before reaching q
			if (pi > qi && !itp.hasNext()) {
				while (pi > qi && itq.hasNext()) qi = itq.next();	//last-ditch attempt to bring qi into range
			}
			//if dp and dq are 'between' pi & qi in this direction
			if (pi < qi) {
				//calculate values
				wi = 2.0/(pi-qi);
				li = wi*wi/2 + c*(2*(i) - wi*(dp - dq));
				//if better than current best
				if (li < l) {
					l = li;
					plane = (pi+qi)/2.0;
					System.err.printf("Found a better l. li:%f  plane:%f\n", li, plane);
				}
			}
			dp += pi;
			dq += qi;
			i++;
		}
		
		System.err.println("switching");
		//then: positive assumed higher than negative. 
		itp = fail.iterator(); 	//positive examples, low to high
		itq = live.descendingIterator();	//negative examples, high to low

		i = 0;							//reset i
		dp = dq = 0;					//running totals
		while (itp.hasNext() && itq.hasNext()) {
			pi = itp.next();
			qi = itq.next();
			//if we've run out of p before reaching q
			if (pi < qi && !itp.hasNext()) {						// this is missing an attempt to help a smaller q
				while (pi < qi && itq.hasNext()) qi = itq.next();	//last-ditch attempt to bring qi into range
			}
			//if dp and dq are 'between' pi & qi in this direction
			if (pi > qi) {
				//calculate values
				wi = 2.0/(pi-qi);
				li = wi*wi/2 + c*(2*(i) - wi*(dp - dq));
				//if better than current best
				if (li < l) {
					l = li;
					plane = (pi+qi)/2.0;
					System.err.printf("Found a better l. li:%f  plane:%f\n", li, plane);
				}
			}
			dp += pi;
			dq += qi;
			i++;
		}



		return (int)plane;
	}
	
	private int avg(LinkedList<Integer> list) {
		int total = 0;
		for (Integer integer : list) {
			total += integer;
		}
		return total / list.size();
	}
	
	public static void main(String[] args) {
		try {
			// Attempt to use the system's native look and feel (buttons etc)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Error changing system look and feel.");
		}
		new Evaluator();
	}
}

/*take in callbacks from player death or reparenting (to further parent)*/