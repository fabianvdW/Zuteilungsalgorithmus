package db;

import java.util.ArrayList;

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
	
	/**
	 * Gibt ein Person-Objekt zurück mit allen Daten die es in der DB gibt
	 * @param String name des schülers
	 * @return Person das Objekt
	 */
	public Person getPerson(String name){
		String[][] p = db.query("SELECT * FROM `Personen" + profile + "` WHERE `name`='" + name + "'");
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
		}*/
		int pCurAG = -1;
		for(int i = 0; i < p[1].length; i++){
			if(p[0][i].equals("curAG")){
				pCurAG = i;
				break;
			}
		}
		if(pCurAG != -1){
			return new Person(name, null, getAG(p[1][pCurAG]));
		}
		return new Person(name, null);
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
