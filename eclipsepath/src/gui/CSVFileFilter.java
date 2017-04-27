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

import java.io.File;

/**
 * @author Agent77326
 */
public class CSVFileFilter extends javax.swing.filechooser.FileFilter{
	/**
     * Get the extension of a file.
     */
    protected static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
    
    /**
     * abstract! automatically called on filter-trigger, returns if the file matches the required extension
     */
	public boolean accept(File f){
		if(f.isDirectory()){
			return true;
		}
		String extension = getExtension(f);
	    if (extension != null) {
	        if (extension.equals("csv")){
	                return true;
	        }
	    }
		return false;
	}
    
    /**
     * abstract! automatically called on filter-trigger, returns the description
     */
	public String getDescription(){
		return ".csv (Comma-separated values) Listendatei";
	}
}
