import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class helpDropDown extends JPanel {
	private JComboBox helpMenu;

	public helpDropDown()
	{
		super(new BorderLayout());
		String items[] = {"Help", "About"};

		helpMenu = new JComboBox(items);
		
		add(helpMenu, BorderLayout.PAGE_START);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		//this.show();
	}
	
	public void show()
	{
		JFrame frame = new JFrame("HelpMenu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComponent newContentPane = new helpDropDown();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		
		frame.pack();
		frame.setVisible(true);
	}

}
