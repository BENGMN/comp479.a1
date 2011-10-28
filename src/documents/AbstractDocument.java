package documents;

import java.util.LinkedList;

public abstract class AbstractDocument {

		private int id;
		private String body;
		private LinkedList<String> terms = new LinkedList<String>();
		
		/**
		 * Filters are expected to be provided by the sub-classes
		 * @param article_id
		 * @param body
		 */
		public AbstractDocument(int article_id, String body) {
			this.id = article_id;
			this.body = body;	
		}
		
		public abstract String getAllText();
		
		public int getArticleID() {
			return this.id;
		}
				
		public String getBody() {
			return this.body;
		}
		
		public LinkedList<String> getTokens() {
			return this.terms;
		}
		
		public void setTokens(LinkedList<String> tokens) {
			this.terms = tokens;
		}
}
