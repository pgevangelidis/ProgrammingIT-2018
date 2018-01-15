import java.sql.*;

public class dbInter {
	
	private String [][] courseTable;
	private Connection connection =null;
	//the constructor

	
	public void connection()
	{
		//String dbname = "m_17_2349102e", username = "m_17_2349102e", password = "2349102e";
		String dbname = "postgres", username = "postgres", password = "glasgowSQL";
		try {
			connection =DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname,username, password);
					//DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname,username, password);
					//DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/"  + dbname,username, password);
			}
		catch (SQLException e) {
			System.err.println("Connection Failed!");
			e.printStackTrace();
			return;
		}
		if (connection != null) {
			System.out.println("Connection successful");
		}else {
			System.err.println("Failed to make connection!");
		}
	}
	/*
	 *  This methods finds the course ID
	 *  @param the name of the course as a string 
	 */
	public String returnID(String s)
	{
		// connect with DB
		this.connection();
		//the query
		String courseID = "";
		Statement stmt = null;
		String query = "SELECT * from gym.course WHERE course_name=";
		query = query + "'"+s+"'";
		//System.out.println(query);
		try {
			 stmt = connection.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 rs.next();
			 courseID = rs.getString("courseid");
		}catch (SQLException e ) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}
		return courseID;
	}
	/*
	 *  this method returns a vector array only for the course table
	 *  @param are each time it is called the course_name, maxplaces, courseid
	 */
	public String[] courseQuery(String s)
	{
		// connect with DB
		this.connection();
		//the query
		Statement stmt = null;
		int i =0;
		String [] course_array;	
		String query = " SELECT * FROM gym.course";
		//this method will return the length of the array
		int size = this.queryLength(query);
		course_array = new String [size];
		try {
		 stmt = connection.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		//the next method of ResultSet allows you to iterate through the results
		 while (rs.next()) {
		// the getString method of the ResultSet object allows you to access the value for the
		//given column name for the current row in the result set as a String. If the value is an
		//integer you can use getInt(“col_name”)
			 if (s.equals("course_name")) {
				 course_array[i] = rs.getString("course_name");
			 }else if(s.equals("maxplaces"))
			 {
				 course_array[i] = rs.getString("maxplaces"); 
			 }else if(s.equals("courseid"))
			 {
				 course_array[i] = rs.getString("courseid");
			 }
			 i+=1;
		 	} 
		 }
		catch (SQLException e ) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}
		return course_array;
	}
	/*
	 * this method handles the data for a single course that appears in the 2nd JFrame
	 * @param id, essential to retrieve the correct course
	 */
	public String [][] viewSingleCourse(String id)
	{
		// connect with DB
		this.connection();
		//the query
		Statement stmt = null;
		int i=0;
		int size =0;
		String temp = "";
		String[][] courseEnrollment;
		String query = " SELECT * FROM gym.member AS m INNER JOIN gym.membercourse AS c ON m.guid = c.guid WHERE c.courseid =";
		query = query +"'"+ id +"'";
		String q = "SELECT maxplaces from gym.course WHERE courseid=";		
		q = q + "'" + id + "'";
		try {
			stmt = connection.createStatement();
			ResultSet rsSize = stmt.executeQuery(q);
			rsSize.next();
			size = rsSize.getInt("maxplaces");
		}catch (SQLException e ) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}
		courseEnrollment = new String [size][6];
		stmt = null;
		try {
		 stmt = connection.createStatement();
		 ResultSet rs = stmt.executeQuery(query);	 
		 while (rs.next()) {
			 int k = i +1;
			 courseEnrollment[i][0] ="" + k; 
			 courseEnrollment[i][1] = rs.getString("guid");
			 temp = rs.getString("sname");
			 courseEnrollment[i][2] = rs.getString("fname") + " " + temp;			 
			 courseEnrollment[i][3] = rs.getString("email");
			 courseEnrollment[i][4] = rs.getString("phone");
			 courseEnrollment[i][5] = rs.getString("status");
			 i+=1;
		 	} 
		 }
		catch (SQLException e ) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}
		return courseEnrollment;
	}
	/*
	 *  method to handle the member table
	 */
	public String [][] memberQuery()
	{
		// connect with DB
		this.connection();
		//the query
		Statement stmt = null;
		int i=0;
		String[][] members;
		String query = " SELECT member.guid, fname, sname, courseid from gym.member INNER JOIN gym.membercourse ON member.guid = membercourse.guid ";
		//this method will return the length of the array
		int size = this.queryLength(query);
		members = new String [size][4];
		try {
		 stmt = connection.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		//the next method of ResultSet allows you to iterate through the results
		 while (rs.next()) {
			 members[i][0] = rs.getString("guid");
			 members[i][1] = rs.getString("fname");
			 members[i][2] = rs.getString("sname");
			 members[i][3] = rs.getString("courseid");
			 i+=1;
		 	} 
		 }
		catch (SQLException e ) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}
		return members;
	}
	/*
	 *  The table with the courses and the instructors
	 */
	public String [][] returnCourseTableQuery()
	{
		// connect with DB
		this.connection();
		//the query	
		Statement stmt = null;
		int i=0, size=0;
		String temp="";
		String queryLong = "SELECT DISTINCT courseid, course_name, fname, sname, maxplaces "
				+ "FROM gym.course FULL OUTER JOIN gym.instructor ON instructorid=staffid";
		try {
			stmt = connection.createStatement();
			size = this.queryLength(queryLong);
			 courseTable = new String [size][5];
			 ResultSet rs = stmt.executeQuery(queryLong);
			 while (rs.next()) { 
				 int k = i +1;
				 courseTable[i][0] ="" + k; 
				 courseTable[i][1] = rs.getString("course_name");
				 temp = rs.getString("sname");
				 courseTable[i][2] = rs.getString("fname")+" "+temp;
				 courseTable[i][3] = rs.getString("maxplaces");
				 courseTable[i][4] = rs.getString("courseid");
				 i+=1;
			 	} 
		}catch (SQLException e ) {
			e.printStackTrace();
			System.err.println("error executing query " + queryLong);
		}
		/*
		 *  With this query it will return the number of members that are enrolled to each course
		 */
		for (i=0; i<size ;i++) {
			String queryShort = "SELECT COUNT(courseid) FROM gym.membercourse WHERE courseid =" +"'"+courseTable[i][4]+"'";
				try {
					stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery(queryShort);
					while (rs.next()) {
						int t = rs.getInt("count");
						courseTable[i][4] = ""+t;
					}
				}catch (SQLException e ) {
					e.printStackTrace();
					System.err.println("error executing query " + queryShort);
				}
			}
		return courseTable;
	}
	//Result set for a single course
	public String[] courseArray(String name)
	{
		// connect with DB
		this.connection();
		//the query
		String[] courseArray = new String[12];
		Statement stmt = null;
		String query ="select * from gym.course FULL OUTER JOIN gym.instructor ON instructorid = staffid WHERE course_name = '"+name+"'";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {				
				for(int j=0;j<=11;j++) {
					courseArray[j] = rs.getString(j+1);
					}
			}
		}catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}
		return courseArray;
	}
	// Calculates the upcoming length of each query
	public int queryLength(String q)
	{
		// connect with DB
		this.connection();
		//the query
		int querylength=0;
		Statement stmt = null;
		try {
		 stmt = connection.createStatement();
		 ResultSet rs = stmt.executeQuery(q);
		//the next method of ResultSet allows you to iterate through the results
		 while (rs.next()) {
			 querylength +=1; 
		 	}
		 }
		catch (SQLException e ) {
			e.printStackTrace();
			System.err.println("error executing query " + q);
		}
		return querylength;
	}
	// Insert Into the table
	public void insertInto(String guid, String courseid)
	{
		// connect with DB
		this.connection();
		//the query
		String book = ""+guid+courseid;
		String query = "UPDATE gym.membercourse SET courseid='"+courseid+"', bookingnumber='"+book+"' WHERE guid="+guid+";";
		try {
		 //stmt = connection.createStatement();
		 PreparedStatement prst = connection.prepareStatement(query);
		 prst.executeUpdate();
		 //close connection
		 this.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}

	}
	// remove member
	public void removeFrom(String guid)
	{
		// connect with DB
		this.connection();
		//the query
		String book = null;
		String query = "UPDATE gym.membercourse SET courseid="+null+", bookingnumber='"+book+"' WHERE guid="+guid+";";
		try {
			PreparedStatement prst = connection.prepareStatement(query);
			prst.executeUpdate();
		 //close connection
		 this.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}

	}
	//check if the course has exceed the max places
	public boolean exceedPlaces(String id, int max)
	{
		// connect with DB
		this.connection();
		//the query
		int currentPlacesInt = 0;
		boolean checkPlaces = false;
		String currentPlacesStr = "";
		String query = "SELECT COUNT(courseid) FROM gym.membercourse WHERE courseid =" +"'"+id+"'";
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			//the next method of ResultSet allows you to iterate through the results
			 while (rs.next()) {
				 currentPlacesStr = rs.getString("COUNT");
				 currentPlacesInt = Integer.parseInt(currentPlacesStr);
				 checkPlaces = currentPlacesInt==max;
			 }
		}catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}
		return checkPlaces;
	}
	// terminates the connection with the database
	public void close()
	{
		try {
			connection.close();
			System.out.println("Connection closed");
			}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection could not be closed – SQL exception");
			}
	}
}
