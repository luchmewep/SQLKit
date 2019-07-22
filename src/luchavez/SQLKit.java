package luchavez;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

abstract class SQLKit {
	protected Connection con;
	private PreparedStatement pst;
	private ResultSetMetaData rsmd;
	@SuppressWarnings("rawtypes")
	private Vector tblHeader, columnData, rowData;
	@SuppressWarnings("rawtypes")
	private Vector<Vector> tblRows;

	/**
	 * Returns PreparedStament (does not require binder)
	 * @param sql (String)
	 * @return PreparedStatement
	 */
	public PreparedStatement getPST(String sql) {
		return getPST(sql, null);
	}
	
	public void testConnection() {
		if(openDatabase()) {
			System.out.println("Connected successfully.");
		}
		closeDatabase();
	}

	/**
	 * Returns PreparedStatement (binder required)
	 * @param sql (String)
	 * @param binder (Object-type Vector)
	 * @return PreparedStatement
	 */
	@SuppressWarnings("rawtypes")
	public PreparedStatement getPST(String sql, Vector binder) {
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
			closeDatabase();
			return null;
		}
	}

	/**
	 * Returns true for success or false for fail from PreparedStatement
	 * @param pst
	 * @return boolean
	 */
	public boolean executePST(PreparedStatement pst) {
		try {
			if(pst.executeUpdate() == 1) {
				return true;
			}
			else return false;
		} catch (Exception e) {
			System.err.println("Error @executePST: "+e.getMessage());
			closeDatabase();
			return false;
		}
	}
	
	/**
	 * Returns ResultSet from PreparedStatement
	 * @param pst (PreparedStatement)
	 * @return ResultSet
	 */
	public ResultSet getRS(PreparedStatement pst) {
		try {
			return pst.executeQuery();
		} catch (Exception e) {
			System.err.println("Error @getRS: "+e.getMessage());
			closeDatabase();
			return null;
		}
	}

	/**
	 * Returns one row from the ResultSet
	 * @param rs (ResultSet)
	 * @return Vector (Object-type)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector getRow(ResultSet rs) {
		try {
			rowData = new Vector<>();
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
			closeDatabase();
			return null;
		}
	}

	/**
	 * Returns one column from the ResultSet
	 * @param rs (ResultSet)
	 * @param columnName (String)
	 * @return Vector (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" } )
	public Vector getColumn(ResultSet rs, String columnName) {
		try {
			columnData = new Vector<>();
			while(rs.next()) {
				columnData.add(rs.getObject(columnName));
			}
			return columnData;
		} catch (Exception e) {
			System.err.println("Error @getColumn: "+e.getMessage());
			closeDatabase();
			return null;
		}
	}

	/**
	 * Returns column names from the ResultSet
	 * @param rs (ResultSet)
	 * @return Vector (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector getColumnNames(ResultSet rs){
		try {
			tblHeader = new Vector<>();
			rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				tblHeader.add(rsmd.getColumnName(i));	
			}
			return tblHeader;
		} catch (Exception e) {
			System.err.println("Error @getColumnNames: "+e.getMessage());
			closeDatabase();
			return null;
		}
	}

	/**
	 * Returns column labels from the ResultSet
	 * @param rs (ResultSet)
	 * @return Vector (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector getColumnLabels(ResultSet rs){
		try {
			tblHeader = new Vector<>();
			rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				tblHeader.add(rsmd.getColumnLabel(i));				
			}
			return tblHeader;
		} catch (Exception e) {
			System.err.println("Error @getColumnLabels: "+e.getMessage());
			closeDatabase();
			return null;
		}
	}

	/**
	 * Returns all rows from the ResultSet
	 * @param rs (ResultSet)
	 * @return 2D Vector (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector<Vector> getRows(ResultSet rs){
		try {
			tblRows = new Vector<>();
			int columns = rs.getMetaData().getColumnCount();
			int row = 0;
			while (rs.next()) {
				tblRows.add(new Vector<>());
				for (int i = 1; i <= columns; i++) {
					tblRows.get(row).add(rs.getObject(i));
				}
				row++;
			}
			return tblRows;
		} catch (Exception e) {
			System.err.println("Error @getRows: "+e.getMessage());
			closeDatabase();
			return null;
		}
	}

	/**
	 * Returns JTable model from a ResultSet
	 * @param rs (ResultSet)
	 * @return DefaultTableModel for JTable
	 */
	public DefaultTableModel getTableModel(ResultSet rs) {
		//Instantiate Vectors
		tblHeader = new Vector<>();
		tblRows = new Vector<>();

		//Get Column Names
		tblHeader = getColumnLabels(rs);

		//Get Rows from Result Set
		tblRows = getRows(rs);

		return new DefaultTableModel(tblRows, tblHeader);
	}

	/**
	 * Returns JComboBox model from an Object-type Vector
	 * @param v (Object-type Vector)
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DefaultComboBoxModel getComboBoxModel(Vector v) {
		return new DefaultComboBoxModel<>(v);
	}
	
	public void setTableModel(JTable tbl, String sql) {
		tbl.setModel(getTableModel(getRS(getPST(sql))));
	}
	
	public void setComboBoxModel(JComboBox cmb, String col_name, String sql) {
		cmb.setModel(getComboBoxModel(getColumn(getRS(getPST(sql)), col_name)));
	}
	
	/*	OPEN and CLOSE DATABASE CONNECTIONS
	 * 	Note: Before opening new connections,
	 * 	all previous connections must be closed.
	 * 	This is to prevent the database from being locked.
	 */
//	private void openDatabase() {
//		closeDatabase();
//		try {
//			con = DriverManager.getConnection();
//		} catch (SQLException e) {
//			System.err.println("Error @openDatabase: "+e);
//		}
//	}
	
	abstract boolean openDatabase();
	
	protected void closeDatabase() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.err.println("Error @closeDatabase: "+e);
		}
	}
}