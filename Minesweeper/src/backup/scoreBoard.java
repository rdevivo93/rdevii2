import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class scoreBoard extends JPanel {
	
	String numbers[] = {"countdown_0.gif","countdown_1.gif", "countdown_2.gif", "countdown_3.gif", "countdown_4.gif",
			"countdown_5.gif", "countdown_6.gif", "countdown_7.gif", "countdown_8.gif", "countdown_9.gif"};
	private Icon iconArray[];
	private JLabel number0;//to display icon tens place
	private JLabel number1;//ones place
	
	public scoreBoard()
	{
		super();
		
		iconArray = new Icon[numbers.length];
		
		for(int count =0; count<numbers.length; count++)
		{
			iconArray[count] = new ImageIcon(numbers[count]);
		}
		//add icon....not working
		ImageIcon logo = new ImageIcon("countdown_0.gif");
		number0 = new JLabel(logo, JLabel.LEFT);
		//number0.setIcon(logo);
		setLayout(new FlowLayout());
		add(number0);
		//number1 = new JLabel(iconArray[0], JLabel.RIGHT);
		//add(number1);
		//make it display ten
		
		
	}
	public void show(){
        JFrame frame = new JFrame();
        frame.getContentPane().add(new scoreBoard());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
	}
	
	
	//decrement score and display amount of flags/mines remaining when user right clicks to mark a mine
	public void decrementScore(){
		
	}
}
	


