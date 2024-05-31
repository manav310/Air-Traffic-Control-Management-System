package atc_project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User{
	private int id;
	private String name;
	int access_level;
	public User(int id, String name, int access_level) {
		this.id = id;
		this.name = name;
		this.access_level = access_level;
	}
	
	//accessor functions
	public int getAccessLevel() {
		return this.access_level;
	}
	public int getEmployee_id() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	
	public static User userVerification(int emp_id, String password, Connection con) throws SQLException {
		Statement st = con.createStatement();
		String query = "select * from user_details where emp_id = " + emp_id +  ";";
		ResultSet rs = st.executeQuery(query);
		if(rs.next() ){
			String obtained_password = rs.getString(3);
			if(obtained_password.equals(password)) {
				int id = Integer.parseInt(rs.getString(1));
				String name = rs.getString(2);
				int access_level = Integer.parseInt(rs.getString(4));
				User user = new User(id, name, access_level);
				return user;
			}
			
			return null;
		}
		return null;
	}
	
	
	public String toString() {
		return "Employee ID:> " + id + " Name: " + name + " Clearance:> " + access_level;
	}
}