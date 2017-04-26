package Data;

import java.util.ArrayList;

public class Person {
	private String name;
	private ArrayList<Rating> ratings;
	private AG besuchtAG;
	private int id;

	/**
	 * Objekt zum speichern einer Person
	 * @param name Der Name der Person
	 * @param ratings Seine AGs bewertet, 0.Element die beste AG, 1.Element die zweit beste usw.
	 */
	public Person(String name, ArrayList<Rating> ratings){
		this.name=name;
		this.ratings=ratings;
	}

	/**
	 * Objekt zum speichern einer Person
	 * @param id Die eindeutige ID einer Person (wird von der DB zugewiesen)
	 * @param name Der Name der Person
	 * @param ratings Seine AGs bewertet, 0.Element die beste AG, 1.Element die zweit beste usw.
	 */
	public Person(int id, String name, ArrayList<Rating> ratings){
		this(name,ratings);
		this.id=id;
		
	}
	
	/**
	 * 
	 * @return ID
	 */
	public int getId(){
		return id;
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
	
	/**
	 * Gibt das Objekt mit allen Attributen als String zur�ck
	 * @return Objekt als String
	 */
	@Override
	public String toString(){
		Object[] r = null;
		if(ratings!=null){
		r= ratings.toArray();
		}
		String ret = "{Class:Person, "
				+ "id:" + id + ", "
				+ "name:" + name + ", "
				+ "ag:" + besuchtAG + ", "
				+ "gewählt:[";
		if(r!=null){
		for(Object a: r){
			ret += a.toString();
		}
		}
		return ret +"]}";
		
	}
}
