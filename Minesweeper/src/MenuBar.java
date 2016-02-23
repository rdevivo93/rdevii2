import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MenuBar extends JMenuBar {
	private Grid grid;
	String aboutFile = "about.txt";
	String helpFile = "help.txt";
	//String line = null;
	FileReader frHelp; 
	BufferedReader brHelp;
	FileReader frAbout;
	BufferedReader brAbout;
	JMenu gameMenu = new JMenu("Game");
	JMenu helpMenu = new JMenu("Help");
	
	
	
	public MenuBar(Grid _grid) throws FileNotFoundException{
		//JMenuBar menu = new JMenuBar();
		super();
		grid = _grid;
		
		//read help file
		frHelp = new FileReader(helpFile);
		brHelp = new BufferedReader(frHelp);
		frAbout = new FileReader(aboutFile);
		brAbout = new BufferedReader(frAbout);
		//read about file

		
		gameMenu = new JMenu("Game");
		helpMenu = new JMenu("Help");
		
		gameMenu.setMnemonic(KeyEvent.VK_F);
		
		add(gameMenu);
		add(helpMenu);
		
		JMenuItem reset = new JMenuItem("reset", KeyEvent.VK_R);
		JMenuItem topTen = new JMenuItem("Top Ten");
		JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_E);
		gameMenu.add(reset);
		gameMenu.add(topTen);
		gameMenu.add(exit);
		
		
		
		JMenuItem help = new JMenuItem("Help", KeyEvent.VK_L);
		JMenuItem about = new JMenuItem("About");
		helpMenu.add(help);
		helpMenu.add(about);
		
		reset.addActionListener(new resetHandler());
		topTen.addActionListener(new topTenHandler());
		exit.addActionListener(new exitHandler());
		help.addActionListener(new helpHandler());
		about.addActionListener(new aboutHandler());
		

	}  
	
	
		  
	class resetHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.out.println("Selected: " + event.getActionCommand());
			grid.reset();
		}
	}
	
	class topTenHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			
		}
	}
	
	class exitHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
	
	class helpHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String line = null;
			String dialog ="";;

			brHelp = new BufferedReader(frHelp);
			
			try {
				//concatenate into one string
				frHelp = new FileReader(helpFile);
				while((line =brHelp.readLine()) != null){
				//print to a dialog box
					dialog +=line+'\n';			
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,  dialog);

			try {
				brHelp.close();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class aboutHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String line = null;
			String dialog ="";;

			brAbout = new BufferedReader(frAbout);


			try {
				//concatenate into one string
				frAbout = new FileReader(aboutFile);
				while((line =brAbout.readLine()) != null){
				//print to a dialog box
					dialog +=line+'\n';			
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,  dialog);

			try {
				brAbout.close();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
		
}
