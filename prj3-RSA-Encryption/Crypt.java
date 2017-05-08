//package SVU;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Crypt class used to crypt
public class Crypt {
	String line; // Entire file
	String parsedLine; // Parts of the file
	Huge_Unsigned_Int edVal; // the E or D values from public/private keys
	Huge_Unsigned_Int nVal; // the nVal from it
	int ArrayOfChar[] = null; // Put into here to turn into char array
	char charArrayConverted[] = null; // Used to create Huge_Unsigned_Int

	Crypt(String filePath) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		line = content;

		parseFile();
	}

	// Parses the file for E,D, and N values
	public void parseFile()

	{
		Pattern pattern = Pattern.compile("<evalue>(.*?)</evalue>"); //Looks for E value pattern
		Matcher matcher = pattern.matcher(line); // Finds it
		while (matcher.find()) {
			parsedLine = matcher.group(1); // Puts it into a string

			ArrayOfChar = new int[parsedLine.length()]; // turns it into an array of ascii ints
			charArrayConverted = new char[parsedLine.length()]; // turns those ascii ints into chars

			for (int i = 0; i < parsedLine.length(); i++) {
				ArrayOfChar[i] = Character.getNumericValue(parsedLine.charAt(i));
				charArrayConverted[i] = (char) ArrayOfChar[i];
			}

			edVal = new Huge_Unsigned_Int(charArrayConverted, charArrayConverted.length); //Creats the Huge_Unsigned_Int
		}

		// Next two chunks same as before

		pattern = Pattern.compile("<dvalue>(.*?)</dvalue>");
		matcher = pattern.matcher(line);
		while (matcher.find()) {
			parsedLine = matcher.group(1);

			ArrayOfChar = new int[parsedLine.length()];
			charArrayConverted = new char[parsedLine.length()];

			for (int i = 0; i < parsedLine.length(); i++) {
				ArrayOfChar[i] = Character.getNumericValue(parsedLine.charAt(i));
				charArrayConverted[i] = (char) ArrayOfChar[i];
			}

			edVal = new Huge_Unsigned_Int(charArrayConverted, charArrayConverted.length);
		}
		// Same as before
		pattern = Pattern.compile("<nvalue>(.*?)</nvalue>");
		matcher = pattern.matcher(line);
		while (matcher.find()) {
			parsedLine = matcher.group(1);

			ArrayOfChar = new int[parsedLine.length()];
			charArrayConverted = new char[parsedLine.length()];

			for (int i = 0; i < parsedLine.length(); i++) {
				ArrayOfChar[i] = Character.getNumericValue(parsedLine.charAt(i));
				charArrayConverted[i] = (char) ArrayOfChar[i];
			}

			nVal = new Huge_Unsigned_Int(charArrayConverted, charArrayConverted.length);
		}

	}

	// Changes the blockfile by crypting it
	void changeBlockFile(String blockFile) {
		String blockLine = "";// Entire file
		String lines[]; // All the lines
		String tempLine = null;
		Huge_Unsigned_Int bLine;// Non crypted
		Huge_Unsigned_Int cLine;// Crypted
		String holder;
		try {
			blockLine = new String(Files.readAllBytes(Paths.get(blockFile)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lines = blockLine.split("\\r?\\n"); // removes new lines
		PrintWriter fileWriter = null; // opens file writer
		try {
			fileWriter = new PrintWriter("encrypted", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < lines.length; i++) { // iterates through entire string of file
			tempLine = lines[i].replace("\n", "").replace("\r", "");// removes new lines and return carriages
			ArrayOfChar = new int[tempLine.length()]; // turns it into an ascii int array
			charArrayConverted = new char[tempLine.length()]; // converts it into a char array

			for (int k = 0; k < tempLine.length(); k++) {
				ArrayOfChar[k] = Character.getNumericValue(tempLine.charAt(k));
				charArrayConverted[k] = (char) ArrayOfChar[k];

			}
			bLine = new Huge_Unsigned_Int(charArrayConverted, charArrayConverted.length); //one line of non encrypted block values

			cLine = bLine.ACrypt(edVal, nVal); // Encrypted the block value
			holder = cLine.returnInt();
			if (holder.length() % 2 != 0) // Padding 0s
				holder = "0" + holder;

			System.out.println(holder);
			fileWriter.println(holder);

		}
		fileWriter.close();
		System.out.println("DONE");

	}
}
