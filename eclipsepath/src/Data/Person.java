/*
 * Copyright (c) 2017 Von Der Warth Fabian, Jung Leo, Pfenning Jan & Selig Leon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
	public ArrayList<Rating> getRatingAL(){
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
	 * Gibt das Objekt mit allen Attributen als String zurueck
	 * @return Objekt als String
	 */
	public String toString(){
		Object[] r = null;
		if(ratings!=null){
		r= ratings.toArray();
		}
		String ret = "{Class:Person, "
				+ "id:" + id + ", "
				+ "name:" + name + ", "
				+ "ag:" + (besuchtAG==null? null:besuchtAG.getName()) + ", "
				+ "gewählt:[";
		if(r!=null){
		for(Object a: r){
			ret += a.toString();
		}
		}
		return ret +"]}";
		
	}
}
