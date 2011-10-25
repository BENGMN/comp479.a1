package spimi;

import index.PostingList;

public class Block {

	// Member Variables
	
	private PostingList postings = null;
	private static final int BLOCK_SIZE = 4096;
	private int bytes_used = 0;
	
	// Estimations
	// 4 kilobytes = 4096 bytes
	// 4 kilobytes = 32 768 bits
	// 1 byte = 1 char
	// 1 byte = 8 bits
	// AVG word is 8 characters => 8 bits x 8 chars = 64 bits  
	// integers are 32 bits
	// each new entry requires:
	//              4 bytes    32 bits for the termID
	//              8 bytes    64 bits for the term itself
	//              4 bytes    32 bits for the postings termID
	// 				8 bytes    64 bits for the  postings docID
	// 	   Total = 24 bytes = 192 bits   
	
	// each repeated entry is only 64 bits or 8 bytes for the new docID

	// a block becomes full when a combination of new entries
	// and repeated entries is equal to the total block size
	
	public Block() {
		postings = new PostingList();
	}
	
	/**
	 * Add token to the block one at a time
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
	
	public void writeBlockToDisk(String location) {
		
	}
	
	public void mergeBlocks() {
		
	}
	
	
	public void clearBlock() {
		postings = new PostingList();
		bytes_used = 0;
		System.out.println("Block Cleared - Postings size =  "+postings.getAllTerms().size());
	}

}
