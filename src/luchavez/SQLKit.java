package luchavez;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

abstract class SQLKit {
	protected Connection con;
	private PreparedStatement pst;
	private ResultSetMetaData rsmd;
	@SuppressWarnings("rawtypes")
	private ArrayList tblHeader, columnData, rowData;
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> tblRows;

	public void testConnection() {
		if(openDatabase()) {
			System.out.println("Connected successfully.");
		}
	}
	
	/**
	 * Returns PreparedStament (does not require binder)
	 * @param sql (String)
	 * @return PreparedStatement
	 */
	private PreparedStatement getPST(String sql) {
		return getPST(sql, null);
	}

	/**
	 * Returns PreparedStatement (binder required)
	 * @param sql (String)
	 * @param binder (Object-type ArrayList)
	 * @return PreparedStatement
	 */
	@SuppressWarnings("rawtypes")
	private PreparedStatement getPST(String sql, ArrayList binder) {
		if(sql == null) {
			System.err.println("Error @getPST: No SQL command.");
			return null;
		}
		openDatabase();
		try {
			pst = con.prepareStatement(sql);
			if(binder != null) {
				for (int i = 0; i < binder.size(); i++) {
					pst.setObject(i+1, binder.get(i));
				}
			}
			return pst;
		} catch (Exception e) {
			System.err.println("Error @getPST: "+e.getMessage());
			return null;
		}
	}

	/**
	 * Returns true for success or false for fail from PreparedStatement
	 * @param pst
	 * @return boolean
	 */
	private boolean executePST(PreparedStatement pst) {
		if(pst == null) {
			System.err.println("Error @executePST: No prepared statement.");
			return false;
		}
		try {
			if(pst != null && pst.executeUpdate() > 0) {
				return true;
			}
			else return false;
		} catch (Exception e) {
			System.err.println("Error @executePST: "+e.getMessage());
			return false;
		}
	}
	
	/**
	 * Returns ResultSet from PreparedStatement
	 * @param pst (PreparedStatement)
	 * @return ResultSet
	 */
	private ResultSet getRS(PreparedStatement pst) {
		if(pst == null) {
			System.err.println("Error @getRS: No prepared statement.");
			return null;
		}
		try {
			return pst.executeQuery();
		} catch (Exception e) {
			System.err.println("Error @getRS: "+e.getMessage());
			return null;
		}
	}
	
	public boolean runQuery(String sql) {
		return executePST(getPST(sql));
	}
	
