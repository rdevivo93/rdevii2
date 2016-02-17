import java.io.*;
import java.util.*;
import java.util.Scanner.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;

public class Grid extends JFrame{
  MyJButton buttons[][];
  //MyJLabel buttons[][];

  GridLayout grid;
  private Container container;
  
  
  public Grid(){
    super("My Grid");
    

    
    grid = new GridLayout(10, 10);
    grid.setVgap(0);
    grid.setHgap(0);
    container = getContentPane();
    container.setLayout(grid);
    
    //populate grid with buttons
    buttons = new MyJButton[10][10];
    for(int i=0; i <10; i++){
      for(int j=0; j<10; j++){
        buttons[i][j] = new MyJButton();
        buttons[i][j].setX(j);
        buttons[i][j].setY(i);
        container.add(buttons[i][j]);
      }
    }
        
    setSize( 500, 500 );
    setVisible(true);
    layMines();
    	
    }
  
  
  public void layMines()
  {
	  int i=0;
	  while(i<10)
	  {
		  int randomx = (int)(Math.random()* 10);
		  int randomy = (int)(Math.random()* 10);
		  if((!buttons[randomy][randomx].hasMine()))
		  {
			  buttons[randomy][randomx].setMine();
			  i++;
		  }
	  }
	  
  }

    
  
   public static void main(String args[]){
     Grid g = new Grid();
   }
}
    
    