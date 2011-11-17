package statistics;

public class CorpusStats {

	private long files    = 0; // # of files in the collection
	private long documents = 0; // # of documents in the collection
	private long terms    = 0; // # of terms after parsing before filtering
	private long tokens   = 0; // # of tokens after filtering
	
	public CorpusStats() {}

	public long getFiles() {
		return files;
	}

	public void setFiles(long number_of_files) {
		this.files = number_of_files;
	}
	
	public long getDocuments() {
		return documents;
	}

	public void setDocuments(long number_of_documents) {
		this.documents = number_of_documents;
	}

	public long getTerms() {
		return terms;
	}

	public void setTerms(long number_of_terms) {
		this.terms = number_of_terms;
	}

	public long getTokens() {
		return tokens;
	}

	public void setTokens(long number_of_tokens) {
		this.tokens = number_of_tokens;
	}
	
	
}
