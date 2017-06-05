package Neuro;

public class ORTest {
	public static void main(String[] args){
		MNISTdata[] train_data =new MNISTdata[4];
		double[] train_1a={0,0};
		double[] train_2a={0,1};
		double[] train_3a={1,0};
		double[] train_4a={1,1};
		train_data[0]= new MNISTdata(train_1a,0);
		train_data[1]=new MNISTdata(train_2a,1);
		train_data[2]= new MNISTdata(train_3a,1);
		train_data[3]=new MNISTdata(train_4a,1);
		int[] layers={2,1,1};
		Network n = new Network(layers);
		n.stochastic_gradient_descent(train_data, 100, 4, 0.1, train_data);
	}
}
