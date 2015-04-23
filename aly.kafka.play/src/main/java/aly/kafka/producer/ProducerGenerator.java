package aly.kafka.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.KafkaConfigFactory;

public class ProducerGenerator
{
	static int NumOfMsgs = 5;
	
	public static void main(String[] args)
	{
		String BASE = "Abra_";
		String topic = "zzz";
		
		if(args.length >= 1)
		{
			if((args[0]).length() > 0)
				BASE = args[0];
		}
		
		ProducerConfig config = KafkaConfigFactory.createProducerConfig(ConfPlay.BROKER_LOCAL);
		Producer<String, String> producer = new Producer<String, String>(config);

		for(int count = 0; count < NumOfMsgs; count++)
		{
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, "" + count, BASE + count);
			producer.send(data);
		}
		producer.close();
	}
}
