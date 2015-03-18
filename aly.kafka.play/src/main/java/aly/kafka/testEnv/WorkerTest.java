package aly.kafka.testEnv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.KafkaConfigFactory;

public class WorkerTest
{
	static public void main(String[] args)
	{
		TestMsgWorker worker = new TestMsgWorker();
		String topic = "zzz";
		int numThreads = 1;   // concurrency level: this many streams will be opened for consumption
		String sGroup = "weka_1";
		KafkaStream<byte[], byte[]> stream = null;
		int threadNumber = 1;
		
		ConsumerConfig consConfig = KafkaConfigFactory.createConsumerConfig(ConfPlay.LOCAL_BROKER_DEF, sGroup);
		ConsumerConnector consumerConnector = (ConsumerConnector) kafka.consumer.Consumer.createJavaConsumerConnector(consConfig);
				
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, numThreads);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
		stream = consumerMap.get(topic).get(0);
//		
//		ConsumerIterator<byte[], byte[]> it = stream.iterator();
//		
//		while(it.hasNext())
//		{
//			byte[] arr  = it.next().message();
//			String s = new String(arr);
//			System.out.println(s);			
//		}
		
		worker.setup(stream, threadNumber);
		worker.run();
	}
}
