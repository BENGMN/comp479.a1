package spimi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *** ASSUMPTIONS / ESTIMATIONS ***
 4 kilobytes = 4096 bytes
 4 kilobytes = 32 768 bits
 1 byte = 1 char
 1 byte = 8 bits
 AVG word is 8 characters => 8 bits x 8 chars = 64 bits  
 integers are 32 bits
 each new entry requires:
              4 bytes    32 bits for the termID
              8 bytes    64 bits for the term itself
              4 bytes    32 bits for the postings termID
  			  8 bytes    64 bits for the  postings docID
 	 Total = 24 bytes = 192 bits   

 each repeated entry is only 64 bits or 8 bytes for the new docID

 a block becomes full when a combination of new entries
 and repeated entries is equal to the total block size
 
 Blocks should be some multiple of the disk block size
**/

public class SPIMInvert {
		
	// Member Variables
	//private PostingList postings = null;
	private static final int BLOCK_SIZE = 6553600;
	private int bytes_used = 0;
	private int block_ctr = 0;
	private int current_block = 0;
	private TreeMap<String, TreeSet<Long>> postings = null; // term , postings [1,2,3,4...]
	private final String output_path = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/copies/index_files/";
	private final String index_files_path = "/home/ben/school/COMP 479 (Information Retrieval)/code/reuters/copies/index_files/";
	
	
	public SPIMInvert() {
		postings = new TreeMap<String, TreeSet<Long>>();
	}
	
	/**
	 * Add tokens to the block one at a time
	 * @param token a term to placed into the index
	 */
	public void addToBlock(String token, long documentID) {
		if (bytes_used + 24 <= BLOCK_SIZE) {
			if (!postings.containsKey(token)) {
				TreeSet<Long> h = new TreeSet<Long>();
				h.add(documentID);
				postings.put(token,h);
				bytes_used += 24;
			}
			else {
				// if the term is already present in our internal dictionary
				// locate the hashset of docID's and append to it.
				postings.get(token).add(documentID);
				bytes_used += 8;
			}
		}
		else {
			flushBlock(); // flush the block
			getNewBlock(); // get a new one
			addToBlock(token, documentID); // add again
		}
	}
	
	/**
	 * Writes the current block to disk
	 */
	public void flushBlock() {
		if (bytes_used > 0) {
			System.out.println("Flushing block number "+current_block);
			writeBlockToDisk(output_path+String.valueOf(current_block)+".txt");
		}
	}
	
	/**
	 * Resets all of the parameters and provides you with a new postings list
	 */
	public void getNewBlock() {
		postings = new TreeMap<String, TreeSet<Long>>();
		bytes_used = 0;
		block_ctr++;
		current_block = block_ctr;
	}
	
	public boolean writeBlockToDisk(String location) {
		try {
			FileWriter outputStream = new FileWriter(location);
			BufferedWriter out = new BufferedWriter(outputStream);
						
			// write all the terms to the documents with their postings
			for (String token : postings.keySet()) {
				out.write(token+" ");
				for (Long docID : postings.get(token)) {
					out.write(docID+" ");
				}
				out.write("\n");
			}
			return true;
		}
	    catch (IOException e) {
			return false;
		}
	}
	
	
	public TreeMap<String, TreeSet<Long>> readBlockFromDisk(String location) {
		try {
			
			// get a handle to the input file
			FileReader inputStream = new FileReader(location);
			BufferedReader in = new BufferedReader(inputStream);
		
			// set up some instance vars
			String line = null;
			String[] details = null;
			TreeMap<String, TreeSet<Long>> pl = new TreeMap<String, TreeSet<Long>>();
			
			while ((line = in.readLine()) != null) {
				// read in a line and split it
				details = line.split(" ");
				// add all the document ID to the terms posting
				TreeSet<Long> ps = new TreeSet<Long>();
				for (int i = 1; i < details.length; i++) {
					ps.add(Long.parseLong(details[i]));
				}
				pl.put(details[0], ps);
			}
			return pl;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param input_locations location of all block files to be merged
	 * @param output_location
	 * @return true if the operation was successful
	 * @throws IOException 
	 */
	public TreeMap<String, TreeSet<Long>> mergeBlocks(String[] input_locations, String output_location) throws IOException {
		// clear the local postings list
		postings = new TreeMap<String, TreeSet<Long>>();		
			
		// Read in one file at a time and merge it into the local postings list
		for (int i = 0; i < input_locations.length; i++) {
			mergeLists(postings, readBlockFromDisk(input_locations[i]));
		}
			
		// write the merged postings to disk
		this.writeBlockToDisk(output_location);
		return postings;
	}
	
	/**
	 * merge the second list into the first
	 * @param pl1
	 * @param pl2
	 */
	public void mergeLists(TreeMap<String, TreeSet<Long>> pl1, TreeMap<String, TreeSet<Long>> pl2) {
		// for every term get all of the doc ID's and add it to the postings list
		for(String term : pl2.keySet()) {
			for(Long docID : pl2.get(term)) {
				if (pl1.containsKey(term)) {
					pl1.get(term).add(docID);
				}
				else {
					TreeSet<Long> h = new TreeSet<Long>();
					h.add(docID);
					pl1.put(term, h);
				}	
			}
		}
	}
}
