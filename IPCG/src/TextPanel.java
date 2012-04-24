import java.awt.Button;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class TextPanel extends JPanel implements Screen{

	private static final long serialVersionUID = 1L;
	private static final String text[][] = { {"Introduction", "This is the introductory text."},
											 {"Instructions", "This is the instructory text."},
											 {"Confirmation", "This is the confirmatory text."}};

	public TextPanel(int textNum, ActionListener listener) {

		// Create GridBagLayout and
		// GridBagConstraints objects.
		// Have container p2 use grid bag layout.
		GridBagConstraints constraints = new GridBagConstraints();
		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weightx = constraints.weighty = 1.0;

		JLabel displayText = new JLabel(text[textNum][1]);
		displayText.setHorizontalAlignment(SwingConstants.LEFT);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(displayText, constraints);
		this.add(displayText);

		

		Button next = new Button("Next >>");
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.EAST;
		gridbag.setConstraints(next, constraints);
		next.addActionListener(listener);
		next.setActionCommand("");
		this.add(next);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(30, 30, 30, 30), BorderFactory
				.createCompoundBorder(
						BorderFactory.createTitledBorder(text[textNum][0]),
						BorderFactory.createEmptyBorder(30, 30, 30, 30))));

	}

	@Override
	public Component getView() {
		return this;
	}

}