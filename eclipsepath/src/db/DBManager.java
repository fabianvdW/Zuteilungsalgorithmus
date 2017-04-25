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
		if(profile < 1){
			Logger lgr = Logger.getLogger(DBManager.class.getName());
			lgr.log(Level.SEVERE, "Given Profile-ID is invalid, using default");
			profile = 1;
		}
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
		db.update("CREATE TABLE IF NOT EXISTS `" + database + "`.`Personen" + profile + "`"
				+ "( `id` INT NOT NULL AUTO_INCREMENT ,"
				+ " `name` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Name des Schülers' ,"
				+ " `ratings` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'Wahl der AGs nach Reihenfolge' ,"
				+ " PRIMARY KEY (`id`)) ENGINE = InnoDB");
		db.update("CREATE TABLE IF NOT EXISTS`" + database + "`.`AG" + profile + "` "
				+ "( `id` INT NOT NULL AUTO_INCREMENT , "
				+ "`name` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Name der AG' , "
				+ "`minAnzahl` INT NOT NULL COMMENT 'Mindest-Anzahl der Teilnehmer' , "
				+ "`maxAnzahl` INT NOT NULL COMMENT 'Maximale-Anzahl der Teilnehmer' , "
				+ "`member` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Schüler die an dieser AG teilnehmen' , "
				+ "PRIMARY KEY (`id`)) ENGINE = InnoDB");
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
	public void initializeJavaObjectsFromDB(){
		String[][] ids = db.query("SELECT `id` FROM `Personen" + profile + "`");
		boolean first = true;
		for(String[] id: ids){
			if(first || id[0]==null || id[0].equals("")){
				first = false;
				continue;
			}
			Algorithmus.Verteilungsalgorithmus.personen.add(getPerson(Integer.parseInt(id[0])));
		}
		ids = db.query("SELECT `id` FROM `AG" + profile + "`");
		first = true;
		for(String[] id: ids){
			if(first || id[0]==null || id[0].equals("")){
				first = false;
				continue;
			}
			Algorithmus.Verteilungsalgorithmus.ag.add(getAG(Integer.parseInt(id[0])));
		}
	}
	
	/**
	 * Gibt ein Person-Objekt zurück mit allen Daten die es in der DB gibt
	 * @param String name des schülers
	 * @return Person das Objekt
	 */
	public Person getPerson(int id){
		String[][] p = db.query("SELECT * FROM `Personen" + profile + "` WHERE `id`='" + id + "'");
		int pRating = -1;
		for(int i = 0; i < p[1].length; i++){
			if(p[0][i].equals("ratings")){
				pRating = i;
				break;
			}
		}
		ArrayList<Rating> rating = new ArrayList<Rating>();
		int j = 1;
		for(String n: p[1][pRating].split(",")){
			if(n==null || n.equals("")){
				continue;
			}
			rating.add(new Rating(getAG(Integer.parseInt(n)),j++));
		}
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
	public AG getAG(int id){
		String[][] p = db.query("SELECT * FROM `AG" + profile + "` WHERE `id`='" + id + "'");
		int pName = -1;
		for(int i = 0; i < p[1].length; i++){
			if(p[0][i].equals("name")){
				pName = i;
				break;
			}
		}
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
		return new AG(id, p[1][pName], Integer.parseInt(p[1][pMinAnzahl]), Integer.parseInt(p[1][pMaxAnzahl]));
	}
	
	public void addAG(AG ag){
		db.query("INSERT INTO `AG" + profile + "` "
				+ "(`id`, `name`, `minAnzahl`, `maxAnzahl`, `member`) "
				+ "VALUES (NULL, '" + ag.getName() + "', '" + ag.getMindestanzahl() + "', '" + ag.getHoechstanzahl() + "', '')");
	}
}
