import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;
	
	// I use this array for temporary calculations for finding the encrypted vectors
	private char [] tempArray;
	// letter frequencies object
	//private LetterFrequencies letterFreq;

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	private char monoChar;
	// Erase constructor
	public MonoCipher()
	{
		clearFields();
	}
	public void clearFields() {
		cipher = new char [SIZE];
		tempArray = new char [SIZE];
	}	
	// file constructor
	public MonoCipher(String keyword, String fileName, boolean actionB)
	{
		char encrypted, decrypted;
		//create alphabet
		alphabet = new char [SIZE];
		tempArray = new char [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
			tempArray[i] = alphabet[i];
		}
		StringBuilder keybuilder = new StringBuilder(keyword);		
		int j = keybuilder.length();
		cipher = new char [SIZE];
		int pace = j;
		for (int i=0; i < j; i++) {
			int k = keybuilder.charAt(i) - 'A';	
			cipher[i] = (char)keybuilder.charAt(i); 
			tempArray[k] = 0; 
		}
		for (int i=25; i > 0; i--) {
			if (tempArray[i]!=0) {
				cipher[pace] = tempArray[i];
				pace +=1;
			}
		}
		// create first part of cipher from keyword
		// create remainder of cipher from the remaining characters of the alphabet
		// print cipher array for testing and tutors
		String inFile = fileName+".txt"; 
		String outFile = this.fileProcessMono(fileName, actionB); 
		
		try{	
			FileReader reader = new FileReader(inFile);
			FileWriter writer = new FileWriter(outFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				StringBuilder line = new StringBuilder(in.nextLine());
				int b=0;
				char chr;
				while (b < line.length()) {
					if ((chr = line.charAt(b)) >= 'A' && (chr = line.charAt(b)) <= 'Z') 
					{
						if (actionB==false)
						{
							encrypted = this.encode(chr);
							writer.write(encrypted);
						}else {
							decrypted = this.decode(chr);
							writer.write(decrypted);
						}
					}else{
						writer.write(line.charAt(b));
					}
					b +=1;
				}
				writer.write("\r\n");
			}
				reader.close();
				writer.close();
				new LetterFrequencies(outFile);
			}
		catch (IOException e)
			{
				System.err.println("File not Found");
			}
	}
	private String fileProcessMono(String fn, boolean ab)
	{
		String fileName="";
		StringBuilder fBuilder = new StringBuilder(fn);
		fileName = fBuilder.substring(0, fBuilder.length()-1);
		boolean action = ab;
		if (action == true)
		{
			fileName +="D.txt";
		}else {
			fileName +="C.txt";
		}		
		return fileName;
		}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char chr)
	{
		int indexInt = chr - 'A';
		monoChar = cipher[indexInt];
		return monoChar;  
	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
	    for(int i=0; i<SIZE; i++)
	    {
	    	if (ch==cipher[i]) 
	    	{
	    		monoChar = alphabet[i];
	    	}
	    }	
		return monoChar; 
	}
}
