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

public class FeedbackPanel extends JPanel implements ActionListener, Screen{

	private static final long serialVersionUID = 1L;
	private JButton next;
	private int qNum;

	public FeedbackPanel(int qNum, ActionListener listener) {

		this.qNum = qNum;
		// Create GridBagLayout and
		// GridBagConstraints objects.
		// Have container p2 use grid bag layout.
		GridBagConstraints constraints = new GridBagConstraints();
		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weightx = constraints.weighty = 1.0;

		constraints.gridwidth = GridBagConstraints.REMAINDER;

		JLabel congrats = new JLabel("Level " + (qNum - 2) + " completed.");
		congrats.setHorizontalAlignment(SwingConstants.LEFT);
		gridbag.setConstraints(congrats, constraints);
		this.add(congrats);

		JLabel question = new JLabel( "Q" +
				qNum
				+ ") The previous level presented a level of challenge that was:");
		question.setHorizontalAlignment(SwingConstants.LEFT);
		gridbag.setConstraints(question, constraints);
		this.add(question);

		JLabel label;
		ButtonGroup group = new ButtonGroup();
		JPanel radioPanel = new JPanel(new GridLayout(2, 7));
		label = new JLabel("too easy");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		radioPanel.add(label);
		radioPanel.add(new JLabel());
		radioPanel.add(new JLabel());
		label = new JLabel("about right");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		radioPanel.add(label);
		radioPanel.add(new JLabel());
		radioPanel.add(new JLabel());
		label = new JLabel("too hard");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		radioPanel.add(label);
		for (int i = 0; i < 7; i++) {
			JRadioButton radioButton = new JRadioButton();
			radioButton.setHorizontalAlignment(SwingConstants.CENTER);
			radioButton.setActionCommand(""+i);
			group.add(radioButton);
			radioButton.addActionListener(this);
			radioPanel.add(radioButton);

		}

		gridbag.setConstraints(radioPanel, constraints);
		this.add(radioPanel);

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
						BorderFactory.createTitledBorder("Evaluation"),
						BorderFactory.createEmptyBorder(30, 30, 30, 30))));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		next.setActionCommand("q" + qNum +": "+ e.getActionCommand() + "/6\n");
		next.setEnabled(true);		
	}

	@Override
	public Component getView() {
		return (Component)this;
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