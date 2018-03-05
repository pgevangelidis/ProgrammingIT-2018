package APIT_2018;

/*
 * This class creates Vertical Threads that moves from North to South and vice versa.
 * */


public class TrafficVertical extends Thread{

	private ModelVehicle mdVeh;
	private int speed;
	private int lane, col, length;
	private String veh;
	private double time;

	
	public TrafficVertical(ModelVehicle v, int d)
	{
		this.mdVeh = v;
		this.veh = "O";
		this.length = mdVeh.getRow();
		this.col = mdVeh.getCol()/d;
		this.lane = (int) (Math.round(Math.random()*(mdVeh.getCol()-1)));
		this.speed = (int) (Math.random()*400+5);
	}
/*
 * The variable length gets either the row integer number or the column number, depending on the direction
 * The constructor does a random allocation of lane to the vehicle/thread.
 * Also, the speed is implemented with the sleep method and is also a random number.  
 * 
 * */
	public void run() {

		long t0 = System.currentTimeMillis();

		for(int i=0; i<=length; i++)
		{
			try {
				Thread.sleep(this.speed); // this simulates the vehicle's speed.
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			if(lane >= col)
				mdVeh.moveNorth(i, lane, veh);
			else
				mdVeh.moveSouth(i, lane, veh);
			
			/*
			 *  This method grid.getAlarm() returns the boolean variable alarm 
			 *  which informs the program that the grid has been printed 2000  
			 *	as was mentioned in the specifications.
			 */
			
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

}
