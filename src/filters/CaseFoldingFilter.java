package filters;

public class CaseFoldingFilter implements IFilter {

	private String upOrDown = "";
	
	public CaseFoldingFilter(String upOrDown) {
		this.upOrDown = upOrDown;
	}
	
	@Override
	public String process(String term) {
		if (this.upOrDown.equalsIgnoreCase("up")) {
			return term.toUpperCase();
		}
		else {
			return term.toLowerCase();	
		}
	}

}
