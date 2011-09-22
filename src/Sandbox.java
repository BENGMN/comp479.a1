import java.io.File;


public class Sandbox {

	public static void main (String[] args) {
		File f = new File("/home/ben/Desktop");
		System.out.println(f.isDirectory()+f.toString());
	}
}
