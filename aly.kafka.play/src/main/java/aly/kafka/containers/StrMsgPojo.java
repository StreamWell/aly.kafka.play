package aly.kafka.containers;

/**
 * 	container to store/pass (all >) String Kafka messages payload and metadata
 */
public class StrMsgPojo
{
	public static void main(String[] args)
	{
	}

	public StrMsgPojo(int store, int transformer, int loader, String payload)
	{
		storeID = store;
		transformerID = transformer;
		loaderID = loader;
		this.payload = payload;
	}

	final int storeID ;
	final int transformerID;
	final int loaderID;
	final String payload;
	
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

	public String getPayload()
	{
		return payload;
	}	
	
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		buf.append("store: " + storeID);
		buf.append(" transformer: " + transformerID);
		buf.append(" loader: " + "" + loaderID);
		buf.append(" payload: " + "" + payload);
		
		return buf.toString();
	}
}
