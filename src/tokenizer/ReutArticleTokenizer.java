package tokenizer;

import technical.Logger;
import documents.AbstractDocument;
import filters.CaseFoldingFilter;
import filters.IFilter;
import filters.CharacterFilter;
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
		 filters.add(new CharacterFilter());
		 filters.add(new CaseFoldingFilter("down"));
	}
	
	public ReutArticleTokenizer(AbstractDocument article){
		 super(article);
		 filters.add(new ReutersFilter());
		 filters.add(new CharacterFilter());
		 filters.add(new CaseFoldingFilter("down"));
	}
	
	@Override
	public void parse() {
		char[] data = this.document.getAllText().toCharArray();
		// Read the articles into memory at once since they are tiny
			  
		// Keep track of the bounds of tokens
		int left = 0, right = 0;
			 
		// Loop through the characters omitting any information that occurs between < >
		for(int i = 0; i < data.length; i++) {
			while(true) {
				if (data[i] == '<') {
					while (i < data.length && data[i] != '>') {
						i++;
					}
					// make certain that we haven't over-stepped our boundaries
					if (i >= data.length) {
						break;
					}
				}
							
				if (data[i] == '>') {
					i++; // if we've gotten here we increment since the current character is >
				}
							
				if (i < data.length && data[i] != '<') {
					// if the next character is an opening bracket we keep looping up here
					break;
				}
			}

			// make certain that we haven't over-stepped our boundaries once again
			if (i >= data.length) {
				break;
			}
			// Finally we are no longer inside a tag
			boolean build_token = false;

			// If we are not on whitespace
			if (!Character.isWhitespace(data[i])) {
				left = i; // take note of the LHS boundary of the term
				// Find the RHS of the term
				// if this loop is never entered we found another tag and go back to the top
				// if this loop is entered and we find whitespace or another <, we form a term
				while(i < data.length && !Character.isWhitespace(data[i]) && data[i] != '<') {
					i++;
					build_token = true;
				}
				while(build_token) {
					right = i;
					StringBuilder term = new StringBuilder(right-left);
					term.append(data, left, right-left);
					String token = term.toString();

					// Do some post-processing on the string to clean it up
					for (IFilter f : this.filters) {
						token = f.process(token);
					}

					// If we're not left with an empty string we add it to the output
					if (!token.isEmpty()) {
						this.tokens.add(token);
						Logger.getUniqueInstance().writeToLog(token); //*** remove me
					}

					i--; // decrement the counter to make sure we don't skip ahead when for loop increments next.
					build_token = false; // break the loop 
				}
			}
		}
		this.document.setTokens(this.tokens);
	}

}
