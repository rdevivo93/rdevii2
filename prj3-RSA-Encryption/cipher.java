//package SVU;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


/**
 * 
 * @author Ray DeVivo && Arnav Dahal CS342 Troy Spring 2016 RSAProject
 * 
 *    The cipher class will hold a string from a file and then convert all characters values in it into two digit numbers 

 *         
 *      
 * 
 *
 */

public class cipher {
	private String line; // Entire file
	private int size; // Size of entire file

	cipher(String filePath, int size) {

		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(filePath))); // Puts the file into the string
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		line = content;
		this.size = size;
	}

	/**
	 *  converts normal ascii to cipher ascii
	 * @param letter ascii character
	 * @return
	 */
	public String charToVal(char letter) {
		String returnString;
		int convertedValue = (int) letter; // Converts the ascii into int
		if (convertedValue >= 32 && convertedValue <= 126) { // If it is within our threshold of blockable asci
			convertedValue -= 27;
		} else {

			switch (convertedValue) {
			case 0:
				convertedValue = 0; // Null
				break;
			case 11:
				convertedValue = 1; // vertical tab (java doesnt support \v)
				break;
			case 9:
				convertedValue = 2; // horizontal tab
				break;
			case 10:
				convertedValue = 3; // new line
				break;
			case 13:
				convertedValue = 4; // return carriage
				break;
			default:
				break;

			}
		}
		if (convertedValue > 9) // If the value is under 9
			returnString = Integer.toString(convertedValue);
		else
			returnString = "0" + Integer.toString(convertedValue); // prepend a 0 to keep it formatted well

		return returnString;
	}//end chartoval

	
	
	/**
	 * 'Converts a cipher ascii to normal ascii see charToVal for indepth
	 * @param in ciphered ascii value
	 * @return
	 */
	public String valToChar(String in) {
		int convertedValue = Integer.parseInt(in);
		if (convertedValue > 4) {
			convertedValue += 27;
		} else {
			switch (convertedValue) {
			case 0:
				return "";
			case 1:
				convertedValue = 11;
				break;
			case 2:
				convertedValue = 9;
				break;
			case 3:
				convertedValue = 10;
				break;
			case 4:
				convertedValue = 13;
				break;
			default:
				break;

			}
		}
		return Character.toString((char) convertedValue);

	}//end valtochar

	
	
	/**
	 *  creates the blocked files
	 */
	public void createBlockedFile() {
		String superString = "";
		int startIndex; // index for position of CHARACTER in a string
		int endIndex;
		int strLength; // length of the string
		int subLength; // length of a substring
		int leftOver;// amount of left over (block size - actual data values) Essentially used to fill 00s
		String blockString;
		char cVal;

		startIndex = 0;
		endIndex = size;
		strLength = line.length();
		subLength = strLength / size;
		leftOver = strLength % size;

		for (int i = 0; i < subLength; i++) {
			blockString = line.substring(startIndex, endIndex); // Gets 1 line by block size
			blockString = new StringBuilder(blockString).reverse().toString(); // reverses it
			for (int j = 0; j < size; j++) {
				cVal = blockString.charAt(j); // gets values of chars
				superString = new StringBuilder(superString).append(charToVal(cVal)).toString();// appends the strings
				//System.out.print(charVal(cVal));
			}

			//System.out.println("\n" + blockString);
			superString += "\n";
			startIndex = endIndex;
			endIndex = endIndex + size;
		}

		if (leftOver != 0) { // Adds 00s to the block lines for formatting and unblocking
			for (int i = 0; i < size - leftOver; i++)
				superString = new StringBuilder(superString).append("00").toString();

			blockString = line.substring(startIndex, startIndex + leftOver);
			blockString = new StringBuilder(blockString).reverse().toString();
			for (int j = 0; j < leftOver; j++) {
				cVal = blockString.charAt(j);
				superString = new StringBuilder(superString).append(charToVal(cVal)).toString();
				//System.out.print(charVal(cVal));
			}

			//	System.out.println("\n" + blockString);
		}
		//		System.out.print(superString);

		List<String> lines = Arrays.asList(superString); // The entire thing to be printed
		Path file = Paths.get("rsaBlocked"); // file name
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end create blocked file
	
	

	/**
	 *  Checks if the block length entered for unblocking is the correct size by comparing it to the file
	 * @return
	 */
	boolean checkForBlock() {
		String lineTest = line.replace("\n", "").replace("\r", ""); // gets rid of all new lines and returnss
		int strLength = lineTest.length();
		if (strLength % (size * 2) == 0)
			return true;
		else
			return false;

	}

	/**
	 *  creates the unblocked file see createBlockedFile for indepth. Same thing except uses valToChar to do it backwards
	 */
	void createUnBlockedFile() {
		int startIndex = 0;
		int endIndex = size * 2;
		int currentIndex = 0;
		int strLength;
		String blockString;
		String superString = "";
		String tempString = "";
		line = line.replace("\n", "").replace("\r", ""); // gets rid of all new lines and returns
		strLength = line.length();
		for (int j = 0; j < (strLength / (size * 2)); j++) {
			for (int i = 0; i < size; i++) {
				blockString = line.substring(currentIndex, currentIndex + 2);
				superString = new StringBuilder(superString).insert(0, valToChar(blockString)).toString();
				currentIndex += 2;
			}
			startIndex = endIndex;
			endIndex = endIndex + (size * 2);
			tempString = new StringBuilder(tempString).append((superString)).toString();
			superString = "";
		}

		List<String> lines = Arrays.asList(tempString);
		Path file = Paths.get("rsaMessage.txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end create unblocked

}
