package documents;

public abstract class AbstractDocument {

		private int id;
		private String body;		
		
		/**
		 * Set the document ID and the main body of the text
		 * @param document_id
		 * @param body
		 */
		public AbstractDocument(int document_id, String body) {
			this.id = document_id;
			this.body = body;	
		}
		
		/**
		 * Abstract method that allows a sublcass to include any other relevant text accumulated during parsing
		 * @return a string representing all of the text in the document
		 */
		public abstract String getAllText();
		
		/**
		 * Get the document ID for a particular document
		 * @return
		 */
		public int getDocumentID() {
			return this.id;
		}
		
		/**
		 * Note this may not return all of the text available within a document
		 * @return a string representing the body of text associated with a document
		 */
		public String getBody() {
			return this.body;
		}
}
