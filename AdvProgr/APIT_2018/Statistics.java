package APIT_2018;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Statistics{
	
	private ArrayList<Double> sec = new ArrayList<Double>();// this is the variable of time
	private double min, max;
	private double avg, var;
	
	private ReentrantLock statsLock = new ReentrantLock();
	
	public Statistics()
	{

		min=100.0;
		max=0.0;
		avg=0.0;
		var=0.0;

	}
	
	
	public void addTime(double t)
	{
		statsLock.lock();
		try 
		{
			
			if(t < this.min)
				setMinTime(t);
			if(t > this.max)
				setMaxTime(t);
			
			sec.add(t); 
			
		}finally 
		{
			statsLock.unlock();
		}
		
	}
	
	private void setMinTime(double t)
	{
		this.min = t;
	}
	
	private void setMaxTime(double t)
	{
		this.max = t;
	}
	
	private void setAverage()
	{
		for(Double i : sec)
		{
			avg +=i;
		}
		avg = avg/sec.size();
		
	}
	
	private void setVar()
	{
		for(Double i : sec)
		{
			double temp = i - avg;
			temp = Math.pow(temp, 2);
			var += temp;
		}
		var = var/sec.size();
	}
	
	public String printStats()
	{
		
		this.setAverage();
		this.setVar();
		
		String line = String.format("The average time was: %4.3fsec\nThe variance time: %4.3f sec\nThe min time:%4.3f sec\n"
				+ "The max time: %4.3f sec\nThe number of vehicles is: %5d",
				avg, var, min, max, sec.size());
		return line;
	}
	
}
