package UI;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class QuestionnairePanel extends JPanel implements Screen, ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton next;
	private String[] responses = new String[2];

	public QuestionnairePanel(ActionListener listener) {

		// Create GridBagLayout and
		// GridBagConstraints objects.
		// Have container p2 use grid bag layout.
		GridBagConstraints constraints = new GridBagConstraints();
		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weightx = constraints.weighty = 1.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;

		JLabel question1 = new JLabel("Q1) I am familiar with videogames in general.");
		question1.setHorizontalAlignment(SwingConstants.LEFT);
		gridbag.setConstraints(question1, constraints);
		this.add(question1);
		
		JPanel radioPanel1 = createLikert5(1, listener);
		gridbag.setConstraints(radioPanel1, constraints);
		this.add(radioPanel1);

		JLabel question2 = new JLabel("Q2) I am familiar with 2D platforming games.");
		question2.setHorizontalAlignment(SwingConstants.LEFT);
		gridbag.setConstraints(question2, constraints);
		this.add(question2);
		
		JPanel radioPanel2 = createLikert5(2, listener);
		gridbag.setConstraints(radioPanel2, constraints);
		this.add(radioPanel2);

		// Create the OK and Cancel buttons.
		next = new JButton("Next >>");
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		gridbag.setConstraints(next, constraints);
		next.addActionListener(listener);
		next.setEnabled(false);
		this.add(next);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(30, 30, 30, 30), BorderFactory
				.createCompoundBorder(
						BorderFactory.createTitledBorder("Initial Questions"),
						BorderFactory.createEmptyBorder(10, 30, 20, 30))));

	}
	
	private JPanel createLikert5(int qNum, ActionListener listener) {
		JLabel label;
		ButtonGroup group = new ButtonGroup();
		JPanel radioPanel = new JPanel(new GridLayout(2, 5));
		label = new JLabel("strongly disagree");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		radioPanel.add(label);
		radioPanel.add(new JLabel());
		label = new JLabel("neutral");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		radioPanel.add(label);
		radioPanel.add(new JLabel());
		label = new JLabel("strongly agree");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		radioPanel.add(label);
		for (int i = 0; i < 5; i++) {
			JRadioButton radioButton = new JRadioButton();
			radioButton.setHorizontalAlignment(SwingConstants.CENTER);
			radioButton.setActionCommand("q" + qNum + ": " + i + "/4\n");
			group.add(radioButton);
			radioButton.addActionListener(this);
			radioPanel.add(radioButton);

		}
		return radioPanel;
	}

	@Override
	public Component getView() {
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int q = (int)(e.getActionCommand().charAt(1)) - (int)('0');	//ActionCommand will be of form "q" + qNum + " : " + i + "\n"
		responses[q-1] = e.getActionCommand(); //question numbers start at 1; array indices 0
		
		next.setActionCommand(responses[0] + responses[1]);
		
		if(responses[0] != null && responses[1] != null) next.setEnabled(true);
		
	}

	@Override
	public void init() { }

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