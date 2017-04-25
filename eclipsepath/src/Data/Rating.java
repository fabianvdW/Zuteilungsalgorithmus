package Data;

public class Rating {
	private AG bewerteteAG;
	private int rating;
	public Rating(AG bewerteteAG, int rating){
		this.bewerteteAG=bewerteteAG;
		this.rating=rating;
	}
	public AG getAG(){
		return this.bewerteteAG;
	}
	public int getRating(){
		return this.rating;
	}
}
