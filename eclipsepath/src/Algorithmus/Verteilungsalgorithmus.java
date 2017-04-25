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
	 * Initialisiert alle Objekte aus MYSQL.
	 * Startet Verteilung
	 * Macht Ausgabe
	 * @param args Argumente.
	 */
	public static void main(String[] args){
		ag = new ArrayList<AG>();
		personen= new ArrayList<Person>();
		DBManager dbm = new DBManager();
		dbm.connect("agent77326.tk", 3306, "fabi", "4ma9vJdZUH7J70Wh", "fabi");
		dbm.initializeJavaObjectsFromDB();
		verteile();
		macheAusgabe();
	}
	/**
	 * Der eigentliche Verteilungsalgorithmus
	 */
	public static void verteile(){
		
	}
	/**
	 * 
	 * @return Score der Verteilung
	 */
	public static int checkScore(){
		if(personen.size()==0) return 0;
		int score =0;
		int[] agen= new int[ag.size()+1];
		for(Person p: personen){
			if(p.getBesuchteAG()==null){
				agen[ag.size()]+=1;
			}else{
				for(int i=0;i<p.getRating().size();i++){
					if(p.getRating().get(i).equals(p.getBesuchteAG())){
						agen[i]+=1;
					}
				}
			}
		}
		for(int i=0;i<agen.length;i++){
			score+=((i+1)*agen[i])/personen.size();
		}
		return score;
	}
	/**
	 * Macht Ausgabe
	 */
	public static void macheAusgabe(){
		System.out.println("Die Zuteilung ist erfolgreich fertig!");
		System.out.println("Folgende Personen sind in folgenden AGen:");
		System.out.println("----------------------------------------------------------");
		ArrayList<AG> findenNichtStatt = new ArrayList<AG>();
		
		for(AG ags: ag){
			ags.finishEintragung();
			if(!ags.kannStattFinden()){
				findenNichtStatt.add(ags);
				
				continue;
			}
			System.out.println("In der AG "+ ags.getName()+" sind folgende Personen: " +"("+ags.getTeilnehmer().size()+"/"+ags.getHoechstanzahl()+")Teilnehmer~"+((double) ags.getTeilnehmer().size())/ ags.getHoechstanzahl()+"%");
			ArrayList<Person> teilnehmer= ags.getTeilnehmer();
			for(Person p: teilnehmer){
				System.out.println(p.getName());
			}
			
			System.out.println("----------------------------------------------------------");
		}
		
		System.out.println("Folgende AGen können nicht stattfinden:");
		
		for(AG ags: findenNichtStatt){
			System.out.println("Die AG "+ ags.getName()+" kann nicht stattfinden.");
			System.out.println("");
		}
		System.out.println("----------------------------------------------------------");
		System.out.println("Folgende Personen haben keine AG:");
		for(Person p: personen){
			if(p.getBesuchteAG()==null)
			System.out.println(p.getName());
		}
		System.out.println("----------------------------------------------------------");
		System.out.println("Damit hat der Algorithmus einen Score von:"+checkScore());
	}
	
}
