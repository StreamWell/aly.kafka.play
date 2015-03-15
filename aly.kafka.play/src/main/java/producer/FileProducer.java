package producer;

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
	final static String LOGFILE = "/Users/Proj/LunaJD/aly.kafka.play/Data/output.aly";
	final static String INPUT_FILE = "/Users/Proj/LunaJD/aly.kafka.play/Data/input.aly";

	static public void main(String[] args) throws IOException
	{
		Logger logger = MyLogger.createMyLogger(LOGFILE, Level.DEBUG);
		logger.debug("main() on entry");
		FileProducer fileProucer = new FileProducer(INPUT_FILE, 10, "zzz", 0);
		fileProucer.init(logger);
		int count = fileProucer.run();
		logger.debug("main() on exit: count - " + count);
	}
	
	public FileProducer(String sFilePath, int maxMsgNum, String sTopic, int startPrefVal)
	{
		filePath = sFilePath;
		topic = sTopic;
		this.startPrefVal = startPrefVal;	// msg will have a ~unique numeric preffix.
		this.maxMsgNum = maxMsgNum;
	}
	
	private Logger logger;
	String filePath;
	String topic;
	int maxMsgNum;
	int startPrefVal;
	Producer<String, String> producer;
	
	public void init(Logger logger)
	{
		this.logger = logger;
		ProducerConfig config = KafkaConfigFactory.createProducerConfig(ConfPlay.BROKER_LOCAL);
		producer = new Producer<String, String>(config);
	}
	
	int run() throws IOException
	{
		int numOfMsgs = 0;
		
		FileLineIter fileLineIter = new FileLineIter(INPUT_FILE);
		
		int msgPrefId = startPrefVal;
		for(int count=0; count<maxMsgNum; count++)
		{
			String line = fileLineIter.nextLine();
			if(line == null)
				break;
			line = "|" + line + "|";
//			line = line + (count + startPrefVal);
			line = Integer.toString(count + startPrefVal) + "__" + line;
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, line);
			producer.send(data);
			logger.debug(line);
		}
		producer.close();
		fileLineIter.close();
		return numOfMsgs;
	}
}
