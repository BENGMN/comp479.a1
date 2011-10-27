package driver;

import java.util.Arrays;

import parsers.ReutersSGML;

public class Sand {
 public static void main (String[] args) {
	 /**
	 String[] s = {"UPPE-RCASE", "LOWER-CASE"};
	 Arrays.sort(s);
	 System.out.println(s[0]);
	 **/
	 
	 ReutersSGML r = new ReutersSGML("/media/320/Users/Ben/School/Concordia University/Classes/COMP 479 (Information Retrieval)/code/reuters/reut2-006.sgm", 1);
	 r.parse();
 }
}
