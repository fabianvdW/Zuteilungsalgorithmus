package db;

public class DBManager {
	
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
	 * @return DB (the handle)
	 */
	public DB connect(String server, int port, String user, String password, String database){
		return new DB(server, port, user, password, database);
	}
	
	public static void initializeJavaObjectsFromDb(){
		
	}
}
