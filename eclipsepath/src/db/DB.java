package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
	protected Connection con;

	/**
	 * Class for managed connections and interactions with a given server
	 * @author Agent77326
	 * @param String the server-address e.g: example.com
	 * @param int the server-port mySQL uses: default is 3306
	 * @param String the user to login
	 * @param String the password to authenticate
	 * @param String the database to connect to
	 */
	protected DB(String server, int port, String user, String password, String database){
		try{
			try{
				Class.forName("com.mysql.jdbc.Driver");
			}
			catch(ClassNotFoundException e){
				Logger lgr = Logger.getLogger(DB.class.getName());
				lgr.log(Level.WARNING, e.getMessage(), e);
			}
			con = DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database + "?useSSL=true", user, password);
		}
		catch(SQLException e){
			Logger lgr = Logger.getLogger(DB.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Gibt die Anzahl der Datensätze für die gegebene Tabelle zurück
	 * @param String table der name der Tabelle
	 * @return int die Größe
	 */
	protected int getSize(String table){
		return query("SELECT `id` FROM `" + table + "`")[0].length - 1;
	}
	
	/**
	 * Use this for updating, inserting and deleting data, if you only want to read use query();
	 * @param String sql the statement with wildcards to fill with the data
	 * 		e.g: "insert into  database.table values (default, ?, ?, ?, ?, ?, ?)"
	 * @param String[] data the values here will be inserted into the wildcards at the sql-statement
	 */
	protected void update(String sql, String[] data){
		PreparedStatement ps = null;
		try{
			ps = con.prepareStatement(sql);
			for(int i = 0; i < data.length; i++){
				ps.setString(i + 1, data[i]);
			}
			ps.executeUpdate();
		}
		catch(SQLException e){
			Logger lgr = Logger.getLogger(DB.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		finally{
			try{
				if(ps != null){
					ps.close();
	            }
	        }
			catch(SQLException e){
				Logger lgr = Logger.getLogger(DB.class.getName());
				lgr.log(Level.SEVERE, e.getMessage(), e);
	        }
		}
	}
	
	/**
	 * Generates a request to the server by executing the given query
	 * @param String query the request as sql-statement
	 * @return ResultSet the returned data
	 */
	protected String[][] query(String sql){
		try{
			Statement st = con.createStatement();
			ResultSet tmp = st.executeQuery(sql);
			try{
				st.close();
			}
			catch(SQLException e){
				Logger lgr = Logger.getLogger(DB.class.getName());
				lgr.log(Level.SEVERE, e.getMessage(), e);
			}
			return fetch(tmp);
		}
		catch(SQLException e){
			Logger lgr = Logger.getLogger(DB.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Generates a request to the server by executing the given query
	 * @param PreparedStatment query the request given as prepared
	 * @return ResultSet the returned data
	 */
	protected String[][] query(PreparedStatement sql){
		try{
			ResultSet tmp = sql.executeQuery();
			try{
				sql.close();
			}
			catch(SQLException e){
				Logger lgr = Logger.getLogger(DB.class.getName());
				lgr.log(Level.SEVERE, e.getMessage(), e);
			}
			return fetch(tmp);
		}
		catch(SQLException e){
			Logger lgr = Logger.getLogger(DB.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Fetches the given data from a ResultSet into a 2-dimensional Array of Strings.
	 * IMPORTANT! the first row is filled with the names of the columns
	 * @param ResultSet the return of a query to the sql-server
	 * @return String[][] with all data. 1.dim is each row and 2.dim is each column
	 */
	private String[][] fetch(ResultSet rs){
		String[][] data = null;
		try {
			int i = 0;
			do{
				i++;
			}
			while(rs.next());
			rs.first();
			int n = rs.getMetaData().getColumnCount();
			data = new String[i + 1][n];
			for(int j = 0; j < n; j++){
				data[0][j] = rs.getMetaData().getColumnName(j + 1);
			}
			i = 1;
			do{
				for(int j = 0; j < n; j++){
					data[i][j] = rs.getString(j + 1);
				}
			}
			while(rs.next());
		}
		catch (SQLException e){
			Logger lgr = Logger.getLogger(DB.class.getName());
			lgr.log(Level.WARNING, e.getMessage(), e);
		}
		finally {
			try{
				rs.close();
			}
			catch(SQLException e){
				Logger lgr = Logger.getLogger(DB.class.getName());
				lgr.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return data;
	}
	
	/**
	 * This must be run after the DB is not used any more to release the used connection
	 */
	public void close(){
		if(con!=null){
			try{
				con.close();
			}
			catch(SQLException e){
				Logger lgr = Logger.getLogger(DB.class.getName());
				lgr.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
}
