package imports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;


import filters.IFilter;
import filters.PunctuationFilter;
import filters.ReutersFilter;


import technical.Logger;

public class SGMImport {

	IFilter p = new PunctuationFilter();
	IFilter r = new ReutersFilter();
	
	private File tmp = null;
	
	public SGMImport(String file) {
		tmp = new File(file);
	}

	/**
	 * Import a document without any content that occurs between < and >
	 * @return an ArrayList which guarantees constant time inserts
	 * @throws IOException
	 */
  public ArrayList<String> NoHTMLImport() throws IOException {	
	// Get the size of the file, create a byte stream and a character array to store the input
	int file_size = (int) tmp.length();
	Reader input = new BufferedReader(new InputStreamReader(new FileInputStream(tmp)));
	char[] data = new char[file_size];
	
	// Create an array list to store the tokens
	ArrayList<String> tokens = new ArrayList<String>();

	// Read the file
	while (input.read(data, 0, file_size) != -1) {
	  
	  // Keep track of the bounds of tokens
	  int left = 0, right = 0;
	  
	  // Loop through the characters omitting any information that occurs between < >
      for(int i = 0; i < data.length; i++) {
    	while(true) {
    		if (data[i] == '<') {
    	      while (data[i] != '>') {
    		    i++;
    		  }
    		  i++;
    		}
    		if (data[i] != '<') {
    		  // if the next character is an opening bracket we keep looping up here
    			break;
    		}
    	}
    	
    	// Finally we are no longer inside a tag
    	boolean build_token = false;
    	
    	// If we are not on whitespace
    	if (!Character.isWhitespace(data[i])) {
    		left = i; // take note of the LHS boundary of the term

    		// Find the RHS of the term
    		// if this loop is never entered we found another tag and go back to the top
    		// if this loop is entered and we find whitespace or another <, we form a term
    		while(!Character.isWhitespace(data[i]) && data[i] != '<') {
    		  i++;
    		  build_token = true;
    	    }
    		while(build_token) {
    		  right = i;
    		  StringBuilder term = new StringBuilder(right-left);
    	      term.append(data, left, right-left);
		      String string_term = term.toString();
    	      
		      
		      // Do some post-processing on the string to clean it up
    	      string_term = p.process(string_term);
              string_term = r.process(string_term);
              
              // If we're not left with an empty string we add it to the output
		      if (!string_term.isEmpty()) {
    	        tokens.add(string_term.toLowerCase());
		        Logger.getUniqueInstance().writeToLog(string_term);
		      }
		      
		      i--; // decrement the counter to make sure we don't skip ahead when for loop increments next.
		      build_token = false; // break the loop 
    		}
    	}
      }
	}
	return tokens;
  }
}