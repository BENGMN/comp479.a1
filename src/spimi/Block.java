package spimi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import index.PostingList;

public class Block {

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
	**/
		
	// Member Variables
	private PostingList postings = null;
	private static final int BLOCK_SIZE = 4096;
	private int bytes_used = 0;
	
	private static final String BEGIN_BLOCK = "$$$ Beginning of block$$$";
	private static final String END_BLOCK   = "$$$ End of block $$$";
	private static final String BEGIN_DIC   = "$$$ Beginning of dictionary $$$";
	private static final String END_DIC     = "$$$ End of dictionary $$$";
	private static final String BEGIN_POST  = "$$$ Beginning of postings $$$";
	private static final String END_POST    = "$$$ End of postings $$$";
	
	public Block() {
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
			else {
				return false;
			}
		}
		else {
			if (bytes_used + 24 <= BLOCK_SIZE) {
				bytes_used += 24;
				postings.addToken(token, documentID);
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public boolean writeBlockToDisk(String location) {
		try {
			FileWriter outputStream = new FileWriter(location);
			BufferedWriter out = new BufferedWriter(outputStream);
			
			out.write(BEGIN_BLOCK+"\n");
			out.write(BEGIN_DIC+"\n");
			
			for (String token : postings.getAllTerms()) {
				out.write(token+"\t"+postings.getTokenID(token)+"\n");
			}
			
			out.write(END_DIC+"\n");
			out.write(BEGIN_POST+"\n");
			
			for (String token : postings.getAllTerms()) {
				out.write(token+" ");
				for (Long docID : postings.getPostings(token)) {
					out.write(docID+" ");
				}
				out.write("\n");
			}
			out.write(END_POST+"\n");
			out.write(END_BLOCK+"\n");
			return true;
		}
	    catch (IOException e) {
			return false;
		}
	}
	
	public boolean mergeBlocks(String[] locations) {
		
		this.postings = new PostingList();
		
		try {
			
			boolean at_block      = false;
			boolean at_dictionary = false;
			boolean at_postings   = false;
			
			
			//FileReader inputStream = new FileReader(locations);
			//BufferedReader in = new BufferedReader(inputStream);
			
			BufferedReader[] inputs = new BufferedReader[locations.length];
			
			// Initialize an array of buffered readers to grab 1 line of text at a time per file
			for (int i = 0; i < locations.length; i++) {
				inputs[i] = new BufferedReader(new FileReader(locations[i]));
			}
			BufferedReader in = new BufferedReader(new FileReader(locations[i]));
			
			String line = null;
			
			while((line = in.readLine()) != null) {
				
				at_block      = line.equals(BEGIN_BLOCK) ? true  : at_block;
				at_block      = line.equals(END_BLOCK)   ? false : at_block;
				at_dictionary = line.equals(BEGIN_DIC)   ? true  : at_dictionary;
				at_dictionary = line.equals(END_DIC)     ? false : at_dictionary;
				at_postings   = line.equals(BEGIN_POST)  ? true  : at_postings;
				at_postings   = line.equals(END_POST)    ? false : at_postings;
				
				if(at_block && at_dictionary) {
				  String[] dictionary_entry = line.split("\t"); // note line is "term \t termID" 
				  this.postings.addToken(dictionary_entry[0]);
				}
				
				if (at_block && at_postings) {
					String[] posting_entries = line.split(" ");
					
					// for all the entries in the postings list, re-add them
					for (int i = 1; i < posting_entries.length; i++) {
						postings.addToken(posting_entries[0], Long.parseLong(posting_entries[i]));	
					}
				}

			}
			
			return true;
		}
		catch(IOException e) {
			return false;
		}
	}
	
	
	public void clearBlock() {
		postings = new PostingList();
		bytes_used = 0;
		System.out.println("Block Cleared - Postings size =  "+postings.getAllTerms().size());
	}

}
