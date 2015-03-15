package aly.kafka.play;

import kafka.utils.ZKStringSerializer$;

import org.I0Itec.zkclient.ZkClient;

import aly.kafka.tools.ConfPlay;

public class ZkHelper
{
	public static ZkClient createZkClient(String connectString)
	{
		try
		{
			String zkHosts = ConfPlay.LOCAL_BROKER_DEF;
			ZkClient zkClient = new ZkClient(zkHosts, 10000, 10000, ZKStringSerializer$.MODULE$);
			return zkClient;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
