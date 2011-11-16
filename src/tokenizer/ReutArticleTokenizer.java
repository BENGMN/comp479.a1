package tokenizer;

import documents.AbstractDocument;
import filters.CaseFoldingFilter;
import filters.IFilter;
import filters.PunctuationFilter;
import filters.ReutersFilter;

public class ReutArticleTokenizer extends DocumentTokenizer {
	/** DIRECTLY ACCESSIBLE ATTRIBUTES OF THE PARENT CLASS
	AbstractDocument article
	LinkedList<IFilter> filters
	LinkedList<String> tokens
	**/
	
	public ReutArticleTokenizer(){
		 super();
		 filters.add(new ReutersFilter());
		 filters.add(new PunctuationFilter());
		 filters.add(new CaseFoldingFilter("down"));
	}
	
	public ReutArticleTokenizer(AbstractDocument article){
		 super(article);
		 filters.add(new ReutersFilter());
		 filters.add(new PunctuationFilter());
		 filters.add(new CaseFoldingFilter("down"));
	}
	

	@Override
	public void parse() {
		// get all the terms by splitting the document text on spaces
		String[] terms = this.document.getAllText().split("\\s");
		
		for (String t : terms) {
			// Do some post-processing on the string to clean it up
			for (IFilter f : this.filters) {
				t = f.process(t);
			}

			// If we're not left with an empty string we add it to the output
			if (!t.isEmpty()) {
				this.tokens.add(t);
				
				//Logger.getUniqueInstance().writeToLog(t); //*** remove me
			}
		}
		
		this.document.setTokens(this.tokens);
		
	}
}
