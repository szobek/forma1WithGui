package forma1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler {
	
	private static Connection  connectToDb() {
		Connection con =null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}
	
	public static void getAllFromDB() {
		 
		try (Connection con = connectToDb()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from test");
			while (rs.next())
				System.out.println(rs.getString("name") + "  ");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
