package aly.kafka.obu.msg;

import java.util.Map;

public class ResAccountant
{
	private static class SingletonHolder
	{
		private static final ResAccountant INSTANCE = build();
	}
	
	public static ResAccountant getInstance() 
	{
        return SingletonHolder.INSTANCE;
	}	
	
	ResAccountant() {}
	
	private static ResAccountant build()
	{
		return ResAccounterBuilder.createInCode();
	}
	
	private Map<Integer,StoreCred> storeMap;
	private Map<Integer,HandlerRecord> transfomerMap;
	private Map<Integer,HandlerRecord> LoaderMap;
	
	StoreCred getStoreCred(int storeID)
	{
		return storeMap.get(storeID);
	}
	
	ITransormer getTransformer(int transformerID)
	{
		HandlerRecord rec = transfomerMap.get(transformerID);
		ITransormer transformer = (ITransormer)rec.getInstance();
		return transformer;
	}
	
	ILoader getLoader(int loaderID)
	{
		HandlerRecord rec = LoaderMap.get(loaderID);
		ILoader loader = (ILoader)rec.getInstance();
		return loader;
	}
}
