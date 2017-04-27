package gui;

import java.io.File;

public class CSVFileFilter extends javax.swing.filechooser.FileFilter{
	/*
     * Get the extension of a file.
     */
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

	public boolean accept(File f){
		String extension = getExtension(f);
	    if (extension != null && !f.isDirectory()) {
	        if (extension.equals("csv")){
	                return true;
	        }
	    }
		return false;
	}
	
	public String getDescription(){
		return ".csv (Comma-separated values) Listendatei";
	}
}
