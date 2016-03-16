//package SVU;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.File;


/**
 * 
 * @author Ray DeVivo && Arnav Dahal CS342 Troy Spring 2016 RSAProject
 * 
 *         This class runs the whole encryption process. It creates a gui with a drop down that lets the user decide 
 *         between encryptionkey creation, blocking and unblocking.
 *         
 *      
 * 
 *
 */


public class MyRSA extends JFrame {

	private JComboBox menu;
	private String[] choices = { "Key Creation", "Block Text file", "Unblock blocked file", "Encrypt/Decrypt" };//drop down choices
	private Huge_Unsigned_Int prime1;//p
	private Huge_Unsigned_Int prime2;//q
	private Huge_Unsigned_Int n; //p*q
	private int maxblockSize;
	private ArrayList<Huge_Unsigned_Int> primes; //list of primes from file
	private Key privateKey;//decrypyt key
	private Key publicKey;//encrypt key
	
	/**
	 * create gui
	 */
	public MyRSA() {
		super("RSA Encryption");

		Container container = getContentPane();
		container.setLayout(new FlowLayout());

		menu = new JComboBox(choices);
		menu.addActionListener(new menuHandler());

		container.add(menu);
		setSize(500, 500);
		setVisible(true);
	}

	
	/**
	 * implements choices from drop down menu
	 * @author Ray
	 *
	 */
	private class menuHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JComboBox c = (JComboBox) event.getSource();
			String option = (String) c.getSelectedItem();

