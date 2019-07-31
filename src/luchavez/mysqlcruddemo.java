package luchavez;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Note: The MySQL database should already be turned on.
 * Also, the JDBC (Java Database Connector) JAR file I used is Version 8.0.13.
 */

public class mysqlcruddemo {
	static MySQLKit db;
	static PreparedStatement pst;
	static ResultSet rs;
	static String sql;
	static ArrayList binder, row, column;
	static ArrayList<ArrayList> result2d;
	static boolean success;
	static DefaultTableModel model;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws SQLException {
		
		db = new MySQLKit("accounts"); //Instantiates connection to MySQL
		db.connectionTest(); //TEST FIRST THE CONNECTION BEFORE ANYTHING ELSE! 
		/*
		 * Below are CRUD (Create-Read-Update-Delete) examples.
		 * Since "insert into", "update" and "delete from" commands do not return a table/result set,
		 * the Database.java method that shall be used is the "executePST(PreparedStatement pst)".
		 * The said method will return a boolean value (true or false).
		 * As for the "select" command, "getRS(PreparedStatement pst)" shall be used.
		 * This method, on the other hand, returns a result set
		 * which can be processed by other Database.java Methods.
		 * Please see the Database.java to see all available methods.
		 */
		
		
		
		/*
		 * (a) Simple INSERT algorithm
		 */
//		sql = "insert into stud_info values (null, 'Juan', 'Tamad')";
//		pst = db.getPST(sql);
//		success = db.executePST(pst);
//		if(success) {
//			System.out.println("Success simple insert!");
//		}else {
//			System.out.println("Failed!");
//		}
		
		/*
		 * (a) INSERT algorithm with "binder" (Object-type ArrayList)
		 * Note: Although the result is the same with the above one, this is more secure
		 * from SQL injections. Research more about SQL injections on the internet.
		 */
//		sql = "insert into stud_info values (null, ?, ?)";
//		binder = new ArrayList<>();
//		binder.add("Carlo");
//		binder.add("Luchavez");
//		pst = db.getPST(sql, binder);
//		success = db.executePST(pst);
//		if(success) {
//			System.out.println("Success!");
//		}
//		else {
//			System.out.println("Failed!");
//		}
		
		/*
		 * (b) Simple UPDATE algorithm
		 */
		
//		sql = "update stud_info set stud_fname = 'James' where stud_id = 1";
//		pst = db.getPST(sql);
//		success = db.executePST(pst);
//		if(success) {
//			System.out.println("Success!");
//		}else {
//			System.out.println("Failed!");
//		}
		
		/*
		 * (c) Simple DELETE algorithm
		 */
		
//		sql = "delete from stud_info where stud_id = 1";
//		pst = db.getPST(sql);
//		success = db.executePST(pst);
//		if(success) {
//			System.out.println("Success!");
//		}else {
//			System.out.println("Failed!");
//		}
		
		//-----DEMO CODES FOR SOME DATABASE.JAVA METHODS-----//
		
		/*
		 * (d) Get One Row from Result Set
		 */
		
//		sql = "select * from stud_info";
//		pst = db.getPST(sql);
//		rs = db.getRS(pst);
//		row = db.getRow(rs);
//		for (Object obj : row) {
//			System.out.println(obj);
//		}
		
		/*
		 * (e) Get One Column from Result Set
		 */
//		sql = "select * from stud_info";
//		pst = db.getPST(sql);
//		rs = db.getRS(pst);
//		column = db.getColumn(rs, "stud_fname");
//		for (Object obj : column) {
//			System.out.println(obj);
//		}
		
		/*
		 * (f) Get Field Names from Result Set
		 */
//		sql = "select stud_id as 'Student ID', stud_fname as 'First Name', stud_lname as 'Last Name' from stud_info";
//		pst = db.getPST(sql);
//		rs = db.getRS(pst);
//		row = db.getColumnNames(rs);
//		for (Object obj : row) {
//			System.out.println(obj);
//		}
		
		/*
		 * (g) Get Field Aliases from Result Set
		 */
//		sql = "select stud_id as 'Student ID', stud_fname as 'First Name', stud_lname as 'Last Name' from stud_info";
//		pst = db.getPST(sql);
//		rs = db.getRS(pst);
//		row = db.getColumnLabels(rs);
//		for (Object obj : row) {
//			System.out.println(obj);
//		}
		
		/*
		 * (h) Get All Rows from Result Set
		 */
//		sql = "select * from stud_info";
//		pst = db.getPST(sql);
//		rs = db.getRS(pst);
//		result2d = db.getRows(rs);
//		for (ArrayList rows : result2d) {
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
		db.setTableModel(t, "select * from view_users");
		s.setBounds(0, 0, 500, 500);
		
		JComboBox<String> cmb = new JComboBox<>();
		db.setComboBoxModel(cmb, "show tables");
		cmb.setBounds(0,500,500,50);
		
		JFrame f = new JFrame();
		f.setLayout(null);
		f.add(s);
		f.add(cmb);
		f.setVisible(true);
		f.setSize(500, 600);
		f.setLocationRelativeTo(null);
	}
}