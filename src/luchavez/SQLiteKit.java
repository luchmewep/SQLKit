package luchavez;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteKit extends SQLKit {

	private String db_url;
	
	/**
	 * <p>Provide value for <em>db_url</em>.<br>
	 * Example of db_url: <b>C:\Users\jsluc\Desktop\students.db</b></p>
	 * @param db_url
	 */
	public SQLiteKit(String db_url) {
		this.db_url = db_url;
	}
	
	/*	OPEN and CLOSE DATABASE CONNECTIONS
	 * 	Note: Before opening new connections,
	 * 	all previous connections must be closed.
	 * 	This is to prevent the database from being locked.
	 */
	
	@Override
	boolean openDatabase() {
		closeDatabase();
		try {
			con = DriverManager.getConnection("jdbc:sqlite:"+db_url);
			return true;
		} catch (SQLException e) {
			System.err.println("Error @openDatabase: "+e);
			return false;
		}
	}
}
