package filters;

public class NumberFilter implements IFilter {

	@Override
	public String process(String term) {
		return term.replaceAll("\\d", "");
	}

}
