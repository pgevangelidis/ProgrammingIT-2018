import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;

	/** Count for each letter */
	private int [] alphaCounts;
	private double [] alphaFrequency;
	private double [] difference;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private double maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies(String fileName)
	{
		alphabet = new char [SIZE];
		alphaCounts = new int[SIZE];
		alphaFrequency = new double[SIZE];
		difference = new double [SIZE];
		for (int i = 0; i < SIZE; i++) 
		{
			alphabet[i] = (char)('A' + i);
			alphaCounts[i] = 0;
			alphaFrequency[i]=0;
			difference[i]=0;
		}
		String reportLine="";		
		try {
			FileReader reader = new FileReader(fileName);
			String outReport = this.fileProcessReport(fileName);
			PrintWriter report = new PrintWriter(outReport);
			//LetterFrequencies calculus = new LetterFrequencies(fileName);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()) {
				StringBuilder line = new StringBuilder(in.nextLine());
				int b=0;
				char chr;
				while (b < line.length()) {
					if ((chr = line.charAt(b)) >= 'A' && (chr = line.charAt(b)) <= 'Z') 
					{
						this.addChar(chr);
					}
					b +=1;
				}
			}	
			this.getFrequencies();
			this.counterArray();
			this.frequency();
			this.differ();
			report.println("LETTER ANALYSIS");
			report.println("Letter  Freq  Freq%  AvgFreq%  Diff");
			for (int i=0;i<SIZE;i++)
			{
				reportLine = String.format("%3c%7d%8.1f%9.1f%8.1f", alphabet[i], alphaCounts[i], alphaFrequency[i], avgCounts[i], difference[i]);
				report.println(reportLine);
			}
			int indexMaxPosition = (int)this.getMaxPC();
			report.println(this.getReport(indexMaxPosition));
			report.close();
		}catch (IOException e){
			System.err.println("File not Found");
		}
		
	}	
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public int[] addChar(char ch)
	{
		int indexInt = (int)ch - 65; // 'A' = 65
	    alphaCounts[indexInt] +=1;
	    totChars += 1;
		return alphaCounts;
	}
	// getters
	public int[] counterArray() 
	{
		return alphaCounts;
	}
	public double[] frequency()
	{
		return alphaFrequency;
	}
	public double[] differ()
	{
		return difference;
	}
	//calculate frequencies
	public void getFrequencies()
	{
		for(int i=0;i<SIZE;i++)
		{
			alphaFrequency[i] = Math.round(alphaCounts[i]*1000.0/totChars)/10.0;
			difference[i] = Math.round((avgCounts[i] - alphaFrequency[i]));
		}
	}
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	public double getMaxPC()
    {
	    double indexPosition=0;
	    maxCh = alphaFrequency[0];
	    for (int i=1;i < SIZE;i++)
	    {
	    	if (alphaFrequency[i]>maxCh)
	    	{
	    		maxCh = alphaFrequency[i];
	    		indexPosition = i;
	    	}
	    }
		return indexPosition;
	}
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport(int i)
	{ 
	    String stringline= String.format("\nThe most frequent letter is %s at %2.1f%%",alphabet[i],alphaFrequency[i]);
		return stringline;  
	}
	public String fileProcessReport(String fn)
	{
		String fileName="";
		StringBuilder fBuilder = new StringBuilder(fn);
		fileName = fBuilder.substring(0, fBuilder.length()-5);
		fileName +="F.txt";		
		return fileName;
	}
}
