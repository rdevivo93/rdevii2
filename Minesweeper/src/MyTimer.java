import java.awt.event.*;
import javax.swing.*;

public class MyTimer implements ActionListener{
    
    private int count = 0;

    private JLabel label;

    public MyTimer(JLabel label){
        this.label = label;
    }
    
    public void resetTimer(){
    	count =0;
    }

    public void actionPerformed(ActionEvent e) {

        count++;

        label.setText("Count: " + count);
    }
    
    public int getTime(){
    	return count;
    }
}