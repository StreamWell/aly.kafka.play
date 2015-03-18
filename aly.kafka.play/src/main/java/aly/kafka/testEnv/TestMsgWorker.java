package aly.kafka.testEnv;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import aly.kafka.driver.IWorker;
import aly.kafka.tools.MyLogger;

public class TestMsgWorker implements IWorker
{
	static void setLogLevel(Level value) { LOG_LEVEL = value; }
	
	static public Level LOG_LEVEL = Level.ERROR ;

	private KafkaStream<byte[], byte[]> m_stream;
	private int m_threadNumber;
	private Logger logger;

	public TestMsgWorker()
	{
		logger = MyLogger.createMyLogger("TestMsgWorker");
	}
	
	@Override
	public void setup(KafkaStream<byte[], byte[]> stream, int threadNumber)
	{
		m_threadNumber = threadNumber;
		m_stream = stream;
	}

	@Override
	public void run()
	{
		logger.debug("run() on entry");
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		int count = 0;
		while (it.hasNext())
		{
			String msg = new String(it.next().message());
			logger.log(LOG_LEVEL, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ *** msg *** " + msg);
			count ++;
			
			
		}
		logger.info("run() Shutting down Thread: " + m_threadNumber + " with count: " + count);
		System.out.println("########## Shutting down Thread: " + m_threadNumber + " with count: " + count);
	}

	@Override
	public void configure()
	{
	}
}
