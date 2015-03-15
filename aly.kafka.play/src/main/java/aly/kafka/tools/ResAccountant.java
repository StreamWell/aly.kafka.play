package aly.kafka.tools;

import java.util.Map;

import aly.kafka.loader.ILoader;
import aly.kafka.obu.msg.StoreCred;
import aly.kafka.tranform.ITransormer;
import aly.kafka.obu.msg.HandlerRecord;

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
	
	public StoreCred getStoreCred(int storeID)
	{
		return storeMap.get(storeID);
	}
	
	public ITransormer getTransformer(int transformerID)
	{
		HandlerRecord rec = transfomerMap.get(transformerID);
		ITransormer transformer = (ITransormer)rec.getInstance();
		return transformer;
	}
	
	public ILoader getLoader(int loaderID)
	{
		HandlerRecord rec = loaderMap.get(loaderID);
		ILoader loader = (ILoader)rec.getInstance();
		return loader;
	}
}
