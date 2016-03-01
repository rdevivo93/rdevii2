import static org.junit.Assert.*;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Lily
 *
 */
public class MyJLabelTest {

    Robot robot;
    JFrame jframe;
    MyJLabel myjlabel;
    JPanel panel;
    GridLayout grid;
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        jframe = new JFrame();
        grid = new GridLayout(1, 1);
        myjlabel = new MyJLabel(0,0);
        myjlabel.setName("myjlabel");
        Container c = jframe.getContentPane();
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(100, 100));
        panel.setLayout(grid);
        panel.add(myjlabel);
        c.add(panel);
        robot = new Robot();
        panel.revalidate();
        myjlabel.setSize(new Dimension(100,100));
        jframe.setSize(100, 100);
        jframe.setVisible(true);
        robot.mouseMove(50, 50);
        robot.setAutoDelay(100);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        
        robot = null;
        jframe= null;
        myjlabel= null;;
        panel= null;;
        grid = null;;
    }

    private void leftClick(){
        
        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
    }
    
    private void rightClick(){
        robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);
    }
    
    @Test
    public void testOneRightClick() {

        rightClick();
        assertEquals("Text should be 'M' ", "M", myjlabel.getText());
    }
      
    @Test
    public void testIsMineOneLeftClick() {
        myjlabel.setMine();
        leftClick();
        assertEquals("Text should be 'BOOM!!!' ", "BOOM!!!", myjlabel.getText());
    }
    
    @Test
    public void testIsNotMineOneLeftClick() {

        leftClick();
        assertEquals("Text should be '0' ", "0", myjlabel.getText());
    }
    
    @Test
    public void testTwoRightClick() {
        rightClick();
        rightClick();

        System.out.println(myjlabel.getText());
        assertEquals("Text should be '?' ", "?", myjlabel.getText());
    }
    
    @Test
    public void testOneRightOneLeftClick() {
        rightClick();
        leftClick();

        assertEquals("Text should be 'M' ", "M", myjlabel.getText());
    }
    
    @Test
    public void testTwoRightOneLeftClick() {
        rightClick();
        rightClick();
        leftClick();

        assertEquals("Text should be '?' ", "?", myjlabel.getText());
    }
    
    @Test
    public void testRightClickOnClearedLabel() {
        leftClick();
        rightClick();
        assertEquals("Text should be '0' ", "0", myjlabel.getText());
    }
    
    @Test
    public void testRightClickOnDisabledLabel() {
        leftClick();
        rightClick();
        assertFalse("MyJLabel should be disable (false)", myjlabel.isEnabled());
    }
    
    @Test
    public void testThreeRightClick() {
        rightClick();
        rightClick();
        rightClick();
        assertEquals("Text should be '' ", "", myjlabel.getText());
    }
    
    @Test
    public void testResetOnMine(){
        myjlabel.setMine();
        leftClick();
        myjlabel.reset();
        assertFalse("Label should not be a mine (mine = false) ''", myjlabel.isMine());
    }
    
    @Test
    public void testincMinesTouching(){
        myjlabel.incMinesTouching();
        leftClick();
        assertEquals("Label should have 1 mines touching", 1, myjlabel.getMinesTouching());
    }
    
    @Test
    public void testResetMyJLabelWithOneMineTouching(){
        myjlabel.incMinesTouching();
        leftClick();
        myjlabel.reset();
        assertEquals("Label should not have mines touching", 0, myjlabel.getMinesTouching());
    }
    
    @Test
    public void testLeftClickOnExplodedBomb(){
        myjlabel.setMine();
        leftClick();
        leftClick();
        assertEquals("Text should be 'BOOM!!!' ", "BOOM!!!", myjlabel.getText());
    }
    
    @Test
    public void testRightClickOnExplodedBomb(){
    	myjlabel.setMine();
        leftClick();
        rightClick();
        assertEquals("Text should be 'BOOM!!!' ", "BOOM!!!", myjlabel.getText());
    	
    }
    
    @Test
    public void testFourRightClicks(){
    	rightClick();
    	rightClick();
    	rightClick();
    	rightClick();
    	
        assertEquals("Text should be 'M' ", "M", myjlabel.getText());

    	
    	
    	
    	
    }
    
   
    

}
