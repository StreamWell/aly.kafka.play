package aly.kafka.obu.msg;

import java.util.HashMap;
import java.util.Map;

/**
 * VERTICA_PRICES_DET - means Vertica, table = PRICES_DET  (details, each JSON elem goes to separate field, 
 * Fields names are JSON paths.
 * 
 *
 */
public class ResAccounterBuilder
{
	enum STORES {UNDEF, 
		VERTICA_PRICES_DET, 					// test table in Vertica to be used with the transformer TransTestPriceDetails
		VERTICA_OFFER_6, 						// 6 fields from offer JSON doc
		VERTICA_OFFER_JSON};					// fragments of JSON offer doc.
	
	static public ResAccountant createInCode()
	{
		ResAccountant accounter = new ResAccountant();
		
		Map<Integer,StoreCred> storeMap = new HashMap<>();
		Map<Integer,HandlerRecord> transfomerMap = new HashMap<>();
		Map<Integer,HandlerRecord> LoaderMap = new HashMap<>();
		
		int storeId = STORES.VERTICA_PRICES_DET.ordinal();		// effectively that gives 1 
		StoreCred vertica_Store = StoreCred.createStoreCred(storeId, 				
				"jdbc:vertica://verticapoc303p.dev.ch3.s.com:5433/nrtdb", "ayakubo", "Ledocol95");
		storeMap.put(1, vertica_Store);
		
		return accounter;
	}
}
