package APIT_2018;

public class SpeedHorizontal extends Thread{

	private ModelVehicle mdVeh;
	private int speed;
	private double time;
	private int lane, row, length;
	private String veh;

	public SpeedHorizontal(ModelVehicle m, int d)
	{
		this.mdVeh = m;
		this.veh = "-";
		this.length = mdVeh.getCol();
		this.row = mdVeh.getRow()/d;
		this.lane = this.getLane();
		this.speed = this.getSpeed();
	}

	public void run() {

		long t0 = System.currentTimeMillis();

		for(int i=0; i<=length; i++)
		{
			try {
				Thread.sleep(this.speed); // time taken to prepare a course
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			if(lane < row)
				mdVeh.moveEast(lane, i, veh);
			else
				mdVeh.moveWest(lane, i, veh);

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
		if( this.lane >= (mdVeh.getRow()/4) && this.lane < (3*mdVeh.getRow()/4) )
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
		lane = (int) (Math.round(Math.random()*(mdVeh.getRow()-1)));
		return lane;
	}
}
