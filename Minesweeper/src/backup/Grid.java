import java.io.*;
import java.util.*;
import java.util.Scanner.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;

public class Grid extends JFrame implements ActionListener, MouseListener {
    private MyJButton buttons[][];
    private MenuBar menu;
    private GridLayout grid;
    private Container container;

    private boolean hasBegun;
    private boolean gameOver;
    
    private JPanel gridPanel;
    private JLabel timeDisplay;
    private JLabel scoreDisplay;
    private javax.swing.Timer timer;
    private MyScoreBoard score;
    private MyTimer myTimer;
    
    public Grid() throws FileNotFoundException {
        super("My Grid");
        hasBegun = false;
        
        
        //Menu addition
        menu = new MenuBar(this);
        setJMenuBar(menu);
        
        
        
        //Set up for our time display
        timeDisplay = new JLabel("0");
        myTimer = new MyTimer(timeDisplay);
        JPanel timePanel = new JPanel();
        timePanel.add(timeDisplay);
        timer = new Timer(1000, myTimer);

        
        //set up for score display
        scoreDisplay = new JLabel("10");
        score = new MyScoreBoard(scoreDisplay);
        JPanel scorePanel = new JPanel();
        scorePanel.add(scoreDisplay);



        
        //This is for minefield
        gridPanel = new JPanel();
        gridPanel.setPreferredSize(new Dimension(500, 500));
        grid = new GridLayout(10, 10);
        grid.setVgap(0);
        grid.setHgap(0);
        
        gridPanel.setLayout(grid);

        container = getContentPane();

        container.setLayout(new BorderLayout());

        // populate grid with buttons
        buttons = new MyJButton[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j] = new MyJButton(i, j);
                buttons[i][j].addMouseListener( this );
                gridPanel.add(buttons[i][j]);
            }
        }

        setSize(800, 800);
        setVisible(true);

        container.add(gridPanel, BorderLayout.SOUTH);
        container.add(timePanel, BorderLayout.NORTH);
        container.add(scorePanel, BorderLayout.EAST);

    }
    
    public void reset(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
            	//buttons[i][j] = new MyJButton(i, j);
                buttons[i][j].reset();
                //buttons[i][j] = new MyJButton(i, j);
            }
        }
        
        hasBegun = false;
        gameOver = false;
        score.resetScore();
        //myTimer.resetTimer();
       // timer = new Timer(1000, myTimer )
        myTimer.resetTimer();
        timer.restart();
    	
    }

    public void layMines() {
        int i = 0;
        while (i < 10) {
            int row = (int) (Math.random() * 10);
            int col = (int) (Math.random() * 10);
            if ((!buttons[row][col].isMine())) {
                buttons[row][col].setMine();
                
                //increase the mineCounter for the 8 surrounding buttons
                
                if (col != 0){
                    buttons[row][col-1].incMinesTouching();   //West button
                    
                }
                if(col != 9){
                    buttons[row][col+1].incMinesTouching();   // East Button

                }
                if(row != 0){
                    buttons[row-1][col].incMinesTouching(); // North button
                    
                    if(col != 0) {
                        buttons[row-1][col-1].incMinesTouching(); //NorthWest Button
                    }
                    if (col!=9){
                        buttons[row-1][col+1].incMinesTouching(); //NorthEast Button
                    }
                }
                
                if (row != 9){
                    buttons[row+1][col].incMinesTouching();       //South Button
                  if (col != 0){
                    buttons[row+1][col-1].incMinesTouching();     //SouthWest button
                  }
                  if (col !=9){
                      buttons[row+1][col+1].incMinesTouching();   //SouthEast Button
                  }
                  
                  
                }
                
                i++;
            }
        }

    }
    
    public void clearZeroSpots(MyJButton b){
    	if(b.isChecked())return;
    	if(b.getMinesTouching()>0)
    	{
    		b.setEnabled(false);
    		b.setDisabled();
    		b.setBackground(Color.RED);
    		b.setText(Integer.toString(b.getMinesTouching()));
    		b.setChecked(true);
    		return;
    	}
    	if(b.isMine())
    	{
    		return;
    	}
    	else
    	{
    		b.setEnabled(false);
    		b.setDisabled();
    		b.setBackground(Color.RED);
    		b.setChecked(true);
    		b.setText(Integer.toString(b.getMinesTouching()));
    		
    		int row = b.getRow();
    		int col = b.getCol();
    		
            if (col != 0){
                clearZeroSpots(buttons[row][col-1]);   //West button
                
            }
            if(col != 9){
                clearZeroSpots(buttons[row][col+1]);   // East Button
            }
            if(row != 0){
                clearZeroSpots(buttons[row-1][col]);
                
                if(col != 0) {
                	clearZeroSpots(buttons[row-1][col-1]); //NorthWest Button
             //   	clearZeroSpots(buttons[row][col-1]);   //West button
                }
                if (col!=9){
                    clearZeroSpots(buttons[row-1][col+1]); //NorthEast Button
               //     clearZeroSpots(buttons[row][col+1]);   // East Button
                }
            }
            
            if (row != 9){
                clearZeroSpots(buttons[row+1][col]);      //South Button
              if (col != 0){
                clearZeroSpots(buttons[row+1][col-1]);     //SouthWest button
              }
              if (col !=9){
                  clearZeroSpots(buttons[row+1][col+1]);   //SouthEast Button
              }
              
              
            }
    	}
    	
    	
    }

    public void endGame(){
    	gameOver = true;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
            	if(buttons[i][j].isEnabled() && buttons[i][j].isMine()){
            		buttons[i][j].setText("Boom");
            		buttons[i][j].setBackground(Color.RED);
            	}
            	
            //buttons[i][j] = new MyJButton(i, j);
            }
        }
        timer.stop();
		JOptionPane.showMessageDialog(null,"You blew up a mine. Game over \n Click restart for a new game");
		int gameTime = myTimer.getTime();
		System.out.println("Time elapsed: " + gameTime);
		
		//code to save time to file if in top ten.....
		//promt for name first
		

    }
    
    public void mouseClicked(MouseEvent e){
        
        MyJButton sq = (MyJButton)e.getSource();
        
        if(SwingUtilities.isRightMouseButton(e)){
        		score.actionPerformed(e, sq);
        		return;
        	//return;  //do nothing to grid with right click
        }
        
        System.out.println("Clicked on mine in row: " + sq.getRow() + " col: " + sq.getCol());
        if (sq.isMine()&& sq.getFlag() ==""){ //make sure not to end game on flagged square
        	endGame();
            System.out.println("This is a mine");
        }
        else{
        	System.out.println("There is no mine here");
        	if(sq.getMinesTouching()==0)
        	{
        		clearZeroSpots(sq);
        	}
        }
    }
    
    public void mousePressed(MouseEvent e){
    }

    /**
     * Event handler; changes an icon on the start button 
     * when the left mouse button is released
     */
    public void mouseReleased(MouseEvent e)
    {
        
        if (!hasBegun){
            hasBegun = true;
            timer.start();
            layMines();
        }
    }
    
    

    public void mouseEntered(MouseEvent e){
    }

    public void mouseExited(MouseEvent e){
    }
    

    public static void main(String args[]) throws FileNotFoundException {
        Grid g = new Grid();
    }

    //@Override
    public void actionPerformed(ActionEvent event) {
    }
    
    
}
