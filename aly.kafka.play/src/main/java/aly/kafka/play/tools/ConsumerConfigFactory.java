										package aly.kafka.play.tools;

import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.producer.ProducerConfig;
import aly.kafka.local.DwHadopCluster;

public class ConsumerConfigFactory
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

		return new ConsumerConfig(props);	
	}
	
	public static ProducerConfig createProviderConfig(String skConnectStr)
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
