package aly.kafka.obu.msg;

import java.util.HashMap;
import java.util.Map;

import aly.kafka.play.tools.ConfPlay;

/**
 * VERTICA_PRICES_DET - means Vertica, table = PRICES_DET  (details, each JSON elem goes to separate field, 
 * Fields names are JSON paths.
 * 
 *
 */
public class ResAccounterBuilder
{
	enum StoreEnum {UNDEF, 
			// table in Vertica - dwhd_gold.ALY_PRICE_DET1 
			// transformer - TransTestPriceDetails
			// 
		VERTICA_PRICES_DET, 					
			// 6 fields from offer JSON doc	
		VERTICA_OFFER_6, 	
		// fragments of JSON offer doc.
		VERTICA_OFFER_JSON};					
	
	static public ResAccountant createInCode()
	{
		Map<Integer,StoreCred> storeMap = new HashMap<>();
		Map<Integer,HandlerRecord> transfomerMap = new HashMap<>();
		Map<Integer,HandlerRecord> LoaderMap = new HashMap<>();
		
		int storeId = StoreEnum.VERTICA_PRICES_DET.ordinal();		// effectively that gives 1 
		StoreCred vertica_Store = StoreCred.createStoreCred(storeId, 				
				ConfPlay.VERTICA_CONN_STR, ConfPlay.ENUSER, ConfPlay.ENPASS);
		storeMap.put(storeId, vertica_Store);
		
		HandlerRecord transformerRec = HandlerRecord.create("aly.kafka.tranform.TransTestPriceDetails");
		int handleID = transformerRec.getInstance().getHandlerID();
		transfomerMap.put(handleID, transformerRec);  
		
		HandlerRecord loaderRec = HandlerRecord.create("aly.kafka.loader.LoadTestPriceDetails");
		int loaderID = transformerRec.getInstance().getHandlerID();
		LoaderMap.put(loaderID, loaderRec);
		
		ResAccountant accountant = new ResAccountant();
		accountant.configure(storeMap, transfomerMap, LoaderMap);
		
		return accountant;
	}
}
