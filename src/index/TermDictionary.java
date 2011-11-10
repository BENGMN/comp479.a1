package index;

import java.util.TreeMap;

public class TermDictionary {

	private TreeMap<String, Long> dictionary = null;
	private long term_id = 0;
	
	public TermDictionary() {
		dictionary = new TreeMap<String, Long>();
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
