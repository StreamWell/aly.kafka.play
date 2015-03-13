package aly.kafka.play;

import java.util.Properties;

import aly.kafka.play.tools.ConfPlay;
import aly.kafka.play.tools.ConsumerConfigFactory;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerGenerator
{
	public static void main(String[] args)
	{
		String BASE = "Abra_";
		String topic = "zzz";
		
		if(args.length >= 1)
		{
			if((args[0]).length() > 0)
				BASE = args[0];
		}
		
//		Properties props = new Properties();
//		props.put("metadata.broker.list", ConfPlay.BROKER_LOCAL);    // PRODUCER_DEF_POC 	PRODUCER_BINDING
//		props.put("serializer.class", "kafka.serializer.StringEncoder");
//		props.put("partitioner.class", "aly.kafka.play.HashPartitioner");
//		props.put("request.required.acks", "1");
//
//		ProducerConfig config = new ProducerConfig(props);
//		
		ProducerConfig config = ConsumerConfigFactory.createProducerConfig(ConfPlay.BROKER_LOCAL);
		Producer<String, String> producer = new Producer<String, String>(config);

		for(int count = 0; count < 3; count++)
		{
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, "" + count, BASE + count);
			producer.send(data);
		}
		producer.close();
	}
}
