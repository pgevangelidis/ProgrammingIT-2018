package APIT_2018;

public class SpeedVertical extends Thread{
	
	private ModelVehicle mdVeh;
	private int speed;
	private int lane, col, length;
	private String veh;
	private double time;;

	public SpeedVertical(ModelVehicle m, int d)
	{
		this.mdVeh = m;
		this.veh = "O";
		this.length = mdVeh.getRow();
		this.col = mdVeh.getCol()/d;
		this.lane = this.getLane();
		this.speed = this.getSpeed();
	}

	public void run() {

		long t0 = System.currentTimeMillis();

		for(int i=0; i<=length; i++)
		{
			try {
				Thread.sleep(this.speed); // this simulates the vehicle's speed
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			if(lane < col)
				mdVeh.moveSouth(i, lane, veh);
			else
				mdVeh.moveNorth(i, lane, veh);

			if(mdVeh.getAlarm()==true)
				break;

			if(i==length)
			{
				long t1 = System.currentTimeMillis();
				time = (double) (t1-t0)/1000;
				mdVeh.addTime(time);
			}
		}

	}
	/*
	 * This method controls the vehicle's speed. According to the lane where is been located 
	 * each vehicle will have different speed. This method simulates the fast lanes and the slow lanes.
	 * */
	private int getSpeed()
	{
		if( this.lane >= (mdVeh.getCol()/4) && this.lane < (3*mdVeh.getCol()/4) )
		{
			speed = (int) (Math.random()*80+5);
		}else
		{
			speed = (int) (Math.random()*400+80);
		}
		return speed;
	}

	private int getLane()
	{
		lane = (int) (Math.round(Math.random()*(mdVeh.getCol()-1)));
		return lane;
	}
}
