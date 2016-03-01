import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * 
 * @author Ray DeVivo && Lily Lu CS342 Troy Spring 2016 Minesweeper Project
 * 
 *         This class handles when a user left and right click on a square
 *         for the minesweeper game
 * 
 *
 */
class MyJLabel extends JLabel implements ActionListener {

    private boolean checked; // This MyJLabel has been cleared, user cannot click on it
    private String flag; // Symbol displayed the state of this MyJLabel to the user
    private boolean mine; // flag for if this MyJPanel is a mine

    private int minesTouching; // counter for how many mines are touching this MyJLabel
    private int row;    // row  where this MyJLabel is in the minesweeper grid
    private int col;    // col  where this MyJLabel is in the minesweeper grid
    private boolean enabled;

    public MyJLabel(int _row, int _col) {
        super();
        enabled = true;
        checked = false;
        mine = false;
        flag = "";

        minesTouching = 0;

        row = _row;
        col = _col;
        setBackground(Color.BLUE);
        setEnabled(true);
        setOpaque(true);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setBorder(border);
        addMouseListener(new MouseClickHandler());

    }

    /**
     * Reset the settings for this MyJLabel
     */
    public void reset() {
        setText("");
        flag = "";
        // setBackground(null);
        checked = false;
        mine = false;
        enabled = true;
        minesTouching = 0;
        setBackground(Color.BLUE);
        setEnabled(true);
        setOpaque(true);

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getMinesTouching() {
        return minesTouching;
    }


    public void setMine() {
        mine = true;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean b) {
        checked = b;
    }

    public boolean isMine() {
        return mine;
    }

    public String getFlag() {
        return flag;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setDisabled() {
        enabled = false;
    }

    /**
     * Increase the mine counter for this MyJLabel
     */
    public void incMinesTouching() {
        minesTouching++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Subclass to handle the mouse click actions on each of the labels
     * for changing the state of the MyJPanel label
     */
    class MouseClickHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {

            //User left click on this MyJLabel
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (flag == "M" || flag == "?") { // do not let user click a
                                                  // flagged mine
                    return;
                }
                if (mine) {
                    setText("BOOM!!!");
                    setBackground(Color.RED);
                    enabled = false;
                } else {
                    setEnabled(false);
                    setBackground(Color.RED);
                    enabled = false;
                    setText(Integer.toString(minesTouching));// display how many
                                                             // bombs touching
                    
                }
            //User right click on this MyJLabel
            } else if (SwingUtilities.isRightMouseButton(e)) {
                if (!enabled)
                    return; // do nothing if already clicked
                if (flag == "") {
                    flag = "M";
                    setText("M");
                    setBackground(Color.GREEN);
                } else if (flag == "M") {
                    flag = "?";
                    setText("?");
                    
                } else if (flag == "?") {
                    setText("");
                    flag = "";
                    setBackground(Color.BLUE);
                }
            }
        }
    }

} // end of MyJPanel class