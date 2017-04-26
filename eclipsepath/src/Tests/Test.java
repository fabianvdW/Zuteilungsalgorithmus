package Tests;
import java.util.ArrayList;
import java.util.UUID;

import Algorithmus.Verteilungsalgorithmus;
import Data.AG;
import Data.Person;
import Data.Rating;
public class Test {

	public static void laufeTestsAufVerteilung(int anzTests) {
		for(int i=1;i<=anzTests;i++){
			String uuid= "";
			int random=(int)(Math.random()*500+5);
			uuid=UUID.randomUUID().toString();
			int agTeilnehmer=0;
			while(agTeilnehmer<random){
				int hoechstanzahl= (int) (Math.random()*(0.1*(double)random)+1);
				int mindestanzahl=(int) (Math.random()*0.4*(double) hoechstanzahl);
				System.out.println("Hoechst: " +hoechstanzahl);
				System.out.println("mindest: " + mindestanzahl);
				AG ag = new AG(agTeilnehmer,uuid,mindestanzahl,hoechstanzahl);
				uuid=new String("");
				uuid=UUID.randomUUID().toString();
				Verteilungsalgorithmus.ag.add(ag);
				agTeilnehmer+=hoechstanzahl;
			}
			for(int k=0;k<random;k++){
				ArrayList<Rating> ratings = new ArrayList<Rating>();
				int sum=0;
				for(int j=0;j<Verteilungsalgorithmus.ag.size();j++){
					if(j==0){
						int rand= (int) (Math.random()*7-3);
						System.out.println("rand: "+rand);
						sum+=rand;
						Rating r = new Rating(Verteilungsalgorithmus.ag.get(j), rand);
						ratings.add(r);
					}else{
						int rand= (int) (Math.random()*7-(sum+3));
						System.out.println("rand: "+rand);
						sum+=rand;
						Rating r= new Rating(Verteilungsalgorithmus.ag.get(j),rand);
						ratings.add(r);
					}
				}
				Person p= new Person(k, uuid,ratings );
				Verteilungsalgorithmus.personen.add(p);
				uuid=new String("");
				uuid=UUID.randomUUID().toString();
				
			}
		}
		
	}
}
