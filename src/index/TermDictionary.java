package index;

import java.util.ArrayList;
import java.util.HashMap;


public class TermDictionary {

	private HashMap<String, Long> dictionary = null;
	private Long term_id = new Long(0);
	
	public TermDictionary() {
		dictionary = new HashMap<String, Long>();
	}
	
	public TermDictionary(int size) {
		dictionary = new HashMap<String, Long>(size);
	}
	
	public void add(String term) {
		if (this.dictionary.get(term) == null) {
			this.dictionary.put(term, term_id);
			term_id++;
		}
	}
	
	/**
	 * Get the termID for any term in the dictionary
	 * @param term
	 */
	public long getTermID(String term) {
		if (this.dictionary.get(term) == null) {
			return (long) -1;
		}
		else return (long)this.dictionary.get(term);
	}
	
	public ArrayList<Long> getAllTermIDs() {
		ArrayList<Long> allIDs = new ArrayList<Long>(this.dictionary.size());
		for (Long termID : this.dictionary.values()) {
			allIDs.add(termID);
		}
		return allIDs;
	}
	
	public ArrayList<String> getAllTerms() {
		ArrayList<String> allTerms = new ArrayList<String>(this.dictionary.size());
		for (String term : this.dictionary.keySet()) {
			allTerms.add(term);
		}
		return allTerms;
	}
}
