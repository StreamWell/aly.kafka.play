package aly.kafka.tools;

import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.producer.ProducerConfig;

public class KafkaConfigFactory
{
	public static ConsumerConfig createConsumerConfig(String skConnectStr, String sGroup)
	{
//		Properties props = new Properties();
//		props.put("zookeeper.connect", skConnectStr);
//		props.put("zookeeper.session.timeout.ms", "10000");
//		props.put("zookeeper.sync.time.ms", "1000");
//		props.put("auto.commit.interval.ms", "1000");
//		props.put("auto.offset.reset", "smallest");
//		props.put("consumer.timeout.ms", "1000");
//		props.put("auto.commit.enable", "false");
//		String sGrp = (sGroup == null ? DwHadopCluster.ALY_GROUP : sGroup);
//		props.put("group.id", sGrp);
//		return new ConsumerConfig(props);

		Properties props = new Properties();

		props.put("zookeeper.connect", skConnectStr);
		props.put("zookeeper.session.timeout.ms", "10000");
		props.put("zookeeper.sync.time.ms", "1000");
		props.put("auto.commit.interval.ms", "1000");
//		props.put("delete-consumer-offsets", "true");
		props.put("auto.offset.reset", "smallest");
		props.put("auto.commit.enable", "false");
//		props.put("consumer.timeout.ms", "1000");
		props.put("consumer.timeout.ms", "-1");
		props.put("group.id", sGroup);
		
		// trying this too - this for Broker configurtation
//		props.put("log.dirs", "/Useers/ayakubo/tmp/kafka-logs");		// tmp/kafka-logs
//		props.put("auto.create.topics.enable", "true");					// auto.create.topics.enable
//		props.put("delete.topic.enable", "true");					// delete.topic.enable

		return new ConsumerConfig(props);	
	}
	
	public static ProducerConfig createProducerConfig(String skConnectStr)
	{
		ProducerConfig prodConfig = null;

		Properties props = new Properties();
		props.put("metadata.broker.list", skConnectStr);    // ConfPlay.BROKER_LOCAL
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("partitioner.class", "aly.kafka.play.HashPartitioner");
		props.put("request.required.acks", "1");

		prodConfig = new ProducerConfig(props);
		
		return prodConfig;
	}
}
