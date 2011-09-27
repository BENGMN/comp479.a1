package technical;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogger extends Logger {

	private final String fileName = "history.log";
	
	@Override
	public void writeToLog(String logMessage) {
		try { 
			FileWriter fw = new FileWriter(fileName, true);
			fw.write(Logger.currentTime() + ": " + logMessage + "\n");
			fw.flush();
			fw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
