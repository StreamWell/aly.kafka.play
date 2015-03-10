package aly.kafka.examples.consumer.group;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import aly.kafka.play.tools.MyLogger;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

//
// https://cwiki.apache.org/confluence/display/KAFKA/Consumer+Group+Example
//

public class ConsumerWorker implements Runnable
{
//	static Logger logger = Logger.getLogger(ConsumerWorker.class);
	static void setLogLevel(Level value) { LOG_LEVEL = value; }
	
	static public Level LOG_LEVEL = Level.ERROR ;

	private KafkaStream m_stream;
	private int m_threadNumber;
	private Logger logger;

	public ConsumerWorker(KafkaStream stream, int threadNumber)
	{
		m_threadNumber = threadNumber;
		m_stream = stream;
		logger = MyLogger.createMyLogger("ConsumerWorker");
	}

	public void run()
	{
		logger.debug("run() on entry");
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		int count = 0;
		while (it.hasNext())
		{
			String msg = new String(it.next().message());
			logger.log(LOG_LEVEL, "*** msg *** " + msg);
//			System.out.println("Thread " + m_threadNumber + ": " + new String(it.next().message()));
			count ++;
		}
		logger.info("run() Shutting down Thread: " + m_threadNumber + " with count: " + count);
		System.out.println("########## Shutting down Thread: " + m_threadNumber + " with count: " + count);
	}
}

/*
The example code expects the following command line parameters:
ZooKeeper connection string with port number
Consumer Group name to use for this process
Topic to consume messages from
# of threads to launch to consume the messages
For example:
server01.myco.com1:2181 group3 myTopic  4
 */
