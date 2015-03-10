package aly.kafka.obu.msg;

public class MetaDirector
{
	MetaDirector(ResAccountant value)
	{
		accountant = value;
	}
	
	ResAccountant accountant;
	
	public String getStoreURL(MsgMetadata meta)
	{
		int storeID =  meta.getStoreID();
		StoreCred storeCred = accountant.getStoreCred(storeID);
		return storeCred.getUrl();
	}
	
	public String getStoreUser(MsgMetadata meta)
	{
		int storeID =  meta.getStoreID();
		StoreCred storeCred = accountant.getStoreCred(storeID);
		return storeCred.getUser();
	}
	
	public String getStorePass(MsgMetadata meta)
	{
		int storeID =  meta.getStoreID();
		StoreCred storeCred = accountant.getStoreCred(storeID);
		return storeCred.getPassword	();
	}
	
	public ITransormer getTransformer(MsgMetadata meta)
	{
		int transID =  meta.getTransformerID();
		ITransormer transformer = accountant.getTransformer(transID);
		return transformer;
	}
	
	public ILoader getLoader(MsgMetadata meta)
	{
		int loadID =  meta.getLoaderID();
		ILoader loader = accountant.getLoader(loadID);
		return loader;
	}
}
