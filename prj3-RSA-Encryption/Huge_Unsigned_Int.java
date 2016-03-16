//package SVU;

/**
 * 
 * @author Ray DeVivo && Arnav Dahal CS342 Troy Spring 2016 RSAProject
 * 
 *         This class is for huge unsigned ints. Handles numbers that can not
 *         fit in normal java variable types. Also has relational and
 *         mathematical operations for them
 * 
 *
 */

public class Huge_Unsigned_Int {
	private int[] intArray;
	private int size;
	private Huge_Unsigned_Int base;


	/**
	 * 
	 * @param number
	 *            integer to change to Huge integer
	 * @param fromNumber
	 *            just a string to distinguish from other constructor. no use
	 */
	Huge_Unsigned_Int(int number, String fromNumber)//string just to distinguish between size and integer constructor
	{
		size = String.valueOf(number).length();
		intArray = new int[size];
		if (size == 1)//if single digit just add and return
		{
			intArray[0] = number;
		} else//divide and add each digit to array 
		{
			int val = (int) Math.pow(10, (size - 1));
			int i = 0;
			int temp;
			while (val > 0) {

				temp = (number / val);
				intArray[i] = temp;
				if (val == 10) //just add last digit
				{
					intArray[i + 1] = number % 10;
					break;
				}
				number = number - (val * temp);
				val = val / 10;
				i++;
			}

		}
	}//end from integer constructor

	/**
	 * constructor from char array
	 * 
	 * @param number
	 *            character array of numbers
	 * @param size
	 *            size of character array
	 */
	Huge_Unsigned_Int(char number[], int size) {
		intArray = new int[size];
		this.size = size;

		int j = size - 1; //last array index is size-1
		int numberSize = number.length;//length of character array(the huge int)
		int k = numberSize - 1;//array index size-1

		for (int i = 0; i < size; i++) //only using each character from number
		{
			intArray[i] = number[i];

		}

	}

	/**
	 * empty array of size size
	 * 
	 * @param size
	 *            length of number
	 */
	Huge_Unsigned_Int(int size) {
		intArray = new int[size];

		this.size = size;

	}

	/**
	 * 
	 * @return length of huge integer
	 */
	public int getSize() {
		return size;
	}

	/**
	 * change from ascii char value to regular int value
	 */
	public void charToInt() {
		for (int i = 0; i < size; i++)
			intArray[i] = intArray[i] - 48;

	}

	/**
	 * 
	 * @param from
	 *            smaller sized huge integer
	 * @param to
	 *            larger size huge integer
	 * 
	 *            pad with zeroes to line up digits from right to left for math
	 *            operations
	 */
	public void makeEqualLength(int from, int to) {
		int temp[] = new int[to];
		this.size = to;
		int _from = from - 1;
		int _to = to - 1;
		for (int i = 0; i < from; i++) {
			temp[_to] = intArray[_from];
			_to--;
			_from--;

		}
		for (int i = 0; i < (to - from); i++) {
			temp[i] = 0;
		}
		intArray = temp;
	}

	/**
	 * 
	 * @param spots
	 *            how many spots to add to huge integer size
	 * 
	 *            add extra decimal places to array of ints
	 */
	public void grow(int spots)//grow array by spots on the left
	{
		int oldSize = size;
		size = size + spots;
		int temp[] = new int[size];
		for (int i = oldSize - 1; i >= 0; i--) {
			temp[i + 1] = intArray[i];
		}
		temp[0] = 0;

		intArray = temp;
		//System.out.println("Grew to" + this.size);

	}

