import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

/**
 * This is a singleton implementation of a logger.
 * It will allow other classes to write out their status to a file
 * as the program is running.
 * 
 * @author sgb
 *
 */
public class Logger {
	public static Logger instance;
	private FileWriter fileWriter;
	private static String fileName;
	private File log;
	private String message;
	
	private Logger(String message) {
		instance = this;
		this.message = message;
		fileName = "log.txt";
		log = new File(fileName);
		
		try {
			fileWriter = new FileWriter(log);
		} catch (IOException e) {
			System.out.println("ERROR: IO Exception on Logger");
		}
	}
	
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger("You haven't instantiated the Logger yet.");
		}
		return instance;
	}
	
	public void printToLog() {
		try {
			FileWriter fileWriter = new FileWriter(Logger.fileName, true);
			fileWriter.write(Instant.now() + "::" + message);
			fileWriter.write(String.format("%n"));
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("ERROR: Could not write to log file.");
		}
		
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
