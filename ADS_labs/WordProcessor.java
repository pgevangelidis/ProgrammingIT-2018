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
		String line = "";
		int counter = 0;
		
		for(E elem : inputSet)
		{
			line += "" + elem + ", ";
			if(counter==4)
			{
				line += "\n";
				counter = 0;
			}else
				counter ++;
		}
			
		return line;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//create a set of type String called wordSet
		Set<String> wordSet = new TreeSet<String>();
		//create a set of type CountedElement<String> called countedWordSet 
		Set<CountedElement<String>> countedWordSet = new TreeSet<CountedElement<String>>();
		
		String word = "";
		int  counter = 0;

		//for each input file (assume 3 arguments, each the name of a file)
		//  for each word w
		//     if wordset doesnt contain w:
		//        add w to wordset
	    //        add new element to countedWordSet
		//     else
		//        increment numeric part of element in countedWordSet containing w
		
		for (int i=0; i< args.length-0; i++)
			{
				try{
					FileReader reader = new FileReader(args[i]);
					Scanner readfile = new Scanner(reader);
				
					while(readfile.hasNext())
					{
						
						word = readfile.next();
						CountedElement<String> elem = new CountedElement<String>(word);
						
						if(! wordSet.contains(word))
						{							
							wordSet.add(word);				
							countedWordSet.add(elem);
					
						}							
						else
						{						
							counter = ((TreeSet<CountedElement<String>>) countedWordSet).ceiling(elem).getCount();
							
							CountedElement<String> incr = new CountedElement<String>(word,(counter+1));
							
							countedWordSet.remove(elem);
							countedWordSet.add(incr);						
						}
					}
					
					readfile.close();
					
				}catch(IOException e)
				{
					System.err.println("File not found.");
				}		
			}
				

	System.out.println(displaySet(countedWordSet));

	}
}
