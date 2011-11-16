package index;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;


public class PostingList {
	
	private TermDictionary internal_dictionary = null; // term, termID
	//private HashMap<Long, Posting> postings = null; // termID, associated posting
	private HashMap<String, LinkedHashSet<Long>> postings = null; // termID, associated posting
	
	public PostingList() {
		this.internal_dictionary = new TermDictionary();
		//this.postings = new HashMap<String, Posting>();
		this.postings = new HashMap<String, LinkedHashSet<Long>>(); // term , postings [1,2,3,4...]
	}
	
	/**
	 * This method will return true if the term was already in the postings list
	 * Either way the term will be added. False just means we made a new entry.
	 * @param token
	 * @param documentID
	 * @return true if the term is present, false if it wasn't
	 */
	public boolean addToken(String token, long documentID) {
		
		//Long tID = this.internal_dictionary.getTermID(token);
		
		// if the token is not part of this postings list dictionary already
		//if (tID == -1) {
		if (!postings.containsKey(token)) {
		// add the token to the dictionary
			//Long termID = this.internal_dictionary.add(token);
			// add the token to the postings list and add document id to the posting
			//Posting newPosting = new Posting(termID);
			//newPosting.add(documentID);
			//this.postings.put(termID, newPosting);
			LinkedHashSet<Long> h = new LinkedHashSet<Long>();
			h.add(documentID);
			this.postings.put(token,h);
			return false;
		}
		else {
				// if the term is already present in our internal dictionary
				// locate the hashset of docID's and append to it.
				//this.postings.get(this.internal_dictionary.getTermID(token)).add(documentID);
				this.postings.get(token).add(documentID);
				return true;
		}
	}
	
	/**
	 * If we already know what the termID we can efficiently add the documentID
	 * @param termID
	 * @param documentID
	 */
	public void addToken(long termID, long documentID) {
		//this.postings.get(termID).add(documentID);
		this.postings.get(termID).add(documentID);
	}
	
	/**
	 * Use this method to add a token to the dictionary alone
	 * since no documentID is specified no addition to the postings list is possible
	 * @param token
	 */
	
	public void addToken(String token) {
		Long tID = internal_dictionary.getTermID(token);
		// only add the token if it is not present
		if (tID == -1) {
			this.internal_dictionary.add(token);
		}
	}
	
	/**
	 * This method checks whether or not the supplied token exists within the internal dictionary
	 * @param token
	 * @return the termID of the token is returned if it is in the dictionary, otherwise -1 is returned.
	 */
	public long hasToken(String token) {
		return this.internal_dictionary.getTermID(token);
	}
	
	/**
	 * Get the tokenID from the dictionary
	 * @param token
	 * @return the tokenID is returned when the token is found. Otherwise -1 is returned 
	 */
	public Long getTokenID(String token) {
		return this.internal_dictionary.getTermID(token);
	}
	
	/**
	 * @return by default this method return the list of terms alphabetically sorted
	 */
	public Collection<String> getAllTerms() {
		return this.postings.keySet();
		//return this.internal_dictionary.getAllTerms();
	}
	

	
	public void clearPostings() {
		this.internal_dictionary = new TermDictionary();
		//this.postings = new HashMap<Long, Posting>();
		this.postings = new HashMap<String, LinkedHashSet<Long>>();
	}
	
	public LinkedHashSet<Long> getPostings(String token) {
		//return postings.get(internal_dictionary.getTermID(token)).getAllPostings();
		return postings.get(token);
	}
	
	public void mergeLists(PostingList posting_list) {
		// for every term get all of the doc ID's and add it to the postings list
		for(String term : posting_list.getAllTerms()) {
			for(Long docID : posting_list.getPostings(term)) {
				this.addToken(term, docID);	
			}
		}
	}
}
