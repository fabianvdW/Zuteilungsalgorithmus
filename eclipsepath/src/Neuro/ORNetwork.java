package Neuro;

public class ORNetwork extends Network {
	
	public ORNetwork(int[] layers){
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
	protected void stochastic_gradient_descent(double[][] train_data, int[] labels, int epochs, int batch_size, double learnrate, double[][] test_data, int[] test_label){
		System.out.println("Epoch 0 beendet!  " + evaluateData(test_data, test_label) + "/" + test_data.length);
		for(int i = 0; i < epochs; i++){
				for(int m = 0; m < train_data.length; m++){
					
					double[][] batchdata = new double[1][2];
					double[][] labeldata = {{0,0}};
					batchdata[0] = train_data[m];
					labeldata[0][labels[m]] = 1;
				
					updateBatch(batchdata, labeldata, learnrate);			
					
				}
				System.out.println("Epoch " + (i + 1) + " beendet!  " + evaluateData(test_data, test_label) + "/" + test_data.length);
		}
	}
	
	protected int evaluateData(double[][] testd, int[] testL){
		int count = 0;
		double meansqe = 0.0;
		for(int i = 0; i < testd.length; i++){
			double[] testdata = testd[i];
			berechneOutput(testdata);
			double[] output = new double[2];
			output[0] = neurons.get(neurons.size() - 1)[0].output;
			output[1] = neurons.get(neurons.size() - 1)[1].output;
			System.out.println("o0:  " + output[0]);
			System.out.println("o1:  " + output[1]);
			int testl=testL[i];
			if(testl == 1){
				meansqe += Math.pow(output[0] - 0,2);
				meansqe += Math.pow(output[1] - 1, 2);
			}else{
				meansqe += Math.pow(output[0] - 1,2);
				meansqe += Math.pow(output[1] - 0, 2);
			}
			if(testl == 1 && output[1] > output[0] || testl == 0 && output[0] > output[1]){
				count++;
			}
		}
		System.out.println("Error:  " + meansqe);
		return count;
	}
}
