package APIT_2018;

import java.util.ArrayList;

public class Vehicle extends Thread{

	private TrafficVertical vN;
	private TrafficHorizontal vW;
	private ModelVehicle mdVeh;
	private int generate;
	private ArrayList<TrafficVertical> vehN;
	private ArrayList<TrafficHorizontal> vehW;
	private int direction, destination;
	private static boolean alarm;


	public Vehicle(ModelVehicle md, int d)
	{
		this.destination = d;
		alarm = false;
		this.mdVeh = md;
	}

	/*
	 * The destination variable takes values 1 or 2 and refers to the number of destination in each direction.
	 * e.g. if destination is 2 means that vehicles can move from North to South and vice versa.
	 * */
	public int getDestination()
	{
		return this.destination;
	}


	private int getFrequency()
	{
		generate = (int) (Math.random()*350+50); //Assumption the vehicle frequency is random for now
		return generate;
	}

	private int getDirection()
	{
		direction = (int) Math.round(Math.random());
		return direction;
	}

	/*=============== Generate Vehicles Method ======================*/

	public void generateVehicle()
	{
		
		vehN = new ArrayList<TrafficVertical>();
		vehW = new ArrayList<TrafficHorizontal>();
		
		//grid.start();
		mdVeh.gridThread();

		while(!alarm)
		{
			try {
				Thread.sleep(this.getFrequency());

			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}

			if(this.getDirection()==0)
			{
				vN = new TrafficVertical(mdVeh, this.destination);
				//vN = new TrafficVertical(mdVeh,this.destination);
				vN.start();
				vehN.add(vN);

			}else
			{
				vW = new TrafficHorizontal(mdVeh, this.destination);
				vW.start();
				vehW.add(vW);
			}

			alarm = mdVeh.getAlarm(); // If the alarm is true, then the Vehicle generator will stop.
		}

		System.err.println("...Please wait...");
		System.err.println("There are some threads still running.");
		
		if(alarm==true)
		{
			try {

				for(TrafficVertical i : vehN)
					i.join();
				for(TrafficHorizontal j : vehW)
					j.join();

			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
