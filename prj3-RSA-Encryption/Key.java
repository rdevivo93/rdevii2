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
 *         This class is what holds and handles the public and private keys
 *         Solves for e and d and creates the xml files
 *         
 *      
 * 
 *
 */

public class Key {
	private Huge_Unsigned_Int d;//private value 
	private Huge_Unsigned_Int e;//public value
	private Huge_Unsigned_Int p;//prime number
	private Huge_Unsigned_Int q;//second prime number
	private Huge_Unsigned_Int n;//(p-1)*(q-1)
	private Huge_Unsigned_Int phi; //q*p
	private Huge_Unsigned_Int one = new Huge_Unsigned_Int(1, "fromInt");//huge unsinged int equal to one
	private Huge_Unsigned_Int zero = new Huge_Unsigned_Int(0, "fromInt");//huge unsigned int equal to zero

	Key(Huge_Unsigned_Int p, Huge_Unsigned_Int q, Huge_Unsigned_Int n) {

		this.p = p;
		this.q = q;
		this.n = n;

		phi = (one.subtractFrom(p)).multiplyBy(one.subtractFrom(q));//used for finding e

		e = makeE(phi, n); //for public

		d = makeD(phi, e);//for public

	}

	/**
	 * creates the e value for encryption 
	 * currently uses the first value after 4
	 * @param phi
	 * @param n
	 * @return e
	 */
	public Huge_Unsigned_Int makeE(Huge_Unsigned_Int phi, Huge_Unsigned_Int n) {
		Huge_Unsigned_Int e = new Huge_Unsigned_Int(4, "fromint");//starts higher than three
		while (e.lessThan(n)) {
			if (e.isCoPrimeTo(phi)) // BOTTLE
				return e;
			e = e.addTo(one);
		}

		return null;

	}

	/**
	 * creates d value for decryption
	 * @param phi
	 * @param e
	 * @return d
	 */
	public Huge_Unsigned_Int makeD(Huge_Unsigned_Int phi, Huge_Unsigned_Int e) {
		Huge_Unsigned_Int k = zero;
		Huge_Unsigned_Int temp;
		while (true) {
			temp = (k.multiplyBy(phi)).addTo(one);
			if (temp.modFast(e).equalTo(zero))
				return temp.divideFast(e);
			k = k.addTo(one);
		}
	}

	/**
	 *  Generates the private file as prikey. See makePublicFile	for more indepth
	 */
	public void makePrivateFile() { // d

		List<String> lines = Arrays.asList("<rsakey>", "<dvalue>" + d.returnInt() + "</dvalue>", // See makePublicFile	
				"<nvalue>" + n.returnInt() + "</nvalue>", "</rsakey>");
		Path file = Paths.get("prikey");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *  Generates the public file as pubkey
	 */
	public void makePublicFile() { //e
		List<String> lines = Arrays.asList("<rsakey>", "<evalue>" + e.returnInt() + "</evalue>", // List of strings to put into file
				"<nvalue>" + n.returnInt() + "</nvalue>", "</rsakey>"); 						// Seperated by new line characters per comma
		Path file = Paths.get("pubkey"); // Gets the file path for the file
		try {
			Files.write(file, lines, Charset.forName("UTF-8")); // Writes to the file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
