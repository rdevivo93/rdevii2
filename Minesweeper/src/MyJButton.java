import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MyJButton extends JButton implements ActionListener
{
  int x;
  private int y;
  private boolean checked;
  private String flag ="";
  private boolean mine;
  
  
  public MyJButton ()
  {
    super ();
    checked = false;
    mine = false;
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
			  if(mine)
			  {
				  setText("BOOM!!!");//endgame()
			  }
			  else
				  setText("###");//display how many bombs touching
				  
		  }
		  else if(SwingUtilities.isRightMouseButton(e))
		  {
			  if(flag == ""){
				  flag = "M";
				  setText("M");
			  }
			  else if(flag == "M"){
				  flag = "?";
				  setText("?");
			  }
			  else if(flag == "?"){
				  setText("");
				  flag = "";
			  }
		  }
		  
		  //JOptionPane.showMessageDialog(null,  s);
	  }
  }
  
  public void actionPerformed(ActionEvent event){
    setText("X");
  }
  
  public void setMine(){
	  mine = true;
  }
  
  public boolean hasMine(){
	  if(mine)
		  return true;
	  return false;
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