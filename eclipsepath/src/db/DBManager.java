package db;

import Data.Person;
import Data.AG;

public class DBManager {
	private DB db;
	private int profile;
	
	/**
	 * @author Agent77326
	 */
	public DBManager(){
		
	}
	
	/**
	 * Get a DB-handle to edit the tables in the db
	 * @param String server
	 * @param int port
	 * @param String user
	 * @param String password
	 * @param String database
	 */
	public void connect(String server, int port, String user, String password, String database){
		db = new DB(server, port, user, password, database);
	}
	
	/**
	 * Closes the current connection to the db and is now able to connect to a new one with connect()
	 */
	public void close(){
		if(db!=null){
			db.close();
		}
	}
	
	/**
	 * Set the current profile
	 * @param int Profile-ID
	 */
	public void setProfile(int profile){
		this.profile = profile;
	}
	
	/**
	 * get the current profile
	 * @return int Profile-ID
	 */
	public int getProfile(){
		return profile;
	}
	
	public static void initializeJavaObjectsFromDb(){
		
	}
	
	public Person getPerson(int id){
		String[][] p = db.query("SELECT * FROM `Personen" + profile + "` WHERE `id`='" + id + "'");
		return null;
	}
}
