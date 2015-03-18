package aly.kafka.driver;


import java.util.List;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import aly.kafka.containers.StrMsgPojo;
import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.MyLogger;
import aly.kafka.tools.ResAccountant;
import aly.kafka.tools.StrMsgUtil;
import aly.kafka.tools.StreamChannelExeption;
import aly.kafka.tranform.ITransormer;
import aly.kafka.loader.ILoader;
import aly.kafka.obu.msg.MetaField;
import aly.kafka.obu.msg.StoreCred;
import aly.kafka.obu.msg.MetaField;

/**
 * 
 * Get msgs from Kafka stream and insert them into Vertica table
 *
 */
public class StrMsgWorker implements IWorker
{
	static Logger logger = MyLogger.createMyLoggerRelPath("StrMsgWorker", Level.DEBUG, "StrMsgWorker");
	static public Level LOG_LEVEL = Level.DEBUG ;
	
	public static void main(String[] args)
	{
	}

	int nThreads;
	KafkaStream<byte[], byte[]> stream;
	ResAccountant accountant;
	
	public void setup(KafkaStream<byte[], byte[]> stream, int threadNumber)
	{
		this.nThreads = threadNumber;
		this.stream = stream;
		accountant = ResAccountant.getInstance();
	}
	
	@Override
	public void run()
	{
		logger.debug("StrMsgWorker.run() on entry");
		
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		int count = 0;
		StrMsgPojo msgPojo;
		ResAccountant accountant = ResAccountant.getInstance();
		while (it.hasNext())
		{
			String msg = new String(it.next().message());
			logger.log(LOG_LEVEL, "*** msg from topic: *** " + msg);
			try
			{
				msgPojo = StrMsgUtil.parse(msg);
				int storeID = msgPojo.getStoreID();
				StoreCred storeCred = accountant.getStoreCred(storeID);
				int transformetID = msgPojo.getTransformerID();
				ITransormer transformer = accountant.getTransformer(transformetID);		// for test msg: aly.kafka.tranform.TestStrMsgTransformer
				int loaderID = msgPojo.getLoaderID();									
				ILoader loader =  accountant.getLoader(loaderID);						// for test msg:  aly.kafka.loader.TestStrMsgLoader
				loader.configure(storeCred, ConfPlay.BATCH_SIZE);						// will check and configure only the first time
				
				String payload = msgPojo.getPayload();
				List<MetaField> fldList = transformer.transform(payload);	
				loader.load(fldList);										
			}
			catch (StreamChannelExeption e)
			{
				logger.error("StrMsgWorker.run(): failed: " + e);
				throw new RuntimeException("StrMsgWorker.run(): failed: " + e);
			}
			//			find store		find transformer	find loader
		}
		logger.debug("StrMsgWorker.run() on exit");				
	}

	@Override
	public void configure()
	{
	}
}
