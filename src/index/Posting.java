package index;

import java.util.Iterator;
import java.util.TreeSet;

public class Posting {

	private long term_id;
	private TreeSet<Long> posting_list = new TreeSet<Long>(); // document ID's
	
	public Posting(long term_id) {
		this.term_id = term_id;
	}
	
	public void add(long document_id) {
		this.posting_list.add(document_id);
	}
	
	public Long[] getAllPostings() {
		Long[] tmp = new Long[posting_list.size()];
		Iterator<Long> itr = posting_list.iterator();
		int ctr = 0;
		while(itr.hasNext()) {
			tmp[ctr] = itr.next();
			ctr++;
		}
		
		return tmp;
	}

	public long getTermID() {
		return term_id;
	}

	public void setTermID(long term_id) {
		this.term_id = term_id;
	}
	
}
