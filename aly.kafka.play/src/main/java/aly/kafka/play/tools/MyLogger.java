package aly.kafka.play.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import aly.kafka.local.LocalConsumer;

public class MyLogger
{
	static private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy:HH:mm:ss:SSS");
	static public final String DEF_LOG_FILE = "/Users/ayakubo/Desktop/LastLogs/log2.log";
	static private int LOG_NUM = 0;
	
	static public void main(String [] args)
	{
		test1();
	}

	static void test1()
	{
		List<Logger> logList = new ArrayList<Logger>();
		for(int i=3; i<15; i++)
		{
			Logger logger = createMyLogger("test" + i, Level.DEBUG);
			logList.add(logger);
		}
		
		for(Logger logger : logList)
		{
			logger.info("info");
			logger.debug("debug");
			logger.warn("warn");
			logger.fatal("fatal");
		}
	}
	
	public static Logger createMyLogger(String baseName)
	{
		return createMyLogger(baseName, Level.DEBUG, DEF_LOG_FILE);
	}
	
	public static Logger createMyLogger(String baseName, Level aThreshold)
	{
		return createMyLogger(baseName, aThreshold, DEF_LOG_FILE);
	}
	
	public static Logger createMyLogger(String baseName, Level aThreshold, String filePath)
	{
		Logger myLogger = Logger.getLogger(baseName + "_" + LOG_NUM);
		FileAppender fileAppender = new FileAppender();
		fileAppender.setName(baseName + "_" + LOG_NUM);
		fileAppender.setFile(filePath + LOG_NUM);
		fileAppender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		fileAppender.setThreshold(Level.DEBUG);
		fileAppender.setAppend(true);
		fileAppender.activateOptions();
		LOG_NUM++;
		
		ConsoleAppender conAppender = new ConsoleAppender();
		conAppender.setName("MyConsoleAppender");
		conAppender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		conAppender.setThreshold(Level.WARN);
		conAppender.activateOptions();
		
		myLogger.setAdditivity(false);
		myLogger.addAppender(fileAppender);
		myLogger.addAppender(conAppender);
		return myLogger;
	}
}
