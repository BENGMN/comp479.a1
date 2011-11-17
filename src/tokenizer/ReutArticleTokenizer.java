package tokenizer;

import spimi.SPIMInvert;
import filters.CaseFoldingFilter;
import filters.IFilter;
import filters.PunctuationFilter;
import filters.ReutersFilter;

public class ReutArticleTokenizer extends DocumentTokenizer {
	/** DIRECTLY ACCESSIBLE ATTRIBUTES OF THE PARENT CLASS
	AbstractDocument article
	LinkedList<IFilter> filters
	LinkedList<String> tokens
	protected long no_tokens_before_filters = 0;
	protected long no_tokens_after_filters = 0;
	**/
	private SPIMInvert spimi = null; // store a local reference to the SPIMI object
	
	public ReutArticleTokenizer(SPIMInvert spimi){
		 super();
		 filters.add(new ReutersFilter());
		 filters.add(new PunctuationFilter());
		 filters.add(new CaseFoldingFilter("down"));
		 this.spimi = spimi;
	}
	

	@Override
	/**
	 * Parse the documents into individual tokens and insert them into the index immediately
	 */
	public void parse() {
		// get all the terms by splitting the document text on spaces
		String[] terms = document.getAllText().split("\\s");
		
		// count the number of terms before filtering
		no_tokens_before_filters = terms.length;
		
		// for every term apply the filter then add it to the index
		for (String t : terms) {
			// Do some post-processing on the string to clean it up
			for (IFilter f : this.filters) {
				t = f.process(t);
			}

			// If we're not left with an empty string we add it to the output
			if (!t.isEmpty()) {
				tokens.add(t);
				spimi.addToBlock(t, document.getDocumentID());
				no_tokens_after_filters += 1; // increment the number of token after parsing
			}
		}
		
		// We keep a running total for all documents in the collection
		stats.setTerms(stats.getTerms()+terms.length);
		stats.setTokens(stats.getTokens()+no_tokens_after_filters);
	}
}