	@SuppressWarnings("rawtypes")
	public boolean runQuery(String sql, ArrayList binder) {
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean runQuery(String sql, Object[] binder) {
		return runQuery(sql, new ArrayList(Arrays.asList(binder)));
	}

	/**
	 * Returns one row from the ResultSet
	 * @param rs (ResultSet)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getRow(ResultSet rs) {
		if(rs == null) {
			System.err.println("Error @getRow: No result set.");
			return null;
		}
		try {
			rowData = new ArrayList<>();
			int columns = rs.getMetaData().getColumnCount();
			while(rs.next()) {
				for (int i = 1; i <= columns; i++) {
					rowData.add(rs.getObject(i));
				}
				break;
			}
			return rowData;
		} catch (Exception e) {
			System.err.println("Error @getRow: "+e.getMessage());
			return null;
		}
	}

	public ArrayList getColumn(String sql) {
		
		return null;
	}
	
	/**
	 * Returns one column from the ResultSet
	 * @param rs (ResultSet)
	 * @param columnName (String)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" } )
	public ArrayList getColumn(ResultSet rs, String columnName) {
		if(rs == null) {
			System.err.println("Error @getColumn: No result set.");
			return null;
		}
		try {
			columnData = new ArrayList<>();
			while(rs.next()) {
				columnData.add(rs.getObject(columnName));
			}
			return columnData;
		} catch (Exception e) {
			System.err.println("Error @getColumn: "+e.getMessage());
			return null;
		}
	}

	/**
	 * Returns column names from the ResultSet
	 * @param rs (ResultSet)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList getColumnNames(ResultSet rs){
		try {
			tblHeader = new ArrayList<>();
			rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				tblHeader.add(rsmd.getColumnName(i));	
			}
			return tblHeader;
		} catch (Exception e) {
			System.err.println("Error @getColumnNames: "+e.getMessage());
			return null;
		}
	}

	/**
	 * Returns column labels from the ResultSet
	 * @param rs (ResultSet)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList getColumnLabels(ResultSet rs){
		try {
			tblHeader = new ArrayList<>();
			rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				tblHeader.add(rsmd.getColumnLabel(i));				
			}
			return tblHeader;
		} catch (Exception e) {
			System.err.println("Error @getColumnLabels: "+e.getMessage());
			return null;
		}
	}

	/**
	 * Returns all rows from the ResultSet
	 * @param rs (ResultSet)
	 * @return 2D ArrayList (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<ArrayList> getRows(ResultSet rs){
		try {
			tblRows = new ArrayList<>();
			int columns = rs.getMetaData().getColumnCount();
			int row = 0;
			while (rs.next()) {
				tblRows.add(new ArrayList<>());
				for (int i = 1; i <= columns; i++) {
					tblRows.get(row).add(rs.getObject(i));
				}
				row++;
			}
			return tblRows;
		} catch (Exception e) {
			System.err.println("Error @getRows: "+e.getMessage());
			return null;
		}
	}

	/**
	 * Returns JTable model from a ResultSet
	 * @param rs (ResultSet)
	 * @return DefaultTableModel for JTable
	 */
	public DefaultTableModel getTableModel(ResultSet rs) {
		//Get Column Names
		tblHeader = getColumnLabels(rs);

		//Get Rows from Result Set
		tblRows = getRows(rs);

		return new DefaultTableModel(getArrayListToArray(tblRows), );
	}

	/**
	 * Returns JComboBox model from an Object-type ArrayList
	 * @param v (Object-type ArrayList)
	 * @return
	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public DefaultComboBoxModel getComboBoxModel(ArrayList v) {
//		return new DefaultComboBoxModel<>(v);
//	}
	
	public void setTableModel(JTable tbl, String sql) {
		tbl.setModel(getTableModel(getRS(getPST(sql))));
	}
	
//	public void setComboBoxModel(JComboBox cmb, String col_name, String sql) {
//		cmb.setModel(getComboBoxModel(getColumn(getRS(getPST(sql)), col_name)));
//	}
	
	/*	OPEN and CLOSE DATABASE CONNECTIONS
	 * 	Note: Before opening new connections,
	 * 	all previous connections must be closed.
	 * 	This is to prevent the database from being locked.
	 */
	
	abstract boolean openDatabase();
	
	protected void closeDatabase() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.err.println("Error @closeDatabase: "+e.getMessage());
		}
	}
	
	/*
	 * STATIC METHODS (Converters, etc.)
	 */
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static Object[] getArrayListToArray(ArrayList input) {
//		if(input == null) {
//			return null;
//		}
//		//Check if 1D or 2D
//		else return input.toArray(new Object[input.size()]);
//		Object output[][] = new Object[input.size()][];
//		for (int i = 0; i < input.size(); i++) {
//			output[i] = input.get(i).toArray(new Object[input.get(i).size()]);
//		}
//		return output;
//	}
	
//	@SuppressWarnings("rawtypes")
//	public static void arrayListToArray(ArrayList input, Object[] output) {
//		output = arrayListToArray(input);
//	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void arrayListToArray(ArrayList<ArrayList> input, Object[][] output) {
		output = new Object[input.size()][];
		for (int i = 0; i < input.size(); i++) {
			output[i] = input.get(i).toArray(new Object[input.get(i).size()]);
		}
	}
}