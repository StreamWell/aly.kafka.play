package aly.kafka.tools;

import java.util.HashMap;
import java.util.Map;

import aly.kafka.obu.msg.HandlerRecord;
import aly.kafka.obu.msg.StoreCred;

/**
 * VERTICA_PRICES_DET - means Vertica, table = PRICES_DET  (details, each JSON elem goes to separate field, 
 * Fields names are JSON paths.
 * 
 *		ResAccountant accountant = ResAccounterBuilder.createInCode();
 */
public class ResAccounterBuilder
{
	static public ResAccountant createInCode()
	{
		Map<Integer,StoreCred> storeMap = new HashMap<>();
		Map<Integer,HandlerRecord> transfomerMap = new HashMap<>();
		Map<Integer,HandlerRecord> LoaderMap = new HashMap<>();
		
		int storeId = StoreEnum.VERTICA_PRICES_DET.ordinal();		// effectively that gives 1 
		StoreCred vertica_Store = StoreCred.createStoreCred(storeId, 				
				ConfPlay.VERTICA_CONN_STR, ConfPlay.ENUSER, ConfPlay.ENPASS);
		storeMap.put(storeId, vertica_Store);
		
		HandlerRecord transformerRec = HandlerRecord.create("aly.kafka.tranform.TestStrMsgTransformer");
		int handleID = transformerRec.getInstance().getHandlerID();
		transfomerMap.put(handleID, transformerRec);  
		
		HandlerRecord loaderRec = HandlerRecord.create("aly.kafka.loader.TestStrMsgLoader");
		int loaderID = transformerRec.getInstance().getHandlerID();
		LoaderMap.put(loaderID, loaderRec);
		
		ResAccountant accountant = new ResAccountant();
		accountant.configure(storeMap, transfomerMap, LoaderMap);
		
		return accountant;
	}
}
