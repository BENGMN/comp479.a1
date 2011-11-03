package index;

import java.util.TreeMap;

public class TermDictionary {

	private TreeMap<String, Long> dictionary = null;
	private long term_id = 0;
	
	public TermDictionary() {
		dictionary = new TreeMap<String, Long>();
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
	
	/**
	 * @return This returns all of the termID's in the alphabetical order of the terms
	 */
	public Long[] getAllTermIDs() {
		Long[] allIDs = new Long[this.dictionary.size()];
		int ctr = 0;
		for (String term : this.dictionary.keySet()) {
			allIDs[ctr] = this.dictionary.get(term);
			ctr++;
		}
		return allIDs;
	}
	
	public String[] getAllTerms() {
		String[] allTerms = new String[this.dictionary.size()];
		int ctr = 0;
		for (String term : this.dictionary.keySet()) {
			allTerms[ctr] = term;
			ctr++;
		}
		return allTerms;
	}
}
