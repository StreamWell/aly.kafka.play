package aly.kafka.consumer;

import java.util.Collections;
import java.util.List;

import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.consumer.SimpleConsumer;

public class ConsumerHelper
{
	ConsumerHelper(List<String> replicaBrokers)
	{
		m_replicaBrokers = replicaBrokers;
	}
	
	private List<String> m_replicaBrokers = null;
	
	private PartitionMetadata findLeader(List<String> a_seedBrokers, int a_port, String a_topic,
			int a_partition)
	{
		PartitionMetadata returnMetaData = null;
		loop: for (String seed : a_seedBrokers)
		{
			SimpleConsumer consumer = null;
			try
			{
				consumer = new SimpleConsumer(seed, a_port, 100000, 64 * 1024, "leaderLookup");
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
				System.out.println("Error communicating with Broker [" + seed
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
		
	
	private String findNewLeader(String a_oldLeader, String a_topic, int a_partition, int a_port) throws Exception {
        for (int i = 0; i < 3; i++) {
            boolean goToSleep = false;
            PartitionMetadata metadata = findLeader(m_replicaBrokers, a_port, a_topic, a_partition);
            if (metadata == null) {
                goToSleep = true;
            } else if (metadata.leader() == null) {
                goToSleep = true;
            } else if (a_oldLeader.equalsIgnoreCase(metadata.leader().host()) && i == 0) {
                // first time through if the leader hasn't changed give ZooKeeper a second to recover
                // second time, assume the broker did recover before failover, or it was a non-Broker issue
                //
                goToSleep = true;
            } else {
                return metadata.leader().host();
            }
            if (goToSleep) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        }
        System.out.println("Unable to find new leader after Broker failure. Exiting");
        throw new Exception("Unable to find new leader after Broker failure. Exiting");
    }
}
