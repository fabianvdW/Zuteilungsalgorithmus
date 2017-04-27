package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Agent77326
 */
public class RWFile{
	/**
	 * Writes to the String on the given file
	 * @param fh Filehandle
	 * @param txt the String to write
	 */
	public static void write(File fh, String txt){
		Writer w = null;
        try{
            w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fh), StandardCharsets.UTF_8));
            w.write(txt);
        }
        catch(IOException ex){
            System.err.println("Es ist ein unerwarteter Fehler beim schreiben der Datei " + fh.getName() + " aufgetreten");
            ex.printStackTrace();
        }
        finally{
    		try{
	        	if(w!=null){
					w.close();
	        	}
			}
    		catch(IOException e){
				e.printStackTrace();
			}
        }
	}
	
	/**
	 * Reads the content of the given File
	 * @param fh the Filehandle
	 * @return the file-content as String
	 */
	public static String read(File fh){
		try{
			byte[] encoded = Files.readAllBytes(Paths.get(fh.getAbsolutePath()));
			return new String(encoded, StandardCharsets.UTF_8);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
