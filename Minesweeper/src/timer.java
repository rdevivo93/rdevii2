import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class timer extends Timer {
 private Timer timeclock;
 public timer()
 {
  timeclock = new Timer(1000, new TimerHandler());
  //super(1000, new TimerHandler());
  
  
 }
 
 private class TimerHandler implements ActionListener{
  public void actionPerformed(ActionEvent event)
  {
   start();
  }
 }
 
 public void show()
 {
  JFrame frame = new JFrame("GameMenu");
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  //JComponent newContentPane = new timer();
  //newContentPane.setOpaque(true);
  frame.add(new timer());
  
  frame.pack();
  frame.setVisible(true);
 }
}


