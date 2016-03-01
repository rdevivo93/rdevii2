import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;


/**
 * 
 * @author Ray DeVivo && Lily Lu CS342 Troy Spring 2016 Minesweeper Project
 * 
 *         This class handles the display of the number of mines the user had flagged
 *
 */

public class MyScoreBoard implements MouseListener{
	private int count =10;
	
	private JLabel label;
	
	public MyScoreBoard(JLabel label){
		this.label = label;
	}
	
	public void resetScore(){
		count = 10;
		label.setText("Mines left: " + count);
	}
	
	/**
	 * increase and decrease the count based on the state of the
     * clicked label
	 * @param label MyJLabel that was clicked
	 */
	public void actionPerformed(MyJLabel _label) {
		if(_label.isChecked())return; //do nothing when user right clicks on a disabled tile
		if(_label.getFlag() =="?") //if switched to no flag, add one to score
			count++;
		else if(_label.getFlag() == "M") count --;
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