			switch (option) {
			case "Key Creation":
				createKey();
				break;
			case "Block Text file":
				blockFile();
				break;
			case "Unblock blocked file":
				unBlock();
				break;
			case "Encrypt/Decrypt":
				encrypt();
				break;
			default:
				System.out.println("No choice selected");
				break;

			}

		}

		/**
		 *  Encrypts the blocked file
		 */
		private void encrypt() {

			String fileName = null;
			String fileName2 = null;
			File f; // File they chose
			fileName = JOptionPane.showInputDialog("Please enter key file name:", "");
			if (fileName == null) // If they exit or press cancel
				return; // exits
			f = new File(fileName); // Finds the file on the system
			if (f.exists() && !f.isDirectory()) { // If the files exists yet or not
				Crypt encde = new Crypt(fileName); // New Crypt object
				fileName2 = JOptionPane.showInputDialog("Please enter block file name:", "");
				if (fileName2 == null) // If they exit or press cancel
					return; // EXIT
				f = new File(fileName2);
				if (f.exists() && !f.isDirectory()) // if the file exists
					encde.changeBlockFile(fileName2);// run this
				else
					JOptionPane.showMessageDialog(null, "Block File doesn't exist"); // if file doesnt exist

			} else
				JOptionPane.showMessageDialog(null, "Key File doesn't exist");

		}

		/**
		 *  unBlocks the blocked File. See Encrypt() for indepth on code
		 */
		private void unBlock() {
			String fileName = null;
			String blockSizeS = null;
			int blockSize = 0;
			File f;
			fileName = JOptionPane.showInputDialog("Please enter file name:", "");
			if (fileName == null)
				return;
			f = new File(fileName);
			blockSizeS = JOptionPane.showInputDialog("Please enter block size: ", "");
			if (blockSizeS == null)
				return;
			blockSize = Integer.parseInt(blockSizeS);

			if (f.exists() && !f.isDirectory()) {
				cipher Letter = new cipher(fileName, blockSize);
				if (Letter.checkForBlock())
					Letter.createUnBlockedFile();
				else
					JOptionPane.showMessageDialog(null, "Unblocker detected INVALID block size");
			} else
				JOptionPane.showMessageDialog(null, "Block File doesn't exist");

		}
		
		/**
		 *  Blocks the message File. See Encrypt() for indepth on code
		 */
		private void blockFile() {
			String fileName = null;
			String blockSizeS = null;
			int blockSize = 0;
			File f;

			fileName = JOptionPane.showInputDialog("Please enter file name:", "");
			if (fileName == null)
				return;
			f = new File(fileName);
			if (f.exists() && !f.isDirectory()) {
				blockSizeS = JOptionPane.showInputDialog("Please enter block size: ", "");
				if (blockSizeS == null)
					return;
				blockSize = Integer.parseInt(blockSizeS);

				cipher Letter = new cipher(fileName, blockSize);
				Letter.createBlockedFile();
			} else
				JOptionPane.showMessageDialog(null, "Block File doesn't exist");
		}

		/**
		 *  Creates the public and private keys.
		 */
		private void createKey() {
			String stringPrime1 = null;
			String stringPrime2 = null;

			Object[] options = { "Generate", "Enter own" };
			
			int j = JOptionPane.showOptionDialog(null, "Generate prime numbers or enter your own?", null,
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (j == -1) // If X is clicked
				return;
			if (j == 1) //enter own
			{


				//keep re prompting until prime number is entered
				while (true)
				{
					
					stringPrime1 = JOptionPane.showInputDialog("Enter a prime number of more digits than your blocking size!", "");



					if(stringPrime1==null)//if canceled
						return;
					if ("".equals(stringPrime1))//if nothing entered
						continue;
					
					prime1 = new Huge_Unsigned_Int(stringPrime1.toCharArray(), stringPrime1.length());
					prime1.charToInt();//change form ascii values
					if (prime1==null || prime1.isPrime()) 
					{
						System.out.println("is prime");
						break;
					} 
					else 
					{
						JOptionPane.showMessageDialog(MyRSA.this, "Not Prime. Try again!");
					}

				}

				while (true) {
					stringPrime2 = JOptionPane.showInputDialog("Enter another prime"
							+ " number of equal or greater length!", "");



					if(stringPrime2==null)//if cancel
						return;
					if ("".equals(stringPrime2))//if nothing entered
						continue;
					
					prime2 = new Huge_Unsigned_Int(stringPrime2.toCharArray(), stringPrime2.length());
					prime2.charToInt();//change from ascii values

					if (prime2.isPrime()) {
						//System.out.println("is prime");
						break;
					} else {
						JOptionPane.showMessageDialog(MyRSA.this, "Not Prime. Try again!");

					}
				}
			}

			else if (j == 0) //user chose to generate prime numbers
			{

				int primesSize = makePrimeList();
				int random1 = (int) (Math.random() * primesSize);
				int random2 = (int) (Math.random() * primesSize);
				while (random2 == random1)//in case they are the same prime number
				{
					random2 = (int) (Math.random() * primesSize);
				}
				
				prime1 = primes.get(random1); //take in array list f primes and pick a random
				prime2 = primes.get(random2);

			}
			maxblockSize = Math.max(prime1.getSize(), prime2.getSize())-1;
			JOptionPane.showMessageDialog(MyRSA.this, "Blocking size should not exceed: " + maxblockSize);
			
			n = prime1.multiplyBy(prime2);
			privateKey = new Key(prime1, prime2, n);
			publicKey = new Key(prime1, prime2, n);
			privateKey.makePrivateFile();
			publicKey.makePublicFile();
		}

	}//end createKey

	/**
	 * creates a prime list from a file in src directory
	 * @return number of primes stored
	 */
	public int makePrimeList() {
		String primeNumberFile = "primeNumbers.rsc";

		String line = null;

		primes = new ArrayList<Huge_Unsigned_Int>();

		try {
			FileReader frPrimes = new FileReader(primeNumberFile);
			BufferedReader brPrimes = new BufferedReader(frPrimes);

			int i = 1; //counter for numbers

			while ((line = brPrimes.readLine()) != null) {
				// change to HUI and add to list
				Huge_Unsigned_Int prime = new Huge_Unsigned_Int(line.toCharArray(), line.length());
				prime.charToInt();//change form ascii values
				primes.add(prime);

				i++;
			}

			brPrimes.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "List of primes made of size: " + primes.size());
		for (Huge_Unsigned_Int item : primes) {
			//item.printInt();
		}
		return primes.size();

	}//end make primelist
	
	/**
	 * main starts up the gui
	 * @param args
	 */
	public static void main(String args[]) {
		MyRSA encrypt = new MyRSA();
		encrypt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
