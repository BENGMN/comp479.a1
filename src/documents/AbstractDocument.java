package documents;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import filters.Filter;

public abstract class AbstractDocument {
	protected File f_handle = null;
	protected HashMap<String, Integer> dictionary = null;
	protected ArrayList<String> tokens = null;
	protected ArrayList<Filter> filters = null;
	
	/**
	 * To create a new document object you must specify a valid path
	 * @param path string argument specifying the location of the file
	 * @throws NullPointerException if the path specified does not point to a file an exception is thrown
	 */
	public AbstractDocument(String path) throws NullPointerException {
		this.f_handle = new File(path);
		 dictionary = new HashMap<String, Integer>(1000);
		 tokens = new ArrayList<String>(1000);
		 filters = new ArrayList<Filter>(5);
	}
	
	public abstract void parse();
	
	public HashMap<String, Integer> getDictionary() {
		return this.dictionary;
	}
	
	/**
	 * All of the tokens from a document are provided in the order they were parsed
	 * @return an ArrayList of String objects which has been trimmed to minimize space
	 * although not in this function so it remains as fast as possible
	 */
	public ArrayList<String> getTokens() {
		return this.tokens;
	}

}
