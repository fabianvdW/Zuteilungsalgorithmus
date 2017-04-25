package Data;
import java.util.ArrayList;

public class AG {
	private String name;
	private int mindestanzahl;
	private int hoechstanzahl;
	private boolean istVoll;
	private boolean kannStattFinden;
	private ArrayList<Person> teilnehmer;
	private int id;

	/**
	 * Klasse AG, Verwaltet die Daten einer AG
	 * @param name Name der AG
	 * @param mindestanzahl Mindestanzahl, damit die AG stattfinden kann
	 * @param hoechstanzahl Hoechste Anzahl an Personen, die die AG besuchen dürfen
	 */
	public AG(String name, int mindestanzahl, int hoechstanzahl){
		this.name=name;
		this.mindestanzahl=mindestanzahl;
		teilnehmer= new ArrayList<Person>();
		this.hoechstanzahl=hoechstanzahl;
	}
	
	/**
	 * Klasse AG, Verwaltet die Daten einer AG
	 * @param id ID der AG
	 * @param name Name der AG
	 * @param mindestanzahl Mindestanzahl, damit die AG stattfinden kann
	 * @param hoechstanzahl Hoechste Anzahl an Personen, die die AG besuchen dürfen
	 */
	public AG(int id, String name, int mindestanzahl, int hoechstanzahl){
		this(name,mindestanzahl,hoechstanzahl);
		this.id=id;
	}
	
	/**
	 * Klasse AG, Verwaltet die Daten einer AG
	 * @param id ID der AG
	 * @param name Name der AG
	 * @param mindestanzahl Mindestanzahl, damit die AG stattfinden kann
	 * @param hoechstanzahl Hoechste Anzahl an Personen, die die AG besuchen dürfen
	 * @param teilnehmer falls personen von beginn an dabei sind
	 */
	public AG(int id, String name, int mindestanzahl, int hoechstanzahl, ArrayList<Person> teilnehmer){
		this(id,name,mindestanzahl,hoechstanzahl);
		this.teilnehmer= teilnehmer;
	}
	
	/**
	 * Gibt die ID der AG zurück
	 * @return ID
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Ein Teilnehmer wird einer AG zugewiesen. Dabei wird auch die Referenz in Person geändert, welche AG der Teilnehmer besucht.
	 * @param perso Die Person die die AG besuchen soll
	 * @throws Exception Throws Exception wenn die AG bereits voll ist.
	 */
	public void addTeilnehmer(Person perso) throws Exception{
		if(!istVoll){
			teilnehmer.add(perso);
			perso.teileAGZu(this);
		}else{
			throw new Exception("Diese Ag ist bereits voll!");
		}
		if(this.teilnehmer.size()==hoechstanzahl){
			istVoll=true;
		}
		if(this.teilnehmer.size()>=mindestanzahl){
			kannStattFinden=true;
		}
	}
	
	/**
	 * Removet ein Teilnehmer von einer AG.
	 * @param perso Die Person, die die AG nicht mehr besuchen soll
	 * @throws Exception Wenn die Person, die removt werden soll nicht die AG besucht.
	 */
	public void removeTeilnehmer(Person perso) throws Exception{
		if(teilnehmer.contains(perso)){
			teilnehmer.remove(perso);
			perso.teileAGZu(null);
			if(teilnehmer.size()<hoechstanzahl){
				istVoll=false;
			}
			if(teilnehmer.size()<mindestanzahl){
				kannStattFinden=false;
			}
		}else{
			throw new Exception("Dieser Teilnehmer ist nicht in der AG!");
		}
	}
	
	/**
	 * Returnt Teilnehmer
	 * @return Teilnehmer
	 */
	public ArrayList<Person> getTeilnehmer(){
		return this.teilnehmer;
	}
	
	/**
	 * Return Name
	 * @return Name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Return Mindestanzahl an Teilnehmern, damit die AG stattfinden kann.
	 * @return Mindestanzahl an Teilnehmern
	 */
	public int getMindestanzahl(){
		return this.mindestanzahl;
	}
	
	/**
	 * Return hoechste Anzahl an Teilnehmern
	 * @return Höchstanzahl
	 */
	public int getHoechstanzahl(){
		return this.hoechstanzahl;
	}
	
	/**
	 * 
	 * @return Return ob die AG bereits voll ist.
	 */
	public boolean istVoll(){
		return this.istVoll;
	}
	
	/**
	 * 
	 * @return Ob die AG statt finden kann.
	 */
	public boolean kannStattFinden(){
		return this.kannStattFinden;
		
	}
	
	/**
	 * Beendet die Eintragung. Wenn die AG nicht stattfinden kann, werden alle Teilnehmer removet.
	 * @return alle Teilnehmer die von der AG removt werden.
	 */
	public ArrayList<Person> finishEintragung(){
		ArrayList<Person> austragung= new ArrayList<Person>();
		if(!kannStattFinden){
			for(Person p: teilnehmer){
				p.teileAGZu(null);
				austragung.add(p);
				
			}
			teilnehmer.clear();
		}
		return austragung;
	}
}
