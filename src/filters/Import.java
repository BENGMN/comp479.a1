package filters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import technical.Logger;

public class Import {
	
	private File tmp = null;
	//private String[] chars_to_strip = {".", "\"", "(", ")", "?", "+", ",", "-", ":", "<", ">", "{", "}"};
	
	public Import(String file) {
		tmp = new File(file);
	}

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
	  
	  // Iterate through the data stream
      for(int i = 0; i < data.length; i++) {
    	while(true) {
    		if (data[i] == '<') {
    	      while (data[i] != '>') {
    		    i++;
    		  }
    		  i++;
    		}
    		if (data[i] != '<') {
    		  break;
    		}
    	}
    	
    	boolean build_token = false;
    	
    	if (!Character.isWhitespace(data[i])) {
    	    left = i;
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
    	      string_term = Punctuation.removeCharFromBeginning(string_term);
    	      string_term = Punctuation.removeCharFromEnd(string_term);

		      if (!string_term.isEmpty()) {
    	        tokens.add(string_term);
		        Logger.getUniqueInstance().writeToLog(string_term);
		      }
		      build_token = false;
		      i--; // decrement the counter to make sure we don't skip ahead when for loop increments next. 
    		}
    	}
      }
	}
	return tokens;
  }
}