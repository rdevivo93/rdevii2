import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class gameDropDown extends JPanel {
	private JComboBox GameMenu;

	public gameDropDown()
	{
		super(new BorderLayout());
		String items[] = {"Reset", "Top ten", "eXit"};

		GameMenu = new JComboBox(items);
		
		add(GameMenu, BorderLayout.PAGE_START);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		//this.show();
		
	}
	
	public void show()
	{
		JFrame frame = new JFrame("GameMenu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComponent newContentPane = new gameDropDown();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		
		frame.pack();
		frame.setVisible(true);
	}

}