package filters;

public class Punctuation {

	public static String removeCharFromEnd(String input, String remove_char) {
		int i = input.length();
		// Important to check that i is > 0 before calling sub-string
		while( i > 0 && input.substring(i-1,i).equals(remove_char)) {
			i--;
		}
		return input.substring(0,i);
	}
	
	public static String removeCharFromBeginning(String input, String remove_char) {
		int i = 0;
		while(i < input.length() && input.substring(i, i+1).equals(remove_char)) {
			i++;
		}
		return input.substring(i, input.length());
	}
	
}
