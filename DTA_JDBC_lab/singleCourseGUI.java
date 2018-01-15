import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
/**
 * Database Theory & App 
 * @author 2349102e
 * Class to display the GUI and listen for events
 */
public class singleCourseGUI extends JFrame implements ActionListener
{
	private JLabel courseNameLabel, courseidLabel, instructorLabel, qualificationLabel, dayLabel, hourLabel, costLabel, emailLabel, maxplacesLabel;
	public JTextField courseNameField, courseidField, instructorField, qualificationField, dayField, hourField, costField, emailField, maxplacesField;
	public JButton addMember, removeMember, doneButton;
	public JComboBox enrolledCombo, availableCombo;
	private JTextArea singleCourseArea;
	private String[]  enrolledDetails, availableDetails, courseArray;
	private String[][] singleCourseArray, members;
	String [] headings = {"A/A","GUID","Member Name", "Email", "Phone", "Status"};
	
	
	//The second GUI
	public singleCourseGUI(String [] course)
	{
		courseArray = course;
		dbInter dbObject = new dbInter();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(680,350);
		setLocation(500,300);
		setTitle("Univeristy of Glasgow Gym");
		// The panel will contain the buttons and the Combo box and the scroll the area
		JPanel northPanel = addNorthPanel();
		JPanel middlePanel = addMiddlePanel();
		JScrollPane scrollpane = addScrollPane();
		//Border Layout 
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(scrollpane, BorderLayout.SOUTH);
		// set the text from course array
		this.northPanelData(course);
	}
	//northPanel
	public JPanel addNorthPanel()
	{
		JPanel top = new JPanel();
		// all the fields
		courseNameLabel = new JLabel("Course name: ");
		courseNameField = new JTextField("",15);
		courseNameField.setEditable(false);
		courseidLabel = new JLabel("Course ID: ");
		courseidField = new JTextField("",3);
		courseidField.setEditable(false);
		instructorLabel = new JLabel("Instructor");
		instructorField = new JTextField("",20);
		instructorField.setEditable(false);
		qualificationLabel = new JLabel("Qualifications");
		qualificationField = new JTextField("",7);
		qualificationField.setEditable(false);
		dayLabel = new JLabel("Course day");
		dayField = new JTextField("",10);
		dayField.setEditable(false);
		hourLabel = new JLabel("Course hour");
		hourField = new JTextField("",5);
		hourField.setEditable(false);
		maxplacesLabel = new JLabel("maximum places");
		maxplacesField = new JTextField("",3);
		maxplacesField.setEditable(false);
		costLabel = new JLabel("Hoursly cost");
		costField = new JTextField("",3);
		costField.setEditable(false);
		emailLabel = new JLabel("email");
		emailField = new JTextField("",19);
		emailField.setEditable(false);
		
		top.setLayout(new GridLayout(3,6));
		top.add(courseidLabel);
		top.add(courseidField);	
		top.add(courseNameLabel);
		top.add(courseNameField);
		top.add(costLabel);
		top.add(costField);
		top.add(dayLabel);
		top.add(dayField);
		top.add(instructorLabel);
		top.add(instructorField);
		top.add(maxplacesLabel);
		top.add(maxplacesField);
		top.add(qualificationLabel);
		top.add(qualificationField);	
		top.add(emailLabel);
		top.add(emailField);
		top.add(hourLabel);
		top.add(hourField);
		//
		return top;
	}
	//the buttons
	public JPanel addMiddlePanel()
	{
		JPanel middle = new JPanel();
		this.updateCombo();
		addMember = new JButton("Add member");
		addMember.addActionListener(this);
		removeMember = new JButton("Remove Button");
		removeMember.addActionListener(this);
		availableCombo = new JComboBox();
		enrolledCombo = new JComboBox();
		// Fill the arrays for the combos
		for(int i=0;i<availableDetails.length;i++) {
			availableCombo.addItem(availableDetails[i]);
		}
		// the enrolled members
		for(int i=0;i<enrolledDetails.length;i++) {
			enrolledCombo.addItem(enrolledDetails[i]);
		}
		availableCombo.addActionListener(this);
		enrolledCombo.addActionListener(this);
		doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		// Layout
		middle.setLayout(new GridLayout(2,3));
		middle.add(addMember);
		middle.add(availableCombo);
		middle.add(doneButton);
		middle.add(removeMember);
		middle.add(enrolledCombo);
		return middle;
	}
	// The scrollPane
	public JScrollPane addScrollPane(){
		singleCourseArea = new JTextArea(10, 50);
		singleCourseArea.setEditable(false);
		JScrollPane scrollpane = new JScrollPane(singleCourseArea);
		return scrollpane;
	}
	// Here I will construct the north Panel with the data of the course []
	public void northPanelData(String[] c)
	{
		courseNameField.setText(c[1]);
		courseidField.setText(c[0]);
		String name = ""+c[8]+" "+c[9];
		instructorField.setText(name);
		qualificationField.setText(c[11]);	
		dayField.setText(c[6]);
		hourField.setText(c[2]);
		maxplacesField.setText(c[3]);
		costField.setText(c[4]);
		emailField.setText(c[10]);
		// Here I update the Area
		this.areaUpdate(c[0]);
	}
	//Actions
	public void actionPerformed(ActionEvent e)
	{
		dbInter dbObject = new dbInter();
		String selectedMember = "";
		String memberID = "";
		boolean check = false;
		String id = courseArray[0];	
		int maxPlaces = Integer.parseInt(courseArray[3]);
		if(e.getSource()==this.addMember)
		{
			selectedMember = (String) availableCombo.getSelectedItem();
			StringBuilder selectMember = new StringBuilder(selectedMember);
			memberID = selectMember.substring(0,3);
			check = dbObject.exceedPlaces(id,maxPlaces);
			if(check!=true) {
				dbObject.insertInto(memberID, id);
			}else {
				JOptionPane.showMessageDialog(null, "The total number of members\n have exceed the maximum places of the course.\n "
						+ "Remove a member before entering a new one.","Exceed maximum places", JOptionPane.ERROR_MESSAGE);
				selectedMember = "";
			}
			this.clearArea();
			this.areaUpdate(id);
			this.updateCombo();
		}
		if(e.getSource()==this.removeMember)
		{
			selectedMember = (String) enrolledCombo.getSelectedItem();
			StringBuilder selectMember = new StringBuilder(selectedMember);
			memberID = selectMember.substring(0, 3);
			dbObject.removeFrom(memberID);
			this.clearArea();
			this.areaUpdate(id);
			this.updateCombo();
		}
		// Done button
		if(e.getSource()==this.doneButton)
		{
			this.clearArea();
			this.areaUpdate(id);
			this.setVisible(false);
		}
		this.repaint();
		this.revalidate();

	}
	public void areaUpdate(String id)
	{
		dbInter dbObject = new dbInter();
		singleCourseArray = dbObject.viewSingleCourse(id);
		String line="";
		line =String.format("%20s\t%20s\t%20s\t%20s\t%42s",headings[0],headings[1],headings[2],headings[3],headings[4],headings[5]);
		singleCourseArea.append(line+"\n");
		for(int i=0;i<200;i++){
			singleCourseArea.append("-");
		}
		singleCourseArea.append("\n");
		line="";
		for(int i=0;i<singleCourseArray.length;i++)
		{
			for(int j=0;j<6;j++) {
				if (singleCourseArray[i][j]!=null) {
					line = String.format("%20s", singleCourseArray[i][j]);
					singleCourseArea.append(line+"\t");	
				}else {
					singleCourseArea.append(""+"\t");
				}
			}
			singleCourseArea.append("\n");
		}
		
	}
	public void clearArea()
	{
		singleCourseArea.setText("");
	}
	public void updateCombo()
	{
		dbInter dbObject = new dbInter();
		members = dbObject.memberQuery();
		String maxplaces = courseArray[3];
		String courseid = courseArray[0];
		String local = "";
		int k=0;
		int m=0;
		//System.out.println(maxplaces);
		int size = Integer.parseInt(maxplaces);
		int size1 = members.length;
		availableDetails = new String[size1];
		enrolledDetails = new String [size];
		for(int i=0;i<members.length;i++) {
			local = members[i][3];
			if (local == null)
				local ="";
			//System.out.println(local+" "+courseid);  
			if(local.equals(courseid)) {
				enrolledDetails[k] =""+members[i][0]+", "+members[i][1]+" "+members[i][2];
				k+=1;
			}else {
				availableDetails[m] =""+members[i][0]+", "+members[i][1]+" "+members[i][2];
				m+=1;
			}
		}
	}
}