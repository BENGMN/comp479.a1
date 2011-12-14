package utils;

import java.util.TreeSet;

public class SetOperation {

	public static TreeSet<Long> IntersectPL(TreeSet<Long> set1, TreeSet<Long> set2) {
		TreeSet<Long> set_intersection = new TreeSet<Long>();	
		for (Long i : set1) {
			if (set2.contains(i)) {
				set_intersection.add(i);
			}
		}
		return set_intersection;
	}
	
	public static TreeSet<Long> UnionPL(TreeSet<Long> set1, TreeSet<Long> set2) {
		TreeSet<Long> set_union = new TreeSet<Long>();	

		// iterate through the first set adding only elements not found to be in the second set
		for (Long i : set1) {
			if (!set2.contains(i)) {
				set_union.add(i);
			}
		}
		// iterate through the second set adding only elements not found to be in the first set
		for (Long i : set2) {
			if (!set1.contains(i)) {
				set_union.add(i);
			}
		}
		
		return set_union;
	}
}
