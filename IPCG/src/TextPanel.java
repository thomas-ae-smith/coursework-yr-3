import java.awt.Button;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;

public class TextPanel extends JPanel implements Screen, HyperlinkListener {

	private static final long serialVersionUID = 1L;
	private static final String text[][] = {
		{
			"Introduction",
			"<html>Welcome to this evaluation system. <p>If you have not already, please read the Participant "
					+ "Instructions here: <a href=\"www.google.com\">address</a></p><p>You may leave the experiment "
					+ " at any time by closing this window. </p><p>Click next to continue.</p></html>", "Next >>" },
			{ "Instructions", "<html><p>The aim of each section is to reach the exit at the right of the level.</p>"
							+ "<p>The left and right arrow keys control the movement of the green avatar."
							+ "<br>Pressing the up arrow will cause it to jump.</p>"
							+ "<p>Falling off the bottom of the screen or touching a red obstacle will send you back a little.</p>"
							+ "<p>Pressing ESC at any time will allow you to skip the level, though this action will be recorded.</p>"
							+ "<p>After each level will be a short evaluation. Click 'Start' to start.</p></html>", "Start >>"},
			{ "Thank You", "<html><p>Your data has successfully been submitted.</p>"
							+ "<p>Thank you for participating in this experiment," 
							+ "<br>please do not submit any more data.</p>" 
							+ "<p>Close this window or tab to exit the experiment.</p>"
							+ "<br><p>Contact taes1g09@ecs.soton.ac.uk if you have any questions.</p></html>", null } 
		};

	public TextPanel(int textNum, ActionListener listener) {

		// Create GridBagLayout and
		// GridBagConstraints objects.
		// Have container p2 use grid bag layout.
		GridBagConstraints constraints = new GridBagConstraints();
		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weightx = constraints.weighty = 1.0;

		JEditorPane displayText = new JEditorPane();
		displayText.setContentType("text/html");
		displayText.setText(text[textNum][1]);

		// credit:
		// http://explodingpixels.wordpress.com/2008/10/28/make-jeditorpane-use-the-system-font/
		Font font = UIManager.getFont("Label.font");
		String bodyRule = "body { font-family: " + font.getFamily() + "; "
				+ "font-size: " + font.getSize() + "pt; }";
		((HTMLDocument) displayText.getDocument()).getStyleSheet().addRule(
				bodyRule);
		// endcredit

		// displayText.setHorizontalAlignment(SwingConstants.LEFT);
		displayText.setEditable(false);
		displayText.setOpaque(false);
		displayText.addHyperlinkListener(this);
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(displayText, constraints);
		this.add(displayText);

		Button next = new Button(text[textNum][2]);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		gridbag.setConstraints(next, constraints);
		next.addActionListener(listener);
		next.setActionCommand("");
		if(text[textNum][2] != null) {
			this.add(next);
		}
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(30, 30, 30, 30), BorderFactory
				.createCompoundBorder(
						BorderFactory.createTitledBorder(text[textNum][0]),
						BorderFactory.createEmptyBorder(10, 30, 10, 30))));

	}

	@Override
	public Component getView() {
		return this;
	}

	@Override
	public void init() {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			System.err.println("clicked");
			if (java.awt.Desktop.isDesktopSupported()) {
				java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
				System.err.println("hasdesktop");
				if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
					System.err.println("browsesupported");
					try {
						System.err.println("sending to google");
						java.net.URI uri = new java.net.URI("http://www.google.com");
						desktop.browse(uri);
					} catch (Exception ex) {
						System.err.println(ex);
						System.err.println(ex.getMessage());
					}
				}

			}

		}
	}

}
