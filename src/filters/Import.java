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

	  // Determine whether we are starting with whitespace
	  boolean at_term = false;
	  if (Character.isWhitespace(data[0]) || data[0] == '<' || data[0] == '>') {
		  at_term = false;
	  }
	  else {
		  at_term = true;
	  }
	  
	  // Count HTML brackets. If the number is uneven we are in a tag.
	  int brackets = 0;
	  
	  // Keep track of the bounds of tokens
	  int left = 0, right = 0;
			  
	  // Iterate through the data stream
      for(int i = 0; i < data.length; i++) {
        if (data[i] == '<') {
        	brackets++;
        	if (at_term == true) {
        		at_term = false;
                right = i;
                StringBuilder term = new StringBuilder(right-left);
        	    term.append(data, left, right-left);
    		  
      		    // Dome some processing here to remove tags
      		    //  Do some processing here to remove punctuation
    		    tokens.add(term.toString());
    		    Logger.getUniqueInstance().writeToLog(term.toString());        		
        	}
        }
        if (data[i] == '>') {
        	brackets++;
        	i++; // if we find the end of a tag we push the counter forward
        	if (!Character.isWhitespace(data[i])) {
        		at_term = true;
        		left = i;
        	}
        }
    	
        // Only if we are not in a bracket do we consider the data
        if (brackets % 2 == 0) {
          // Find the beginning of a term
          if (!Character.isWhitespace(data[i]) && at_term == false) {
            at_term = true;
            left = i;
          }
          // Found the end of a token
          if (Character.isWhitespace(data[i]) && at_term == true) {	
            at_term = false;
            right = i;
            StringBuilder term = new StringBuilder(right-left);
    	    term.append(data, left, right-left);
		  
  		    // Dome some processing here to remove tags
  		    //  Do some processing here to remove punctuation
		    tokens.add(term.toString());
		    Logger.getUniqueInstance().writeToLog(term.toString());
          }
        }
      }
	}
	return tokens;
  }
}