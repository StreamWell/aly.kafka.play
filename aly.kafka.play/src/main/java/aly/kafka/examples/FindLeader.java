package aly.kafka.examples;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindLeader
{
	static public void main(String [] args)
	{
		FindLeader test = new FindLeader();
		List<String> brokers = Collections.singletonList("127.0.0.1");
		PartitionMetadata metaPart = test.findLeader(brokers, 9092, "test", 0);   // 127.0.0.1:2181
		
		String host = metaPart.leader().host();
		
		System.out.println("topic-test : part-0 : leader-" + host);
	}
	
	private PartitionMetadata findLeader(List<String> brokers, int a_port, String a_topic,
			int a_partition)
	{
		List<String> m_replicaBrokers = new ArrayList<String>();
		PartitionMetadata returnMetaData = null;
		loop: for (String host : brokers)
		{
			SimpleConsumer consumer = null;
			try
			{
				consumer = new SimpleConsumer(host, a_port, 100000, 64 * 1024, "leaderLookup");
				List<String> topics = Collections.singletonList(a_topic);
				TopicMetadataRequest req = new TopicMetadataRequest(topics);
				kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);

				List<TopicMetadata> metaData = resp.topicsMetadata();
				for (TopicMetadata item : metaData)
				{
					for (PartitionMetadata part : item.partitionsMetadata())
					{
						if (part.partitionId() == a_partition)
						{
							returnMetaData = part;
							break loop;
						}
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("Error communicating with Broker [" + host
						+ "] to find Leader for [" + a_topic + ", " + a_partition + "] Reason: "
						+ e);
			}
			finally
			{
				if (consumer != null)
					consumer.close();

			}
		}

		if (returnMetaData != null)
		{
			m_replicaBrokers.clear();
			for (kafka.cluster.Broker replica : returnMetaData.replicas())
			{
				m_replicaBrokers.add(replica.host());
			}
		}

		return returnMetaData;
	}

}
