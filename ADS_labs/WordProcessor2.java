import java.io.FileReader;
import java.util.Set;
import java.util.TreeSet;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;


public class WordProcessor2 {
	private static <E> String displaySet(BSTBag<CountedElement<String>> inputSet){
		//implement this static method to create a
		// String representation of set - 5 comma separated elements per line
		// assume that type E has a toString method
		String line = "";
		String pair = "";
		int counter = 0;
		
		for(CountedElement<String> elem : inputSet)
		{
			pair = elem.toString();
			line += "" + pair + ", ";
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
		BSTBag<CountedElement<String>> countedWordSet = new BSTBag<CountedElement<String>>();
		BSTBag<CountedElement<String>> countedWordSet2 = new BSTBag<CountedElement<String>>();
		
		
		System.out.println("is empty: "+countedWordSet.isEmpty());
		String word = "";

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
						CountedElement<String> elem = new CountedElement<String>(word,1);
						
						if(! wordSet.contains(word))
						{							
							wordSet.add(word);				
							countedWordSet.add(elem);
							countedWordSet2.add(elem);
					
						}							
						else
						{						
							//Iterator<CountedElement<String>> iter = countedWordSet.iterator();
							countedWordSet.add(elem);
							countedWordSet2.add(elem);
							/*while(iter.hasNext())
							{
								CountedElement<String> element = iter.next();
								
								if(element.compareTo(elem)==0)
								{
									element.setCount(element.getCount()+1);
								}
							}*/												
						}
						
					}
					
					readfile.close();
					
					
				}catch(IOException e)
				{
					System.err.println("File not found.");
				}		
			}
		System.out.println("the size is: "+countedWordSet.size());		
		CountedElement<String> elem = new CountedElement<String>("table",1);
		countedWordSet.remove(elem);
		System.out.println("Is countedWordSet equals to CountedWordSet2: "+countedWordSet.equals(countedWordSet2));
		System.out.println("Does the set contains element (table,1): "+countedWordSet.contains(elem));
		System.out.println("the size is: "+countedWordSet.size());
		System.out.println("is empty: "+countedWordSet.isEmpty());
		System.out.println(displaySet(countedWordSet));
	}
}
