package index;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashSet;

import documents.AbstractDocument;

public class PostingList {
	
	private TermDictionary internal_dictionary = null;
	private TreeMap<Long, HashSet<Long>> postings = new TreeMap<Long, HashSet<Long>>();

	public PostingList() {
		this.internal_dictionary = new TermDictionary();
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
