package Neuro;

public class MNISTdata{
	// val: 0(white) - 1(black)
	protected final double[] img;
	// solution
	protected final int solution;
	
	protected MNISTdata(double[] imgData, int label){
		solution = label;
		img = imgData;
	}
}