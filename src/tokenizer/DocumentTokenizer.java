package tokenizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import documents.AbstractDocument;
import filters.IFilter;

public abstract class DocumentTokenizer {
		
	protected AbstractDocument document = null;
	protected LinkedList<IFilter> filters = new LinkedList<IFilter>();
	protected LinkedList<String> tokens = new LinkedList<String>();
	
		/**
		 * Make sure to set the filters in the sub-classes
		 * @param supply an article object
		 */
	public DocumentTokenizer(){}
	
	
	public DocumentTokenizer(AbstractDocument document){
		this.document = document;
	}
	
	public void setDocument(AbstractDocument document) {
		 this.document = document;
	 }
	 
	 public AbstractDocument getDocument() {
		 return this.document;
	 }
	
	public abstract void parse();
		
	/**
	 * All of the tokens from a document are provided in the order they were parsed
	 * @return an LinkedList of String objects
	 */
	public LinkedList<String> getTokens() {
		return this.tokens;
	}
		
	public boolean writeToFile(String output_path) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(output_path));
			for(String s : tokens) {
				out.write(s+" "+document.getDocumentID()+"\n");
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
