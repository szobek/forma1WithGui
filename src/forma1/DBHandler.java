package forma1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JOptionPane;

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
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void insertAllToDb(List<Pilota> pilots) {
		try (Connection con = connectToDb()){
			 Statement statement = con.createStatement();
	            statement.executeUpdate("truncate table test");
			String query = "INSERT into test " + "VALUES (?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			for (Pilota pilota: pilots) {
			    preparedStmt.setString (1, pilota.getName());
			    preparedStmt.executeUpdate();
			}
			JOptionPane.showMessageDialog(null, "DB-be írás kész!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
