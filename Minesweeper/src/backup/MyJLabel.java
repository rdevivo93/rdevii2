import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyJLabel extends JLabel implements ActionListener
{
  int x;
  private int y;
  private boolean checked;
  
  public MyJLabel ()
  {
    super ();
    checked = false;
    //this.x = i;
    //this.y = j;
    addMouseListener(new MouseClickHandler());
    
  }
  
  class MouseClickHandler extends MouseAdapter
  {
	  
	  public void mouseClicked(MouseEvent e)
	  {
		  
		  String s ="";
		  
		  if(SwingUtilities.isLeftMouseButton(e))
		  {
			s="Left " + x;  //do some shit
		  }
		  else if(SwingUtilities.isRightMouseButton(e))
		  {
			s="Right";  //do some shit
		  }
		  
		  JOptionPane.showMessageDialog(null,  s);
	  }
  }
  
  public void actionPerformed(ActionEvent event){
    setText("X");
  }
  
  public void setX(int X){
    x=X;
  }
  public void setY(int Y){
    y=Y;
  }
  
  public int getY()
  {
    return y;
  }
  
  public int getX()
  {
    return x;
  }
  
  public void printtest(){
    System.out.println("work");
  }
  
}