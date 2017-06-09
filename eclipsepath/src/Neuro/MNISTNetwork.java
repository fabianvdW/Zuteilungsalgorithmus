package Neuro;

import java.util.ArrayList;

public class MNISTNetwork extends Network {
	
	public MNISTNetwork(int[] layers){
		super(layers);
	}
	/**
	 * 
	 * @param train_data
	 * @param epochs
	 * @param batch_size
	 * @param learnrate
	 * @param test_data
	 */
	protected void stochastic_gradient_descent(Data[] train_data, int epochs, int batch_size, double learnrate, Data[] test_data){
		System.out.println("Epoch 0 beendet!  " + evaluateData(test_data) + "/" + test_data.length);
		for(int i = 0; i < epochs; i++){
			this.shuffleTrainData(train_data);
			ArrayList<Data[]> batches = new ArrayList<Data[]>();
			for(int k = 0; k < train_data.length / batch_size; k++){
				Data[] minibatch = new Data[batch_size];
				for(int j = 0; j < batch_size; j++){
					minibatch[j] = train_data[k * batch_size + j];
				}
				
				batches.add(minibatch);
			}
			
			for(int m= 0; m<batches.size();m++){
				double[][] batchdata= new double[batch_size][train_data[0].data.length];
				double[][] labeldata=new double[batch_size][10];
				Data[] currBatch= batches.get(m);
				for(int b=0; b<currBatch.length;b++){
					batchdata[b]=currBatch[b].data;
					double[] label={0,0,0,0,0,0,0,0,0,0};
					label[currBatch[b].solution]=1;
					labeldata[b]=label;
				}
				updateBatch(batchdata, labeldata,learnrate);
			}
			
			
			System.out.println("Epoch " + (i + 1) + " beendet!  " + evaluateData(test_data) + "/" + test_data.length);
		}
	}
	/**
	 * zählt wie viele der bilder richtig erkannt wurden
	 * @param test_data
	 * @return
	 */
	protected int evaluateData(Data[] test_data){
		//Bild erkennen
		int count = 0;
		double meansquarederror=0.0;
		for(int i = 0; i < test_data.length; i++){
			//System.out.println("output:  " + getOutputInt(test_data[i]));
			//System.out.println("sol:  " + test_data[i].solution);
			int output= getOutputInt(test_data[i]);
			double[] solution = new double[10];
			for(int k = 0; k < 9; k++){
				solution[k] = 0;
				if(test_data[i].solution == k){
					solution[k] = 1;
				}
			}
			for(int m=0 ;m<10;m++){
				meansquarederror+=Math.pow(solution[m]-neurons.get(neurons.size()-1)[m].output,2);
			}
			if(output == test_data[i].solution){
				count++;
			}
		}
		System.out.println("Error: " +meansquarederror);
		return count;
	}
	
	/**
	 * gibt den output des netzwerks anhand eines datensets als int aus (Wert der am größten ist)
	 * @param data
	 * @return
	 */
	protected int getOutputInt(Data data){
		berechneOutput(data.data);
		double max = -1;
		int pos = -1;
		for(int i = 0; i < neurons.get(neurons.size() - 1).length; i++){
			if(neurons.get(neurons.size() - 1)[i].output > max){
				max = neurons.get(neurons.size() - 1)[i].output;
				pos = i;
			}
		}
		return pos;
	}
	
	/**
	 * shuffled die Testdaten von MNIST
	 * @param train_data
	 * @return
	 */
	private Data[] shuffleTrainData(Data[] train_data){
		ArrayList<Data> arraydata = new ArrayList<Data>();
		for(int i = 0; i < train_data.length; i++){
			arraydata.add(train_data[i]);
		}
		ArrayList<Data> shufflearraydata = new ArrayList<Data>();
		while(arraydata.size() > 0){
			int rand = (int)(Math.random() * arraydata.size());
			shufflearraydata.add(arraydata.get(rand));
			arraydata.remove(rand);
		}
		for(int i = 0; i < shufflearraydata.size(); i++){
			train_data[i] = shufflearraydata.get(i);
		}
		return train_data;
	}
}
