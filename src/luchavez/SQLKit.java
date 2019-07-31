package luchavez;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

abstract class SQLKit {
	protected Connection con;
	private PreparedStatement pst;
	private ResultSetMetaData rsmd;
	@SuppressWarnings("rawtypes")
	private ArrayList tblHeader, columnData, rowData;
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> tblRows;
	
	/**
	 * Returns PreparedStatement (binder required)
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @return PreparedStatement
	 */
	private PreparedStatement getPST(String sql, Object[] binder) {
		if(sql == null) {
			System.err.println("Error @getPST: No SQL command.");
			return null;
		}
		connectionOpen();
		try {
			pst = con.prepareStatement(sql);
			if(binder != null) {
				for (int i = 0; i < binder.length; i++) {
					pst.setObject(i+1, binder[i]);
				}
			}
			return pst;
		} catch (Exception e) {
			System.err.println("Error @getPST: "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Returns PreparedStament (does not require binder)
	 * @param sql (String)
	 * @return PreparedStatement
	 */
	private PreparedStatement getPST(String sql) {
		return getPST(sql, arrayListToArray(null));
	}

	/**
	 * Returns PreparedStatement (binder required)
	 * @param sql (String)
	 * @param binder (Object-type ArrayList)
	 * @return PreparedStatement
	 */
	@SuppressWarnings("rawtypes")
	private PreparedStatement getPST(String sql, ArrayList binder) {
		return getPST(sql, arrayListToArray(binder));
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
	 * Returns true or false for Create, Update, Delete Queries
	 * @param sql (String)
	 * @param binder (Object-type ArrayList)
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public boolean runQuery(String sql, ArrayList binder) {
		return executePST(getPST(sql, binder));
	}
	
	/**
	 * Returns true or false for Create, Update, Delete Queries
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @return boolean
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean runQuery(String sql, Object[] binder) {
		return runQuery(sql, new ArrayList(Arrays.asList(binder)));
	}
	
	/**
	 * Returns true or false for Create, Update, Delete Queries
	 * @param sql (String)
	 * @return boolean
	 */
	public boolean runQuery(String sql) {
		return executePST(getPST(sql));
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
	
	/**
	 * Returns one row from the ResultSet
	 * @param rs (ResultSet)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList getOneRow(ResultSet rs) {
		if(rs == null) {
			System.err.println("Error @getOneRow: No result set.");
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
			if(rowData.size() == 0) {
				return null;
			}
			else {
				return rowData;
			}
		} catch (Exception e) {
			System.err.println("Error @getOneRow: "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Returns one column from the ResultSet
	 * @param sql (String)
	 * @param binder (ArrayList)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getOneRow(String sql, ArrayList binder) {
		return getOneRow(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns one column from the ResultSet
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getOneRow(String sql, Object[] binder) {
		return getOneRow(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns one column from the ResultSet
	 * @param sql (String)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getOneRow(String sql) {
		return getOneRow(getRS(getPST(sql)));
	}
	
	/**
	 * Returns one column from the ResultSet
	 * @param rs (ResultSet)
	 * @param columnName (String)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" } )
	private ArrayList getOneColumn(ResultSet rs, String columnName) {
		if(rs == null) {
			System.err.println("Error @getOneColumn: No result set.");
			return null;
		}
		try {
			columnData = new ArrayList<>();
			while(rs.next()) {
				if(columnName == null) {
					columnData.add(rs.getObject(1));
				}else {
					columnData.add(rs.getObject(columnName));
				}
			}
			return columnData;
		} catch (Exception e) {
			System.err.println("Error @getOneColumn: "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Returns one column from the ResultSet
	 * @param sql (String)
	 * @param binder (ArrayList)
	 * @param columnName (String)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getOneColumn(String sql, ArrayList binder, String columnName) {
		return getOneColumn(getRS(getPST(sql, binder)), columnName);
	}
	
	/**
	 * Returns one column from the ResultSet
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @param columnName (String)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getOneColumn(String sql, Object[] binder, String columnName) {
		return getOneColumn(getRS(getPST(sql, binder)), columnName);
	}
	
	/**
	 * Returns one column from the ResultSet
	 * @param sql (String)
	 * @param binder (ArrayList)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getOneColumn(String sql, ArrayList binder) {
		return getOneColumn(getRS(getPST(sql, binder)), null);
	}

	/**
	 * Returns one column from the ResultSet
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getOneColumn(String sql, Object[] binder) {
		return getOneColumn(getRS(getPST(sql, binder)), null);
	}

	/**
	 * Returns one column from the ResultSet
	 * @param sql (String)
	 * @param columnName (String)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getOneColumn(String sql, String columnName) {
		return getOneColumn(getRS(getPST(sql)), columnName);
	}
	
	/**
	 * Returns one column from the ResultSet
	 * @param sql (String)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getOneColumn(String sql) {
		return getOneColumn(getRS(getPST(sql)), null);
	}

	/**
	 * Returns column names from the ResultSet
	 * @param rs (ResultSet)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList getColumnNames(ResultSet rs){
		if(rs == null) {
			System.err.println("Error @getColumnNames: No result set.");
			return null;
		}
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
	 * Returns column names from the ResultSet
	 * @param sql (String)
	 * @param binder (ArrayList)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getColumnNames(String sql, ArrayList binder) {
		return getColumnNames(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns column names from the ResultSet
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getColumnNames(String sql, Object[] binder) {
		return getColumnNames(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns column names from the ResultSet
	 * @param sql (String)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getColumnNames(String sql) {
		return getColumnNames(getRS(getPST(sql)));
	}

	/**
	 * Returns column labels from the ResultSet
	 * @param rs (ResultSet)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList getColumnLabels(ResultSet rs){
		if(rs == null) {
			System.err.println("Error @getColumnLabels: No result set.");
			return null;
		}
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
	 * Returns column labels from the ResultSet
	 * @param sql (String)
	 * @param binder (ArrayList)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getColumnLabels(String sql, ArrayList binder) {
		return getColumnLabels(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns column labels from the ResultSet
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getColumnLabels(String sql, Object[] binder) {
		return getColumnLabels(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns column labels from the ResultSet
	 * @param sql (String)
	 * @return ArrayList (Object-type)
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getColumnLabels(String sql) {
		return getColumnLabels(getRS(getPST(sql)));
	}

	/**
	 * Returns all rows from the ResultSet
	 * @param rs (ResultSet)
	 * @return 2D ArrayList (Object-type)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<ArrayList> getAllRows(ResultSet rs){
		if(rs == null) {
			System.err.println("Error @getAllRows: No result set.");
			return null;
		}
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
			System.err.println("Error @getAllRows: "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Returns all rows from the ResultSet
	 * @param sql (String)
	 * @param binder (ArrayList)
	 * @return 2D ArrayList (Object-type)
	 */
	@SuppressWarnings({ "rawtypes" })
	public ArrayList<ArrayList> getAllRows(String sql, ArrayList binder){
		return getAllRows(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns all rows from the ResultSet
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @return 2D ArrayList (Object-type)
	 */
	@SuppressWarnings({ "rawtypes" })
	public ArrayList<ArrayList> getAllRows(String sql, Object[] binder){
		return getAllRows(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns all rows from the ResultSet
	 * @param sql (String)
	 * @return 2D ArrayList (Object-type)
	 */
	@SuppressWarnings({ "rawtypes" })
	public ArrayList<ArrayList> getAllRows(String sql){
		return getAllRows(getRS(getPST(sql)));
	}

	/**************************************************/
	/***********GETTER FOR SWING COMPONENTS***********/
	/**************************************************/
	
	/**
	 * Returns JTable model from a ResultSet
	 * @param rs (ResultSet)
	 * @return DefaultTableModel for JTable
	 */
	private DefaultTableModel getTableModel(ResultSet rs) {
		if(rs == null) {
			System.err.println("Error @getTableModel: No result set.");
			return null;
		}
		//Get Column Names
		tblHeader = getColumnLabels(rs);

		//Get Rows from Result Set
		tblRows = getAllRows(rs);

		return new DefaultTableModel((Object[][]) arrayListToArray(tblRows), (Object[]) arrayListToArray(tblHeader));
	}
	
	/**
	 * Returns JTable model from a ResultSet
	 * @param sql (String)
	 * @param binder (ArrayList)
	 * @return DefaultTableModel for JTable
	 */
	@SuppressWarnings("rawtypes")
	public DefaultTableModel getTableModel(String sql, ArrayList binder) {
		return getTableModel(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns JTable model from a ResultSet
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @return DefaultTableModel for JTable
	 */
	public DefaultTableModel getTableModel(String sql, Object[] binder) {
		return getTableModel(getRS(getPST(sql, binder)));
	}
	
	/**
	 * Returns JTable model from a ResultSet
	 * @param sql (String)
	 * @return DefaultTableModel for JTable
	 */
	public DefaultTableModel getTableModel(String sql) {
		return getTableModel(getRS(getPST(sql)));
	}

	/**
	 * Returns JComboBox model from an Object-type ArrayList
	 * Best to combine with getOneRow, getOneColumn, getColumnNames and getColumnLabels
	 * @param arr (Object-type ArrayList)
	 * @return DefaultComboBoxModel for JComboBox
	 */
	@SuppressWarnings({ "rawtypes"})
	public DefaultComboBoxModel getComboBoxModel(ArrayList arr) {
		return new DefaultComboBoxModel<>(arrayListToArray(arr));
	}
	
	/**
	 * Returns JComboBox model from an Object-type ArrayList
	 * Best to combine with getOneRow, getOneColumn, getColumnNames and getColumnLabels
	 * @param arr (Object-type array)
	 * @return DefaultComboBoxModel for JComboBox
	 */
	@SuppressWarnings({ "rawtypes"})
	public DefaultComboBoxModel getComboBoxModel(Object[] arr) {
		return new DefaultComboBoxModel<>(arr);
	}
	
	
	/**************************************************/
	/***********SETTER FOR SWING COMPONENTS***********/
	/**************************************************/
	
	/**
	 * Sets the JTable Model
	 * @param tbl (JTable)
	 * @param sql (String)
	 * @param binder (ArrayList)
	 */
	@SuppressWarnings("rawtypes")
	public void setTableModel(JTable tbl, String sql, ArrayList binder) {
		tbl.setModel(getTableModel(getRS(getPST(sql, binder))));
	}
	
	/**
	 * Sets the JTable Model
	 * @param tbl (JTable)
	 * @param sql (String)
	 * @param binder (Object-type array)
	 */
	public void setTableModel(JTable tbl, String sql, Object[] binder) {
		tbl.setModel(getTableModel(getRS(getPST(sql, binder))));
	}
	
	/**
	 * Sets the JTable Model
	 * @param tbl (JTable)
	 * @param sql (String)
	 * @param binder (ArrayList)
	 */
	public void setTableModel(JTable tbl, String sql) {
		tbl.setModel(getTableModel(getRS(getPST(sql))));
	}
	
	/**
	 * Sets the JComboBox Model
	 * @param cmb (JComboBox)
	 * @param sql (String)
	 * @param binder (ArrayList)
	 * @param columnName (String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setComboBoxModel(JComboBox cmb, String sql, ArrayList binder, String columnName) {
		cmb.setModel(getComboBoxModel(getOneColumn(sql, binder, columnName)));
	}
	
	/**
	 * Sets the JComboBox Model
	 * @param cmb (JComboBox)
	 * @param sql (String)
	 * @param binder (Object-type array)
	 * @param columnName (String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setComboBoxModel(JComboBox cmb, String sql, Object[] binder, String columnName) {
		cmb.setModel(getComboBoxModel(getOneColumn(sql, binder, columnName)));
	}
	
	/**
	 * Sets the JComboBox Model
	 * @param cmb (JComboBox)
	 * @param sql (String)
	 * @param binder (ArrayList)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setComboBoxModel(JComboBox cmb, String sql, ArrayList binder) {
		cmb.setModel(getComboBoxModel(getOneColumn(sql, binder)));
	}
	
	/**
	 * Sets the JComboBox Model
	 * @param cmb (JComboBox)
	 * @param sql (String)
	 * @param binder (Object-type array)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setComboBoxModel(JComboBox cmb, String sql, Object[] binder) {
		cmb.setModel(getComboBoxModel(getOneColumn(sql, binder)));
	}
	
	/**
	 * Sets the JComboBox Model
	 * @param cmb (JComboBox)
	 * @param sql (String)
	 * @param columnName (String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setComboBoxModel(JComboBox cmb, String sql, String columnName) {
		cmb.setModel(getComboBoxModel(getOneColumn(sql, columnName)));
	}
	
	/**
	 * Sets the JComboBox Model
	 * @param cmb (JComboBox)
	 * @param sql (String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setComboBoxModel(JComboBox cmb, String sql) {
		cmb.setModel(getComboBoxModel(getOneColumn(sql)));
	}
	
	/*	OPEN and CLOSE DATABASE CONNECTIONS
	 * 	Note: Before opening new connections,
	 * 	all previous connections must be closed.
	 * 	This is to prevent the database from being locked.
	 */
	
	abstract protected boolean connectionOpen();
	abstract protected void connectionTest();
	protected boolean connectionClose() {
		try {
			if (con != null) {
				con.close();
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			System.err.println("Error @connectionClose: "+e.getMessage());
			return false;
		}
	}
	
	/*
	 * STATIC METHODS (Converters, etc.)
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object[] arrayListToArray(ArrayList input) {
		if(input == null || input.isEmpty()) {
			return null;
		}
		if(input.get(0) instanceof ArrayList) { //check if multi-dimensional
			Object output[][] = new Object[input.size()][];
			for (int i = 0; i < input.size(); i++) {
				output[i] = ((ArrayList) input.get(i)).toArray(new Object[((ArrayList) input.get(i)).size()]);
			}
			return output;
		}else { //if not multi-dimensional
			return input.toArray(new Object[input.size()]);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void arrayListToArray(ArrayList input, Object[] output) {
		output = arrayListToArray(input);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static void arrayListToArray(ArrayList<ArrayList> input, Object[][] output) {
		output = (Object[][]) arrayListToArray(input);
	}
}