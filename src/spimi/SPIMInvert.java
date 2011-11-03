package spimi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import index.PostingList;

public class SPIMInvert {

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
		
	// Member Variables
	private PostingList postings = null;
	private static final int BLOCK_SIZE = 4096;
	private int bytes_used = 0;
	private int block_ctr = 0;
	private int current_block = 0;
	
	public SPIMInvert() {
		postings = new PostingList();
	}
	
	/**
	 * Add tokens to the block one at a time
	 * @param token a term to placed into the index
	 * @return true if successfully inserted, returns false when the block is full
	 */
	public boolean addToBlock(String token, long documentID) {
		if (postings.hasToken(token)) {
			// do some math
			if (bytes_used + 8 <= BLOCK_SIZE) {
				bytes_used += 8;
				postings.addToken(token, documentID);
				return true;
			}
		}
		else { // the token is not in the dictionary
			if (bytes_used + 24 <= BLOCK_SIZE) {
				bytes_used += 24;
				postings.addToken(token, documentID);
				return true;
			}
			else return false; // no room we need a flush
		}
		// no room we need a flush
		return false;
	}
	
	public boolean writeBlockToDisk(String location) {
		try {
			FileWriter outputStream = new FileWriter(location);
			BufferedWriter out = new BufferedWriter(outputStream);
						
			// write all the terms to the documents with their postings
			for (String token : postings.getAllTerms()) {
				out.write(token+" ");
				for (Long docID : postings.getPostings(token)) {
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
	
	/**
	 * Writes the current block to disk
	 */
	public void flushBlock() {
		if (bytes_used > 0) {
			System.out.println("Flushing block number " + current_block);
			writeBlockToDisk("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/copies/index_files/"+String.valueOf(current_block));
		}
	}
	
	/**
	 * Resets all of the parameters and provides you with a new postings list
	 */
	public void getNewBlock() {
		this.postings = new PostingList();
		bytes_used = 0;
		block_ctr++;
		current_block = block_ctr;
	}
	
	public PostingList readBlockFromDisk(String location) {
		try {
			
			// get a handle to the input file
			FileReader inputStream = new FileReader(location);
			BufferedReader in = new BufferedReader(inputStream);
		
			// set up some instance vars
			String line = null;
			String[] details = null;
			PostingList pl = new PostingList();
			
			while ((line = in.readLine()) != null) {
				// read in a line and split it
				details = line.split(" ");
				// add all the document ID to the terms posting
				for (int i = 1; i < details.length; i++) {
					pl.addToken(details[0], Long.parseLong(details[i]));
				}
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
	public void mergeBlocks(String[] input_locations, String output_location) throws IOException {
		
		// clear the local postings list
		this.postings = new PostingList();		
			
		// Read in one file at a time and merge it into the local postings list
		for (int i = 0; i < input_locations.length; i++) {
			this.postings.mergeLists(readBlockFromDisk(input_locations[i]));
		}
			
		// write the merged postings to disk
		this.writeBlockToDisk(output_location);
	}
}
