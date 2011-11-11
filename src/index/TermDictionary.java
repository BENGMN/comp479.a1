package index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class TermDictionary {

	private HashMap<String, Long> dictionary = null;
	private long term_id = 0;
	
	public TermDictionary() {
		dictionary = new HashMap<String, Long>();
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 */
	public long add(String term) {
		Long termID = this.dictionary.get(term);
		if (termID == null) {
			this.dictionary.put(term, term_id);
			termID = term_id;
			term_id++;
		}
		return termID;
	}
	
	/**
	 * Get the termID for any term in the dictionary
	 * @param term
	 * @return if the term is not in the dictionary we return -1;
	 */
	public long getTermID(String term) {
		Long termID = this.dictionary.get(term);
		if (termID == null) {
			return (long) -1;
		}
		else return termID;
	}
	
	/**
	 * This method returns all of the terms in the dictionary sorted alphabetically
	 * This is a time consuming operation and should be used sparingly.
	 * @return This returns all of the termID's in the alphabetical order of the terms
	 */
	
	public Collection<String> getAllTerms() {
	    List<String> sorted = new ArrayList<String>(this.dictionary.keySet());
	    Collections.sort(sorted);
	    return sorted;		
	}
}
