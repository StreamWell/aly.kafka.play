package aly.kafka.tools;

import java.util.Properties;

import kafka.producer.ProducerConfig;

// OLD ?
//zookeepers on dwhadoopm
//dwhadoopm303p.prod.ch3.s.com:2181,dwhadoopm302p.prod.ch3.s.com:2181,dwhadoopm301p.prod.ch3.s.com:2181	 	

public class ConfPlay
{
	enum BINDING_TYPE {
		ADMIN, PRODUCE, CONSUME
	};
	public final static String ENUSER = "ayakubo";
	public final static String ENPASS = "ayakubo15";
	
	public final static String BROKERS_PARAM_NAME = "metadata.broker.list";
	public final static String BROKERS_POC_KAFKA = "omniturert301p.dev.ch3.s.com, omniturert302p.dev.ch3.s.com, omniturert304p.dev.ch3.s.com"; 
	public final static String BROKERS_DEF_POC = "omniturert301p.dev.ch3.s.com"; 
	
	public final static String LOCAL_BROKER_DEF = "127.0.0.1:2181";
	public final static String BROKER_LOCAL = "localhost:9092";
	public final static String BROKER_DEF_POC = "omniturert301p.dev.ch3.s.com:9092";

//	public final static String BROKER_LIST_VAL_LOCAL = "127.0.0.1:2181";

	public final static String SERIAL_CLASS_NAME = "serializer.class";
//	public final static String SERIAL_CLASS_VAL = "kafka.serializer.StringEncoder";
	public final static String SERIAL_CLASS_VAL = "kafka.serializer.DefaultEncoder";

	public final static String PARTITIONR_CLASS_NAME = "partitioner.class";
	public final static String PARTITIONR_CLASS_VAL = "aly.kafka.play.PlayPart";

	public final static String REQUEST_ASK_NAME = "request.required.acks";
	public final static String REQUEST_ASK_VAL = "1";

	
	public final static String VERTICA_CONN_STR = "jdbc:vertica://verticapoc303p.dev.ch3.s.com:5433/nrtdb";    
	public final static String VTBL_PRICES_DET_1 = "ALY_PRICE_DET1";  // vertica table for TransTestPriceDetails
	
	public static Properties configureProp(BINDING_TYPE eBindType)
	{
		Properties props = new Properties();
		String BROKERS;

		switch (eBindType)
		{
		case ADMIN:
			BROKERS = BROKERS_PARAM_NAME;
			break;

		case PRODUCE:
			BROKERS = BROKER_LOCAL;
			break;

		default:
			BROKERS = BROKERS_PARAM_NAME;
			break;
		}

		props.put(ConfPlay.BROKERS_PARAM_NAME, BROKERS);

		props.put(ConfPlay.PARTITIONR_CLASS_NAME, ConfPlay.PARTITIONR_CLASS_VAL);
		props.put(ConfPlay.SERIAL_CLASS_NAME, ConfPlay.SERIAL_CLASS_VAL);
		props.put(ConfPlay.REQUEST_ASK_NAME, ConfPlay.REQUEST_ASK_VAL);
		return props;
	}

	public static ProducerConfig configure(BINDING_TYPE eBindType)
	{
		Properties props = configureProp(eBindType);
		ProducerConfig config = new ProducerConfig(props);
		return config;
	}
}

// see https://cwiki.apache.org/confluence/display/KAFKA/0.8.0+Producer+Example#
// props.put("metadata.broker.list", "broker1:9092,broker2:9092");
// props.put("serializer.class", "kafka.serializer.StringEncoder");
// props.put("partitioner.class", "example.producer.SimplePartitioner");
// props.put("request.required.acks", "1");
