import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author Ray DeVivo && Lily Lu CS342 Troy Spring 2016 Minesweeper Project
 * 
 *         This class is the whole frame of our minesweeper game. Includes main
 *         and launches the game.
 * 
 *
 */

public class Minesweeper extends JFrame implements MouseListener {

    private boolean hasBegun;
    private boolean gameOver;
    private int squaresLeft;

    // GUI components
    private MyJLabel buttons[][];
    private JLabel timeDisplay;
    private JLabel scoreDisplay;
    private javax.swing.Timer timer;
    private MyScoreBoard score;
    private MyTimer myTimer;
    private JButton resetButton;

    public Minesweeper() throws FileNotFoundException {

        super("Minesweeper");
        hasBegun = false; // flag to let game know that it has not started yet
        squaresLeft = 90; // how many tiles left to clear

        // Menu addition
        MenuBar menu = new MenuBar(this);
        setJMenuBar(menu);

        JPanel topPanel = new JPanel(new BorderLayout());
        GridLayout topPanelGrid = new GridLayout(1, 3);
        
        topPanel.setLayout(topPanelGrid);
        topPanel.setPreferredSize(new Dimension(500, 50));
        // Set up for our time display
        timeDisplay = new JLabel("Time: 0");
        myTimer = new MyTimer(timeDisplay);
        timer = new Timer(1000, myTimer);
        topPanel.add(timeDisplay);

        // Add the reset button
        resetButton = new JButton("Reset");
        resetButton.addMouseListener(this);
        topPanel.add(resetButton);

        // set up for score display
        scoreDisplay = new JLabel("Mines left: 10");
        score = new MyScoreBoard(scoreDisplay);
        topPanel.add(scoreDisplay);

        // This is for minefield
        JPanel gridPanel = new JPanel();
        gridPanel.setPreferredSize(new Dimension(500, 500));
        GridLayout grid = new GridLayout(10, 10);
        grid.setVgap(0);
        grid.setHgap(0);

        gridPanel.setLayout(grid); // adds grid to display

        Container container = getContentPane();

        container.setLayout(new BorderLayout());

        // populate grid with buttons
        buttons = new MyJLabel[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j] = new MyJLabel(i, j);
                buttons[i][j].addMouseListener(this);
                gridPanel.add(buttons[i][j]);
            }
        }

        setSize(500, 625);
        setVisible(true);

        container.add(gridPanel, BorderLayout.SOUTH);
        container.add(topPanel, BorderLayout.NORTH);

    }

    /**
     * Reset the game back to original setting as well as each button in the
     * grid
     */
    public void reset() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j].reset();
            }
        }
        squaresLeft = 90; // game will end when this depletes
        timeDisplay.setText("Time: 0");
        myTimer.resetTimer();
        timer.stop();
        hasBegun = false;
        gameOver = false;
        score.resetScore();
        score.setCount(10);// sets score back to ten

    }

    /**
     * Randomly place ten mines onto the grid
     */
    public void layMines(int _row, int _col) {
        int i = 0;
        while (i < 10) {
            int row = (int) (Math.random() * 10);
            int col = (int) (Math.random() * 10);

            // make sure bomb is not already there and not the spot the user
            // clicked on
            if ((!buttons[row][col].isMine()) && (row != _row && col != _col)) {
                buttons[row][col].setMine();

                // increase the mineCounter for the 8 surrounding buttons
                // if statements to prevent out of array/grid bounds
                if (col != 0) {
                    buttons[row][col - 1].incMinesTouching(); // West button
                }
                if (col != 9) {
                    buttons[row][col + 1].incMinesTouching(); // East Button
                }
                if (row != 0) {
                    buttons[row - 1][col].incMinesTouching(); // North button

                    if (col != 0) {
                        buttons[row - 1][col - 1].incMinesTouching(); // NorthWest
                                                                      // button
                    }
                    if (col != 9) {
                        buttons[row - 1][col + 1].incMinesTouching(); // NorthEast
                                                                      // button
                    }
                }
                if (row != 9) {
                    buttons[row + 1][col].incMinesTouching(); // South Button
                    if (col != 0) {
                        buttons[row + 1][col - 1].incMinesTouching(); // SouthWest
                                                                      // button
                    }
                    if (col != 9) {
                        buttons[row + 1][col + 1].incMinesTouching(); // SouthEast
                                                                      // button
                    }
                }
                i++;
            }
        }
    }// end of layMines()

    /**
     * recursive call to deactivate any squares that are touching a square with
     * zero mines touching
     * 
     * @param b MyJPanel the user clicked to begin clearing
     */
    public void clearZeroSpots(MyJLabel b) {
        if (b.isChecked() || b.getFlag() != "") // if already looked at or is
                                                // flagged skip this square
            return;
        if (b.getMinesTouching() > 0) { // end of recursive call, last square
                                        // that has a mine touching
            b.setEnabled(false);
            squaresLeft--;
            b.setDisabled();
            b.setBackground(Color.RED);
            b.setText(Integer.toString(b.getMinesTouching()));
            b.setChecked(true);
            return;
        }
        if (b.isMine()) { // stop at a spot with mine, (should never actually
                          // come to this)
            return;

        } else {
            squaresLeft--;
            b.setEnabled(false);
            b.setDisabled();
            b.setBackground(Color.RED);
            b.setChecked(true);
            b.setText(Integer.toString(b.getMinesTouching()));

            int row = b.getRow();
            int col = b.getCol();

            // clear left and do not go passed left side of grid
            if (col != 0) {
                clearZeroSpots(buttons[row][col - 1]); // West button
            }
            // clear right and do not go passed right side of grid
            if (col != 9) {
                clearZeroSpots(buttons[row][col + 1]); // East Button
            }
            // do not go passed bottom of grid
            if (row != 0) {
                clearZeroSpots(buttons[row - 1][col]);
                // do not pass left side of grid
                if (col != 0) {
                    clearZeroSpots(buttons[row - 1][col - 1]); // NorthWest
                                                               // button
                }
                // do not go passed right side of grid
                if (col != 9) {
                    clearZeroSpots(buttons[row - 1][col + 1]); // NorthEast
                                                               // button

                }
            }
            // make sure to not go passed the right end of grid
            if (row != 9) {
                clearZeroSpots(buttons[row + 1][col]); // South Button
                if (col != 0) {// do not go passed top of grid
                    clearZeroSpots(buttons[row + 1][col - 1]); // SouthWest
                                                               // button
                }
                if (col != 9) {// do not go passed bottom grid
                    clearZeroSpots(buttons[row + 1][col + 1]); // SouthEast
                                                               // Button
                }
            }
        }
    } // end of clearZeroSpots()

    /**
     * User clicked a mine and lost the game
     */
    public void endGame() {
        gameOver = true;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (buttons[i][j].isEnabled() && buttons[i][j].isMine()) {
                    buttons[i][j].setText("Boom");
                    buttons[i][j].setBackground(Color.RED);
                }
            }
        }
        myTimer.resetTimer();
        timer.stop();
        
        Object[] options = {"New Game",
        "Exit"};
        int n = JOptionPane.showOptionDialog(null,
                "You blew up a mine. Game over \n "
                        + "Want to play again?",
                null, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options,
                options[1]);
            
            if(n == 0 ){
        
                reset();
            }
            else{
                System.exit(0);
            }
            
            
    } // end of endGame()

    public void mouseClicked(MouseEvent e) {

        // reset button resets game
        if (e.getSource() == resetButton) {
            reset();

        } else if (e.getSource() instanceof MyJLabel) {
            // get instance of square clicked
            MyJLabel sq = (MyJLabel) e.getSource();

            if (gameOver)
                return; // do nothing if game is over

            // decrement mine counter when flagged
            if (SwingUtilities.isRightMouseButton(e)) {
                score.actionPerformed(sq);
                return;
                // return; //do nothing to grid with right click
            }

            if (sq.isMine() && sq.getFlag() == "") { // make sure not to end
                                                     // game on flagged square
                endGame();
                // System.out.println("This is a mine");
            } else {
                // System.out.println("There is no mine here");
                squaresLeft--;

                // clear boxes around boxes with zero mines touching
                if (sq.getMinesTouching() == 0) {
                    clearZeroSpots(sq);
                }
                // if after clearing mines there are no empty boxes left, end
                // game
                if (squaresLeft == -1) {
                    winGame();
                }
            }
        }
    }

    /**
     * If user clicks all squares without mines, end game and check if user has
     * set new high score
     */
    public void winGame() {
        int gameTime = myTimer.getTime();
        timer.stop();// make sure to stop before dialog box

        JOptionPane.showMessageDialog(null,
                "You win. Game over \n Click restart for a new game");

        myTimer.resetTimer();

        // check for high score, create file if does not exist
        File inFile = new File("topTen.txt");
        if (!inFile.exists()) {
            try {
                inFile.createNewFile();
                writeEmptyList(inFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // parse lines into two tokens: name and score from high score list
        String line = null;
        String[] tokens = new String[2];

        String[] names = new String[10];
        int[] scores = new int[10];

        if (inFile.exists()) {
            if (inFile.isFile() && inFile.canRead()) {
                Scanner input = null;

                try {
                    input = new Scanner(inFile);
                    int i = 0, j = 0;
                    while (input.hasNextLine()) {

                        line = input.nextLine();
                        tokens = line.split(" ");

                        names[i] = tokens[0];
                        scores[i] = Integer.parseInt(tokens[1]);
                        i++;

                    }

                } catch (FileNotFoundException e) {
                    System.err.println("Error opening file");
                    System.exit(1);
                }
            }
        }
        // if game score is greater than or equal to lowest score, add to top
        // ten
        if (gameTime <= scores[9]) {
            updateScores(inFile, gameTime, names, scores);
        }

    } // end of winGame()

    /**
     * Update top ten scores list, incoming score is at least better then tenth
     * place
     * 
     * @param f file with top ten scores
     * @param newScore score that will be paced in top ten
     * @param names array of size ten of names of top ten scores best to worst
     * @param scores array of size ten of scores of top ten scores best to worst
     */
    public void updateScores(File f, int newScore, String[] names, int[] scores) {
        int i = 9;
        Object[] choices = null;
        String newName = (String) JOptionPane.showInputDialog(this,
                "New High Score!\n" + "Please enter your name... ",
                "New High Score", JOptionPane.PLAIN_MESSAGE, null, choices,
                null);

        // navigate array from the end to find spot for new score
     
        boolean scorePlace = false;
        while (!scorePlace && i >= 0) {
            if (i == 0 && newScore < scores[0]) { // if first place score make
                                                // i=0 update the scoreboard
                i = 0;
                scorePlace = true;
            }
            if (newScore < scores[i]) // move down from ten until reach a score
                // better than new and save i
                i--;
            else if (newScore == scores[i]) {// if same score put value after
                // previous score
                i++;
                scorePlace = true;
            } else {
                i++;
                scorePlace = true;
            }
            
        }

        int j = 9; // save i and start from back again removing last score and
                   // moving every score down one
        if (i == 0) { // if top score set top score manually to prevent array
                      // out of bounds
            scores[i] = newScore;
            names[i] = newName;
            i++;

            while (j > i) {// move everything down
                scores[j] = scores[j - 1];
                names[j] = names[j - 1];
                j--;
            }
        } else { // if not top score...do not have to worry about array bounds
            while (j > i && j > 0) {
                scores[j] = scores[j - 1];
                names[j] = names[j - 1];
                j--;
            }
            scores[i] = newScore; // set new top score
            names[i] = newName;
        }

        // write new arrays to file in order from least to greatest
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(f));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // write ten scores
        i = 0;
        while (i < 10) {
            try {
                bw.write("" + names[i] + " " + scores[i]);
                bw.newLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            i++;
        }
        try {
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } // end of update scores

    /**
     * if file does not exist create empty file with stars as names and very
     * high times for scores
     * 
     * @param f  previously made file to be written to
     */
    public void writeEmptyList(File f) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(f));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int i = 0;
        while (i < 10) {
            try {
                bw.write("***" + " " + "99999"); // blank name and unrealistic
                                                 // high score
                bw.newLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            i++;
        }
        try {
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } // end of writeEmptyList

    /**
     * Event handler; changes an icon on the start button when the left mouse
     * button is released
     */
    public void mouseReleased(MouseEvent e) {

        if ((e.getSource() instanceof MyJLabel) && !hasBegun) {
            MyJLabel sq = (MyJLabel) e.getSource();
            hasBegun = true; // sets flag to to not lay mines after every click
            timer.start();
            layMines(sq.getRow(), sq.getCol());
            squaresLeft--;
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public static void main(String args[]) throws FileNotFoundException {
        Minesweeper g = new Minesweeper();
    }

}
