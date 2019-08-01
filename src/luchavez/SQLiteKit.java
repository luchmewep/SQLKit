package luchavez;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLiteKit extends SQLKit {

	private String db_url;
	
	/**
	 * <p>Provide value for <em>db_url</em>.<br>
	 * Example of db_url: <b>C:\Users\jsluc\Desktop\students.db</b></p>
	 * @param db_url
	 */
	public SQLiteKit(String db_url) {
		this.db_url = db_url;
		if(connectionOpen()) {
			connectionClose();
		}
	}
	
	/*	OPEN and CLOSE DATABASE CONNECTIONS
	 * 	Note: Before opening new connections,
	 * 	all previous connections must be closed.
	 * 	This is to prevent the database from being locked.
	 */
	
	@Override
	protected boolean connectionOpen() {
		connectionClose();
		try {
			con = DriverManager.getConnection("jdbc:sqlite:"+db_url);
			return true;
		} catch (SQLException e) {
			System.err.println("Error @connectionOpen: "+e);
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionTest() {
		if(connectionOpen()) {
			System.out.println("Connected successfully.");
			ArrayList tables = getOneColumn("SELECT name FROM sqlite_master WHERE type='table'");
			if(tables != null) {
				String message = "The database contains "+tables.size()+" tables.";
				if(tables.size() != 0) {
					for (Object object : tables) {
						message += "\n> "+object;
					}
				}
				System.out.println(message);
			}
		}
	}
}
