package aly.kafka.play;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class HashPartitioner implements Partitioner 
{
	public HashPartitioner(VerifiableProperties props)
	{
		
	}

	public int partition(Object key, int numPartitions)
	{
		String stringKey = (String) key;
		int hash = stringKey.hashCode();
		return hash % numPartitions;
	}
}
