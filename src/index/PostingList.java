package index;

import java.util.TreeMap;
import java.util.HashSet;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.CaseInsensitiveMap;

public class PostingList {
	
	private TreeMap<String, TreeMap<Integer>> postings = new TreeMap<String, TreeMap<Integer>>(new CaseInsensitiveMap());

	public PostingList() {}
	
	public void add(String term, int docID) {
		// If the term we are looking for is in the list, append to the HashSet
		if (postings.containsKey(term)) {
			postings.get(term).add(docID);
		}
		else {
			
		}
	}
	
	
	
}