	/**
	 * 
	 * @param x
	 *            number to addto
	 * @return this+x
	 * 
	 *         add two huge integers together
	 */
	public Huge_Unsigned_Int addTo(Huge_Unsigned_Int x) {
		int newSize;
		if (this.size < x.size) {
			newSize = x.size;
			//System.out.println("Before make equal"); this.printInt();
			this.makeEqualLength(this.size, newSize);//when adding, there will never be more digits than largest of the ints size+1
		} else if (this.size > x.size) {
			newSize = this.size;
			x.makeEqualLength(x.size, newSize);
		} else
			newSize = this.size; //same size

		Huge_Unsigned_Int newInt = new Huge_Unsigned_Int(newSize);
		//System.out.println(""+newSize);

		int carry = 0;
		int temp = 0;
		for (int i = newSize - 1; i >= 0; i--) {
			//System.out.print("add");
			//this.printInt();x.printInt();
			temp = this.intArray[i] + x.intArray[i] + carry;
			carry = temp / 10;
			newInt.intArray[i] = temp % 10;
		}
		if (carry > 0) //extra digit needed
		{
			newInt.grow(1); //add one array place
			newInt.intArray[0] = carry;
		}
		x.removeLeadingZeroes();
		this.removeLeadingZeroes();
		newInt.removeLeadingZeroes();
		return newInt;
	}//end addTo

	/**
	 * remove any leading zeroes at the front of a huge int
	 */
	public void removeLeadingZeroes() {
		int i = 0;
		while (intArray[i] == 0) {
			if (i == size - 1) //if at the end if the array
				break;
			i++;
		}
		//index i will not be zero
		int newSize = size - i;
		int[] temp = new int[newSize];
		for (int j = 0; j < newSize; j++) {
			temp[j] = intArray[i];
			i++;
		}
		this.intArray = new int[newSize];
		this.size = newSize;
		this.intArray = temp;
	}

	/**
	 * 
	 * @param x the larger number to subtract this from
	 *            
	 * @return x-this
	 */
	public Huge_Unsigned_Int subtractFrom(Huge_Unsigned_Int x) {
		//x.printInt();this.printInt();

		if (this.size > x.size) {
			//	System.out.println("This wile be negative..should not happen");
			return null;
		}

		int[] temp = new int[x.size];
		for (int i = 0; i < x.size; i++) //copy x so it doesnt change
		{
			temp[i] = x.intArray[i];
		}

		int newSize;

		if (this.size < x.size) {
			newSize = x.size;
			this.makeEqualLength(this.size, newSize);
		} else
			newSize = this.size; //same size

		int[] total = new int[newSize];
		//x.printInt();this.printInt();
		for (int i = newSize - 1; i >= 0; i--) {
			if (temp[i] < this.intArray[i] && i != 0) {
				//System.out.println("" + temp[i] + this.intArray[i] + "Size: " + this.size + x.size);
				temp[i - 1] = (temp[i - 1]) - 1;
				temp[i] = temp[i] + 10;
			}
			total[i] = temp[i] - this.intArray[i];
		}

		Huge_Unsigned_Int ret = new Huge_Unsigned_Int(newSize);
		ret.intArray = total;
		this.removeLeadingZeroes();
		ret.removeLeadingZeroes();
		
		if (ret.intArray[0] < 0)
			return null;
		return ret;

	}//end subtract

	/**
	 * check is this is less than x
	 * @param x
	 * @return
	 */
	public boolean lessThan(Huge_Unsigned_Int x) {
		if (this.size < x.size) {
			return true;
		} else if (this.size > x.size) {
			return false;
		}

		for (int i = 0; i < size; i++) {
			if (this.intArray[i] < x.intArray[i])
				return true;
		}
		return false;
	}

	/**
	 * check if this is greater than or equal to x
	 * @param x
	 * @return
	 */
	public boolean greaterThan(Huge_Unsigned_Int x) {
		if (this.size < x.size) {
			return false;
		} else if (this.size > x.size) {
			return true;
		}

		for (int i = 0; i < size; i++) {
			if (this.intArray[i] > x.intArray[i])
				return true;
		}
		return false;
	}

