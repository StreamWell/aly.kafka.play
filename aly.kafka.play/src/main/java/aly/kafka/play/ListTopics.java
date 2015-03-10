package aly.kafka.play;

import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.admin.TopicCommand;
import kafka.utils.ZkUtils;

import org.I0Itec.zkclient.ZkClient;

public class ListTopics
{
	static public void main(String[] args)
	{
		ZkClient zkClient = ZkHelper.createZkClient("localhost:2181");
		Properties topicConfig = new Properties();
//		var arr = Array[String];
//		TopicCommand.listTopics(zkClient, new TopicCommandOptions( Array[String]));		
		zkClient.close();
	}
}
