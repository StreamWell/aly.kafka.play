package aly.kafka.examples.consumer.group;

/**
 * 
 * https://cwiki.apache.org/confluence/display/KAFKA/Consumer+Group+Example
 *
 */

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import aly.kafka.play.tools.ConfPlay;
import aly.kafka.play.tools.ConsumerConfigFactory;
import aly.kafka.play.tools.MyLogger;

public class GroupConsumer
{
	private final ConsumerConnector consumer;
	private final String topic;
	private ExecutorService executor;

	static Logger logger = MyLogger.createMyLogger("GroupConsumer");
	
	public static void main(String[] args)
	{
		String zooKeeper = ConfPlay.LOCAL_BROKER_DEF;
		String groupId = "grp_" + 7;
		String topic = "zzz";
		int threads = 2;

		GroupConsumer grpConsumer = new GroupConsumer(zooKeeper, topic, groupId);
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
	
	public GroupConsumer(String zooConnStr, String a_topic, String grp)
	{		
		ConsumerConfig config = ConsumerConfigFactory.createConsumerConfig(ConfPlay.LOCAL_BROKER_DEF, grp);
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

	public void run(int a_numThreads)
	{
		logger.debug("GroupConsumer:run() on entry");
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(a_numThreads));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
				.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

		// now launch all the threads
		//
		executor = Executors.newFixedThreadPool(a_numThreads);

		// now create an object to consume the messages
		//
		int threadNumber = 0;
		for (final KafkaStream stream : streams)
		{
			executor.submit(new ConsumerWorker(stream, threadNumber));
//			ConsumerWorker groupTest = new ConsumerWorker(stream, threadNumber);
//			groupTest.run();
			threadNumber++;
		}
		logger.debug("GroupConsumer:run() on exit");
	}

//	private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId)
//	{
//		Properties props = new Properties();
//		props.put("zookeeper.connect", a_zookeeper);
//		props.put("group.id", a_groupId);
//		props.put("zookeeper.session.timeout.ms", "400");
//		props.put("zookeeper.sync.time.ms", "200");
//		props.put("auto.commit.interval.ms", "1000");
//
//		return new ConsumerConfig(props);
//	}

}
