package Data;

import java.util.ArrayList;

public class Person {
	private String name;
	private ArrayList<Rating> ratings;
	public Person(String name, ArrayList<Rating> ratings){
		this.name=name;
		this.ratings=ratings;
		ratings= new ArrayList<Rating>();
	}
	public String getName(){
		return this.name;
	}
	public ArrayList<Rating> getRating(){
		return this.ratings;
	}
}
