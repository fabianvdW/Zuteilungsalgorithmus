package Algorithmus;

import java.util.ArrayList;

import Data.AG;
import Data.Person;
import db.DBManager;

public class Verteilungsalgorithmus {
	public static ArrayList<AG> ag;
	public static ArrayList<Person> personen;

	/**
	 * 
	 * Initialisiert alle Objekte aus MYSQL. Startet Verteilung Macht Ausgabe
	 * 
	 * @param args
	 *            Argumente.
	 */
	public static void main(String[] args) {
		ag = new ArrayList<AG>();
		personen = new ArrayList<Person>();
		DBManager dbm = new DBManager();
		dbm.connect("agent77326.tk", 3306, "fabi", "4ma9vJdZUH7J70Wh", "fabi");
		dbm.initializeJavaObjectsFromDB();
		verteile();
		macheAusgabe();
	}

	/**
	 * Der eigentliche Verteilungsalgorithmus
	 */
	public static void verteile() {

	}

	/**
	 * 
	 * @return Score der Verteilung
	 */
	public static double checkScore() throws Exception{
		double score=0;
		int[] ratings = new int[ag.size()];
		for(Person p:personen){
			if(p.getBesuchteAG()==null){
				//throw new Exception("Der Schüler " + p.toString() + " hat keine AG");
				score+=ag.size()+1;
				continue;
			}
			for(int i=0;i<ag.size();i++){
				if(p.getBesuchteAG().equals(p.getRating().get(i))){
					ratings[i]+=1;
				}
			}
			
		}
		for(int i=1;i<ag.size()+1;i++){
			score+=ratings[i-1]*i;
		}
		score/=(double)personen.size();
		return score;
	}

	/**
	 * Macht Ausgabe
	 */
	public static void macheAusgabe() {
		System.out.println("Die Zuteilung ist erfolgreich fertig!");
		System.out.println("Folgende Personen sind in folgenden AGen:");
		System.out.println("----------------------------------------------------------");
		ArrayList<AG> findenNichtStatt = new ArrayList<AG>();

		for (AG ags : ag) {
			ags.finishEintragung();
			if (!ags.kannStattFinden()) {
				findenNichtStatt.add(ags);

				continue;
			}
			System.out.println("In der AG " + ags.getName() + " sind folgende Personen: " + "("
					+ ags.getTeilnehmer().size() + "/" + ags.getHoechstanzahl() + ")Teilnehmer~"
					+ ((double) ags.getTeilnehmer().size()) / ags.getHoechstanzahl() + "%");
			ArrayList<Person> teilnehmer = ags.getTeilnehmer();
			for (Person p : teilnehmer) {
				System.out.println(p.getName());
			}

			System.out.println("----------------------------------------------------------");
		}

		System.out.println("Folgende AGen können nicht stattfinden:");

		for (AG ags : findenNichtStatt) {
			System.out.println("Die AG " + ags.getName() + " kann nicht stattfinden.");
			System.out.println("");
		}
		System.out.println("----------------------------------------------------------");
		System.out.println("Folgende Personen haben keine AG:");
		for (Person p : personen) {
			if (p.getBesuchteAG() == null)
				System.out.println(p.getName());
		}
		System.out.println("----------------------------------------------------------");
		try{
		System.out.println("Damit hat der Algorithmus einen Score von:" + checkScore());
		System.out.println("Bester Score: 1 " + "\n"
				+ "Schlechtester Score "
				+(ag.size()+1)+"");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/*for(AG ags: ag){
			System.out.println(ags.toString());
		}
		for(Person p: personen){
			System.out.println(p.toString());
			
		}*/
		
	}

}
