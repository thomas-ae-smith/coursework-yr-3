package UI;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;

public class ConfirmPanel extends JPanel implements ActionListener, Screen, HyperlinkListener {

	private static final long serialVersionUID = 1L;
	private JButton submit;
	private JTextArea logDisplay;
	private StringBuilder log;
	private ActionListener listener;

	public ConfirmPanel(StringBuilder log, ActionListener listener) {

		this.log = log;
		this.listener = listener;
		// Create GridBagLayout and
		// GridBagConstraints objects.
		// Have container p2 use grid bag layout.
		GridBagConstraints constraints = new GridBagConstraints();
		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);

		constraints.fill = GridBagConstraints.HORIZONTAL;

		constraints.weightx = 1.0;

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		JLabel question = new JLabel(
				"This data has been collected during the course of this session:");
		question.setHorizontalAlignment(SwingConstants.LEFT);
		gridbag.setConstraints(question, constraints);
		this.add(question);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1.0;

		JScrollPane scroll = new JScrollPane(logDisplay = new JTextArea(
				log.toString()));
		logDisplay.setEditable(false);
		gridbag.setConstraints(scroll, constraints);
		this.add(scroll);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weighty = 0;

		submit = new JButton("Submit");
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		gridbag.setConstraints(submit, constraints);
		submit.addActionListener(this);
		submit.setActionCommand("");
		this.add(submit);

		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(30, 30, 30, 30), BorderFactory
				.createCompoundBorder(BorderFactory
						.createTitledBorder("Submission Confirmation"),
						BorderFactory.createEmptyBorder(30, 30, 30, 30))));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean success = false;
		try {
			// Construct data
			String data = URLEncoder.encode("data", "UTF-8") + "="
					+ URLEncoder.encode(log.toString(), "UTF-8");
			data += "&" + URLEncoder.encode("submit", "UTF-8") + "="
					+ URLEncoder.encode("Submit", "UTF-8");
//			System.err.println("compiled: " + data);
			// Send data
			URL url = new URL(
					"http://users.ecs.soton.ac.uk/taes1g09/3YP/submit.php");
			URLConnection conn = url.openConnection();
//			System.err.println("urlopened");
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
//			System.err.println("writer");
			wr.write(data);
			wr.flush();

//			System.err.println("written");
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			if ((line = rd.readLine()).charAt(4) == '1')
				success = true; // if the first character in the comment on the
								// first line of the response is 1: success
			else
				success = false; // somewhat redundant
			while ((line = rd.readLine()) != null) {
				// Process line...
//				System.err.println("line: " + line);
			}
			wr.close();
			rd.close();
		} catch (Exception ex) {
			//System.err.println("Error: " + ex.getLocalizedMessage());
		}
		if (success) {
			listener.actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, ""));
		} else {
			this.remove(submit);
			
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.weightx = 1.0;

			constraints.gridwidth = GridBagConstraints.REMAINDER;
			
			JEditorPane pane = this.createErrorWarning();
			((GridBagLayout)this.getLayout()).setConstraints(pane, constraints);
			this.add(pane);
			this.logDisplay.selectAll(); 
			this.logDisplay.requestFocus();
			this.validate();
		}
		
	}

	private JEditorPane createErrorWarning() {
		JEditorPane displayError = new JEditorPane();
		displayError.setContentType("text/html");
		displayError.setText("<html><div style=\"color:red\">Error: automatic submission failed. Please copy the text above into the manual submission system at <a href=\"address\">http://users.ecs.soton.ac.uk/taes1g09/3YP/submit.php</a>.</div></html>");//TODO correct address

		// credit:
		// http://explodingpixels.wordpress.com/2008/10/28/make-jeditorpane-use-the-system-font/
		Font font = UIManager.getFont("Label.font");
		String bodyRule = "body { font-family: " + font.getFamily() + "; "
				+ "font-size: " + font.getSize() + "pt; }";
		((HTMLDocument) displayError.getDocument()).getStyleSheet().addRule(
				bodyRule);
		// endcredit

		displayError.setEditable(false);
		displayError.setOpaque(false);
		displayError.addHyperlinkListener(this);
		return displayError;
	}

	@Override
	public Component getView() {
		return (Component) this;
	}

	@Override
	public void init() {
		log.append("# EOF");
		logDisplay.setText(log.toString());
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
						java.net.URI uri = new java.net.URI("http://users.ecs.soton.ac.uk/taes1g09/3YP/submit.php");
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