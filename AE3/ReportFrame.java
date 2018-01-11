
import java.awt.Font;

import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
    // your code here

	private JTextArea reportArea;
	private final int totalClasses = 7;
	// Constructor
	public ReportFrame(FitnessClass [] fcl) {

		// creating the JFrame
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Attendance Report");
		setLocation(250,400);
		setSize(700, 260);		
		reportArea = new JTextArea();
		reportArea.setEditable(false);
		reportArea.setFont(new Font("Courier",Font.PLAIN,14));
		// this method updates the text area
		this.updateReportArea(fcl);
		
		add(reportArea);

		setVisible(true);
	}
	//Method to build the report for display on the JTextArea
	public void updateReportArea(FitnessClass [] fcl) {

		//write on the Text Area
		String heading = "  ID\tClass\tTutor\t           Attendances\t  Average Attendance";
		reportArea.append(heading + "\n");
		
		for(int i=0;i<100;i++){
			reportArea.append("=");
		}
	    reportArea.append("\n");
	    
	    int counter = 0;
	    double totalAverage = 0.0;
		for(int i=0;i<totalClasses;i++)
		{
			String fClassID = fcl[i].getClassID();
	    	if(fClassID.length()>0)
	    	{
	    		reportArea.append(fcl[i].getReportLine()+"\n");
	    		totalAverage += fcl[i].getAverage();
	    		counter++;
	    	}
		}
    	
		totalAverage = totalAverage/counter;
		String lastLine = String.format("\n\t\t\t Overall average:\t%2.2f", totalAverage);
    	reportArea.append(lastLine + "\n");
	}
}
