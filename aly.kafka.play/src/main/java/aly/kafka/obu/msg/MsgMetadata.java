package aly.kafka.obu.msg;

/**
 * This class captures the metadata part of (each) Kafka message
 */
public class MsgMetadata
{
	public MsgMetadata(int store, int transformer, int loader)
	{
		storeID = store;
		transformerID = transformer;
		loaderID = loader;
	}
	
	public final int storeID;
	public final int transformerID;
	public final int loaderID;
	
	public int getStoreID()
	{
		return storeID;
	}
	public int getTransformerID()
	{
		return transformerID;
	}
	public int getLoaderID()
	{
		return loaderID;
	}
}
