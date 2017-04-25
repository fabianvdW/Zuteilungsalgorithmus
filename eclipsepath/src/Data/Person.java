package Data;

import java.util.ArrayList;

public class Person {
	private String name;
	private ArrayList<Rating> ratings;
	private AG besuchtAG;
	
	/**
	 * Objekt zum speichern einer Person
	 * @param name Der Name der Person
	 * @param ratings Seine AGs bewertet, 0.Element die beste AG, 1.Element die zweit beste usw.
	 */
	public Person(String name, ArrayList<Rating> ratings){
		this.name=name;
		this.ratings=ratings;
		ratings= new ArrayList<Rating>();
	}
	
	/**
	 * 
	 * @return Name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * 
	 * @return Das Rating
	 */
	public ArrayList<Rating> getRating(){
		return this.ratings;
	}
	
	/**
	 * 
	 * @param ag Wird der AG zugewiesen
	 */
	public void teileAGZu(AG ag){
		this.besuchtAG=ag;
	}
	
	/**
	 * 
	 * @return Return die aG, die die Person besucht.
	 */
	public AG getBesuchteAG(){
		return this.besuchtAG;
	}
}
