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
