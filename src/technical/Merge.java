package technical;

import java.util.TreeSet;

public class Merge {

	public static TreeSet<Long> PostingsList(TreeSet<Long> set1, TreeSet<Long> set2) {
		TreeSet<Long> set_union = new TreeSet<Long>();	
		for (Long i : set1) {
			if (set2.contains(i)) {
				set_union.add(i);
			}
		}
		return set_union;
	}
}
