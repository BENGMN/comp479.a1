package tokenizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import statistics.CorpusStats;

import documents.AbstractDocument;
import filters.IFilter;

public abstract class DocumentTokenizer {
		
	protected AbstractDocument document = null;
	protected LinkedList<IFilter> filters = new LinkedList<IFilter>();
	protected long no_tokens_before_filters = 0;
	protected long no_tokens_after_filters = 0;
	protected CorpusStats stats;
	
	// Keep a list of tokens generated locally to later set the document tokens with
	protected LinkedList<String> tokens = new LinkedList<String>();
	
		/**
		 * Make sure to set the filters in the sub-classes
		 * @param supply an article object
		 */
	public DocumentTokenizer(){
		stats = new CorpusStats();
	}
	
	public void setDocument(AbstractDocument document) {
		 this.document = document;
		 no_tokens_before_filters = 0;
		 no_tokens_after_filters = 0;
	 }
	 
	 public AbstractDocument getDocument() {
		 return this.document;
	 }
	
	public abstract void parse();
	
	public CorpusStats getStats() {
		return this.stats;
	}
	
	/**
	 * Write all of the token/document ID pairs to a file
	 * @param output_path file path where the token, doc ID pairs should be written
	 * @param tokens LinkedList of tokens to be written
	 * @return True is returned if the tokens have been successfully written. False is returned if the specified file
	 * path causes an IO Exception to be raised
	 */
	public boolean writeToFile(String output_path, AbstractDocument document) {
		try {
			
			long docID = document.getDocumentID();
			BufferedWriter out = new BufferedWriter(new FileWriter(output_path));
			
			for(String s : tokens) {
				out.write(s+" "+docID+"\n");
			}
			
			return true;
			
		} catch (IOException e) {
			return false;
		}
	}
}
