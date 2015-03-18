package aly.kafka.driver;

import kafka.consumer.KafkaStream;

public interface IWorker extends Runnable
{
	void configure();
	void setup(KafkaStream<byte[], byte[]> stream, int threadNumber);
}
