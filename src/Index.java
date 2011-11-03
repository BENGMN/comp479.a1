import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class Index {

	public static void main (String[] args) {
		TreeSet<Long> id = new TreeSet<Long>();
		id.add((long) 10);
		id.add((long) 8);
		id.add((long) 6);
		id.add((long) 12);
		id.add((long) 1);
		
		Long[] i = new Long[id.size()];
		int ctr = 0;
		Iterator itr = id.iterator();
		while(itr.hasNext()) {
			i[ctr] = (Long)itr.next();
			//System.out.println(itr.next());
			ctr++;
		}
			}
	
}
