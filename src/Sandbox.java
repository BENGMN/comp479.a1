import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import technical.Logger;

public class Sandbox {

	public static void main (String[] args) {
  	  //File test_file = new File ("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-000.sgm");
  	  //Logger.getUniqueInstance().writeToLog(f.isDirectory()+f.toString());
		//System.out.println(f.isDirectory()+f.toString());
		
		String[] regex = { "^<\\S*>.*","<\\S*>$","^<\\S*", "\\S*>$" };
		String[] input = { "<a>HeyBen", "<a", "a>", "</a>" }; //,"<PLACES><D>el-salvador</D><D>usa</D><D>uruguay</D></PLACES>", "<PEOPLE></PEOPLE>" };
		
		
		for (String r : regex) {
			Pattern pattern = Pattern.compile(r);
			for (String i : input) {
				Matcher matcher = pattern.matcher(i);
				System.out.println("Regex: " + r + "\t\tString: " + i + "\t\tMatches? " + matcher.matches());
			}
			
		}
	}
}
