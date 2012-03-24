// tests the collisions with a flatplatformpart
import java.awt.Point;

public class TestHarness extends Player {
	
	Point loc;
	int radius;
	
public static void main(String[] args) {
	new TestHarness();
}

public TestHarness() {
	super(10,10);//cruft
	GameObject plat = new FlatPlatformPart(new Point(10, 10), 10);
	radius = 10;
	System.out.println("Starting Testing");
	System.out.println("Collisions:");
	setLoc(5, 5);
	System.out.println(plat.collide(this) + " (-5, -5)ish");
	setLoc(15, 5);
	System.out.println(plat.collide(this) + " (0, -5)");
	setLoc(25, 5);
	System.out.println(plat.collide(this) + " (5, -5)ish");
	setLoc(25, 10);
	System.out.println(plat.collide(this) + " (5, 0)");
	setLoc(25, 15);
	System.out.println(plat.collide(this) + " (5, 5)ish");
	setLoc(15, 15);
	System.out.println(plat.collide(this) + " (0, 5)");
	setLoc(5, 15);
	System.out.println(plat.collide(this) + " (-5, 5)ish");
	setLoc(5, 10);
	System.out.println(plat.collide(this) + " (-5, 0)");
	System.out.println("Non-Collisions:");
	setLoc(0, 0);
	System.out.println(plat.collide(this) + " (-5, -5)ish");
	setLoc(15, 0);
	System.out.println(plat.collide(this) + " (0, -5)");
	setLoc(25, 0);
	System.out.println(plat.collide(this) + " (5, -5)ish");
	setLoc(30, 10);
	System.out.println(plat.collide(this) + " (5, 0)");
	setLoc(25, 20);
	System.out.println(plat.collide(this) + " (5, 5)ish");
	setLoc(15, 20);
	System.out.println(plat.collide(this) + " (0, 5)");
	setLoc(0, 15);
	System.out.println(plat.collide(this) + " (-5, 5)ish");
	setLoc(0, 10);
	System.out.println(plat.collide(this) + " (-5, 0)");
}

private void setLoc(int x, int y) { loc = new Point(x,y); }

@Override
public void update(double delta) {
	// TODO Auto-generated method stub
	
}

@Override
public Point getLoc() {
	return loc;
}

@Override
public int getRadius() {
	return radius;
}

}