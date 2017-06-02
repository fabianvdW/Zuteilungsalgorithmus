package Neuro;

public class Maths{
	protected static double learnRate = 0.1;
	// mit h und der learnRate muss noch etwas herum experimentiert werden, dazu wird aber das komplette neuro-netz benötigt...
	protected static double h = 1;
	// eulersche Zahl
	protected static double e = 2.71828182845904523536028747135266249775724709369995957496696762772407663035354759457138217852516642742746;
	
	// für test-zwecke
	public static void main(String[] args){
		MNIST test = new MNIST("", "");
		int n;
		double number;
		// Test MNIST data-loader
		for(MNISTdata d: test.data){
			String tmp = "";
			n = 0;
			System.out.println("\n" + d.solution);
			for(int i = 0; i < d.img.length; i++){
				if(n++ >= test.nCols){
					n = 0;
					System.out.println("	" + tmp);
					tmp = "";
				}
				number = Math.round(d.img[i] * 100);
				number = number/100;
				tmp += number + ", ";
			}
		}
		
		// Test gradient
		double[] aktuellerWert = new double[]{3, 1};
		int duration = 0;
		double tmp;
		for(int i = 0; i < duration; i++){
			tmp = f(aktuellerWert[0], aktuellerWert[1]);
			System.out.println("x=" + aktuellerWert[0] + ", y=" + aktuellerWert[1] + " -> " + tmp + " Epoch: " + (i + 1) + "/" + duration);
			if(tmp<0){
				System.out.println("Die Funktion hat nun einen negativen Wert erreicht, was nicht sein kann...");
				System.exit(0);
			}
			else if(tmp == 0){
				System.out.println("Yay, got minimum!");
			}
			aktuellerWert = gradientDescent(aktuellerWert);
		}
	}
	
	protected static double sigmoid(double z){
		return 1/(1 + Math.pow(e, -z));
	}
	
	protected static double[] gradientDescent(double[] aktuellerWert){
		double[] tmp;
		tmp = gradient(aktuellerWert);
		aktuellerWert = new double[]{aktuellerWert[0] - learnRate * tmp[0], aktuellerWert[1] - learnRate * tmp[1]};
		return aktuellerWert;
	}
	
	protected static double[] gradient(double[] input){
		double[] result = new double[2];
		// je größer h ist, desto schneller verringert sich das ergebnis, aber nur bis 2, danach hat es den gegenteiligen Effekt
		result[0] = (f(input[0] + h, input[1]) - f(input[0] - h, input[1])) / 2*h;
		result[1] = (f(input[0], input[1] + h) - f(input[0], input[1] - h)) / 2*h;
		return result;
	}
	
	/**
	 * Dies ist eine Beispielfunktion zum testen des Gradienten
	 * f(x,y) = 2 * x^2 + 3 * y^3
	 * @param input
	 * @return
	 */
	protected static double f(double x, double y){
		// Bei nicht quadratischen funktionen, kommt man früher oder später auf negative Werte
		return 2*x*x + 3*y*y*y;
	}
}
