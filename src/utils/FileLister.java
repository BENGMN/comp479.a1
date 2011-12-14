package utils;


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * This is an iterative implementation used to build a list of 
 * all sub directories for a given base directory
 * @author ben
 *
 */

public class FileLister {

	private LinkedList<File> folders = null;
	private List<File> allFiles = null;
	
	
	/**
	 * Create a new File Lister
	 * @param base_directory specify a base directory for the listings to begin
	 */
	public FileLister(String base_directory) {
		File f = new File(base_directory);
		allFiles = new ArrayList<File>();
		folders = new LinkedList<File>();
		folders.add(f);
		
	}

	/**
	 * Use this method to get all files from the sub-directories
	 * @return a list of files that are contained at any level of the specified base_directory
	 * (in the base directory and it's sub-directories, etc...)
	 */
	public List<File> getAllFiles() {
			
			while(folders.size() > 0) {
				
				for (File f : folders.getFirst().listFiles()) {
					// if the file is a directory we add it to the list
					// of directories to be crawled
					if (f.isDirectory() && !f.isHidden()) {
						folders.addLast(f);
					}
					else {
							allFiles.add(f);
					}
				}
				// remove the current directory from the list since we have just processed it.
				// Note: this can break the loop if no folders were added to the list.
				folders.removeFirst();
			}
		return allFiles;
	}
	
	// used to debug
	public static void main (String[] args) {
		
		//FileLister f = new FileLister(System.getProperty("user.dir")+"/corpus"); //for ben
		FileLister f = new FileLister("C://Users//Richard//workspace//comp-479-finalproject//trunk//NorthernLight//corpus");
		
		List<File> allFiles = f.getAllFiles();
		System.out.println("Total Number of Files "+allFiles.size());
	}
	
}
