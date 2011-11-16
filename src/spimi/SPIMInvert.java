package spimi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import documents.AbstractDocument;

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
	//private PostingList postings = null;
	private static final int BLOCK_SIZE = 655360000;
	private static final int LinkedList = 0;
	private int bytes_used = 0;
	private int block_ctr = 0;
	private int current_block = 0;
	private long document_id = 0;
	private final String output_path = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/copies/index_files/";
	private final String index_files_path = "/home/ben/school/COMP 479 (Information Retrieval)/code/reuters/copies/index_files/";
	private LinkedList<String> token_stream;
	private LinkedList<AbstractDocument> doc_stream;
	private HashMap<String, LinkedHashSet<Long>> postings = null; // term , postings [1,2,3,4...]
	
	public SPIMInvert(LinkedList<AbstractDocument> document_stream) {
		//postings = new PostingList();
		postings = new HashMap<String, LinkedHashSet<Long>>();
		doc_stream = document_stream;
	}
	
	/**
	 * Constructor that is meant to be used when multi-threading
	 * @param unique_marker this will be appended to the output file to ensure each thread produces unique file names
	 * @param token_stream
	 * @param document_id
	 */
	public SPIMInvert(LinkedList<String> token_stream, long document_id) {
		//postings = new PostingList();
		postings = new HashMap<String, LinkedHashSet<Long>>();
		this.token_stream = token_stream;
		this.document_id = document_id;
	}
	
	public void invert() {
		for (AbstractDocument d : doc_stream) {
			long docID = d.getDocumentID();
			for(String token : d.getTokens()) {
    			if (addToBlock(token, docID)) {
    				// keep iterating and adding
    			}
    			else {
    				flushBlock(); // flush the block
    				getNewBlock(); // get a new one
    				addToBlock(token,docID); // add again
    			}
    		}
		}
	}
	
	/**
	 * Add tokens to the block one at a time
	 * @param token a term to placed into the index
	 * @return true if successfully inserted, returns false when the block is full
	 */
	public boolean addToBlock(String token, long documentID) {
		if (bytes_used + 24 <= BLOCK_SIZE) {
			if (!postings.containsKey(token)) {
				LinkedHashSet<Long> h = new LinkedHashSet<Long>();
				h.add(documentID);
				postings.put(token,h);
				bytes_used += 24;
				return true;
			}
			else {
				// if the term is already present in our internal dictionary
				// locate the hashset of docID's and append to it.
				postings.get(token).add(documentID);
				bytes_used += 8;
				return true;
			}
		}
		else {
			return false;
		}
	}
	/**
		if (postings.addToken(token, documentID)) {
			// if the token is already in the dictionary
			// do some math
			if (bytes_used + 8 <= BLOCK_SIZE) {
				bytes_used += 8;
				/**postings.addToken(tokenID, documentID); //  shortcut here using the tokenID we just found above
				postings.addToken(token, documentID); // shortcut here using the tokenID we just found above
				return true;
			}
		}
		else { // the token is not in the dictionary, slower and consumes more memory
			if (bytes_used + 24 <= BLOCK_SIZE) {
				bytes_used += 24;
				//postings.addToken(token, documentID); // add the token in string form since we have no valid ID
				return true;
			}
			else return false; // no room we need a flush
		}
		// no room we need a flush
		return false;
	}**/
	
	/**
	 * Add tokens to the block in a stream
	 * @param token stream held in a linked list
	 */
	/**
	public void addToBlock(LinkedList<String> tokens, long documentID) {
		for(String token : tokens) {
			long tokenID = postings.hasToken(token);
			
			if (tokenID > -1) {
				// if the token is already in the dictionary
				// do some math
				if (bytes_used + 8 <= BLOCK_SIZE) {
					bytes_used += 8;
					postings.addToken(tokenID, documentID); // shortcut here using the tokenID we just found above
				}
				else {
					flushBlock(); // no room we need a flush
					getNewBlock();
					bytes_used += 24;
					postings.addToken(token, documentID); // add the token in string form since we have no valid ID since we're in a new block
				}
			}
			else { // the token is not in the dictionary, slower and consumes more memory
				if (bytes_used + 24 <= BLOCK_SIZE) {
					bytes_used += 24;
					postings.addToken(token, documentID); // add the token in string form since we have no valid ID
				}
				else {
					flushBlock(); // no room we need a flush
					getNewBlock();
					bytes_used += 24;
					postings.addToken(token, documentID); // add the token in string form since we have no valid ID
				}
			}
		}
		flushBlock(); // We made it through the entire list of tokens so we flush
	}
	
	**/
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
		postings = new HashMap<String, LinkedHashSet<Long>>();
		token_stream = null;
		document_id = 0;
		bytes_used = 0;
		block_ctr++;
		current_block = block_ctr;
	}
	
	public HashMap<String, LinkedHashSet<Long>> readBlockFromDisk(String location) {
		try {
			
			// get a handle to the input file
			FileReader inputStream = new FileReader(index_files_path+location);
			BufferedReader in = new BufferedReader(inputStream);
		
			// set up some instance vars
			String line = null;
			String[] details = null;
			HashMap<String, LinkedHashSet<Long>> pl = new HashMap<String, LinkedHashSet<Long>>();
			
			while ((line = in.readLine()) != null) {
				// read in a line and split it
				details = line.split(" ");
				// add all the document ID to the terms posting
				LinkedHashSet<Long> ps = new LinkedHashSet<Long>();
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
	public void mergeBlocks(String[] input_locations, String output_location) throws IOException {
		// clear the local postings list
		this.postings = new HashMap<String, LinkedHashSet<Long>>();		
			
		// Read in one file at a time and merge it into the local postings list
		for (int i = 0; i < input_locations.length; i++) {
			mergeLists(postings, readBlockFromDisk(input_locations[i]));
		}
			
		// write the merged postings to disk
		this.writeBlockToDisk(output_location);
	}
	
	/**
	 * merge the second list into the first
	 * @param pl1
	 * @param pl2
	 */
	public void mergeLists(HashMap<String, LinkedHashSet<Long>> pl1, HashMap<String, LinkedHashSet<Long>> pl2) {
		// for every term get all of the doc ID's and add it to the postings list
		for(String term : pl2.keySet()) {
			for(Long docID : pl2.get(term)) {
				if (pl1.containsKey(term)) {
					pl1.get(term).add(docID);
				}
				else {
					LinkedHashSet<Long> h = new LinkedHashSet<Long>();
					h.add(docID);
					pl1.put(term, h);
				}	
			}
		}
	}
}
