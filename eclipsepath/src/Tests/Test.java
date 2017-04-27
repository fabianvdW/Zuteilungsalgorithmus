/*
 * This content is released under the MIT License (MIT)
 * 
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
package Tests;
import java.util.ArrayList;
import java.util.UUID;

import Algorithmus.Verteilungsalgorithmus;
import Data.AG;
import Data.Person;
import Data.Rating;
public class Test {

	public static void laufeTestsAufVerteilung(int anzTests) {
		double score=0;
		for(int i=1;i<=anzTests;i++){
			Verteilungsalgorithmus.ag.clear();
			Verteilungsalgorithmus.personen.clear();
			String uuid= "";
			int random=(int)(Math.random()*150+50);
			uuid=UUID.randomUUID().toString();
			int agTeilnehmer=0;
			while(agTeilnehmer<random){	
				int hoechstanzahl= (int) (Math.random()*(0.2*(double)random)+1);
				int mindestanzahl=0;
				do{
					mindestanzahl=(int) (Math.random()*0.6*(double) hoechstanzahl);
				}while(mindestanzahl==0);
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
						
						sum+=rand;
						Rating r = new Rating(Verteilungsalgorithmus.ag.get(j), rand);
						ratings.add(r);
					}else{
						int rand=0;
						if(sum>0){
							rand= (int) (Math.random()*3-3);
						}else{
							if(k==random-1){
								rand=-sum;
							}else{
								rand=(int) (Math.random()*4);
							}
						}
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
			Verteilungsalgorithmus.verteile();
			Verteilungsalgorithmus.macheAusgabe();
			try{
			score+=Verteilungsalgorithmus.checkScore();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("Nach "+anzTests+" Versuchen erhält der Algorithmus eine durschnittliche Punktzahl von " +score/anzTests);
	}
}
