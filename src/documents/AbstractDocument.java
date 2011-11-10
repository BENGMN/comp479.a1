package documents;

import java.util.LinkedList;

public abstract class AbstractDocument {

		private int id;
		private String body;
		private LinkedList<String> tokens = new LinkedList<String>();
		
		/**
		 * Filters are expected to be provided by the sub-classes
		 * @param article_id
		 * @param body
		 */
		public AbstractDocument(int document_id, String body) {
			this.id = document_id;
			this.body = body;	
		}
		
		public abstract String getAllText();
		
		public int getDocumentID() {
			return this.id;
		}
				
		public String getBody() {
			return this.body;
		}
		
		public LinkedList<String> getTokens() {
			return this.tokens;
		}
		
		public void setTokens(LinkedList<String> tokens) {
			this.tokens = tokens;
		}
}
