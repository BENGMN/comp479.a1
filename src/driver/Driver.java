package driver;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import parsers.ReutersSGML;




public class Driver {

	public static void main(String[] args) {
		
        //S test_file_1 = new File ("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-000.sgm");
        //String test_file_1 = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/test-reut.sgm";
        //String test_file_1 = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-000.sgm";
		//File test_file = new File ("/home/ben/Desktop/test.txt");
		//Iterate through the files and perform the necessary actions
		
		
		// Set the location of the corpus
		File corpus = new File("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters");
		
		// Get the list of documents stored in the corpuses directory
		File[] files = corpus.listFiles();
		
		// Create an index to store the document documentID mappings
		Hashtable<String, Long> document_index = new Hashtable<String, Long>(files.length);
		
		// Create a counter to act as a fileID generator
		long fileID = 0;
		
		// initialize the document index
		// and parse each document along the way
		for(File f : files) {
			document_index.put(f.getAbsolutePath(), fileID);
		
			// For each document in the doc_index, tokenize it.
			ReutersSGML reut = new ReutersSGML(f.getAbsolutePath(), fileID);
			reut.parse();
			System.out.println(f.getParent()+"/"+fileID+"\n");
			System.out.println(f.getAbsolutePath()+"\n");
			reut.writeTermDocIDPairs(f.getParent()+"/"+fileID);
			fileID++;
		}
		
			
		
		/*
		
		for(File f : files) {
			try {
				document_index.put(f, fileID);
				ReutersSGML data = new ReutersSGML(f.getAbsolutePath());
				ArrayList<String> tokens = data.parse();

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
		*/
	}
}
