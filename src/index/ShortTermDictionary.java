package index;

import java.util.HashMap;


public class ShortTermDictionary {

	private HashMap<String, Integer> dictionary = null;
	private int term_id = 0;
	
	public ShortTermDictionary() {
		dictionary = new HashMap<String, Integer>();
	}
	
	public ShortTermDictionary(int size) {
		dictionary = new HashMap<String, Integer>(size);
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
}
