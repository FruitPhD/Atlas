package core;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log
{
	private static final Logger LOG = Logger.getLogger(Reference.LOGGER_NAME);
	private static List logs;
	private static int logCount = 0;
	
	public static void init()
	{
		LOG.setLevel(Level.CONFIG);
		logs = new ArrayList();
	}
	
	/** Prints the crash report to the file */
	public static void crash()
	{
		try {
			FileHandler fileTxt;
			SimpleFormatter formatTxt;
			
			String file = "CRASH REPORT " + getTimeStamp();
			fileTxt = new FileHandler(Reference.LOGGER_FILE + file + ".txt");
			formatTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatTxt);
			
			PrintWriter write = new PrintWriter("file");
			write.println(file + "\tWell that didn't go well did it?");
			write.println();
			Iterator iter = logs.iterator();
			while (iter.hasNext())
			{
				write.println(iter.next());
			}
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getTimeStamp()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd [HH.mm.ss]");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		return dateString;
	}
	
	public static void severe(String message)
	{
		LOG.severe(message);
		logs.add(logCount, "[SEVERE] " + getTimeStamp() + ": message");
		logCount++;
	}
	
	public static void info(String message)
	{
		LOG.info(message);
		logs.add(logCount, "[INFO] " + getTimeStamp() + ": message");
		logCount++;
	}
	
	private static void config(String message)
	{
		LOG.config(message);
		logs.add(logCount, "[CONFIG] " + getTimeStamp() + ": message");
		logCount++;
	}
}
