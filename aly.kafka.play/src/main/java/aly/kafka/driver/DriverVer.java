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
import aly.kafka.msgsource.CreateSrcEnum;
import aly.kafka.msgsource.GenMsgSource;
import aly.kafka.msgsource.MsgSourceFactory;
import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.KafkaConfigFactory;
import aly.kafka.tools.MyLogger;
import aly.kafka.tools.RandomKey;
import aly.kafka.tools.ResBuilder;
import aly.kafka.tools.StoreEnum;
import aly.kafka.tools.StrMsgUtil;
import aly.kafka.tools.StreamChannelExeption;
import aly.kafka.tranform.TestStrMsgTransformer;

/**
 *	This is a test driver (starting point) that deal with kafka and Ver5tica.
 *You supposed to be adding msgs to kafka, and those msgs are immediately transferred to Vertica table
 *In internally is uses transformer and loader to transform msgs into RDB form and insert into (Vertica) DB
 */
public class DriverVer
{
	static Logger logger = MyLogger.createMyLoggerDefPath("DriverVer", Level.DEBUG);
//	String workerClassName = "aly.kafka.testEnv.TestMsgWorker";
	String workerClassName = "aly.kafka.driver.StrMsgWorker";

	static int nThreads = 1;
	static String topic = "zzz";
	static String sGroup = "grp-35";

	static public void main(String[] args) throws StreamChannelExeption
	{
			// this initializes the only ResAccounter instance (it is singleton)
			// and allows to access ResAccounter instance as ResAccounter.getInstance() 
		ResBuilder.createInCode();
		produce(10);
//		consume();
	}
	
	static void produce(int numMsg)
	{
		DriverVer driver = new DriverVer();
		driver.addMessagesToTopic(ConfPlay.BROKER_LOCAL, topic, numMsg);
		logger.debug("Added msgs to " + topic + " : " + topic);
	}
	
	static void consume() throws StreamChannelExeption
	{
		DriverVer driver = new DriverVer();
		driver.getMsgsFromTopic(ConfPlay.BROKER_LOCAL, topic, sGroup, nThreads);
		logger.debug("Got msgs from " + topic);
	}
	
	private DriverVer()
	{
	}
		
	void addMessagesToTopic(String zooKeeper, String sTopic, int msgNum)
	{
		Map<String,Object> createConfigMap = new HashMap<>();
		createConfigMap.put(GenMsgSource.MSG_NUM_NAME, new Integer(msgNum));

		GenMsgSource genMsgSrc = (GenMsgSource)MsgSourceFactory.create(CreateSrcEnum.VerTEST, createConfigMap);
		ProducerConfig prodConfig = KafkaConfigFactory.createProducerConfig(ConfPlay.BROKER_LOCAL);
		Producer<String, String> producer = new Producer<String, String>(prodConfig);
			
		int count  = 0;
		while(true)
		{
			String msgAsStr = genMsgSrc.nextAsStr();
			if (msgAsStr == null)
				break;
			
			// those three lines makes this code (Driver) specific for test msg
			int storeID = StoreEnum.MYSQL_TEST_TBL.ordinal();
			int transID = TestStrMsgTransformer.handlerID;
			int loadID = TestStrMsgLoader.handlerID;		
			
			String testMsg = StrMsgUtil.decorate(msgAsStr, storeID, transID, loadID);
 			int hKey = RandomKey.nextKey();
			String msgKey = topic + hKey;
			KeyedMessage<String, String> kMsg = new KeyedMessage<String, String>(topic, msgKey, testMsg);
			producer.send(kMsg);
			count++;
			
			logger.debug("Driver.addMessagesToTopic(): just sent: " + testMsg);
		}
		logger.debug("msgs added, count: " + count);
	}
	
	int getMsgsFromTopic(String zooKeepers, String sTopic, String sGroup, int nThreads) throws StreamChannelExeption
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
			String errMsg = "DriverVer.createWorkers() failed: " + ex;
			logger.error(errMsg);
			throw new StreamChannelExeption(errMsg);
		}
	}
}
