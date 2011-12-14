package retrieval;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import utils.SetOperation;

public class BooleanRetrieval {
	
	public BooleanRetrieval() {
		
	}
	
	public static Set<Long> Union(Map<String, TreeSet<Long>> posting_lists) {
		// Time to take the union of the results
    	TreeSet<Long> union_set = new TreeSet<Long>();
    	
    	// get a handle to postings lists that have been matched
    	ArrayList<String> keys = new ArrayList<String>();
    	
    	for(String s : posting_lists.keySet()) {
    		keys.add(s);
    	}
    	
    	// iterate through the postings lists merging them
    	// check to make sure we have more than 1 list to merge
    	
    	for(int i = 0; i < keys.size() -1; i++) {
    		if (i == 0) {
    			union_set = SetOperation.UnionPL(posting_lists.get(keys.get(i)), posting_lists.get(keys.get(i+1))); 
    		}
    		else {
    			union_set = SetOperation.UnionPL(union_set, posting_lists.get(keys.get(i+1)));
    		}
    	}
    	
    	return union_set;
	}
	
	public static Set<Long> Intersection(Map<String, TreeSet<Long>> posting_lists) {
		// Time to take the union of the results
    	TreeSet<Long> intersect_set = new TreeSet<Long>();
    	
    	// get a handle to postings lists that have been matched
    	ArrayList<String> keys = new ArrayList<String>();
    	
    	for(String s : posting_lists.keySet()) {
    		keys.add(s);
    	}
    	
    	// iterate through the postings lists merging them
    	// check to make sure we have more than 1 list to merge
    	
    	for(int i = 0; i < keys.size() -1; i++) {
    		if (i == 0) {
    			intersect_set = SetOperation.IntersectPL(posting_lists.get(keys.get(i)), posting_lists.get(keys.get(i+1))); 
    		}
    		else {
    			intersect_set = SetOperation.IntersectPL(intersect_set, posting_lists.get(keys.get(i+1)));
    		}
    	}
    	
    	return intersect_set;
	}

}
