package APIT_2018;

/**
 * Advance Programming Assessed Exercise
 * Specification 1
 * 
 * @author 2349120E
 *
 */

public class APSpec1{

	public static void main(String[] args)
	{
		/*========= Create the grid =============*/

		int r = 10; // Specified from the exercise. But it can range from 1 to N, (regarding the memory size).
		int c = 20; // Specified from the exercise. Same as the r variable.
		
		Statistics stats = new Statistics();
		
		Grid grid = new Grid(r,c);
		
		ModelVehicle mdVeh = new ModelVehicle(grid,stats);
		
		int destination = 1; // This is the number of destination for each direction
		
		/*========== This is the vehicle generator =======================  */

		Vehicle veh = new Vehicle(mdVeh, destination);

		veh.generateVehicle();
		
		System.out.println("Great News. All threads have finished");
		System.out.println("End of simulation.");

	}
}
