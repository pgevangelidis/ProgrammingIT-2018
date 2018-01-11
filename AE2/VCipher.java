import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   //the letters of the alphabet
	private final int SIZE = 26;
	private char VigeChar;
        // more instance variables
	/** The cipher array. */
	private char [][] cipher;
	//erase constructor
	public VCipher()
	{
		clearFields();
	}
	public void clearFields() {
		cipher = new char [1][SIZE];
	}
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword, String fileName, boolean actionB)
	{
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++) 
		{
			alphabet[i] = (char)('A' + i);
		}
		StringBuilder keybuilder = new StringBuilder(keyword);		
		int k = (int)keybuilder.length();
		cipher = new char [k][SIZE];
		for (int i=0;i<k;i++)
		{
			int j=0;
			cipher[i][j] = keybuilder.charAt(i);
			int position = Character.getNumericValue(keybuilder.charAt(i))-9;
			int t =SIZE - position;
			for (j=1;j<=t;j++)
			{
				cipher[i][j] = alphabet[position];
				position ++;
			}
			position=0;
			for(j=t+1;j<SIZE;j++)
			{
				cipher[i][j] = alphabet[position];
				position ++;
			}
		}
		String inFile = fileName+".txt";
		String outFile = this.fileProcessVigenere(fileName, actionB);
		try{	
			FileReader reader = new FileReader(inFile);
			FileWriter writer = new FileWriter(outFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				StringBuilder line = new StringBuilder(in.nextLine());
				//System.err.println("\n"+line+"\nCIBE JMGRI XNQJICBVKW");
				int b=0;
				char chr;
				int p=0;
				while (b < line.length()) {
					if (p==k)
					{
						p=0;
					}
					if ((chr = line.charAt(b)) >= 'A' && (chr = line.charAt(b)) <= 'Z') 
					{
						if (actionB==false)
						{
							char temp = this.encode(chr,p);
							writer.write(temp);
						}else {
							writer.write(this.decode(chr,p));
						}
						p +=1;
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
	private String fileProcessVigenere(String fn, boolean ab)
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
	public char encode(char chr,int i)
	{
		int indexInt = Character.getNumericValue(chr) - 10; // 'A' = 65
		VigeChar = cipher[i][indexInt];
		return VigeChar; 
	}	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch, int i)
	{
		for(int j=0; j<SIZE; j++)
	    {
	    	if (ch==cipher[i][j]) 
	    	{
	    		VigeChar = alphabet[j];
	    	}
	    }	
		return VigeChar;  
	}
}
