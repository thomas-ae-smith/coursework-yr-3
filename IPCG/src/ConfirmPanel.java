import java.awt.Component;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ConfirmPanel extends JPanel implements ActionListener, Screen{

	private static final long serialVersionUID = 1L;
	private JButton submit;
	private JTextArea logDisplay;
	private StringBuilder log;

	public ConfirmPanel(StringBuilder log, ActionListener listener) {

		this.log = log;
		// Create GridBagLayout and
		// GridBagConstraints objects.
		// Have container p2 use grid bag layout.
		GridBagConstraints constraints = new GridBagConstraints();
		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);

		constraints.fill= GridBagConstraints.HORIZONTAL;

		constraints.weightx = 1.0;

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		JLabel question = new JLabel( "This data has been collected during the course of this session:");
		question.setHorizontalAlignment(SwingConstants.LEFT);
		gridbag.setConstraints(question, constraints);
		this.add(question);

		constraints.fill= GridBagConstraints.BOTH;
		constraints.weighty = 1.0;
		
		
		JScrollPane scroll = new JScrollPane(logDisplay = new JTextArea(log.toString()));
		logDisplay.setEditable(false);
		gridbag.setConstraints(scroll, constraints);
		this.add(scroll);
		
		constraints.fill= GridBagConstraints.HORIZONTAL;
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
				.createCompoundBorder(
						BorderFactory.createTitledBorder("Submission Confirmation"),
						BorderFactory.createEmptyBorder(30, 30, 30, 30))));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		    // Construct data
		    String data = URLEncoder.encode("data", "UTF-8") + "=" + URLEncoder.encode(log.toString(), "UTF-8");
		    data += "&" + URLEncoder.encode("submit", "UTF-8") + "=" + URLEncoder.encode("Submit", "UTF-8");
System.err.println("compiled: " + data);
		    // Send data
		    URL url = new URL("http://users.ecs.soton.ac.uk/taes1g09/submit.php");
		    URLConnection conn = url.openConnection();
		    System.err.println("urlopened");
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    System.err.println("writer");
		    wr.write(data);
		    wr.flush();

		    System.err.println("written");
		    // Get the response
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		        // Process line...
System.err.println("line: " + line);		    	
		    }
		    wr.close();
		    rd.close();
		} catch (Exception ex) {
			System.err.println(ex.getLocalizedMessage());
		}	
	}

	@Override
	public Component getView() {
		return (Component)this;
	}

	@Override
	public void init() {
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

}