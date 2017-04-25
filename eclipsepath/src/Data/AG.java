package Data;
import java.util.ArrayList;
public class AG {
	private String name;
	private int mindestanzahl;
	private int hoechstanzahl;
	private ArrayList<Person> teilnehmer;
	public AG(String name, int mindestanzahl, int hoechstanzahl){
		this.name=name;
		this.mindestanzahl=mindestanzahl;
		teilnehmer= new ArrayList<Person>();
		this.hoechstanzahl=hoechstanzahl;
	}
	
	public void addTeilnehmer(Person perso){
		teilnehmer.add(perso);
	}
	public ArrayList<Person> getTeilnehmer(){
		return this.teilnehmer;
	}
	public String getName(){
		return this.name;
	}
	public int getMindestanzahl(){
		return this.mindestanzahl;
	}
	public int getHoechstanzahl(){
		return this.hoechstanzahl;
	}
}
