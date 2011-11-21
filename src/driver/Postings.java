package driver;

import java.util.TreeSet;

import technical.Merge;

public class Postings {

	public static void main(String[] args) {
		TreeSet<Long> l1 = new TreeSet<Long>();
		TreeSet<Long> l2 = new TreeSet<Long>();
		
		l1.add((long) 1);
		l1.add((long) 2);
		l1.add((long) 3);
		l1.add((long) 4);
		l1.add((long) 5);
		l1.add((long) 6);
		
		l2.add((long) 5);
		l2.add((long) 6);
		l2.add((long) 7);
		l2.add((long) 8);
		l2.add((long) 9);
		l2.add((long) 10);
		l2.add((long) 11);
		l2.add((long) 12);
		

		TreeSet<Long> l3 = Merge.PostingsList(l1, l2);
		
		for(Long i : l3) {
			System.out.print(" "+i);
		}
	}
	
	
	
}
