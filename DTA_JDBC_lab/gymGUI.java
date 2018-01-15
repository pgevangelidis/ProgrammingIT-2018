import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * Database Theory & App 
 * @author 2349102e
 * Class to display the GUI and listen for events
 */
public class gymGUI extends JFrame implements ActionListener
{
	public JButton viewCourse, singleCourse;
	private JTextArea allCourses;
	private JComboBox courseCombo;
	String [] headings = {"A/A","Course", "Instructor", "Max Places","Enrolled"};
	String [] courseName, memberFname, memberSname, guid, memberDetails, courseArray;
	JScrollPane scrollpane;
	//the GUI
	public gymGUI()
	{
		dbInter dbObject = new dbInter();
		courseName = dbObject.courseQuery("course_name");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(150,150);
		setSize(700,350);
		setTitle("Univeristy of Glasgow Gym");
		// The panel will contain the buttons and the Combo box and the scroll the area
		JPanel northPanel = addNorthPanel();
		JScrollPane scrollPane = addScrollPane();
		//Border Layout 
		add(northPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);

	}
	// The northPanel
	public JPanel addNorthPanel(){
		JPanel top = new JPanel();
		viewCourse = new JButton("View Courses");
		viewCourse.addActionListener(this);
		singleCourse = new JButton("Single Course");
		singleCourse.addActionListener(this);
		courseCombo = new JComboBox(courseName);
		courseCombo.addActionListener(this);
		top.setLayout(new GridLayout(1,3));	
		top.add(viewCourse);
		top.add(singleCourse);
		top.add(courseCombo);
		return top;
	}
	//The scrollPane
	public JScrollPane addScrollPane(){
		allCourses = new JTextArea(60, 50);
		allCourses.setEditable(false);
		scrollpane = new JScrollPane(allCourses);
		return scrollpane;
	}
	// actions
	public void actionPerformed(ActionEvent p)
	{
		String [][] temp;
		String line="";
		dbInter dbObject = new dbInter();
		if(p.getSource()==this.viewCourse)
		{
			this.clearArea();
			temp = dbObject.returnCourseTableQuery();
			line += String.format("%20s\t%30s\t%35s\t%15s\t%15s",headings[0],headings[1],headings[2],headings[3],headings[4]);
			allCourses.append(line+"\n");
			for(int i=0;i<=180;i++){
				allCourses.append("-");
			}
			allCourses.append("\n");
			line="";
			for(int i=0;i<temp.length;i++)
			{
				line = String.format("%20s\t%30s\t%35s\t%15s\t%15s", temp[i][0],temp[i][1],temp[i][2],temp[i][3],temp[i][4]);
				allCourses.append(line+"\n");
			}
		}
		if(p.getSource()==this.singleCourse)
		{
			String selectedCourse = (String) courseCombo.getSelectedItem();
			courseArray = dbObject.courseArray(selectedCourse);
			singleCourseGUI view = new singleCourseGUI(courseArray);
			view.setVisible(true);
		}
	}
	// clear the text Area
	public void clearArea()
	{
		allCourses.setText("");
	}
		
}