package parsers;

import index.TermDictionary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import filters.Filter;

public abstract class AbstractDocument {
	protected File f_handle = null;
	protected TermDictionary dictionary = null;
	protected ArrayList<String> tokens = null;
	protected ArrayList<Filter> filters = null;
	private long file_id = 0;
	
	/**
	 * To create a new document object you must specify a valid path
	 * @param path string argument specifying the location of the file
	 * @throws NullPointerException if the path specified does not point to a file an exception is thrown
	 */
	public AbstractDocument(String path, long file_id) throws NullPointerException {
		this.f_handle = new File(path);
		 dictionary = new TermDictionary(1000);
		 tokens = new ArrayList<String>(1000);
		 filters = new ArrayList<Filter>(5);
		 this.file_id = file_id;
	}
	
	public abstract void parse();
	
	public TermDictionary getDictionary() {
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
	
	public long getDocumentID() {
		return this.file_id;
	}
	
	public boolean writeTermDocIDPairs(String output_path) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(output_path));
			for(String s : tokens) {
				out.write(s+" "+this.file_id+"\n");
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
