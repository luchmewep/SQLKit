package luchavez;
import java.sql.*;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author luchmewep
 * <p>Hello, Programmer!<br>
 * To use this SQLite Toolkit for CRUD operations,<br>
 * just UNCOMMENT the CRUD operation you want to try out.</p>
 */

public class sqlitecruddemo {
	static SQLiteKit db;
	static PreparedStatement pst;
	static ResultSet rs;
	static String sql;
	static Vector<Object> binder, row, column;
	@SuppressWarnings("rawtypes")
	static Vector<Vector> result2d;
	static boolean success;
	static DefaultTableModel model;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws SQLException {
		
		db = new SQLiteKit("C:\\Users\\jsluc\\Desktop\\sqlitejavatoolkit-master\\students.db");
		db.testConnection(); //TEST FIRST THE CONNECTION BEFORE ANYTHING ELSE!
		
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
		 * (a) INSERT algorithm with "binder" (Object-type Vector)
		 * Note: Although the result is the same with the above one, this is more secure
		 * from SQL injections. Research more about SQL injections on the internet.
		 */
//		sql = "insert into stud_info values (null, ?, ?)";
//		binder = new Vector<>();
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
//		for (Vector rows : result2d) {
//			for (Object data : rows) {
//				System.out.print(data + "\t");
//			}
//			System.out.println();
//		}
		
		/*
		 * Get Table Model for JTable from Result Set
		 */
//		db.testConnection();
//		sql = "select * from stud_info";
//		model = db.getTableModel(rs);
//		JTable t = new JTable();
//		t.setModel(model);
//		JScrollPane s = new JScrollPane(t);
//		s.setBounds(0, 0, 500, 500);
//		
//		JFrame f = new JFrame();
//		f.setLayout(null);
//		f.add(s);
//		f.setVisible(true);
//		f.setSize(500, 500);
//		f.setLocationRelativeTo(null);
	}
}