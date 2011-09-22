import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;

public class Driver {

	public static void main(String[] args) {
		File corpus = new File("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters");
		File[] files = corpus.listFiles();
		for(File f : files) {
			try {
				int file_size = (int) f.length();
				Reader input = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				char[] output = new char[file_size];
				while (input.read(output, 0, file_size) != -1) {
					int N = 0;
					char delimiter = ' ';
					// calculating the number of delimiters and thus tokens
					for(int i = 0; i < output.length; i++) {
						if (output[i] == delimiter) {
							N++;
						}
					}
					// parse N+1 documents
					String[] tokens = new String[N+1]; 
					int left = 0, right = 0;
					for(int i = 0; i < N; i++) {
							while(output[right] != delimiter) {
								right++;
							}
							StringBuilder token = new StringBuilder(right-left);
							token.append(output, left, right-left);
							tokens[i] = token.toString();
							right++;
							left = right;
					}
					StringBuilder token = new StringBuilder(output.length-right);
					token.append(output, right, output.length-right);
					tokens[N] = token.toString();
					
					for(String s : tokens) {
						System.out.println(s);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
