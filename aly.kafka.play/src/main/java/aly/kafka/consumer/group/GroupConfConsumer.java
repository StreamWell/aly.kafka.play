package aly.kafka.consumer.group;

/**
 * 
 * https://cwiki.apache.org/confluence/display/KAFKA/Consumer+Group+Example
 *
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import aly.kafka.driver.IWorker;
import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.KafkaConfigFactory;
import aly.kafka.tools.MyLogger;

public class GroupConfConsumer
{
	private final ConsumerConnector consumer;
	private final String topic;
	private ExecutorService executor;

	static Logger logger = MyLogger.createMyLoggerRelPath("GroupConfConsumer", Level.DEBUG, "GroupConfConsumer");
	
//	public GroupConfConsumer(String zooKeeper, String topic, String groupId)
//	{
//		consumer = consumer;
//		topic = topic;
//	}
//	
	public static void main(String[] args)
	{
		String zooKeeper = ConfPlay.LOCAL_BROKER_DEF;
		String groupId = "group35";
		String topic = "zzz";
		int threads = 1;

		GroupConfConsumer grpConsumer = new GroupConfConsumer(zooKeeper, topic, groupId);
		grpConsumer.run(threads);

		try
		{
			Thread.sleep(10000);
		}
		catch (InterruptedException ie)
		{

		}
		grpConsumer.shutdown();
	}
	
	public GroupConfConsumer(String zooConnStr, String a_topic, String grp)
	{		
		ConsumerConfig config = KafkaConfigFactory.createConsumerConfig(ConfPlay.LOCAL_BROKER_DEF, grp);
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		this.topic = a_topic;
	}
	
	List<IWorker> workers;
	
	public void configure(List<IWorker> workers)
	{
		this.workers = workers;		
	}

	public void run(int nThreads)
	{
		logger.debug("GroupConfConsumer() on entry");
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(nThreads));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
				.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

		executor = Executors.newFixedThreadPool(nThreads);
		int threadNumber = 0;
		for (final KafkaStream<byte[], byte[]> stream : streams)
		{
			IWorker worker = workers.get(threadNumber);
			worker.setup(stream, threadNumber);
//			executor.submit(worker);
			worker.run();
			threadNumber++;
		}
		logger.debug("GroupConfConsumer() on exit. threadNumber: " + threadNumber);
	}

	public void shutdown()
	{
		if (consumer != null)
			consumer.shutdown();
		if (executor != null)
			executor.shutdown();
	}
}
