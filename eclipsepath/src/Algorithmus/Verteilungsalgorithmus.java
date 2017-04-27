package Algorithmus;

import java.util.ArrayList;

import Data.AG;
import Data.Person;
import Data.Rating;
import Tests.Test;

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
		Test.laufeTestsAufVerteilung(1);
		//verteile();
		//macheAusgabe();
	}

	/**
	 * Der eigentliche Verteilungsalgorithmus
	 */
	public static void verteile() {

	}

	/**
	 *	Beliebtheit	 
	 */
	public static void berechneBeliebtheit(){
		int i = 0;
		for(AG ags: ag){
			ags.setBeliebtheit(0);
			for(Person p: personen){
				ags.setBeliebtheit(ags.getBeliebtheit()+p.getRatingAL().get(i).getRatingValue());
			}
			i++;
			System.out.println(ags.getBeliebtheit());
		}
	}

	/**
	 * Kurze Ausgabe aller Objekte
	 */
	public static void statusCheck(){
		for(AG ags: ag){
			System.out.println(ags.toString());
		}
		for(Person p: personen){
			System.out.println(p.toString());
			
		}
	}


	/**
	 * 
	 * @return Score der Verteilung
	 */
	public static double checkScore() throws Exception{
		double score=0;
		for(Person p: personen){
			if(p.getBesuchteAG()==null){
				score+=Math.pow(7, 2);
			}else{
				int currRating=0;
				for(Rating r: p.getRatingAL()){
					if( r.getAG().equals(p.getBesuchteAG())){
						currRating=r.getRatingValue();
					}
				}
				score+=Math.pow(3-currRating, 2);
			}
		}
		return score/personen.size();
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
		System.out.println("Bester Score: 0 " + "\n"
				+ "Schlechtester Score "
				+("49"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		statusCheck();
		
	}

}