	/**
	 * check if this is less than or equal to x
	 * @param x
	 * @return
	 */
	public boolean lessThanEqualto(Huge_Unsigned_Int x) {
		if (this.size < x.size) {
			return true;
		} else if (this.size > x.size) {
			return false;
		}

		for (int i = 0; i < size; i++) {
			if (this.intArray[i] < x.intArray[i])
				return true;
			if (this.intArray[i] > x.intArray[i])
				return false;
		}

		return true;
	}

	/**
	 * check if this is greater than or equal to x
	 * @param x
	 * @return
	 */
	public boolean greaterThanEqualto(Huge_Unsigned_Int x) {
		if (this.size < x.size)
			return false;
		else if (this.size > x.size)
			return true;

		int tempSize = x.size;

		for (int i = 0; i < tempSize; i++) {
			if (this.intArray[i] < x.intArray[i])
				return false;
			if (this.intArray[i] > x.intArray[i])
				return true;
		}

		return true;
	}

	/**
	 * check if this is equal to x
	 * @param x
	 * @return true or false
	 */
	public boolean equalTo(Huge_Unsigned_Int x) {
		if (this.size != x.size)
			return false;

		int i;
		for (i = 0; i < size; i++) {
			if (this.intArray[i] != x.intArray[i])
				return false;
		}
		return true;
	}

	/**
	 * wrapper for multiply
	 * @param x
	 * @return
	 */
	public Huge_Unsigned_Int multiplyBy(Huge_Unsigned_Int x) {
		return multiply(x, this);
	}

	/**
	 * multiplies x by y
	 * @param x
	 * @param y
	 * @return x*y
	 */
	public static Huge_Unsigned_Int multiply(Huge_Unsigned_Int x, Huge_Unsigned_Int y) {
		//System.out.println(""+x.size);
		//System.out.println(""+y.size);
		//x.printInt();
		//y.printInt();

		//make equal sizes by adding leading zeroes
		int newSize;
		if (y.size < x.size) {
			newSize = x.size;
			//System.out.println("Before make equal"); this.printInt();
			y.makeEqualLength(y.size, newSize);//when adding, there will never be more digits than largest of the ints size+1
		} else if (y.size > x.size) {
			newSize = y.size;
			x.makeEqualLength(x.size, newSize);
		} else
			newSize = y.size; //same size

		int productSize = newSize * 2;
		Huge_Unsigned_Int product = new Huge_Unsigned_Int(productSize);

		int i, j;
		//initialize to zero
		for (i = 0; i < productSize; i++)
			product.intArray[i] = 0;

		//multiply each digit of x by each digit of y
		for (i = newSize - 1; i >= 0; i--) {
			for (j = newSize - 1; j >= 0; j--) {
				product.intArray[i + j + 1] += x.intArray[i] * y.intArray[j];
			}
		}

		//check for carry. if any index is greater than or equal to ten, then need to carry over
		for (i = 2 * newSize - 1; i >= 0; i--) {
			if (product.intArray[i] >= 10) {
				product.intArray[i - 1] += product.intArray[i] / 10;
				product.intArray[i] %= 10;
			}
		}
		x.removeLeadingZeroes();
		y.removeLeadingZeroes();
		product.removeLeadingZeroes();
		return product;

	}//end multiply

	/**
	 *  Prints the Huge_Unsigned_Int int
	 */
	public void printInt() {
		for (int i = 0; i < size; i++) {
			System.out.print(intArray[i]);
		}
		System.out.println();
	}
	
	

	/**
	 *  Returns the int as a string
	 * @return
	 */
	public String returnInt() {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < size; i++) {
			stringBuilder.append(intArray[i]);
		}

		String finalString = stringBuilder.toString();

