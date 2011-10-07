package filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sgml {

	String regex = "^<>";
	
	public Sgml(String data) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
	}
}

/**
 * If we find an < bracket we disregard everything else until we find the closing bracket
 * <a
 * a>
 * <a>
 * </a>
 */