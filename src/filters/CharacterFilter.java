package filters;

public class CharacterFilter implements IFilter {
	
	private static final String[] CHARS_TO_STRIP_B = {" ", ".", "\"", "(", ")", "?", "*", "+", ",", "-", ":", "<", ">", "{", "}"};
	private static final String[] CHARS_TO_STRIP_E = {" ", ".", "\"", "(", ")", "?", "*", "+", ",", "-", ":", "<", ">", "{", "}"};
	
	public CharacterFilter() {}
	
	private static String removeCharFromBeginning(String input) {
		int i = 0;
		
		while(i < input.length()) {
			for (String s : CHARS_TO_STRIP_B) {
				if(input.substring(i, i+1).equals(s)) {
					i++;
					if (i == input.length()) {
						break;
					}
				}
			}
			break;
		}
		if (i == input.length()) { 
		  return "";
		}
		else {
		  return input.substring(i, input.length()); // substring is non inclusive at the end
		}
	}

	private static String removeCharFromEnd(String input) {
		int i = input.length();
		// Important to check that i is > 0 before calling sub-string
		while( i > 0) { //.equals(remove_char)) {
			for(String s : CHARS_TO_STRIP_E) {
				if  (input.substring(i-1,i).equals(s)) {
					i--;
					if (i == 0) {
						break;
					}
				}
			}
			break;
		}
		if (i == 0) { 
			  return "";
		}
		else {
			return input.substring(0, i);
		}
	}

	@Override
	public String process(String term) {
		String clean = removeCharFromBeginning(term);
	    clean = removeCharFromEnd(clean);
		return clean;
	}
}
