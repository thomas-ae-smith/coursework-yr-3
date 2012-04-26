package Model;
// tests the limits of the different jump ratings
import java.awt.Point;


public class GapRatingTestHarness {
	
public static void main(String[] args) {
	new GapRatingTestHarness();
}

public GapRatingTestHarness() {
	for (int i = 0; i < 5; i++) {
		for (int j = -7; j < 7; j++) {
			GapPart p = new GapPart(new Point(), i, j);
			System.out.println("w:" + i + ", h:" + j + ",\trating:" + p.rate());
			
		}
		System.out.println("");
		
	}
}

}