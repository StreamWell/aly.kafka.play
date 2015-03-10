package aly.kafka.local;

public class DwHadopCluster
{
//	KAFKA HOME
//	dmhadoop301p.prod.ch3.s.com
//	
//	Wenye kafka admin in github:  https://github.com/zhuwchicago/hadoop-admin
	
	
	
//	  zookeepers on dwhadoopm
//	  dwhadoopm303p.prod.ch3.s.com:2181,dwhadoopm302p.prod.ch3.s.com:2181,dwhadoopm301p.prod.ch3.s.com:2181	 	
	
	final static String ALY_TOPIC = "aly-t1";
	public final static String ALY_GROUP = "aly-group";
	
	final static String ZOO_CONNECT_DW = "dwhadoopm303p.prod.ch3.s.com:2181";
	final static String ZOO_CONNECT_LOC = "127.0.0.1:2181";
	
	final static String BROKER_LIST_DW = "dmhadoop301p.prod.ch3.s.com:9092";
	final static String BROKER_LIST_LOC = "localhost:9092";
	
	final static String STR_SERIOALIZER = "kafka.serializer.StringEncoder";
	
	final static String SMPL_PARTITIONER = "aly.kafka.local.SmplPartitioner";
}
