package driver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.util.ArrayList;

import technical.Logger;
//Logger.getUniqueInstance().writeToLog("Write something");

public class Driver {

	public static void main(String[] args) {
		// Set the location of the corpus
		File corpus = new File("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters");
        //File test_file = new File ("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-000.sgm");
		//File test_file = new File ("/home/ben/Desktop/test.txt");
		// Iterate through the files and perform the necessary actions
		File[] files = corpus.listFiles();
		for(File f : files) {
			try {
				
				// Get the size of the file, create a byte stream and a character array to store the input
				int file_size = (int) f.length();
				Reader input = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				char[] data = new char[file_size];
				
				// Create an array list to store the tokens
				ArrayList<String> tokens = new ArrayList<String>();

				// Read the file
				while (input.read(data, 0, file_size) != -1) {

				  // Determine whether we are starting with whitespace
				  boolean at_token = Character.isWhitespace(data[0]) ? false: true;
				  
				  // Keep track of the bounds of tokens
				  int left = 0, right = 0;
				  
				  // Iterate through the data stream
                  for(int i = 0; i < data.length; i++) {
                	// Found the beginning of a token
                	if (!Character.isWhitespace(data[i]) && at_token == false) {
                      at_token = true;
                      left = i;
                    }
                	// Found the end of a token
                	if (Character.isWhitespace(data[i]) && at_token == true) {	
                      at_token = false;
                      right = i;
                      StringBuilder token = new StringBuilder(right-left);
  					  token.append(data, left, right-left);
  					  tokens.add(token.toString());
                    }
                  }
				}
				int ctr = 0;
				for (String s : tokens) {
					System.out.println("Token #"+ctr+"\t"+s);
					ctr++;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
