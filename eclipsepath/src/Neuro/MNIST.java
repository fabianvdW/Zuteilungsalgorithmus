package Neuro;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;



// TODO: Test byte to int conversion
// sollte das bild [][] oder [] sein???? für das neuro-netz sollte es irrelevant sein...





/**
 * Wrapper to read MNIST-datasets
 * @author Leo Jung
 *
 */
public class MNIST{
	protected int nLabels;
	protected int nImgs;
	protected int nRows;
	protected int nCols;
	protected MNISTdata[] data;
	
	/**
	 * programmiert auf basis der datei-struktur wie hier definiert: http://yann.lecun.com/exdb/mnist/
	 * @param labelFilename
	 * @param imgFilename
	 */
	protected MNIST(String labelFilename, String imgFilename){
		/*
		 * Bsp. für Dateiangabe, wird später noch irgendwie übergeben oder relativ angegeben
		imgFilename = "C:/ ... /GitHub/Zuteilungsalgorithmus/MNIST_TestData/train-images.idx3-ubyte";
		labelFilename = "C:/ ... /GitHub/Zuteilungsalgorithmus/MNIST_TestData/train-labels.idx1-ubyte";
		*/
		//fck this try catch blocks
		try{
			DataInputStream labels = new DataInputStream(new FileInputStream(labelFilename));
			DataInputStream images = new DataInputStream(new FileInputStream(imgFilename));
			
			// check magic numbers -> make sure right files...
			int magicN = labels.readInt();
			if(magicN != 2049){
				labels.close();
				images.close();
				throw new Error("Label-file hat falsche magic number: " + magicN + " (erwartet 2049)");
			}
			magicN = images.readInt();
			if(magicN != 2051){
				labels.close();
				images.close();
				throw new Error("IMG-file hat falsche magic number: " + magicN + " (erwartet 2051)");
			}
			
			// get basic infos
			nLabels = labels.readInt();
			nImgs = images.readInt();
			nRows = images.readInt();
			nCols = images.readInt();
			if(nLabels!=nImgs){
				labels.close();
				images.close();
				throw new Error("Anzahl der Einträge bei der Bilddatei(" + nImgs + ") und der Labeldatei(" + nLabels + ") sind unterschiedlich");
			}
			
			// einlesen der Datei in Byte-arrays
			byte[] labelsData = new byte[nLabels];
			labels.read(labelsData);
			int imgVectorSize = nCols * nRows;
			byte[] imgsData = new byte[nLabels * imgVectorSize];
			images.read(imgsData);
			
			// aufbereitung zu double[]-bild und int-label
			data = new MNISTdata[nLabels];
			int label;
			int counter = 0;
			double[] img;
			for(int i = 0; i < nLabels; i++){
				label = new Integer(labelsData[i]);
				img = new double[imgVectorSize];
				for(int n = 0; n < imgVectorSize; n++){
					img[n] = ((double)new Integer(imgsData[counter++])/255.0);
				}
				data[i] = new MNISTdata(img, label);
			}
			labels.close();
			images.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
