package APIT_2018;

/*
 * This Model class is responsible for transfer the information correctly through out the system.
 * It connects the Threads with the Grid and Statistics class.
 * */

public class ModelVehicle {
	
	private Grid grid;
	private Statistics stats;
	private int row,col;
	private boolean alarm;
	
	public ModelVehicle(Grid g, Statistics s)
	{
		this.grid = g;
		this.stats = s;
		this.row = grid.getRow();
		this.col = grid.getCol();
	}
	
	/**
	 * 
	 */
	public void gridThread()
	{
		grid.start();
	}
	/**
	 *  This methods are calling the grid
	 */
	public void moveNorth(int r, int lane, String v)
	{
		grid.moveNorth(r, lane, v);
	}
	
	public void moveSouth(int r, int lane, String v)
	{
		grid.moveSouth(r, lane, v);
	}
	
	public void moveWest(int lane, int c, String v)
	{
		grid.moveWest(lane, c, v);
	}
	
	public void moveEast(int lane, int c, String v)
	{
		grid.moveEast(lane, c, v);
	}
	
	/**
	 *  This method is calling the add time to the Statistics class
	 */
	public void addTime(double t)
	{
		stats.addTime(t);
	}
	
	// The grid rows and columns are obtained from here

	public int getRow()
	{
		return this.row;
	}
	
	public int getCol()
	{
		return this.col;
	}
	
	// This method gets the alarm value from the Grid class
	public boolean getAlarm()
	{
		alarm = grid.getAlarm();
		return this.alarm;
	}
}
