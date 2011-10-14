package filters;

public class Punctuation {
	
	//private static String[] CHARS_TO_STRIP = {".", "\"", "(", ")", "?", "+", ",", "-", ":", "<", ">", "{", "}"};

	public Punctuation() {}
	
	public static String removeCharFromBeginning(String input) {
		String[] CHARS_TO_STRIP = {".", "\"", "(", ")", "?", "+", ",", "-", ":", "<", ">", "{", "}"};
		int i = 0;
		
		while(i < input.length()) {
			for (String s : CHARS_TO_STRIP) {
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

	public static String removeCharFromEnd(String input) {
		String[] CHARS_TO_STRIP = {".", "\"", "(", ")", "?", "+", ",", "-", ":", "<", ">", "{", "}"};
		int i = input.length();
		// Important to check that i is > 0 before calling sub-string
		while( i > 0) { //.equals(remove_char)) {
			for(String s : CHARS_TO_STRIP) {
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
}
