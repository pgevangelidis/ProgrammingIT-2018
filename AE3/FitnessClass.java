/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
    // Should have appropriate instance variables
	private String classid, courseName, tutor, reportLine;
	private int startTime;
	private double average;
	private int [] attendance;
	// Should have class constant for the number of weeks
	final int numClasses = 7;
	final int weekAt=5;

	//default constructor
	public FitnessClass() {
		classid = "";
		courseName = "";
		tutor = "";
		startTime = 0;
		//attendance = new String [numClasses][weekAt];
		attendance = new int[weekAt];
	}
	// constructor with String as parameter
	public FitnessClass(String line) {
		String [] tokens = line.split(" ");
		classid = tokens[0];
		courseName = tokens[1];
		tutor = tokens[2];
		startTime = Integer.parseInt(tokens[3]);
		attendance = new int[weekAt];
	}
	
	// Constructs the attendance matrix
	public void setAttendance(String line)
	{
		String [] tokens = line.split(" ");
		
		for(int i=0;i<weekAt;i++)
		{
			attendance[i] = Integer.parseInt(tokens[i+1]);
		}
		
		average = this.averageAttendance(attendance);
	}
    
	// From professor?
	public int compareTo(FitnessClass other)
	{
		
		double thisAverage = this.getAverage();
		double otherAverage = other.getAverage(); 
		
		if (thisAverage==otherAverage) 
		  return 0;

		else if(thisAverage>otherAverage)
		  return -1;  
			
		else
		  return 1; 
    }
    
    // This method returns the average attendance for the class
    public double averageAttendance(int [] attendance) {
    	average=0.0;
    	for(int i=0;i<weekAt;i++) {
    		average += attendance[i]; 
    	}
    	return average/5;
    }
    // Access method for the average value 
    public double getAverage() {
    	return average;
    }
    //access methods for class ID, course name, tutor name and start time of class 
    public String getClassID(){
    	return classid;
    }
    public String getCourseName() {
    	return courseName;
    }
    public String getTutor(){
    	return tutor;
    }
    public int getStartTime() {
    	return startTime;
    }
    public int[] getAttendances()
    {
    	return attendance;
    }
    // mutator methods for the instance variables
    public void setClassID(String id){
    	classid = id;
    }
    public void setCourseName(String cn) {
    	courseName = cn;
    }
    public void setTutor(String name){
    	tutor = name;
    }
    public void setStartTime(int time) {
    	startTime = time;
    }
    
    // This method returns the string which will be printed on the attendance report
    public String getReportLine() {  	
    	
    
    	reportLine = String.format("  %s\t%s\t%s\t%10d%5d%5d%5d%5d\t %15.2f", classid, courseName, tutor, attendance[0], attendance[1], attendance[2], attendance[3], attendance[4], average); 
    	return reportLine;
    }
}
