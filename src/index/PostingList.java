package index;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashSet;

import parsers.AbstractDocument;


public class PostingList {
	
	private TermDictionary internal_dictionary = null;
	private TreeMap<Long, HashSet<Long>> postings = null;

	public PostingList() {
		this.internal_dictionary = new TermDictionary();
		this.postings = new TreeMap<Long, HashSet<Long>>();
	}
	
	public void addToken(String token, long documentID) {
		// if the token is not part of this postings list dictionary already
		if (this.internal_dictionary.getTermID(token) == -1) {
		// add the token to the dictionary
			this.internal_dictionary.add(token);
			// add the token to the postings list and create a new hashset for the docID's
			this.postings.put(this.internal_dictionary.getTermID(token), new HashSet<Long>());
			// add the document ID  to the newly created hashset
			this.postings.get(token).add(documentID);
		}
		else {
				// if the term is already present in our internal dictionary
				// locate the hashset of docID's and append to it.
				this.postings.get(this.internal_dictionary.getTermID(token)).add(documentID); 
		}
	}
	
	/**
	 * Use this method to add a token to the dictionary alone
	 * since no documentID is specified no addition to the postings list is possible
	 * @param token
	 */
	
	public void addToken(String token) {
		if (this.internal_dictionary.getTermID(token) == -1) {
			this.internal_dictionary.add(token);
		}
	}
	
	public boolean hasToken(String token) {
		if (this.internal_dictionary.getTermID(token) == -1) {
			return false;
		}
		else return true;
	}
	
	/**
	 * Get the tokenID from the dictionary
	 * @param token
	 * @return the tokenID is returned when the token is found. Otherwise -1 is returned 
	 */
	public Long getTokenID(String token) {
		return this.internal_dictionary.getTermID(token);
	}
	
	public ArrayList<String> getAllTerms() {
		return this.internal_dictionary.getAllTerms();
	}
	
	public ArrayList<Long> getAllTermIDs() {
		return this.internal_dictionary.getAllTermIDs();
	}
	
	public void clearPostings() {
		this.internal_dictionary = new TermDictionary();
		this.postings = new TreeMap<Long, HashSet<Long>>();
	}
	
	public Long[] getPostings(String token) {
		Long[] postings = (Long[])this.postings.get(token).toArray();
		return postings;
	}
	
	public void addDocument(AbstractDocument document) {
		//document_set.add(document);
		
		// get all of the tokens from the document
		ArrayList<String> tokens = document.getTokens();
		
		// loop through every token in the document
		for (String token : tokens) {
			// if the token is not part of this postings list dictionary already
			if (this.internal_dictionary.getTermID(token) == -1) {
				// add the token to the dictionary
				this.internal_dictionary.add(token);
				// add the token to the postings list and create a new hashset for the docID's
				this.postings.put(this.internal_dictionary.getTermID(token), new HashSet<Long>());
				// add the document ID the term appears in to the newly created hashset
				this.postings.get(token).add(document.getDocumentID());
			}
			else {
				// if the term is already present in our internal dictionary
				// locate the hashset of docID's and append to it.
				this.postings.get(this.internal_dictionary.getTermID(token)).add(document.getDocumentID()); 
			}
		}
		
	}	
}