		return finalString;

	}

	/**
	 * checks to see if a number is prime
	 * @return true or false if it is or not
	 */
	public boolean isPrime() {
		int last = size - 1;
		//0 1 2 3 5 7 are all prime
		if (size == 1) {
			if (this.intArray[last] == 0)
				return true;
			if (this.intArray[last] == 1)
				return true;
			if (this.intArray[last] == 2)
				return true;
			if (this.intArray[last] == 3)
				return true;
			if (this.intArray[last] == 5)
				return true;
			if (this.intArray[last] == 7)
				return true;
		} 
		else//if even or ends in 5 not prime
		{
			if (this.intArray[last] % 2 == 0)
				return false;
			if (this.intArray[last] == 5)
				return false;
		}
		Huge_Unsigned_Int two = new Huge_Unsigned_Int(2, "fromInt");
		Huge_Unsigned_Int zero = new Huge_Unsigned_Int(0, "fromInt");
		for (Huge_Unsigned_Int i = new Huge_Unsigned_Int(3, "fromInt"); (i.multiplyBy(i)).lessThan(this); i = i.addTo(two)) {
			if ((this.modFast(i)).equalTo(zero))
				return false;
		}
		return true;

	}//end isPrime

	/**
	 * wrapper for coprime function
	 * @param x
	 * @return
	 */
	public boolean isCoPrimeTo(Huge_Unsigned_Int x) {
		return isCoPrime(this, x);
	}

	/**
	 * function to see if y is coprime to x
	 * @param x
	 * @param y
	 * @return true or false
	 */
	public static boolean isCoPrime(Huge_Unsigned_Int x, Huge_Unsigned_Int y) {
		Huge_Unsigned_Int temp;
		Huge_Unsigned_Int x2 = x;
		Huge_Unsigned_Int y2 = y;
		Huge_Unsigned_Int zero = new Huge_Unsigned_Int(0, "fromInt");
		Huge_Unsigned_Int one = new Huge_Unsigned_Int(1, "fromint");
		//if(x.mod(y).equalTo(zero))
		//return y;
		//return isCoPrimeTo(y, x.mod(y));
		while (!y2.equalTo(zero)) {
			temp = x2.modFast(y2); // GIANT BOTTLE NECK
			x2 = y2;
			y2 = temp;

		}
		//return y;
		if (x2.equalTo(one))
			return true;
		else
			return false;

	}

	/**
	 *  First encryption method 
	 * @param e exponent
	 * @param n what is modded by
	 * @return C
	 */
	public Huge_Unsigned_Int ACrypt(Huge_Unsigned_Int e, Huge_Unsigned_Int n) {
		Huge_Unsigned_Int M = this;
		Huge_Unsigned_Int zero = new Huge_Unsigned_Int(0, ""); // The int 0
		Huge_Unsigned_Int one = new Huge_Unsigned_Int(1, ""); // The int 1
		Huge_Unsigned_Int two = new Huge_Unsigned_Int(2, ""); // The int 2
		Huge_Unsigned_Int C = one;
		Huge_Unsigned_Int i;

		for (i = zero; i.lessThan(e); i = i.addTo(one)) {
			C = (C.multiplyBy(M));
			C = C.modFast(n);
		}
		return C;
	}//end first encryption
	
	

	/**
	 *  Second encryption method not used
	 * @param exp
	 * @param mod
	 * @return C
	 */
	Huge_Unsigned_Int bCrypt(Huge_Unsigned_Int exp, Huge_Unsigned_Int mod) {
		return expmod(this, exp, mod);
	}

	public Huge_Unsigned_Int expmod(Huge_Unsigned_Int base, Huge_Unsigned_Int exp, Huge_Unsigned_Int mod) {
		Huge_Unsigned_Int zero = new Huge_Unsigned_Int(0, "");
		Huge_Unsigned_Int one = new Huge_Unsigned_Int(1, "");
		Huge_Unsigned_Int two = new Huge_Unsigned_Int(2, "");

		if (exp.equalTo(zero))
			return one;
		if (exp.modFast(two).equalTo(zero)) {
			return ((expmod(base, (exp.divideFast(two)), mod)).multiplyBy((expmod(base, (exp.divideFast(two)), mod))))
					.modFast(mod);
		} else {
			return (base.multiplyBy(expmod(base, one.subtractFrom(exp), mod))).modFast(mod);
		}
	}//end second encryption
	/**
	 * end second unused encryptor
	 */
	
	

	/**
	 * Divides the Huge_Unsigned_Int by another Huge_Unsigned_Int
	 * @param small divisor
	 * @return quotient
	 */
	public Huge_Unsigned_Int divideBy(Huge_Unsigned_Int small) {
		Huge_Unsigned_Int temp = this;
		Huge_Unsigned_Int counter = new Huge_Unsigned_Int(0, "");
		Huge_Unsigned_Int one = new Huge_Unsigned_Int(1, "");

		while (small.subtractFrom(temp) != null) {
			temp = small.subtractFrom(temp);
			counter = counter.addTo(one);
		}

		return counter;

	}
	
	
