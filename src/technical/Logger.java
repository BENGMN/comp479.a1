package technical;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public abstract class Logger {
	
	private static Logger uniqueInstance = null;
	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	
	protected Logger() {};
	
	public static synchronized Logger getUniqueInstance() {
		if (uniqueInstance == null) {
			Properties properties = new Properties();
			try {
				FileInputStream fis = new FileInputStream("reverseIndex.properties");
				properties.load(fis);
				String classNeededToLoad = properties.getProperty("LoggerClass");
				uniqueInstance = (Logger) Class.forName(classNeededToLoad).newInstance();
			}
			catch (Exception e) {};
		}
		return uniqueInstance;
	}
	
	/** 
	 * Override this method in the subClasses
	 * @param logMessage
	 */
	public abstract void writeToLog(String logMessage);
	
	protected static String currentTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
}
