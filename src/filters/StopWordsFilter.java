package filters;

public class StopWordsFilter implements Filter {
// private static final String THIRTY = "the be to of and a in that have I it for not on with he as you do at this but his by from they we say her she";
	private String[] stop_words = null;

	public StopWordsFilter(String stop_words) {
		this.stop_words = stop_words.split(" ");
	}
	
	@Override
	public String process(String term) {
		String tmp = term;
		for (String sw : this.stop_words) {
			if (term.equalsIgnoreCase(sw)) {
				tmp = "";
			}
		}
		return tmp;
	}
}
