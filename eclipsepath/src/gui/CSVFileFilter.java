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
