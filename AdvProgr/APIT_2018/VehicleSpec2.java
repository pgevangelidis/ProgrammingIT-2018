package APIT_2018;

import java.util.ArrayList;

public class VehicleSpec2 extends Thread{

	private ModelVehicle mdVeh;
	private SpeedVertical sV;
	private SpeedHorizontal sH;
	private TrafficVertical tV;
	private TrafficHorizontal tH;
	private int generate, rad;
	private ArrayList<SpeedVertical> speV;
	private ArrayList<SpeedHorizontal> speH;
	private ArrayList<TrafficVertical> traV;
	private ArrayList<TrafficHorizontal> traH;
	private int direction, destination;
	private static boolean alarm;


	public VehicleSpec2(ModelVehicle m, int d)
	{
		this.mdVeh = m;
		this.destination = d;
		alarm = false;
		this.rad = 0;
	}

	public int getDestination()
	{
		return this.destination;
	}

	/*
	 * This method controls the traffic in the grid taking into consideration the peak hours.
	 * In certain point the grid is full of vehicles, which could simulate the peak hour after work.
	 * */
	private int getFrequency(int r)
	{
		int temp = (int) (2.5*(Math.round(Math.cos(r*Math.PI/180)*100)));
		generate = Math.abs(temp)+50; // regulates the traffic.
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
		
		speV = new ArrayList<SpeedVertical>();
		speH = new ArrayList<SpeedHorizontal>();
		traV = new ArrayList<TrafficVertical>();
		traH = new ArrayList<TrafficHorizontal>();
				
		mdVeh.gridThread();

		while(!alarm)
		{
			rad++;
			try {
				Thread.sleep(this.getFrequency(rad));

			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
			if(this.getDirection()==0)
			{
				sV = new SpeedVertical(mdVeh, this.destination);
				tV = new TrafficVertical(mdVeh, this.destination);				
				sV.start();
				tV.start();				
				speV.add(sV);
				traV.add(tV);

			}else
			{
				sH = new SpeedHorizontal(mdVeh, this.destination);
				tH = new TrafficHorizontal(mdVeh, this.destination);
				sH.start();
				tH.start();
				speH.add(sH);
				traH.add(tH);
			}
			alarm = mdVeh.getAlarm();
		}

		System.err.println("...Please wait...");
		
		getStackTrace();
		
		if(alarm==true)
		{
			try {

				for(SpeedVertical i : speV)
					i.join();
				for(SpeedHorizontal j : speH)
					j.join();
				for(TrafficVertical k : traV)
					k.join();
				for(TrafficHorizontal l : traH)
					l.join();

			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
