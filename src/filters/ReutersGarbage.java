package filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReutersGarbage {

	private static String REGEX_1 = "\\S*&#\\d+;";
	private static String REGEX_2 = "\\S*&lt;\\S*";
	
	
	public static String remove(String input) {
	  Pattern pattern = null;
	  Matcher matcher = null;
	  
	  // Replace all matching REGEX_1
	  pattern = Pattern.compile(REGEX_1);
	  matcher = pattern.matcher(input);
	  input = matcher.replaceAll("");

	  // Replace all matching REGEX_2
	  pattern = Pattern.compile(REGEX_2);
	  matcher = pattern.matcher(input);
	  input = matcher.replaceAll("");
	 
	  return input;
	}
}
