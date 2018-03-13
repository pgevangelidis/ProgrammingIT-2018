package Assessment;


//import classes for file input - scanner etc.
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
//import implementing set (eg. TreeSet)




public class WordProcessor {
	private static <E> String displaySet(Set<E> inputSet){
		//implement this static method to create a
		// String representation of set - 5 comma separated elements per line
		// assume that type E has a toString method
		String line = "(";
		
		for(E elem : inputSet) 
			line += elem + ",";
		
		line+=")";

		return line;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//create a set of type String called wordSet
		Set<String> wordSet = new TreeSet<String>();
		//create a set of type CountedElement<String> called countedWordSet 
		CountedElement<String> countedWordSet = new TreeSet<String>();
		
		String[] files = {"file0.txt","file1.txt","file2.txt"};
		
		
		for (int i=0; i< 1; i++)
			{
				try{
					FileReader reader = new FileReader(files[i]);
					Scanner readfile = new Scanner(reader);
				
					while(readfile.hasNext())
					{
						CountedElement<String> countedWordSet = new CountedElement<String>(readfile.next());
												
						if(! wordSet.contains(countedWordSet.getElement()))
						{
							System.err.println(countedWordSet.toString());
							
							
							wordSet.add(countedWordSet);
							
						}							
						else
						{
							
						}
					}
					
					readfile.close();
					
				}catch(IOException e)
				{
					System.err.println("File not found.");
				}		
			}
		//for each input file (assume 3 arguments, each the name of a file)
		//  for each word w
		//     if wordset doesnt contain w:
		//        add w to wordset
	    //        add new element to countedWordSet
		//     else
		//        increment numeric part of element in countedWordSet containing w
		

	System.out.println(displaySet(wordSet));

	}
}
