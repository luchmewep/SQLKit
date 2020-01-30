package luchavez2;

import java.util.ArrayList;

import luchavez2.MySQLKit;

public class testSQL {

	public static void main(String[] args) {
		MySQLKit db = new MySQLKit();
		db.connectionTest();
		System.out.println(db.get_db_name());
//		System.out.println(db.runQuery("update tbl_employees set type = 'HR' where type = 'HR'"));
		db.set_db_name("payrollsystem");
		System.out.println(db.get_db_name());
		ArrayList<Object> obj = db.getOneRow("select * from tbl_employees");
		for (int i = 0; i < obj.size(); i++) {
			System.out.println(obj.get(i));
		}
		db.addUser("semaj", "semaj");
	}

}
