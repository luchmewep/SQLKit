package luchavez;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author James Carlo Luchavez
 * Note: The MySQL database should already be turned on.
 * Also, the JDBC (Java Database Connector) JAR file I used is Version 8.0.13.
 */

public class mysqlcruddemo {
	static MySQLKit db;
	
	@SuppressWarnings({ "rawtypes" })
	public static void main(String[] args) throws SQLException {
		sysout
		db = new MySQLKit("accounts"); //Instantiates connection to MySQL
		db.connectionTest(); //TEST FIRST THE CONNECTION BEFORE ANYTHING ELSE! 
			
		/*
		 * (a) Simple INSERT algorithm
		 */
//		if(db.runQuery("insert into stud_info values (null, 'Juan', 'Tamad')")) {
//			System.out.println("Success simple insert!");
//		}else {
//			System.out.println("Failed!");
//		}
		
		/*
		 * (a) INSERT algorithm with "binder" (Object-type ArrayList)
		 * Note: Although the result is the same with the above one, this is more secure
		 * from SQL injections. Research more about SQL injections on the internet.
		 */
//		if(db.runQuery("insert into stud_info values (null, ?, ?)", new Object[] {"James Carlo", "Luchavez"})) {
//			System.out.println("Success!");
//		}
//		else {
//			System.out.println("Failed!");
//		}
		
		/*
		 * (b) Simple UPDATE algorithm
		 * Note: You can also use binder style like on the Insert Algorithm for more secure query.
		 */
//		if(db.runQuery("update stud_info set stud_fname = 'James' where stud_id = 1")) {
//			System.out.println("Success!");
//		}else {
//			System.out.println("Failed!");
//		}
		
		/*
		 * (c) Simple DELETE algorithm
		 * Note: You can also use binder style like on the Insert Algorithm for more secure query.
		 */
//		if(db.runQuery("delete from stud_info where stud_id = 1")) {
//			System.out.println("Success!");
//		}else {
//			System.out.println("Failed!");
//		}
		
		//-----DEMO CODES FOR SOME DATABASE.JAVA METHODS-----//
		
		/*
		 * (d) Get One Row from Result Set
		 */
//		for (Object obj : db.getOneRow("select * from stud_info")) {
//			System.out.println(obj);
//		}
		
		/*
		 * (e) Get One Column from Result Set
		 */
//		for (Object obj : db.getOneColumn("select * from stud_info")) {
//			System.out.println(obj);
//		}
		
		/*
		 * (f) Get Field Names from Result Set
		 */
//		for (Object obj : db.getColumnNames("select stud_id as 'Student ID', stud_fname as 'First Name', stud_lname as 'Last Name' from stud_info")) {
//			System.out.println(obj);
//		}
		
		/*
		 * (g) Get Field Aliases from Result Set
		 */	
//		for (Object obj : db.getColumnLabels("select stud_id as 'Student ID', stud_fname as 'First Name', stud_lname as 'Last Name' from stud_info")) {
//			System.out.println(obj);
//		}
		
		/*
		 * (h) Get All Rows from Result Set
		 */
//		for (ArrayList rows : db.getAllRows("select * from stud_info")) {
//			for (Object data : rows) {
//				System.out.print(data + "\t");
//			}
//			System.out.println();
//		}
		
		/*
		 * Get Table Model for JTable from Result Set
		 */
		JTable t = new JTable();
		JScrollPane s = new JScrollPane(t);
		s.setBounds(0, 0, 500, 500);
		JComboBox<String> cmb = new JComboBox<>();
		cmb.setBounds(0,500,500,50);
		JFrame f = new JFrame();
		f.setLayout(null);
		f.add(s);
		f.add(cmb);
		f.setVisible(true);
		f.setSize(500, 600);
		f.setLocationRelativeTo(null);
		
		//Insert Data to JComboBox and JTable
		db.setComboBoxModel(cmb, "select * from view_users");
		db.setTableModel(t, "select * from view_users");
	}
}