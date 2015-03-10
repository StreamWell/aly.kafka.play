package aly.kafka.examples;

import java.util.Collections;
import java.util.List;

import kafka.api.OffsetRequest;
import kafka.api.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.TopicMetadataRequest;

//import kafka.javaapi.consumer.SimpleConsumer;

public class GetTopicMeta_n_Leader
{
	public static void main(String[] args)
	{
		String host = "localhost";
		int port = 9092;
		String topic = "test";
		int soTimeout = -1;
		int bufferSize = 100000;
		String clientId = "me";

		SimpleConsumer consumer = new SimpleConsumer(host, port, 100000, 64 * 1024, "leaderLookup");
		List<String> topics = Collections.singletonList(topic);
		TopicMetadataRequest req = new TopicMetadataRequest(topics);
		kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);
		System.out.println(resp);
	}
}