/**
 * grows array and adds digit to the end
 * @param x number to add to end of this int
 * @return new number
 */
	public Huge_Unsigned_Int addToEnd(int x) {
		int i;
		Huge_Unsigned_Int ret = new Huge_Unsigned_Int(this.size + 1);
		for (i = 0; i < this.size; i++)
			ret.intArray[i] = this.intArray[i];
		ret.intArray[i] = x;
		return ret;
	}
	
	
	/**
	 * faster division for larger numbers
	 * 
	 * @param divisor
	 * @param dividend
	 * @return quotient
	 */
	public static Huge_Unsigned_Int divideNew(Huge_Unsigned_Int divisor, Huge_Unsigned_Int dividend) {
		Huge_Unsigned_Int quotient = new Huge_Unsigned_Int(dividend.size);
		//Huge_Unsigned_Int temp = new Huge_Unsigned_Int(dividend.size);
		Huge_Unsigned_Int temp2;
		Huge_Unsigned_Int temp = new Huge_Unsigned_Int(dividend.intArray[0], "fromtint");//.intArray[0] = dividend.intArray[0];
		for (int i = 0; i < dividend.size - 1; i++) {
			temp2 = temp.divideBy(divisor);
			//temp2.printInt();
			quotient.intArray[i] = temp2.intArray[0];
			temp2 = temp2.multiplyBy(divisor);
			//temp.printInt();
			temp2 = temp2.subtractFrom(temp);
			temp2 = temp2.addToEnd(dividend.intArray[i + 1]);//temp2.intArray[i+1] = dividend.intArray[i+1];
			//temp2.printInt();
			temp = temp.copy(temp2);// = temp2;

		}
		temp2 = temp; //remainder

		temp = temp.divideBy(divisor);
		quotient.intArray[quotient.size - 1] = temp.intArray[0];
		quotient.removeLeadingZeroes();

		return quotient;

	}//end dividenew
	
	/**
	 * wrapper for fast division method
	 * @param divisor
	 * @return
	 */
	Huge_Unsigned_Int divideFast(Huge_Unsigned_Int divisor) {
		return divideNew(divisor, this);
	}
	
	/**
	 * faster implementation of mod
	 * @param divisor
	 * @param dividend
	 * @return remainder
	 */
	public static Huge_Unsigned_Int modNew(Huge_Unsigned_Int divisor, Huge_Unsigned_Int dividend) {
		Huge_Unsigned_Int quotient = new Huge_Unsigned_Int(dividend.size);
		//Huge_Unsigned_Int temp = new Huge_Unsigned_Int(dividend.size);
		Huge_Unsigned_Int temp2;
		Huge_Unsigned_Int temp = new Huge_Unsigned_Int(dividend.intArray[0], "fromtint");//.intArray[0] = dividend.intArray[0];
		for (int i = 0; i < dividend.size - 1; i++) {
			temp2 = temp.divideBy(divisor);
			//temp2.printInt();
			quotient.intArray[i] = temp2.intArray[0];
			temp2 = temp2.multiplyBy(divisor);
			//temp.printInt();
			temp2 = temp2.subtractFrom(temp);
			temp2 = temp2.addToEnd(dividend.intArray[i + 1]);//temp2.intArray[i+1] = dividend.intArray[i+1];
			//temp2.printInt();
			temp = temp.copy(temp2);// = temp2;

		}
		temp2 = temp;

		temp = temp.divideBy(divisor);
		//quotient.intArray[quotient.size-1] = temp.intArray[0];
		//quotient.removeLeadingZeroes();
		temp = temp.multiplyBy(divisor);
		temp2 = temp.subtractFrom(temp2);

		return temp2;

	}//end modnew
	
	/**
	 * wrapper for faster mod
	 * @param divisor
	 * @return
	 */
	public Huge_Unsigned_Int modFast(Huge_Unsigned_Int divisor) {
		return modNew(divisor, this);
	}
	
	
	/**
	 * will make a copy of from
	 * @param from huge integer to copy
	 * @return copied huge integer
	 */
	public Huge_Unsigned_Int copy(Huge_Unsigned_Int from) {
		this.size = from.size;
		int[] temp = new int[from.size];
		int i = 0;
		for (i = 0; i < from.size; i++)
			temp[i] = from.intArray[i];

		this.intArray = temp;
		return this;

	}

	/**
	 * slower version of mod
	 * @param small divisor
	 * @return
	 */
	public Huge_Unsigned_Int modHSU(Huge_Unsigned_Int small) {
		Huge_Unsigned_Int temp = this;
		while (small.subtractFrom(temp) != null) {
			temp = small.subtractFrom(temp);
		}
		return temp;
	}//end mod
	
	/* main for testing
	
	public static void main(String[] args) {
		char num1[] = { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		char num2[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 5 };
		char num3[] = { 8, 8 };
		char num4[] = { 9, 2, 3, 4, 5 };
		char num5[] = { 9 };
		char num6[] = { 3 };

		// Huge_Unsigned_Int first parameter is an array, second parameter is size of array
		Huge_Unsigned_Int test1 = new Huge_Unsigned_Int(num1, 10);
		Huge_Unsigned_Int test2 = new Huge_Unsigned_Int(num2, 10);
		Huge_Unsigned_Int test3 = new Huge_Unsigned_Int(num3, 2);
		Huge_Unsigned_Int test4 = new Huge_Unsigned_Int(num4, 5);
		Huge_Unsigned_Int test5 = new Huge_Unsigned_Int(num5, 1);
		Huge_Unsigned_Int test6 = new Huge_Unsigned_Int(num6, 1);

		Huge_Unsigned_Int test7 = new Huge_Unsigned_Int(75, "fromInt");
		Huge_Unsigned_Int test8 = new Huge_Unsigned_Int(8, "fromInt");
		Huge_Unsigned_Int test9 = new Huge_Unsigned_Int(9, "edfkj");
		//small, big
		Huge_Unsigned_Int ZAB = test9.modFast(test8);
		ZAB.printInt();

		char prikey[] = { 1, 4, 1 };
		char pubkey[] = { 5 };
		char nKey[] = { 3, 9, 1 };
		char enCode[] = { 2, 8 };

		int prikeySize = prikey.length;
		int pubkeySize = pubkey.length;
		int nKeySize = nKey.length;
		int enCodeSize = enCode.length;
		/*	
			System.out.println(prikeySize);
			System.out.println(pubkeySize);
			System.out.println(nKeySize);
		

		Huge_Unsigned_Int prikeyH = new Huge_Unsigned_Int(prikey, prikeySize);
		Huge_Unsigned_Int pubkeyH = new Huge_Unsigned_Int(pubkey, pubkeySize);
		Huge_Unsigned_Int nKeyH = new Huge_Unsigned_Int(nKey, nKeySize);
		Huge_Unsigned_Int enCodeH = new Huge_Unsigned_Int(enCode, enCodeSize);
	}*/

}