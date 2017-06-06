package Neuro;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

//THIS IS A TEST (leo)
//---------------------------------------------------------------------------------------------------------------------------------------
public class Network2{
	private ArrayList<Neuron[]> network;
	private boolean debug = true;
	private Writer log;
	
	private void log(String txt){
		try{
			log.write(txt + " \n");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public Network2(int[] layers){
		try{
			log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("logNeuro.txt"), "utf-8"));
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		// init network
		network = new ArrayList<Neuron[]>();
		for(int i = 0; i < layers.length; i++){
			Neuron[] layer = new Neuron[layers[i]];
			for(int n = 0; n < layers[i]; n++){
				layer[n] = new Neuron((i + 1 == layers.length) ? 0 : layers[i + 1]);
			}
			network.add(layer);
		}
		if(debug){
			log("Network:");
			for(int i = 0; i < network.size(); i++){
				log("	" + i + "-Layer");
				for(int n = 0; n < network.get(i).length; n++){
					Neuron a = network.get(i)[n];
					log("		" + n + "-Neuron");
					for(int m = 0; m < a.weights.length; m++){
						log("			W" + m + "=" + a.weights[m]);
					}
				}
			}
		}
	}
	
	protected void train(int epochs, MNISTdata[] data, double learnRate){
		for(int i = 0; i < epochs; i++){
			double totalError = 0;
			for(MNISTdata train: data){
				output(train.img);
				
				//prepare solution
				double[] solution = new double[10];
				for(int n = 0; n < 10; n++){
					solution[n] = 0;
					if(train.solution == n){
						solution[n] = 1;
					}
				}
				
				//calc error
				for(int n = 0; n < solution.length; n++){
					totalError += solution[n] - network.get(network.size() - 1)[n].output;
				}
				
				//here the magic is happening
				backPropagate(solution);
				updateWeigths(train.img, learnRate);
			}
			log("Epoch " + (i + 1) + "/" + epochs + ";	Learn=" + learnRate + ";	Error=" + totalError);
		}
	}
	
	/**
	 * errechnet den output
	 * @param input
	 */
	private void output(double[] input){
		if(debug){
			log("-------------------------------------------------------------------------------------------------------------------");
			String tmp = " {";
			for(double s: input){
				tmp += s + ", ";
			}
			log("output with: total=" + input.length + tmp + "}");
		}
		// set inputs as first layers' output
		for(int i = 0; i < network.get(0).length; i++){
			network.get(0)[i].output = input[i];
		}
		
		// feedforward
		for(int layer = 1; layer < network.size() - 1; layer++){
			for(int neuron = 0; neuron < network.get(layer).length; neuron++){
				double activation = 0;
				for(int lastNeuron = 0; lastNeuron < network.get(layer - 1).length; lastNeuron++){
					activation += network.get(layer - 1)[lastNeuron].weights[neuron] * network.get(layer - 1)[lastNeuron].netH;
				}
				network.get(layer)[neuron].output = Maths.sigmoid(activation);
			}
		}
	}
	
	/**
	 * errechnet delta für jedes neuron
	 * @param solution
	 */
	private void backPropagate(double[] solution){
		if(debug){
			log("-------------------------------------------------------------------------------------------------------------------");
			String tmp = " {";
			for(double s: solution){
				tmp += s + ", ";
			}
			log("backPropagate with: total=" + solution.length + tmp + "}");
		}
		for(int layer = network.size() - 1; layer >= 0; layer--){
			ArrayList<Double> errors = new ArrayList<Double>();
			if(debug){
				log("	" + layer + "-Layer");
			}
			// check output layer
			if(layer == network.size() - 1){
				for(int neuron = 0; neuron < network.get(layer).length; neuron++){
					errors.add(solution[neuron] - network.get(layer)[neuron].output);
				}
			}
			else{
				for(int neuron = 0; neuron < network.get(layer).length; neuron++){
					double error = 0;
					for(int weigth = 0; weigth < network.get(layer)[neuron].weights.length; weigth++){
						error += network.get(layer)[neuron].weights[weigth] * network.get(layer)[neuron].output;
					}
					errors.add(error);
				}
			}
			
			// calc delta
			for(int neuron = 0; neuron < network.get(layer).length; neuron++){
				if(debug){
					log("		" + neuron + "-Neuron");
					log("			error=" + errors.get(neuron));
					log("			output=" + network.get(layer)[neuron].output);
				}
				network.get(layer)[neuron].delta = errors.get(neuron) * Maths.sigmoidPrime(network.get(layer)[neuron].output);
				if(debug){
					log("			delta=" + network.get(layer)[neuron].delta);
				}
			}
		}
	}
	
	/**
	 * passt die Weigths an
	 * @param input
	 * @param learnRate
	 */
	private void updateWeigths(double[] input, double learnRate){
		if(debug){
			log("-------------------------------------------------------------------------------------------------------------------");
			String tmp = " {";
			for(double s: input){
				tmp += s + ", ";
			}
			log("updateWeigths with: learnRate=" + learnRate + " total=" + input.length + tmp + "}");
		}
		for(int layer = 1; layer < network.size(); layer++){
			double[] inputs;
			if(debug){
				log("	" + layer + "-Layer");
			}
			
			// check if first layer
			if(layer == 0){
				inputs = input;
			}
			else{
				inputs = new double[network.get(layer - 1).length];
				for(int neuron = 0; neuron < network.get(layer - 1).length; neuron++){
					inputs[neuron] = network.get(layer - 1)[neuron].output;
				}
			}
			if(debug){
				String tmp = " {";
				for(double s: inputs){
					tmp += s + ", ";
				}
				log("	Inputs: total=" + inputs.length + tmp + "}");
			}
			
			// hier passiert die MAGIE
			for(int neuron = 0; neuron < network.get(layer).length; neuron++){
				if(debug){
					log("		Zu " + neuron + "-Neuron mit delta=" + network.get(layer)[neuron].delta);
				}
				for(int lastNeuron = 0; lastNeuron < network.get(layer - 1).length; lastNeuron++){
					if(debug){
						log("			Von " + lastNeuron + "-Neuron");
						log("				Old W=" + network.get(layer - 1)[lastNeuron].weights[neuron]);
					}
					network.get(layer - 1)[lastNeuron].weights[neuron] += learnRate * network.get(layer)[neuron].delta * inputs[lastNeuron];
					if(debug){
						log("				New W=" + network.get(layer - 1)[lastNeuron].weights[neuron]);
					}
				}
			}
		}
	}
}
//---------------------------------------------------------------------------------------------------------------------------------------
//End of test