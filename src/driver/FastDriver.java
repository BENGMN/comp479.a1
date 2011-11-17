package driver;

    import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.Reader;
    import java.util.TreeMap;
    import java.util.TreeSet;

	import javax.xml.parsers.ParserConfigurationException;
	import javax.xml.parsers.SAXParser;
	import javax.xml.parsers.SAXParserFactory;

	import org.xml.sax.InputSource;
	import org.xml.sax.SAXException;

	import documents.AbstractDocument;

	import parsers.SAXHandlerReuters;
	import spimi.SPIMInvert;
	import technical.DateUtils;
	import tokenizer.DocumentTokenizer;
    import tokenizer.ReutArticleTokenizer;
	

	public class FastDriver {

		public static void main (String[] args) throws ParserConfigurationException {
		    try {
		    	    	
				// Get a list of files that need to be parsed
		    	String documentCollection = "/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/copies";
		    	File root = new File(documentCollection);
		    	File[] all_files = root.listFiles();
		    	
		    	// Create a new SAX Parser for which we have created a custom handler
		    	// The handler contains a list of AbstractDoucments which have been parsed
		    	// out of the file specified in the parse function
		    	SAXParserFactory factory = SAXParserFactory.newInstance();
		    	SAXParser saxParser = factory.newSAXParser();
		    	SAXHandlerReuters handler = new SAXHandlerReuters();
		    	
		    	// Now that we have a list of files and a parser we can parse them
		    	// and place each article into it's own object which we can then tokenize
		    	
		    	System.out.println("Begin gathering and parsing all files \t\t"+DateUtils.now());
		    	int file_ctr = 0;
		    	
		    	for (File f : all_files) {
		    		// make sure we only parse XML files
		    		if (f.getAbsolutePath().endsWith("xml")) {
		    			//System.out.println("Processing file: "+f.getName());
		    			InputStream inputStream = new FileInputStream(f);
		    			Reader reader = new InputStreamReader(inputStream,"UTF-8");
		    			InputSource is = new InputSource(reader);
		    			is.setEncoding("UTF-8");
		    			saxParser.parse(is, handler);
		    			file_ctr++;
		    		}
		    	}
		    	System.out.println(file_ctr+" files successfully gathered\t\t\t"+DateUtils.now());
		    	System.out.println(handler.getDocuments().size()+" articles have been parsed\t\t\t"+DateUtils.now());
		    	
		    	// Now that the handler is full of articles that need parsing we
		    	// get to work on that and we add the items to our SPIMI index from within the tokenizer for speed
		    	
		    	SPIMInvert spimi = new SPIMInvert();
		    	
		    	// Create a tokenizer and pass the SPIMI object
		    	DocumentTokenizer tokenizer = new ReutArticleTokenizer(spimi);
		    	
		    	// Add some stats in
		    	tokenizer.getStats().setFiles(file_ctr);
		    	tokenizer.getStats().setDocuments(handler.getDocuments().size());
		    	
		    	System.out.println("Begin Indexing Process\t\t\t\t"+DateUtils.now());
		    	
		    	// Loop through every document, tokenize it and add it to the index
		    	for (AbstractDocument d : handler.getDocuments()) {
		    		tokenizer.setDocument(d);
		    		tokenizer.parse(); 				// this adds to the index too
		    	}
		    	
		    	spimi.flushBlock(); // just in case we didn't reach the buffer limit

		    	System.out.println("All files have been tokenized and Indexed\t"+DateUtils.now());
		    	System.out.println("Begin merging the indexes\t\t\t"+DateUtils.now());
		    	
		    	File input_files = new File(documentCollection+"/index_files");
		    	TreeMap<String, TreeSet<Long>> complete_index = spimi.mergeBlocks(input_files.list(), documentCollection+"/spimi_index/s_index.txt");
		    	tokenizer.getStats().setDistinctTerms(complete_index.size());
		    	
		    	
		    	System.out.println("Index Merged\t\t\t\t\t"+DateUtils.now());
		    	System.out.println("################ Statistics ################");
		    	System.out.println("Number of Files\t\t\t"+tokenizer.getStats().getFiles());
		    	System.out.println("Number of Documents\t\t"+tokenizer.getStats().getDocuments());
		    	System.out.println("Number of Terms\t\t\t"+tokenizer.getStats().getTerms());
		    	System.out.println("Number of NonPosPostings\t"+tokenizer.getStats().getNonPosPostings());
		    	System.out.println("Number of Distinct Terms\t"+tokenizer.getStats().getDistinctTerms());
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
