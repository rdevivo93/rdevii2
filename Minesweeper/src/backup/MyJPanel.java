import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

class MyJPanel extends JLabel implements ActionListener {
    
    
    private boolean checked;
    private String flag = "";
    private boolean mine;

    private int minesTouching;
    private int row;
    private int col;
    private boolean enabled = true;

    public MyJPanel(int _row, int _col) {
        super();
        checked = false;
        mine = false;

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
    
    public void reset(){
     setText("");
     flag = "";
     //setBackground(null);
     checked = false;
     mine = false;
     enabled = true;
     minesTouching =0;
     setBackground(Color.BLUE);
        setEnabled(true);
        setOpaque(true);
        
    }

    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    public int getMinesTouching(){
     return minesTouching;
    }
    
    public void actionPerformed(ActionEvent event) {
        setText("X");
    }

    public void setMine() {
        mine = true;
    }
    
    public boolean isChecked(){
     return checked;
    }
    
    public void setChecked(boolean b){
     checked = b;
    }

    public boolean isMine() {
        return mine;
    }
    
    public String getFlag(){
    	return flag;
    }
    
    public boolean isEnabled(){
     return enabled;
    }
    
    public void setDisabled(){
     enabled = false;
    }
    
    
    public void incMinesTouching(){
        minesTouching++;
    }

    public void printtest() {
        System.out.println("work");
    }
    
    class MouseClickHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {

            String s = "";

            if(SwingUtilities.isLeftMouseButton(e))
            {
             if(flag == "M" || flag == "?"){ //do not let user click a flagged mine
              return;
             }
                if(mine)
                {
                    setText("BOOM!!!");
                    setBackground(Color.RED);
                    enabled = false;//endgame()
                }
                else
                {
                    setEnabled(false);
                    setBackground(Color.RED);
                    enabled = false;
                    setText(Integer.toString(minesTouching));//display how many bombs touching
                    if(minesTouching ==0)
                    {
                        //if square has zero mines touching clear the rest
                        //clearTouching(myGrid);
                    }
                }
            }
            else if(SwingUtilities.isRightMouseButton(e))
            {
             if(!enabled) return; //do nothing if already clicked
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

            // JOptionPane.showMessageDialog(null, s);
        }
    }


}