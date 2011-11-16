package parsers;

import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import documents.AbstractDocument;
import documents.ReutersArticle;



public class SAXHandlerReuters extends DefaultHandler {
	
	// The title and body occurs after the text tag
	// for each document
	private boolean at_text = false;
	private boolean at_title = false;
	private boolean at_body = true;

	private int article_id = -1;
	private String title;
	private String body;
	
	private LinkedList<AbstractDocument> ra = new LinkedList<AbstractDocument>();
	
	public SAXHandlerReuters() {}
	
	public LinkedList<AbstractDocument> getDocuments() {
		return this.ra;
	}
	
	/**
	 * This method is used to turn all the flags for the tags of interest on
	 */
	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
		if (qName.equals("REUTERS")) {
			// grab the ID from the Reuters header
			this.article_id = Integer.parseInt(attributes.getValue("NEWID"));
			this.title = "";
			this.body = "";
		}

		if (qName.equalsIgnoreCase("TEXT")) {
			at_text = true;
		}
		
		if (qName.equalsIgnoreCase("TITLE")) {
			at_title = true;
		}
		
		if (qName.equalsIgnoreCase("BODY")) {
			at_body = true;
		}		
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
		// if we are inside a text tag and we find a title tag
		if (at_text && at_title) {
			this.title += new String (ch, start, length);
		}
		// if we are inside a text and we find a body tag
		if (at_text) {
			this.body += new String(ch, start, length);
		}
	}
	
	/**
	 * This method is used to turn all the flags for the tags of interest off
	 * and to pass the data along that we've accumulated somewhere useful
	 */
	public void endElement(String uri, String localName,String qName) throws SAXException {
		
		// When this is true we are at the end of an article
		if (qName.equalsIgnoreCase("TEXT")) {
			at_text = false;
			ra.add(new ReutersArticle(this.article_id, this.title, this.body));
			// DEBUG
			//System.out.println("Parsed article # "+this.article_id);
			// reset the local vars
			this.article_id = -1; 
			this.title = "";
			this.body = "";
		}
		
		if (qName.equalsIgnoreCase("TITLE")) {
			at_title = false;
		}
		
		if (qName.equalsIgnoreCase("BODY")) {
			at_body = false;
		}
	}
	/**
	public static void main (String[] args) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
		    saxParser.parse(new File("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/copies/reut2-000.xml"), new SAXHandlerReuters());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	**/
}
