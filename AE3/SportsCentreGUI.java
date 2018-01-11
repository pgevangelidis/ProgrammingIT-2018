import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** String for adding or deleting class */
	private String classid, course, tutor;
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;
	private FitnessProgram fProgram;
	private FitnessClass fClass;
	public FitnessClass [] fitClasses;
	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	private final int totalClasses = 7;
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI(FitnessProgram fp) {
		//Creating a FitnessClass object
		FitnessProgram fProgram = fp;
		//construct the list of object
		fitClasses = new FitnessClass[totalClasses];
		// the Gui
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setLocation(250,100);
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier",Font.PLAIN,14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		// more code needed here
		this.initLadiesDay(classesInFile, fProgram);
		this.initAttendances(attendancesFile, fProgram);
		this.updateDisplay(fProgram);
		//store the fProgram 
		this.setFitnessProgram(fProgram);

	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay(String classesInFile, FitnessProgram fProgram) {
		try {
			FileReader reader = new FileReader(classesInFile);
			Scanner in = new Scanner(reader);
			int counter = 0;
			
			while (in.hasNextLine()) {
				// FitnessClass Object
				fProgram.openClass(in.nextLine());
				counter++;
			}
			
			if(counter<totalClasses)
			{
				for(int i=0; i<(totalClasses-counter); i++)
				{
					fProgram.openEmptyClass();
				}
			}
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "There in no file with that name in the directory","File Not Found",JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Initializes the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances(String attendancesFile, FitnessProgram fProgram) {
		try {
			FileReader reader = new FileReader(attendancesFile);
			Scanner in = new Scanner(reader);

			while (in.hasNextLine())
			{
				fProgram.populateAttendances(in.nextLine());
			}
		}
		catch(IOException e) 
		{
			JOptionPane.showMessageDialog(null, "There in no file with that name in the directory","File Not Found",JOptionPane.ERROR_MESSAGE);
		}
		
	    // preparing the Text Area for the Attendance report

	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay(FitnessProgram fProgram) {
		// clear the area in case there is already a program
		display.selectAll();
		display.setText("");
		// sort the fitness list
		fProgram.sortClassStartTime();
		//
		String heading = "  9-10\t10-11\t11-12\t12-13\t13-14\t14-15\t15-16";
	    String courseLine = "  ";
	    String tutorLine = "  ";
	    display.append(heading+"\n");
		
	    for(int i=0;i<120;i++){
			display.append("-");
		}
	    display.append("\n");
	    for(int i=0;i<totalClasses;i++)
	    {
	    	FitnessClass fClass = fProgram.getClassObject(i);
	    	if(fClass!=null)
	    	{
	    		courseLine += fClass.getCourseName() + "\t";
	    		tutorLine += fClass.getTutor() + "\t";
		    	//System.out.println(fClass.getAttendances());
	    	}

	    }
	    display.append(courseLine+"\n");
	    display.append(tutorLine+"\n");
	    
	    fProgram.sortClassAverage();
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding(String cid, String course, String tutor, boolean checkId, FitnessProgram fp) {
		
		fitClasses = fp.getfitClasses();
		
		if(cid.isEmpty() || course.isEmpty() || tutor.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"All fields must be filled before adding a new fitness class.","Error Message:",JOptionPane.ERROR_MESSAGE);
			this.eraseFields();
		}
		else if(checkId==true)
		{
			JOptionPane.showMessageDialog(null,"This class ID already exists.\nPlease enter an other ID.","Error Message:",JOptionPane.ERROR_MESSAGE);
			this.eraseFields();
		}else
		{
		// this test will inform the user if the program is full
			for(int i=0; i<totalClasses; i++)
			{
				if(fitClasses[i].getCourseName().equals("Available"))
				{
					fitClasses[i].setClassID(cid);
					fitClasses[i].setCourseName(course);
					fitClasses[i].setTutor(tutor);
					checkId = true;
					break;

				}
			}	
			if(checkId==false)
			{
				JOptionPane.showMessageDialog(null,"The Fitness Program is full.\nPlease remove a class before adding one.","Error Message:",JOptionPane.ERROR_MESSAGE);
				this.eraseFields();
			}	
		}
		
		// Update the Text area
		
		this.eraseFields();
		this.updateDisplay(fp);
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion(String cid, boolean checkId, FitnessProgram fp) {
		
		fitClasses = fp.getfitClasses();
		
		if(cid.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"The 'Enter Class ID' field must be filled before deleting a new fitness class.","Error Message:",JOptionPane.ERROR_MESSAGE);
			this.eraseFields();

		}
		else if(checkId==false)
		{
			JOptionPane.showMessageDialog(null,"This class ID does not exist.\nPlease enter an existing ID.","Error Message:",JOptionPane.ERROR_MESSAGE);
			this.eraseFields();
		}
		else
		{
			int index = fp.findClass(cid);
			double avg = fitClasses[index].getAverage();
			int hour = fitClasses[index].getStartTime();
		
		
			if (avg>=12.0)
			{
				JOptionPane.showMessageDialog(null,"It is prefered to delete a fitness class with average attendance lower than 12.","Warning Message:",JOptionPane.WARNING_MESSAGE);
				this.eraseFields();
			}
			else
			{
				
				fitClasses[index] = new FitnessClass();
				fitClasses[index].setCourseName("Available");
				fitClasses[index].setStartTime(hour);
			}
		}
		// Update the Text area
		//fp.sortClassStartTime();
		this.eraseFields();
		this.updateDisplay(fp);
		
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport(FitnessClass [] fitnessClasses) 
	{	
		report = new ReportFrame(fitnessClasses);
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose(FitnessClass [] fitClasses) 
	{

		try {
			FileWriter writer = new FileWriter(classesOutFile);
			for (int i=0; i<totalClasses; i++)
			{
				String name = fitClasses[i].getCourseName();
				if(name.equals("Available")) 
				{	
				}
				else 
				{	
				writer.write(fitClasses[i].getClassID() + " ");
				writer.write(name + " ");
				writer.write(fitClasses[i].getTutor() + " ");
				writer.write(fitClasses[i].getStartTime() + "\n");				
				}
			}
				
			writer.close();		
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "There in no file with that name in the directory","File Not Found",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "The fitness Classes have been successfully saved to ClassesOut.txt","Save Successfully",JOptionPane.OK_OPTION);
		System.exit(NORMAL);
	}
	
	
	// this methods will store the FitnessProgram object
	public void setFitnessProgram(FitnessProgram fp)
	{
		fProgram = fp;
	}
	// this method will return the object
	public FitnessProgram getFitnessProgram()
	{
		return fProgram;
	}

	
	
	// this method will erase the text fields of the class
	public void eraseFields() {
		this.idIn.setText("");
		this.classIn.setText("");
		this.tutorIn.setText("");
	}
	
	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) 
	{
		//passing the Fitness Program object to the events
		fProgram = this.getFitnessProgram();
		fitClasses = fProgram.getfitClasses();
		
		if(ae.getSource()==this.attendanceButton)
		{
			this.displayReport(fitClasses);
		}
		
		else if(ae.getSource()==this.closeButton) 
		{
			this.processSaveAndClose(fitClasses);
		}
		
		classid = this.idIn.getText().trim();
		course = this.classIn.getText().trim();
		tutor = this.tutorIn.getText().trim();
		boolean checkId = false;
		// test if the entered id already exists
		for(int i=0; i<totalClasses ;i++)
		{
			if(fitClasses[i].getClassID().equals(classid))
			{
				checkId = true;
				break;
			}
		}
			
		
		if(ae.getSource()==this.addButton)
		{
			this.processAdding(classid, course, tutor, checkId, fProgram);
		}
		
		else if(ae.getSource()==this.deleteButton)
		{
			this.processDeletion(classid, checkId, fProgram);
		}

	}
}
