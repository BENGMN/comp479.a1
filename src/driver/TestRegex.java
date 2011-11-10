package driver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import filters.CharacterFilter;
import filters.ReutersFilter;

public class TestRegex {

	private static String regex = "\\S*&lt;\\S+>\\S*";
	private static String one = "SALVADOR,";
	private static String two = "\"help";
	private static String three = "&#22;&#22;&#1;f0708&#31;reute"; 
	private static String four = "test";
	private static String five = "BC-STANDARD-OIL-&lt;SRD>-TO";
	private static String six = "&lt;BP>";
	
	private static String test = "-`(-|-_'**Saint. Lic-ios	trt";
	//private static char test ='"';
	//private static String test1 = test+"he	j";
	
	
	public static void main (String[] args) {

			//Pattern pattern = Pattern.compile(regex);
			//Matcher matcher = pattern.matcher(six);
		
		
			test = test.replaceAll("[\\.,:;`'\"~_|\\^\\$\\?\\+\\*-<>{}\\[\\]\\(\\)]", "");
			test = test.split("\\s");
			
			System.out.println(test);
		
			//System.out.println(matcher.matches());
			//System.out.println(matcher.replaceAll(""));
			
			//System.out.println(matcher.group(1));
			
			//System.out.println(Punctuation.removeCharFromEnd(one, ","));
			//System.out.println(Punctuation.removeCharFromBeginning(two, "\""));
	}
}
