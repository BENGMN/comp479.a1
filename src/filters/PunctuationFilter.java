package filters;

public class PunctuationFilter implements IFilter {
	
	public PunctuationFilter() {}

	@Override
	public String process(String term) {
		return term.replaceAll("[\\.,:;`'\"~_=|&\\^\\$\\?\\+\\*-<>{}\\[\\]\\(\\)]", "");
	}
}
