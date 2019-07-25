package luchavez;

public class testArea {
	public static void main(String[] args) {
		SQLiteKit db = new SQLiteKit("C:\\Users\\jsluc\\Desktop\\sqlitejavatoolkit-master\\student.db");
		db.testConnection();
	}
}
