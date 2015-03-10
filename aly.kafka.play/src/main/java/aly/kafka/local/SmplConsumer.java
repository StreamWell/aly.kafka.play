package aly.kafka.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class SmplConsumer
{

	public static void main(String[] args)
	{
		SmplConsumer consumer = new SmplConsumer(DwHadopCluster.ALY_TOPIC);
		consumer.run();
	}

	private final ConsumerConnector consumerConn;
	private final String topic;

	private SmplConsumer(String topic)
	{
		ConsumerConfig config = createConsumerConfig();
		consumerConn = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		this.topic = topic;
	}

	// zookeepers on dwhadoopm
	// dwhadoopm303p.prod.ch3.s.com:2181,dwhadoopm302p.prod.ch3.s.com:2181,dwhadoopm301p.prod.ch3.s.com:2181

	private ConsumerConfig createConsumerConfig()
	{
		Properties props = new Properties();
		props.put("zookeeper.connect", DwHadopCluster.ZOO_CONNECT_LOC);
		props.put("group.id", "abra");//DwHadopCluster.ALY_GROUP);
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");

		return new ConsumerConfig(props);
	}

	public void run()
	{
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConn
				.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);		
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		
	    System.out.println("SmplConsumer.run(): trying to get messages");
	    while(it.hasNext())
	    {
	      System.out.println(new String(it.next().message()));
	    }
	    System.out.println("SmplConsumer.run(): no more messages");
	}
}
