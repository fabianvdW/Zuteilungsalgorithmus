package db;

public class DBManager {
	
	/**
	 * @author Agent77326
	 */
	public DBManager(){
		
	}
	
	public DB connect(String server, int port, String user, String password, String database){
		return new DB(server, port, user, password, database);
	}
  
	public static void initializeJavaObjectsFromDb(){
		
	}
}
