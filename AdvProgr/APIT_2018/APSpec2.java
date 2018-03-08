package APIT_2018;

//import java.util.ArrayList;

public class APSpec2{

	public static void main(String[] args)
	{
		/*========= Create the grid =============*/

		int r = 10; // Specified from the exercise. But it can range from 1 to N, (regarding the memory size).
		int c = 20; // Specified from the exercise. But it can be dynamic.
		
		Statistics stats = new Statistics();
		
		Grid grid = new Grid(r,c);
		
		ModelVehicle mdVeh = new ModelVehicle(grid,stats);
		
		int destination = 2; // This is the number of destination for each direction
		/*========== This is the vehicle generator =======================  */
		
		VehicleSpec2 veh = new VehicleSpec2(mdVeh, destination);

		veh.generateVehicle();
		
		System.out.println("Great News. All threads have finished");
		System.out.println("End of simulation.");
		
		//String line = stats.printStats();
		
		//System.out.println(line);
	}
}
