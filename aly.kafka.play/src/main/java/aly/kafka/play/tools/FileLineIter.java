package aly.kafka.play.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class FileLineIter
{
	final static Charset ENCODING = StandardCharsets.UTF_8;
	final static String LOGFILE = "/Users/Proj/LunaJD/files7/Data/output.aly";
	final static String INPUT_FILE = "/Users/Proj/LunaJD/files7/Data/input.aly";
	static Logger logger;

	public static void main(String[] args) throws IOException
	{
		logger = MyLogger.createMyLogger(LOGFILE, Level.DEBUG);

		FileLineIter test = new FileLineIter(INPUT_FILE);
//		test.readAll(INPUT_FILE);
		test.iterateLines(INPUT_FILE);
	}
	
	private BufferedReader reader;
	private boolean bClosed = false;
	
	public FileLineIter(String fileName) throws IOException
	{
		Path path = Paths.get(fileName);
		reader = Files.newBufferedReader(path, ENCODING);
	}

	public void iterateLines(String fileName) throws IOException
	{
		Path path = Paths.get(fileName);
		BufferedReader reader = Files.newBufferedReader(path, ENCODING);
		
		while(true)
		{
			String line = nextLine();
			if(line == null)
			{
				logger.debug("null, EOF");
				break;
			}
			logger.debug(line);
		}
	}
	
	public String nextLine() throws IOException
	{
		if(bClosed == true)
			return null;
		
		String line = reader.readLine();	// can be null, if EOF
		if(line == null)
		{
			bClosed = true;
			reader.close();
			reader = null;
		}
		return line;
	}
	
	public void close() throws IOException
	{
		if(bClosed == true)
			return;
		
		reader.close();
		reader = null;
	}
	
	public void readAll(String fileName) throws IOException
	{
		Path path = Paths.get(fileName);
		try (BufferedReader reader = Files.newBufferedReader(path, ENCODING))
		{
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				FileLineIter.logger.debug(line);
			}
			reader.close();
		}
	}
}
