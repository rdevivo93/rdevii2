import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyScoreBoard extends JLabel implements MouseListener{
	private int count =10;
	
	private JLabel label;
	
	public MyScoreBoard(JLabel label){
		this.label = label;
	}

	
	public void resetScore(){
		count = 10;
		label.setText("Mines Left: " + count);
	}
	
	public void actionPerformed(MouseEvent e, MyJPanel b) {
		if(b.isChecked())return; //do nothing when user right clicks on a disabled tile
		if(b.getFlag() =="") //if switched to no flag, add one to score
			count++;
		else if(b.getFlag() == "M") count --;
		else;//else decrement amount of mines left
        label.setText("Mines Left: " + count);
    }
	
	public void setCount(int c){
		count = c;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



}
