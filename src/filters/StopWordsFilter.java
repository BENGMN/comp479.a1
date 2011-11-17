package filters;

public class StopWordsFilter implements IFilter {
	
	private final String stop_words_30 = "the be to of and a in that have I it for not on with he as you do at this but his by from they we say her she";
	private final String stop_words_174 = "i me my myself we our ours ourselves you your yours "+
										"yourself yourselves he him his himself she her hers herself it "+
									 	"its itself they them their theirs themselves what which who whom "+
									 	"this that these those am is are was were be been being have has "+
									 	"had having do does did doing would should could ought i'm "+
									 	"you're he's she's it's we're they're i've you've we've they've "+
									 	"i'd you'd he'd she'd we'd they'd i'll you'll he'll she'll we'll "+
									 	"they'll isn't aren't wasn't weren't hasn't haven't hadn't "+
									 	"doesn't don't didn't won't wouldn't shan't shouldn't can't "+
									 	"cannot couldn't mustn't let's that's who's what's here's "+
									 	"there's when's where's why's how's a an the and but if or "+
									 	"because as until while of at by for with about against between "+
									 	"into through during before after above below to from up down in "+
									 	"out on off over under again further then once here there when "+
									 	"where why how all any both each few more most other some such "+
									 	"no nor not only own same so than too very";
	
	private String[] stop_words = null;

	/**
	 * Prepares the filter for processing
	 * @param number_stop_words specify either 50 or 174;
	 */
	public StopWordsFilter(int number_stop_words) {
		switch(number_stop_words) {
		case 30: stop_words = stop_words_30.split("\\s");
			break;
		case 174: stop_words = stop_words_174.split("\\s");
			break;
		default: stop_words = stop_words_30.split("\\s");
			break;
		}
	}
	
	@Override
	public String process(String term) {
		for (String sw : this.stop_words) {
			if (term.equalsIgnoreCase(sw)) {
				return "";		
			}
		}
		return term;
	}
}
