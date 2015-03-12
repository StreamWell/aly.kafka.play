package aly.kafka.play;

import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;

import aly.kafka.play.tools.ConfPlay;
import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;

// another way - see
//
//http://stackoverflow.com/questions/16946778/how-can-we-create-a-topic-in-kafka-from-the-ide-using-api
//
public class CreateTopic
{
	static public void main(String[] args)
	{
		String TOPIC_NAME = "zzz";
		int partitionNumber = 1;
		
		ZkClient zkClient = ZkHelper.createZkClient("localhost:2181");
		Properties topicConfig = new Properties();
		AdminUtils.createTopic(zkClient, TOPIC_NAME, partitionNumber, 1, topicConfig); 
		zkClient.close();
		System.out.println("####### Created the topic:  " + TOPIC_NAME);
	}

//	public static ZkClient createZkClient(String connectString)
//	{
//		try
//		{
//			String zkHosts = ConfPlay1.BROKER_LIST_VAL_LOCAL;
//			ZkClient zkClient = new ZkClient(zkHosts, 10000, 10000, ZKStringSerializer$.MODULE$);
//			return zkClient;
//		}
//		catch (Exception e)
//		{
//			throw new RuntimeException(e);
//		}
//	}
}

// createTopic( zkClient: ZkClient,
// topic: String,
// partitions: Int,
// replicationFactor: Int,
// topicConfig: Properties = new Properties
// ): Unit

// val sessionTimeoutMs = 10000
// val connectionTimeoutMs = 10000
// val zkClient = new ZkClient("zookeeper1:2181", sessionTimeoutMs,
// connectionTimeoutMs,
// ZKStringSerializer)