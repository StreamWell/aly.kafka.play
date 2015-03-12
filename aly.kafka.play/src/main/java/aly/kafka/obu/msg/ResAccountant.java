package aly.kafka.obu.msg;

import java.util.Map;

import aly.kafka.loader.ILoader;
import aly.kafka.tranform.ITransormer;

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
	
	/**
	 *  some protection provided by package visibility
	 */
	void configure(Map<Integer,StoreCred> storeMap, Map<Integer,HandlerRecord> transfomerMap, Map<Integer,HandlerRecord> loaderMap)
	{
		this.storeMap = storeMap;
		this.transfomerMap = transfomerMap;
		this.loaderMap = loaderMap;
	}
	
	private Map<Integer,StoreCred> storeMap;
	private Map<Integer,HandlerRecord> transfomerMap;
	private Map<Integer,HandlerRecord> loaderMap;
	
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
		HandlerRecord rec = loaderMap.get(loaderID);
		ILoader loader = (ILoader)rec.getInstance();
		return loader;
	}
}
