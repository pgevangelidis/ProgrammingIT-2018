package APIT_2018;
import java.util.concurrent.locks.*;

public class Grid extends Thread{
	
	private int row,col;
	private int counter;
	public int speed;
	private boolean alarm;
	private StringBuilder line;
	private String[][] junction;
	private ReentrantLock gridLock = new ReentrantLock();
	private Condition moveForward = gridLock.newCondition();
	
	/*========== Constructor ============*/
	public Grid(int r, int c) {
		this.row = r;
		this.col = c;
		this.junction = new String[r][c];
		line = new StringBuilder();
		line.setLength((c+1));
		this.counter = 2001;		
	}
	
	/*  
	 * This class moves the north vehicles to the south
	 */
	public void moveSouth(int i, int c, String v)
	{
		gridLock.lock();
		if(i < this.row)
		{
			try {
	
				while(this.junction[i][c] != null)
				{
					moveForward.await();

				}
				this.junction[i][c]=v;
				if(i>0)
					this.junction[i-1][c]=null;
			
			moveForward.signalAll();
			
			}catch(InterruptedException e) 
			{
				e.printStackTrace();
			}
			finally 
			{				
				gridLock.unlock();			
			}
		}
		else
		{
			this.junction[i-1][c]=null;
			gridLock.unlock();	
		}

	}
	
	// This method moves the vehicles north.
	
	public void moveNorth(int i, int c, String v)
	{
		gridLock.lock();
		
		if(i < this.row)
		{
			try {
	
				while(this.junction[this.row-1-i][c] != null)
				{
					moveForward.await();
				}
				this.junction[this.row-1-i][c]=v;
				if(i > 0)
					this.junction[this.row-i][c]=null;
			
			moveForward.signalAll();
			
			}catch(InterruptedException e) 
			{
				e.printStackTrace();
			}
			finally 
			{				
				gridLock.unlock();			
			}
		}
		else
		{
			this.junction[this.row-i][c]=null;
			gridLock.unlock();	
		}

	}
	/*  
	 * This class moves the west vehicles that going east
	 */
	public void moveEast(int r, int i, String v)
	{
		gridLock.lock();

		if(i < this.col)
		{
			try 
			{				
				while(this.junction[r][i] != null)
				{
					moveForward.await();
				}

				this.junction[r][i]=v;
				if(i>0)
					this.junction[r][i-1]=null;
						
			moveForward.signalAll();
				
			}catch(InterruptedException e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				gridLock.unlock();
			}
		}			
		else
		{
			this.junction[r][i-1]=null;
			gridLock.unlock();
		}
	}
	
	// This method moves the vehicles to the west.
	
	public void moveWest(int r, int i, String v)
	{
		gridLock.lock();

		if(i < this.col)
		{
			try 
			{				
				while(this.junction[r][this.col-1-i] != null)
				{
					moveForward.await();
				}

				this.junction[r][this.col-1-i]=v;
				if(i>0)
					this.junction[r][this.col-i]=null;
						
			moveForward.signalAll();
				
			}catch(InterruptedException e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				gridLock.unlock();
			}
		}			
		else
		{
			this.junction[r][this.col-i]=null;
			gridLock.unlock();
		}
	}
	/* 
	 * this method creates the grid for the system out.
	 */
	public void makeGrid(int c) 
	{	
		gridLock.lock();
		try {
			System.out.println("Iteration: " + c );

			for(int i=-1; i<=row; i++)
			{
				line.delete(0, line.length());
				for(int j=0; j<col; j++)
				{
					if(i==-1 || i==row)
						line.append("--");
					else 
					{
						if(junction[i][j] != null)
							line.append("|" + junction[i][j]);
						else
							line.append("| ");
						if(j==(col-1))
							line.append("|");
					}						
				}
				System.out.println(line);
			}

		}finally {
			gridLock.unlock();
		}

		if(c==(this.counter-1))
		{
			alarm = true;
			this.setAlarm(alarm);
		}
		
	}
	
	/*
	 *	This is the print-grid thread  
	 */
	public void run()
	{
		for(int i=0; i < counter; i++)
		{
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.makeGrid(i);
		}
	}
	
	/*====================  Access Methods  =====================*/
	public int getRow()
	{
		return this.row;
	}
	
	public int getCol()
	{
		return this.col;
	}
	
	public void setAlarm(boolean a)
	{
		alarm = a;
	}
	public boolean getAlarm()
	{
		return alarm;
	}
	
}
