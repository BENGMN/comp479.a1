package filters;

public class PunctuationFilter implements IFilter {
	
	private static final String[] CHARS_TO_STRIP_B = {" ", ".", "\"", "(", ")", "?", "+", ",", "-", ":", "<", ">", "{", "}"};
	private static final String[] CHARS_TO_STRIP_E = {" ", ".", "\"", "(", ")", "?", "+", ",", "-", ":", "<", ">", "{", "}"};
	
	public PunctuationFilter() {}
	
	private static String removeCharFromBeginning(String input) {
		return "";
	}

	private static String removeCharFromEnd(String input) {
		return "";
	}

	@Override
	public String process(String term) {
		return term.replaceAll("[\\.,:;`'\"~_=|&\\^\\$\\?\\+\\*-<>{}\\[\\]\\(\\)]", "");
	}
}
