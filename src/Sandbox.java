import java.io.File;
import technical.Logger;

public class Sandbox {

	public static void main (String[] args) {
		File f = new File("/home/ben/Desktop");
		Logger.getUniqueInstance().writeToLog(f.isDirectory()+f.toString());
		//System.out.println(f.isDirectory()+f.toString());
	}
}
