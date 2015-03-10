package aly.kafka.play;

import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;

import org.I0Itec.zkclient.ZkClient;

/**
 * This steps will delete all topics and data
 * Stop Kafka-server and Zookeeper-server
 * Remove the tmp data directories of both services, by default they are C:/tmp/kafka-logs and C:/tmp/zookeeper.
 * then start Zookeeper-server and Kafka-server
 */

public class DeleteTopic
{
	static public void main(String[] args)
	{
		String TOPIC_NAME = "cat";
		int partitionNumber = 1;
		
		ZkClient zkClient = ZkHelper.createZkClient("localhost:2181");
		Properties topicConfig = new Properties();
//		AdminUtils.deleteTopic(zkClient, TOPIC_NAME); 
		String topicPath = ZkUtils.getTopicPath(TOPIC_NAME);
		System.out.println("####### TOPIC_PATH:  " + topicPath);
		
		zkClient.deleteRecursive(topicPath);
		zkClient.close();
		System.out.println("####### Deleted the topic:  " + TOPIC_NAME);
	}

}
