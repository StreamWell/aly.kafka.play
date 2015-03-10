package aly.kafka.local;

import java.util.Date;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class SmplProducer 
{
/**
 * 	some 'pretty-poor' test - run script (from /appl/hdobudw/kafka/current) 
 * ./consumeTopic.sh aly-t1
 * 
 * then stop it with Ctrl+C
 */
	
	public static void main(String [] argv) 
	{
        Properties props = new Properties();
        props.put("metadata.broker.list", DwHadopCluster.BROKER_LIST_DW);		// BROKER_LIST_LOC   BROKER_LIST_DW
        props.put("serializer.class", DwHadopCluster.STR_SERIOALIZER);			// default ?
        props.put("partitioner.class", DwHadopCluster.SMPL_PARTITIONER);			// same as default, and can be not specified?
        props.put("request.required.acks", "1");									// the default would be no acks 
 
        ProducerConfig config = new ProducerConfig(props);
 
        Producer<String, String> producer = new Producer<String, String>(config);
 
        for (int i = 0; i < 10; i++) { 
        	   String sKey = "key_" + i;
         	   String sValue = "msg-val_" + i;
               KeyedMessage<String, String> msg = new KeyedMessage<String, String>(DwHadopCluster.ALY_TOPIC, sKey, sValue);
               producer.send(msg);
        }
        producer.close();
        System.out.println("SmplProducer.main() : seems ok");
    }
}