package driver;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import documents.ReutersSGML;



public class Driver {

	public static void main(String[] args) {
		// Set the location of the corpus
		File corpus = new File("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters");
        //S test_file_1 = new File ("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-000.sgm");
        //String test_file_1 = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/test-reut.sgm";
        //String test_file_1 = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-000.sgm";
		//File test_file = new File ("/home/ben/Desktop/test.txt");
		//Iterate through the files and perform the necessary actions
		
		File[] files = corpus.listFiles();
		Hashtable<File, Integer> docList = new Hashtable<File, Integer>();
		int fileID = 0;
		
		Hashtable<String, ArrayList<Integer>> postings = new Hashtable<String, ArrayList<Integer>>(); 
		
		for(File f : files) {
			try {
				docList.put(f, fileID);
				ReutersSGML data = new ReutersSGML(f.getAbsolutePath());
				ArrayList<String> tokens = data.NoHTMLImport();

				for (String s : tokens) {
				  if (postings.get(s) == null) {
					  postings.put(s, new ArrayList<Integer>());
					  postings.get(s).add(fileID);
				  }
				  else {
					  postings.get(s).add(fileID);
				  }
				}
				
				fileID++;	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Enumeration<String> keys = postings.keys();
		while(keys.hasMoreElements()) {
			System.out.print(keys.nextElement()+" --> ");
			ArrayList<Integer> l = postings.get(keys.nextElement());
			for(Integer i : l) {
				System.out.print(i+" ");
			}
			System.out.print("\n");
		}	
	}
}
