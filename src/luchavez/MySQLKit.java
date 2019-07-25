package luchavez;

import java.sql.DriverManager;

public class MySQLKit extends SQLKit {

	private String db_host, db_name, db_username, db_password;
	
	/**
	 * <p>No default database info.<br>
	 * Provide values for all parameters.
	 * </p>
	 * @param db_host (String)
	 * @param db_name (String)
	 * @param db_username (String)
	 * @param db_password (String)
	 */
	public MySQLKit(String db_host, String db_name, String db_username, String db_password) {
		this.db_host = db_host;
		this.db_name = db_name;
		this.db_username = db_username;
		this.db_password = db_password;
	}

	/**
	 * <p>Default Host: "localhost"<br>
	 * Default Username: "root"<br>
	 * Provide values for other parameters.
	 * </p>
	 * @param db_name (String)
	 * @param db_password (String)
	 */
	public MySQLKit(String db_name, String db_password) {
		this.db_host = "localhost";
		this.db_name = db_name;
		this.db_username = "root";
		this.db_password = db_password;
	}

	/**
	 * <p>Default Host: "localhost"<br>
	 * Default Username: "root"<br>
	 * Default Password: ""<br>
	 * Provide values for other parameters.
	 * </p>
	 * @param db_name (String)
	 */
	public MySQLKit(String db_name) {
		this.db_host = "localhost";
		this.db_name = db_name;
		this.db_username = "root";
		this.db_password = "";
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
			con = DriverManager.getConnection("jdbc:mysql://"+db_host+":3306/"+db_name+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowMultiQueries=true", db_username, db_password);
			return true;
		} catch (Exception e) {
			System.err.println("Error @openDatabase: "+e.getMessage());
			return false;
		}
	}

}
