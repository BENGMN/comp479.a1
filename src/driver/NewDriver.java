package driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import documents.AbstractDocument;

import parsers.SAXHandlerReuters;
import spimi.SPIMInvert;
import tokenizer.DocumentTokenizer;
import tokenizer.ReutArticleTokenizer;

public class NewDriver {

	public static void main (String[] args) throws ParserConfigurationException {
	    try {
			// Get a list of files that need to be parsed
	    	String documentCollection = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/copies";
	    	File root = new File(documentCollection);
	    	File[] all_files = root.listFiles();
	    	
	    	System.out.println("All files successfully gathered");
	    	
	    	// Create a new SAX Parser for which we have created a custom handler
	    	// The handler contains a list of AbstractDoucments which have been parsed
	    	// out of the file specified in the parse function
	    	SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	    	SAXHandlerReuters handler = new SAXHandlerReuters();
	    	
	    	// Now that we have a list of files and a parser we can parse them
	    	// and place each article into it's own object which we can then tokenize
	    	for (File f : all_files) {
	    		// make sure we only parse XML files
	    		if (f.getAbsolutePath().endsWith("xml")) {
	    			System.out.println("Processing file: "+f.getName());
	    			InputStream inputStream = new FileInputStream(f);
	    			Reader reader = new InputStreamReader(inputStream,"UTF-8");
	    			InputSource is = new InputSource(reader);
	    			is.setEncoding("UTF-8");
	    			saxParser.parse(is, handler);
	    		}
	    	}
	    	
	    	System.out.println("All files have been parsed");
	    	
	    	// Now that the handler is full of articles that need parsing we
	    	// get to work on that
	    	DocumentTokenizer tokenizer = new ReutArticleTokenizer();
	    	
	    	for (AbstractDocument d : handler.getDocuments()) {
	    		tokenizer.setDocument(d);
	    		tokenizer.parse();
	    	}

	    	System.out.println("All files have been tokenized");
	    	// At this point every AbstractDocument in the handlers collection
	    	// should have it's token attribute full of tokens
	    	// I guess it's time to build an index!
	    	
	    	// create a SPIMI Inverter
	    	SPIMInvert spimi_inverter = new SPIMInvert();
	    	
	    	// Send every single token from all documents into the inverter
	    	for (AbstractDocument d : handler.getDocuments()) {
	    		for(String token : d.getTokens()) {
	    			if (spimi_inverter.addToBlock(token, d.getDocumentID())) {
	    				// keep iterating and adding
	    			}
	    			else {
	    				spimi_inverter.flushBlock(); // flush the block
	    				spimi_inverter.getNewBlock(); // get a new one
	    				spimi_inverter.addToBlock(token, d.getDocumentID()); // add again
	    			}
	    		}
	    	}
	    	
	    	System.out.println("All files have gone through the spimi inverter");
	    	
	    	//spimi_inverter.mergeBlocks(input_locations, output_location)()
	    } 
	    
	    catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
