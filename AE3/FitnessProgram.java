
import java.util.*;

import javax.swing.JOptionPane;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {
	//define an array of object
	public FitnessClass [] fitClasses;
	private FitnessClass temp;
	private int numClasses;
	//constant number of classes 7
	final int totalClasses = 7;
	final int weekAt = 5; // this is the number of columns of the attendance matrix
    // default constructor for the list object
	public FitnessProgram()
	{
		fitClasses = new FitnessClass[totalClasses];
		numClasses = 0;
	}
	
	// this method will return the list of fitness class objects
	public FitnessClass [] getfitClasses()
	{
		return fitClasses;
	}
	// this method will create an object for a fitness class of the program
	public FitnessClass openClass(String line) 
	{
			FitnessClass fClass = new FitnessClass(line);
			//fitClasses[numClasses] = fClass;
			this.programTime(fClass);
			numClasses++;
		return fClass;
	}

	
	// this method will build the list according to the start time
	public void programTime(FitnessClass fclass) 
	{
		int index = fclass.getStartTime()-9;
		fitClasses[index] = fclass;
	}
	// this method will return a single object from the list
	public FitnessClass getClassObject(int i)
	{
		return fitClasses[i];
	}
	// method to populate the attendance lists for a given Fitness Class
	public void populateAttendances(String line) 
	{
		String [] tokens = line.split(" ");
		
		int index = this.findClass(tokens[0]);
		fitClasses[index].setAttendance(line);
	}
	
	//this method will look for existing courses depending on class id
	public int findClass(String id)
	{

		int i=0;
		for(i=0; i<totalClasses; i++) 
		{
			String classid = fitClasses[i].getClassID();
			if( classid.equals(id))
			{
				return i;
			}

		}
		return -1;
	}
	//this method will sort the list of fitness class object regarding the average attendance
	public void sortClassAverage()
	{
		//System.out.println(fitClasses[0].getAttendances());
		Arrays.sort(fitClasses, 0, totalClasses);
	}
	
	//this method will sort the list regarding on the start time
	public void sortClassStartTime()
	{
		int counter = totalClasses;

		while (counter>1)
		{
			
			int i = 0;
			int j = i+1;
			
			for(i=0; i<counter-1; i++)
			{
				if(fitClasses[i].getStartTime()>fitClasses[j].getStartTime())
				{
					temp = fitClasses[i];
					fitClasses[i] = fitClasses[j];
					fitClasses[j] = temp;
				}
				j++;
			}
		 counter--;
		}
		
	}
	
	// this method will fill the list with empty fitness classes
	public void openEmptyClass()
	{
		FitnessClass fnew = new FitnessClass();
		for(int i=0; i<totalClasses; i++)
		{
			if(fitClasses[i]==null)
			{
				fnew.setStartTime(i+9);
				fnew.setCourseName("Available");
				break;
			}
		}
		this.programTime(fnew);
	}
}
