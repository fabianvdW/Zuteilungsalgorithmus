package db;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Data.AG;
import Data.Person;
import Data.Rating;

public class DBManager {
	private DB db;
	private int profile;
	
	/**
	 * @author Agent77326
	 */
	public DBManager(){
		profile = 1;
	}
	
	/**
	 * @author Agent77326
	 * @param int profileNumber the ID of the profile when there are multiple tables on the same database
	 */
	public DBManager(int profileNumber){
		profile = profileNumber;
	}
	
	/**
	 * Connect to a server with a database
	 * @param String server
	 * @param int port
	 * @param String user
	 * @param String password
	 * @param String database
	 */
	public void connect(String server, int port, String user, String password, String database){
		db = new DB(server, port, user, password, database);
		db.query("CREATE TABLE IF NOT EXISTS `" + database + "`.`Personen" + profile + "`"
				+ "( `id` INT NOT NULL AUTO_INCREMENT ,"
				+ " `name` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Name des Schülers' ,"
				+ " `ratings` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'Wahl der AGs nach Reihenfolge' ,"
				+ " PRIMARY KEY (`id`)) ENGINE = InnoDB;");
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
	
	/**
	 * Reads all the data from the database with the given profile into data and returns it
	 * @param int profile the profile to read the data from
	 */
	public static void initializeJavaObjectsFromDB(String server, int port, String user, String password, String database, int profile){
		if(profile < 1){
			Logger lgr = Logger.getLogger(DBManager.class.getName());
			lgr.log(Level.SEVERE, "Given Profile-ID is invalid, using default");
			profile = 1;
		}
		DB db = new DB(server, port, user, password, database);
		ArrayList<Person> personen = new ArrayList<Person>();
		String[][] ids = db.query("SELECT `id` FROM `Personen" + profile + "`");
		for(String[] id: ids){
			personen.add(getPerson(Integer.parseInt(id[0])));
		}
	}
	
	/**
	 * Gibt ein Person-Objekt zurück mit allen Daten die es in der DB gibt
	 * @param String name des schülers
	 * @return Person das Objekt
	 */
	public Person getPerson(int id){
		String[][] p = db.query("SELECT * FROM `Personen" + profile + "` WHERE `id`='" + id + "'");
		//int i = p[1].length;
		/*
		ArrayList<Rating> rating = new ArrayList<Rating>();
		int pRating = -1;
		for(int i = 0; i < p[1].length; i++){
			if(p[0][i].equals("rating")){
				pRating = i;
				break;
			}
		}
		int posSubStr = 0;
		while(posSubStr != -1){
			posSubStr = p[1][pRating].indexOf(";;", 0);
			rating.add(new Rating(new AG(),1));
		}
		int pCurAG = -1;
		for(int i = 0; i < p[1].length; i++){
			if(p[0][i].equals("curAG")){
				pCurAG = i;
				break;
			}
		}
		if(pCurAG != -1){
			return new Person(name, null, getAG(p[1][pCurAG]));
		}*/
		int pName = -1;
		for(int i = 0; i < p[1].length; i++){
			if(p[0][i].equals("name")){
				pName = i;
				break;
			}
		}
		return new Person(id, p[1][pName], null);
	}
	
	/**
	 * Adds a person to the db
	 * @param Person p die Person die hinzuzufügen ist
	 */
	public void addPerson(Person p){
		String tmp = "";
		Rating[] t = new Rating[p.getRating().size()];
		t = p.getRating().toArray(t);
		for(Rating n: t){
			tmp += n.getAG() + ";;";
		}
		db.query("INSERT INTO `Personen" + profile + "` "
				+ "(`id`, `name`, `ratings`) "
				+ "VALUES (NULL, '" + p.getName() + "', '" + tmp + "')");
	}
	
	/**
	 * Gibt ein AG-Objekt zurück mit allen Daten die es in der DB gibt
	 * @param String name der AG
	 * @return AG das Objekt
	 */
	public AG getAG(String name){
		String[][] p = db.query("SELECT * FROM `AG" + profile + "` WHERE `name`='" + name + "'");
		int pMinAnzahl = -1;
		for(int i = 0; i < p[1].length; i++){
			if(p[0][i].equals("minAnzahl")){
				pMinAnzahl = i;
				break;
			}
		}
		int pMaxAnzahl = -1;
		for(int i = 0; i < p[1].length; i++){
			if(p[0][i].equals("maxAnzahl")){
				pMaxAnzahl = i;
				break;
			}
		}
		return new AG(name, Integer.parseInt(p[1][pMinAnzahl]), Integer.parseInt(p[1][pMaxAnzahl]));
	}
}
