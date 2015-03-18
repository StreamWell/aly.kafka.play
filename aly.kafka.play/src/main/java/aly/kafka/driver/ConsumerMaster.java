package aly.kafka.driver;

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

import org.apache.log4j.Logger;

import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.KafkaConfigFactory;
import aly.kafka.tools.MyLogger;
import aly.kafka.tools.StreamChannelExeption;

public class ConsumerMaster
{
	private final ConsumerConnector consumer;
	private final String topic;
	private ExecutorService executor;

	static Logger logger = MyLogger.createMyLogger("GroupConsumer");
	
	public static void main(String[] args) throws StreamChannelExeption
	{
		String zooKeeper = ConfPlay.LOCAL_BROKER_DEF;
		String groupId = "group35";
		String topic = "zzz";
		int threads = 1;

		ConsumerMaster grpConsumer = new ConsumerMaster(zooKeeper, topic, groupId);
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
	
	public ConsumerMaster(String zooConnStr, String a_topic, String grp)
	{		
		ConsumerConfig config = KafkaConfigFactory.createConsumerConfig(ConfPlay.LOCAL_BROKER_DEF, grp);
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		this.topic = a_topic;
	}

	public void shutdown()
	{
		if (consumer != null)
			consumer.shutdown();
		if (executor != null)
			executor.shutdown();
	}

	public void run(int numThreads, IWorker ... workers) throws StreamChannelExeption
	{
		logger.debug("GroupConsumer:run() on entry");
		
		int len = workers.length;
		if(len != numThreads)
			throw new StreamChannelExeption("run(): illegal arguments");
		
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(numThreads));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
				.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

		// now launch all the threads
		//
		executor = Executors.newFixedThreadPool(numThreads);

		// now create an object to consume the messages
		//
		int threadNumber = 0;
		for (final KafkaStream stream : streams)
		{
			IWorker worker = workers[threadNumber];
			worker.setup(stream, threadNumber);
			executor.submit(worker);
//			worker.run();					// for testing in one thread
			threadNumber++;
		}
		logger.debug("GroupConsumer:run() on exit. threadNumber: " + threadNumber);
	}

}
