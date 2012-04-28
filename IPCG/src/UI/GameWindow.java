package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

import Core.Controller;
import Model.Constants;

public class GameWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8255319694373975038L;
	private StringBuilder log = new StringBuilder();
	private Screen screens[] = { //new TextPanel(0, this),
			// new QuestionnairePanel(this),
//			new TextPanel(1, this),
//			  new Controller(this),
//			 new FeedbackPanel(3, this),
//			  new Controller(this),
//			 new FeedbackPanel(4, this),
//			 // new Controller(this),
//			 new FeedbackPanel(5, this),
//			 // new Controller(this),
//			 new FeedbackPanel(6, this),
			new ConfirmPanel(log, this), 
			new TextPanel(2, this) };
	private int curr_screen = 0;

	//DEPRECATED
//	public static void main(String[] args) {
//		try {
//			// Attempt to use the system's native look and feel (buttons etc)
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//			System.err.println("Error changing system look and feel.");
//		}
//		new GameWindow();
//	}

	public GameWindow() {
		// this.log = new StringBuilder("# this is the data file\n---\n");
		log.append("# this is the data file\n---\n");
		this.setTitle("IPCG");
		// this.setIgnoreRepaint(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		// Create and set up the content pane
		this.add(screens[0].getView());

		this.setFocusable(true);
		this.pack();
		this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		this.setLocationRelativeTo(null);
		this.addKeyListener(screens[0]);
		this.setVisible(true);

		screens[0].init();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.log.append(e.getActionCommand());
		System.err.println(log);
		this.removeKeyListener(screens[curr_screen]);
		this.getContentPane().remove(0);
		curr_screen++;
		if (curr_screen < screens.length) {
			this.add(screens[curr_screen].getView());
			this.validate();
			this.addKeyListener(screens[curr_screen]);
			screens[curr_screen].init();
		} else {
			System.exit(0);
		}

	}

}
