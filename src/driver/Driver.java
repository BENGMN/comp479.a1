package driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import filters.Import;
//Logger.getUniqueInstance().writeToLog("Write something");

public class Driver {

	public static void main(String[] args) {
		// Set the location of the corpus
		//File corpus = new File("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters");
        //S test_file_1 = new File ("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-000.sgm");
        //String test_file_1 = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/test-reut.sgm";
        String test_file_1 = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-000.sgm";
		//File test_file = new File ("/home/ben/Desktop/test.txt");
		// Iterate through the files and perform the necessary actions
		//File[] files = corpus.listFiles();
		//for(File f : files) {
			try {
				
				Import data = new Import(test_file_1);
				ArrayList<String> tokens = data.NoHTMLImport();
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
	//}
}
