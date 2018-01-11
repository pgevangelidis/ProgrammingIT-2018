import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class TestClass {

	public static void main(String [] args) 
	{
		char [] alphabet;
		char [] cipher;
		char [] tempArray;
		int SIZE = 26;
		String keyword = "LEOPARD";
		
		alphabet = new char [SIZE];
		tempArray = new char [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
			tempArray[i] = alphabet[i];
		}
		StringBuilder sbuilder = new StringBuilder(keyword);		
		int j = sbuilder.length();
		cipher = new char [SIZE];
		int pace = j;
		for (int i=0; i <= 6; i++) {
			int k = sbuilder.charAt(i) - 'A';	
			cipher[i] = (char)sbuilder.charAt(i); 
			tempArray[k] = 0; 
		}
		for (int i=25; i > 0; i--) {
			if (tempArray[i]!=0) {
				cipher[pace] = tempArray[i];
				pace +=1;
			}
		}
		System.err.println(cipher);
		String inFile = "messageP.txt"; // I have to insert the object from GUI that reads the message
		String outFile = "messageC.txt"; // I have to insert the object from GUI that reads the message
		try{	
			FileReader reader = new FileReader(inFile);
			FileWriter writer = new FileWriter(outFile);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				StringBuilder line = new StringBuilder(in.nextLine());
				System.out.println(line);
				int b=0;
				char chr;
				while (b < line.length()) {
					if ((chr = line.charAt(b)) >= 'A' && (chr = line.charAt(b)) <= 'Z') 
					{
						int indexInt = chr - 'A';
						writer.write(cipher[indexInt]);
					}else 
					{
						writer.write(line.charAt(b));
					}
					b +=1;
				}
				writer.write("\r\n");
			}
				reader.close();
				writer.close();
			}
		catch (IOException e)
			{
				System.err.println("File not Found");
			}
	}
}
