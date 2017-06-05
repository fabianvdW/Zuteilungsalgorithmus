package Neuro;

import java.util.Random;

public class Neuron {
	protected double weights[];
	protected double bias;
	protected double netH;
	protected double output;
	public Neuron(int anzW){
		weights= new double[anzW];
		Random r= new Random();
		bias= r.nextGaussian();
		for(int i = 0; i < weights.length; i++){
			weights[i]= r.nextGaussian();
		}
	}
}
