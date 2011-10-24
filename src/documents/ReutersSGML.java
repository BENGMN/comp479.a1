package documents;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import filters.Filter;
import filters.PunctuationFilter;
import filters.ReutersFilter;

import technical.Logger;

public class ReutersSGML extends AbstractDocument {
	
	/**
	 * To create a new document object you must specify a valid path
	 * @param path string argument specifying the location of the file
	 */
	public ReutersSGML(String path) {
		super(path);
		filters.add(new PunctuationFilter());
		filters.add(new ReutersFilter());
		filters.trimToSize(); // slight optimization
		
	}

	/**
	 * Import a document without any content that occurs between < and >
	 * @return an ArrayList which guarantees constant time inserts
	 */
  public void parse() {	
	// Get the size of the file, create a byte stream and a character array to store the input
	int file_size = (int) f_handle.length(); // note f_handle is from the super class
	
	try {
	  Reader input = new BufferedReader(new InputStreamReader(new FileInputStream(f_handle)));
	  char[] data = new char[file_size];
	
	// Create an array list to store the tokens
    //	ArrayList<String> tokens = new ArrayList<String>();

	// Read the file into memory at once since they are not usually larger than 2MB
	
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
			      for (Filter f : this.filters) {
			    	  string_term = f.process(string_term);
			      }
		          
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
		tokens.trimToSize(); // small optimization here
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//return tokens;
  }
}