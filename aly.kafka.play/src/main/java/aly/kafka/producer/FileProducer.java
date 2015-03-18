package aly.kafka.producer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.FileLineIter;
import aly.kafka.tools.KafkaConfigFactory;
import aly.kafka.tools.MyLogger;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class FileProducer
{
	final static Charset ENCODING = StandardCharsets.UTF_8;
//	final static String LOGFILE = "/Users/Proj/LunaJD/aly.kafka.play/Data/output.aly";
	final static String INPUT_FILE = "/Users/ayakubo/git/aly.kafka.play/aly.kafka.play/Data/input.aly";

	private static Logger logger = MyLogger.createMyLoggerDefPath("FileProducer", Level.DEBUG);

	static public void main(String[] args) throws IOException
	{
		logger.debug("main() on entry");
		FileProducer fileProucer = new FileProducer(INPUT_FILE, 3, "zzz");
		fileProucer.init(logger);
		int count = fileProucer.run();
		logger.debug("main() on exit: count - " + count);
	}
	
	public FileProducer(String sFilePath, int maxMsgNum, String sTopic)
	{
		filePath = sFilePath;
		topic = sTopic;
		this.maxMsgNum = maxMsgNum;
	}
	
	String filePath;
	String topic;
	int maxMsgNum;
	Producer<String, String> producer;
	
	public void init(Logger logger)
	{
//		this.logger = logger;
		ProducerConfig config = KafkaConfigFactory.createProducerConfig(ConfPlay.BROKER_LOCAL);
		producer = new Producer<String, String>(config);
	}
	
	int run() throws IOException
	{
		logger.debug("FileProducer.run() on entry");
		int numOfMsgs = 0;
		
		FileLineIter fileLineIter = new FileLineIter(INPUT_FILE);
		
		for(int count=0; count<maxMsgNum; count++)
		{
			String line = fileLineIter.nextLine();
			if(line == null)
				break;
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, line);
			producer.send(data);
			numOfMsgs++;
			logger.debug(line);
		}
		producer.close();
		fileLineIter.close();

		logger.debug("FileProducer.run() on exit: numOfMsgs: " + numOfMsgs);
		return numOfMsgs;
	}
}
