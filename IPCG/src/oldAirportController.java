//package lights.controller;
//import javax.swing.UIManager;
//
//import lights.gui.GUIMainWindow;
//import lights.model.AirportModel;
//
///**
// * The top-level class for the application. Owns the window, the current view, and the model.
// * @author taes1g09
// *
// */
//public class AirportController {
//	
//	AirportModel airportModel;
//	
//	public static void main(String[] args) {
//		
//		new AirportController(args);
//	}
//	
//	public AirportController(String[] args) {
//		try {
//		    // Attempt to use the system's native look and feel (buttons etc)
//	        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//	    } catch (Exception e) {
//	    	System.err.println("Error changing system look and feel.");
//	    }
//	    
//		airportModel = new AirportModel();
//	    // Launch the GUI.
//		new GUIMainWindow(airportModel, args);
//	}	
//
//}
