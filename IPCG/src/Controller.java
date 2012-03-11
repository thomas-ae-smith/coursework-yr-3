import java.applet.Applet;

import javax.swing.UIManager;

public class Controller extends Applet {

	private static final long serialVersionUID = 8027372715016548764L;

	public static void main(String[] args) {
		
		new Controller();
	}
	
	public Controller() {
		try {
		    // Attempt to use the system's native look and feel (buttons etc)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
	    	System.err.println("Error changing system look and feel.");
	    }
	    
		GameWindow window = new GameWindow();
		window.run();
	}	

}