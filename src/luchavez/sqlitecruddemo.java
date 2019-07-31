package luchavez;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author James Carlo Luchavez
 * <p>Hello, Programmer!<br>
 * To use this SQLite Toolkit for CRUD operations,<br>
 * just UNCOMMENT the CRUD operation you want to try out.</p>
 */

public class sqlitecruddemo {
	static SQLiteKit db;
	static PreparedStatement pst;
	static ResultSet rs;
	static String sql;
	static ArrayList<Object> binder, row, column;
	@SuppressWarnings("rawtypes")
	static ArrayList<ArrayList> result2d;
	static boolean success;
	static DefaultTableModel model;
	
	public static void main(String[] args) throws SQLException {
		
		db = new SQLiteKit("C:\\Users\\jsluc\\Desktop\\sqlitejavatoolkit-master\\students.db");
		db.connectionTest(); //TEST FIRST THE CONNECTION BEFORE ANYTHING ELSE!
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
		System.out.println("Result of insert: "+db.runQuery("insert into tbl_student values (null, 'Renz', 'Arriola', 30, 'male', 3, 2, 1)"));
//		System.out.println("Result of update: "+db.runQuery("update tbl_student set stud_fname = 'Renzuah' where stud_id = 5"));
//		System.out.println("Result of delete: "+db.runQuery("delete from tbl_student where stud_id = 5"));
		JTable t = new JTable();
		JScrollPane s = new JScrollPane(t);
		db.setTableModel(t, "select * from tbl_student");
		s.setBounds(0, 0, 500, 500);
		System.out.println("After tbl");
		JComboBox<String> cmb = new JComboBox<>();
		db.setComboBoxModel(cmb, "select stud_age from tbl_student");
		cmb.setBounds(0,500,500,50);
		System.out.println("After cmb");
		JFrame f = new JFrame();
		f.setLayout(null);
		f.add(s);
		f.add(cmb);
		f.setVisible(true);
		f.setSize(500, 600);
		f.setLocationRelativeTo(null);
	}
}