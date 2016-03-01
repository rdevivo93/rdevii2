import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;


/**
 * 
 * @author Ray DeVivo && Lily Lu CS342 Troy Spring 2016 Minesweeper Project
 * 
 *         This class is the MenuBar for the user to click
 *         handles  mnemonic
 *
 */

public class MenuBar extends JMenuBar {
    
    private Minesweeper grid;
    
    public MenuBar(Minesweeper _grid) throws FileNotFoundException {
        // JMenuBar menu = new JMenuBar();
               
        super();
      
        grid = _grid;

        // adding components for the MenuBar
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("Help");
        add(gameMenu);
        add(helpMenu);

        JMenuItem reset = new JMenuItem("Reset");
        JMenuItem topTen = new JMenuItem("Top Ten");
        JMenuItem resetTopTen = new JMenuItem("Reset Top Ten");
        JMenuItem exit = new JMenuItem("Exit");
        gameMenu.add(reset);
        gameMenu.add(topTen);
        gameMenu.add(resetTopTen);
        gameMenu.add(exit);
        

        JMenuItem help = new JMenuItem("Help");
        JMenuItem about = new JMenuItem("About");
        helpMenu.add(help);
        helpMenu.add(about);
        
        
        // Adding in key bindings
        HelpHandler helpHandler = new HelpHandler();
        help.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("H"),"Help");
        help.getActionMap().put("Help", helpHandler);
        help.addActionListener(helpHandler);
        
       
        
        ResetHandler resetHandler = new ResetHandler(_grid);
        reset.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"),"Reset");
        reset.getActionMap().put("Reset", resetHandler);
        reset.addActionListener(resetHandler);
        
        
        
        ExitHandler exitHandler = new ExitHandler();
        exit.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("X"),"exit");
        exit.getActionMap().put("exit", exitHandler);
        exit.addActionListener(exitHandler);
        
        
        topTen.addActionListener(new TopTenHandler());
        resetTopTen.addActionListener(new ResetTopTenHandler());
        about.addActionListener(new AboutHandler());

    } // end of MenuBar() constructor
    
    
    /**
     * Handles the keybinding and the actionlistener for reset
     */
    @SuppressWarnings("serial")
    class ResetHandler extends AbstractAction {
        private Minesweeper grid;
        ResetHandler(Minesweeper g){
            super();
            grid = g;
        }
        
        public void actionPerformed(ActionEvent event) {
            
            Object[] options = {"Restart",
                    "Cancel"};
            int n = JOptionPane.showOptionDialog(null,
                "Would you like to restart?",
                null, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options,
                options[1]);
            
            if(n == 0 ){

                grid.reset();
            }
        }
    }
    
    

    /**
    * delete top ten.txt so that a new top ten list can be started
    *
    */
   class ResetTopTenHandler implements ActionListener{
       public void actionPerformed(ActionEvent event){
           File f = new File("topTen.txt");
           if(f.exists()){
               f.delete();
           }
           else{
               JOptionPane.showMessageDialog(null, "There are no top ten scores to reset.");
           }
           
       }
   }
    
    
    
    class TopTenHandler implements ActionListener {

        private final String topTenFile = "topTen.txt";
        public void actionPerformed(ActionEvent event) {
            String line = null;
            String dialog = "";

          
            try {
                FileReader frTopTen = new FileReader(topTenFile);
                BufferedReader brTopTen = new BufferedReader(frTopTen);

                int i=1; //counter for numbers
           
                while ((line = brTopTen.readLine()) != null) {
                    // print to a dialog box
                    dialog += i + ". " + line + " seconds" + '\n';
                    i++;
                }

                
                brTopTen.close();
            } catch (IOException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, dialog);

            
            

        }
    }
    
    
    /**
     * Handles the keybinding and the actionlistener for exit
     *
     */
    @SuppressWarnings("serial")
    class ExitHandler extends AbstractAction {
        public void actionPerformed(ActionEvent event) {
            Object[] options = {"Exit",
            "Cancel"};
            int n = JOptionPane.showOptionDialog(null,
                "Would you like to exit?",
                null, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options,
                options[1]);
            
            if(n == 0 ){
        
                System.exit(0);
            }
        }
            
    } // end of ExitHandler class

    
    /**
     * Handles the keybinding and the actionlistener for help
     *
     */
    @SuppressWarnings("serial")
    class HelpHandler extends AbstractAction {
        
    
        private final String helpFile = "help.txt";
        
        public void actionPerformed(ActionEvent event) {
            
            String line = null;
            String dialog = "";
            try {

                FileReader frHelp = new FileReader(helpFile);

                BufferedReader brHelp = new BufferedReader(frHelp);
                while ((line = brHelp.readLine()) != null) {
                    // print to a dialog box
                    dialog += line + '\n';
                    
                }
                
                brHelp.close();
                JOptionPane.showMessageDialog(null, dialog);
            } catch (IOException e2) {
                System.err.println("Check to see if you have a file called \"help.txt\"");
            }
            
        }
    } // end of HelpHandler() class

    /**
     * Handles the display for the about file
     * brings up information about the game and developers
     */
    class AboutHandler implements ActionListener {
        private final String aboutFile = "about.txt";
        public void actionPerformed(ActionEvent event) {
                   
            try {
                String line = null;
                String dialog = "";
                
                FileReader frAbout = new FileReader(aboutFile);
                
                BufferedReader brAbout = new BufferedReader(frAbout);
            
            
                // concatenate into one string
                while ((line = brAbout.readLine()) != null) {
                    // print to a dialog box
                    dialog += line + '\n';
                }

                JOptionPane.showMessageDialog(null, dialog);
                brAbout.close();
            } catch (HeadlessException | IOException e1) {
                
                System.err.println("Check to see if you have a file called \"about.txt\"");
            }

            
           
        }
    }
    
    
    
} // end of MenuBar class


