package luchavez;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteKit extends SQLKit {

	private String db_url;
	
	public SQLiteKit(String db_url) {
		this.db_url = db_url;
	}
	
	@Override
	void openDatabase() {
		closeDatabase();
		try {
			con = DriverManager.getConnection("jdbc:sqlite:"+db_url);
		} catch (SQLException e) {
			System.err.println("Error @openDatabase: "+e);
		}		
	}
	
}
