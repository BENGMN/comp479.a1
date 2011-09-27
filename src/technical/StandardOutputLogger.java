package technical;

public class StandardOutputLogger extends Logger {
	
	@Override
	public void writeToLog(String logMessage) {
		System.out.println(currentTime() + ": " + logMessage);
	}
}
