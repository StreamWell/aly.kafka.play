package aly.kafka.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import aly.kafka.consumer.group.GroupConfConsumer;
import aly.kafka.loader.TestStrMsgLoader;
import aly.kafka.msgsource.GenMsgSource;
import aly.kafka.msgsource.MsgSourceFactory;
import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.KafkaConfigFactory;
import aly.kafka.tools.MyLogger;
import aly.kafka.tools.RandomKey;
import aly.kafka.tools.ResAccounterBuilder;
import aly.kafka.tools.StrMsgUtil;
import aly.kafka.tools.StreamChannelExeption;
import aly.kafka.tranform.TestStrMsgTransformer;

/**
 * 
 *
 */

public class Driver
{
	static Logger logger = MyLogger.createMyLoggerDefPath("Driver", Level.DEBUG);
//	String workerClassName = "aly.kafka.testEnv.TestMsgWorker";
	String workerClassName = "aly.kafka.driver.StrMsgWorker";

	static int nThreads = 1;
	static String topic = "yyy";
	static String sGroup = "group1";

	static public void main(String[] args) throws StreamChannelExeption
	{
			// this initializes the only ResAccounter instance (it is singleton)
			// and allows to access ResAccounter instance as ResAccounter.getInstance() 
		ResAccounterBuilder.createInCode();
//		produce(2);
		consume();
	}
	
	static void produce(int numMsg)
	{
		Driver driver = new Driver();
		driver.addMessagesToTopic(ConfPlay.BROKER_LOCAL, topic, numMsg);
		logger.debug("Added msgs to " + topic + " : " + topic);
	}
	
	static void consume() throws StreamChannelExeption
	{
		Driver driver = new Driver();
		driver.getMsgsFromTopic(ConfPlay.BROKER_LOCAL, topic, sGroup, nThreads);
		logger.debug("Got msgs from " + topic);
	}
	
	private Driver()
	{
	}
		
	void addMessagesToTopic(String zooKeeper, String sTopic, int msgNum)
	{
		Map<String,Object> createConfigMap = new HashMap<>();
		createConfigMap.put(GenMsgSource.MSG_NUM_NAME, new Integer(msgNum));

		GenMsgSource genMsgSrc = (GenMsgSource)MsgSourceFactory.create(MsgSourceFactory.SourceEnum.TEST, createConfigMap);
		ProducerConfig prodConfig = KafkaConfigFactory.createProducerConfig(ConfPlay.BROKER_LOCAL);
		Producer<String, String> producer = new Producer<String, String>(prodConfig);
			
		int count  = 0;
		while(true)
		{
			String msgAsStr = genMsgSrc.nextAsStr();
			if (msgAsStr == null)
				break;
			
			// those three lines makes this code (Driver) specific for test msg
			int storeID = MsgSourceFactory.SourceEnum.TEST.getPos();
			int transID = TestStrMsgTransformer.handlerID;
			int loadID = TestStrMsgLoader.handlerID;		
			
			String testMsg = StrMsgUtil.decorate(msgAsStr, storeID, transID, loadID);
			RandomKey key = RandomKey.create();
			String msgKey = topic + key;
			KeyedMessage<String, String> kMsg = new KeyedMessage<String, String>(topic, msgKey, testMsg);
			producer.send(kMsg);
			count++;
			
			logger.debug("Driver.addMessagesToTopic(): just sent: " + testMsg);
		}
		logger.debug("msgs added, count: " + count);
	}
	
	int getMsgsFromTopic(String ooKeeper, String sTopic, String sGroup, int nThreads) throws StreamChannelExeption
	{
		int count = 0;
		
		String zooKeeper = ConfPlay.LOCAL_BROKER_DEF;
		String groupId = "group35";
		String topic = sTopic;
		int threads = 1;

		List<IWorker> workers = createWorkers(workerClassName, threads);
		GroupConfConsumer grpConsumer = new GroupConfConsumer(zooKeeper, topic, groupId);
		grpConsumer.configure(workers);
		grpConsumer.run(threads);
		
//		try { Thread.sleep(10000); } catch (InterruptedException ie) { ; }

		grpConsumer.shutdown();
		return count;
	}

	private List<IWorker> createWorkers(String workerClassName, int threads) throws StreamChannelExeption
	{
		List<IWorker> workerList = new ArrayList<>(threads);
		try
		{
			for (int i=0; i<threads; i++)
			{
				Class<?> workerClass = Class.forName(workerClassName);
				IWorker workerInstance = (IWorker)workerClass.newInstance();
			    workerList.add(workerInstance);
			}
			return workerList;
		}
		catch(Exception ex)
		{
			logger.error("Driver.createWorkers() failed: " + ex);
			throw new StreamChannelExeption("createWorkers() failed: " + ex);
		}
	}
}
