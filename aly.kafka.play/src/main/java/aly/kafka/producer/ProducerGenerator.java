package aly.kafka.producer;

import java.util.Properties;

import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.KafkaConfigFactory;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerGenerator
{
	public static void main(String[] args)
	{
		String BASE = "Abra_";
		String topic = "test";
		
		if(args.length >= 1)
		{
			if((args[0]).length() > 0)
				BASE = args[0];
		}
		
		ProducerConfig config = KafkaConfigFactory.createProducerConfig(ConfPlay.BROKER_LOCAL);
		Producer<String, String> producer = new Producer<String, String>(config);

		for(int count = 0; count < 4; count++)
		{
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, "" + count, BASE + count);
			producer.send(data);
		}
		producer.close();
	}
}
