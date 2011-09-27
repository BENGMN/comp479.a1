package driver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;

import technical.Logger;

public class Driver {

	public static void main(String[] args) {
		// Set the location of the corpus
		//File corpus = new File("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters");
        //File test_file = new File ("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-000.sgm");
		File test_file = new File ("/home/ben/Desktop/test.txt");
        char space = ' ';
		// Iterate through the files and perform the necessary actions
		//File[] files = corpus.listFiles();
		//for(File f : files) {
			try {
				
				// Get the size of the file, create a byte stream and a character array to store the data
				int file_size = (int) test_file.length();
				Reader input = new BufferedReader(new InputStreamReader(new FileInputStream(test_file)));
				char[] data = new char[file_size];
				
				// Read the file
				while (input.read(data, 0, file_size) != -1) {
					int N = 0;
					boolean at_token = true;
					/* 
					 * STEP: 1 --> Calculate the number of delimiters (spaces)
					 *             and thus tokens ( = # spaces + 1) 
					*/
					for(int i = 0; i < data.length; i++) {
					  if (Character.isWhitespace(data[i]) && at_token == true) {
						    N++;
						    at_token = false;
					  }
					  if (!Character.isWhitespace(data[i]) && at_token == false) {
						  at_token = true;
					  } 
					}
					Logger.getUniqueInstance().writeToLog("Total Number of Tokens Counted "+N);

					// STEP: 2 -->  Parse N+1 tokens and store the values in a string array called tokens
					String[] tokens = new String[N+1]; 
					int left = 0, right = 0;
					at_token = true;
					
					for(int i = 0; i < N; i++) {
					  while(!Character.isWhitespace(data[right]) && at_token == true) {
								right++;
					  }
					  StringBuilder token = new StringBuilder(right-left);
					  token.append(data, left, right-left);
					  tokens[i] = token.toString();
					  Logger.getUniqueInstance().writeToLog("Token # " +i+"\t"+tokens[i]);
					  right++;
					  left = right;
					}
					// Here we are at the last token, the Nth + 1 spaces
					StringBuilder token = new StringBuilder(data.length-right);
					token.append(data, right, data.length-right);
					tokens[N] = token.toString();
					Logger.getUniqueInstance().writeToLog("Token # " +N+"\t"+tokens[N]);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		//}
	}
}
