import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

import Model.Constants;


public class GameWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8255319694373975038L;
	private Controller controller;
	private StringBuilder log;
	private Screen screens[] = {new TextPanel(0, this),
								new QuestionnairePanel(this), 
								new TextPanel(1, this),
								new FeedbackPanel(3, this), 
								new FeedbackPanel(4, this), 
								new FeedbackPanel(5, this), 
								new FeedbackPanel(6, this),
								new TextPanel(2, this) };
	private int curr_screen = 0;

	

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
		this.log = new StringBuilder("# this is the data file\n---\n");
		this.setTitle("IPCG");
//		this.setIgnoreRepaint(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		
//		this.controller = new Controller();
		
//		this.add(controller.getView());

		//Create and set up the content pane.
		this.add(screens[0].getView());
//        JComponent newContentPane = new FeedbackPanel(2,this);
//        newContentPane.setOpaque(true); //content panes must be opaque
//        this.setContentPane(newContentPane);
		
		
		
		this.setFocusable(true);
		this.pack();
		this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		this.setLocationRelativeTo(null);
//		this.addKeyListener(controller);
		this.setVisible(true);

//		controller.init();
//		controller.run();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.log.append(e.getActionCommand());
		System.err.println(log);
		this.getContentPane().remove(0);
		curr_screen++;
		this.add(screens[curr_screen].getView());
		this.validate();
		
	}

}
