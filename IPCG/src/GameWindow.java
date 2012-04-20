import javax.swing.JFrame;
import javax.swing.UIManager;


public class GameWindow extends JFrame {

	private static final long serialVersionUID = -8255319694373975038L;
	private Controller controller;


	public static void main(String[] args) {

//		Applet IPCGsystem = new Applet();
		try {
			// Attempt to use the system's native look and feel (buttons etc)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("Error changing system look and feel.");
		}
		GameWindow window = new GameWindow();
	}	

	public GameWindow() {


		this.setTitle("IPCG");
		this.setIgnoreRepaint(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		this.controller = new Controller();
		
		this.add(controller.getView());
		this.setFocusable(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.addKeyListener(controller);
		this.setVisible(true);

		controller.init();
		controller.run();

	}

}
