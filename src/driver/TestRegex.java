package driver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

	private static String regex = "^< >";
	private static String one = "<DATE>26-FEB-1987 15:01:01.79</DATE>";
	private static String two = "<TOPICS><D>cocoa</D></TOPICS>";
	private static String three = "<PLACES><D>el-salvador</D><D>usa</D><D>uruguay</D></PLACES>"; 
	private static String four = "<PEOPLE></PEOPLE>"; 

	
	
	public static void main (String[] args) {
		
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(one);
			System.out.println(matcher.matches());
	}
}
