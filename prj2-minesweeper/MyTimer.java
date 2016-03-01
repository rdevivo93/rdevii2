import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author Ray DeVivo && Lily Lu CS342 Troy Spring 2016 Minesweeper Project
 * 
 *         Timer class that handles how many seconds have passed since the 
 *          user began their minesweeper game
 *
 */


public class MyTimer implements ActionListener{
    
    private int count;    // number of seconds that passed

    private JLabel label; // JLabel that displays the time
    
    
    public MyTimer(JLabel label){
        count = 0;
        this.label = label;
    }
    
    public void resetTimer(){
    	count =0;
    }

    //increment the number of seconds passed
    public void actionPerformed(ActionEvent e) {

        count++;
        label.setText("Time: " + count);
    }
    
    public int getTime(){
    	return count;
    }
    
}