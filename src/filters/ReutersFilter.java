package filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReutersFilter implements IFilter {
	
	private final String REGEX_1 = "\\S*&#\\d+;";
	private final String REGEX_2 = "\\S*&lt;\\S*";
	
	
	
	public String process(String input) {
	  Pattern pattern = null;
	  Matcher matcher = null;
	  
	  // Replace all matching REGEX_1
	  pattern = Pattern.compile(REGEX_1 +"|"+REGEX_2);
	  matcher = pattern.matcher(input);
	  input = matcher.replaceAll("");
	  
	  return input;
	}
}
