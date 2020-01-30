package luchavez2;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import luchavez2.SQLKit;

public class MySQLKit extends SQLKit {

	private String db_host = null, db_name = null, user_name = null, user_password = null;
	
	/**
	 * <p>No default database info.<br>
	 * Provide values for all parameters.
	 * </p>
	 * @param db_host (String)
	 * @param db_name (String)
	 * @param user_name (String)
	 * @param user_password (String)
	 */
	public MySQLKit(String db_host, String db_name, String user_name, String user_password) {
		set_db_host(db_host);
		set_user_name(user_name);
		set_user_password(user_password);
		connectionOpen();
		set_db_name(db_name);
	}

	/**
	 * <p>
	 * Default Database: null<br>
	 * Provide values for other parameters.
	 * </p>
	 * @param db_host (String)
	 * @param user_name (String)
	 * @param user_password (String)
	 */
	public MySQLKit(String db_host, String user_name, String user_password) {
		this(db_host, null, user_name, user_password);
	}
	
	/**
	 * <p>Default Host: "localhost"<br>
	 * Default Username: "root"<br>
	 * Provide values for other parameters.
	 * </p>
	 * @param db_name (String)
	 * @param user_password (String)
	 */
	public MySQLKit(String db_name, String user_password) {
		this("localhost", db_name, "root", user_password);
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
		this("localhost", db_name, "root", "");
	}
	
	/**
	 * <p>Default Host: "localhost"<br>
	 * Default Database: null<br>
	 * Default Username: "root"<br>
	 * Default Password: ""<br>
	 * </p>
	 */
	public MySQLKit() {
		this("localhost", null, "root", "");
	}
	
	//Getters
	public String get_db_host() { return db_host; }
	public String get_db_name() { return db_name; }
	public String get_user_name() { return user_name; }
	private String get_user_password() { return user_password; }
	
	//Setters
	private void set_db_host(String db_host) { this.db_host = db_host; }
	public void set_db_name(String db_name) {
		if(db_name != null && !db_name.isEmpty() && changeDatabase(db_name)) this.db_name = db_name;
	}
	private void set_user_name(String user_name) { this.user_name = user_name; }
	public void set_user_password(String user_password) {
		if(!isConnected) {
			this.user_password = user_password;
		}
		else{
			this.user_password = user_password;
			System.out.println(runQuery(String.format("SET PASSWORD FOR '%s'@'%s' = PASSWORD('%s')", get_user_name(), get_db_host(), get_user_password())));
		}
	}
	
	/*	OPEN and CLOSE DATABASE CONNECTIONS
	 * 	Note: Before opening new connections,
	 * 	all previous connections must be closed.
	 * 	This is to prevent the database from being locked.
	 */
	
	@Override
	protected void connectionOpen() {
		if(!isConnected){
			try {
				con = DriverManager.getConnection("jdbc:mysql://"+get_db_host()+":3306?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowMultiQueries=true", get_user_name(), get_user_password());
				isConnected = true;
			} catch (Exception e) {
				System.err.println("Error @connectionOpen: "+e.getMessage());
				isConnected = false;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void connectionTest() {
		if(isConnected) {
			System.out.println("Connected successfully.");
			if(get_db_name() != null){
				ArrayList tables = getOneColumn("show tables");
				if(tables != null) {
					String message = "The database contains "+tables.size()+" tables.";
					if(tables.size() != 0) {
						for (Object object : tables) {
							message += "\n> "+object;
						}
					}
					System.out.println(message);
				}
			}else{
				System.out.println("No selected database.");
			}
		}
	}
	
	private boolean changeDatabase(String db_name){
		if(isConnected) {
			try {
				con.setCatalog(db_name);
				return true;
			} catch (SQLException e) {
				System.err.println("Error @changeDatabase: "+e.getMessage());
				return false;
			}
		} else return false;
	}
	
	public void addUser(String user_name, String user_password){
		if(isConnected){
			runQuery(String.format("CREATE USER '%s'@'%' IDENTIFIED VIA mysql_native_password USING '%s';", user_name, user_password));
			runQuery(String.format("GRANT ALL PRIVILEGES ON *.* TO '%s'@'%' REQUIRE NONE WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;", user_name));
		}
	}

}
