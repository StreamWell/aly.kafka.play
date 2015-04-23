package aly.kafka.tools;

import java.util.HashMap;
import java.util.Map;

import aly.kafka.obu.msg.HandlerRecord;
import aly.kafka.obu.msg.StoreDesc;

/**
 * VERTICA_TESTTBL - means Vertica, table = TESTTBL  (details, each JSON elem goes to separate field, 
 * Fields names are JSON paths.
 * 
 *		ResAccountant accountant = ResAccounterBuilder.createInCode();
 */
public class ResBuilder
{
	static public void main (String [] args[])
	{
		ResBuilder.createInCode();
	}
	
//	static ResAccountant
	
	static public ResAccountant createInCode()
	{
		Map<Integer,StoreDesc> storeMap = new HashMap<>();
		Map<Integer,HandlerRecord> transfomerMap = new HashMap<>();
		Map<Integer,HandlerRecord> LoaderMap = new HashMap<>();
		
		int storeId = StoreEnum.VER_TEST_TBL.ordinal();		// effectively that gives maybe 1, see StoreEnum def.
		StoreDesc vertica_TestCred = StoreDesc.createStoreDesc(storeId, 				
				ConfPlay.VERTICA_CONN_STR, ConfPlay.VER_TEST_USER, 
				ConfPlay.VER_TEST_PASS, "aly.kafka.driver.StrMsgWorker", ConfPlay.BreedStoreEnum.VERTICA);
		storeMap.put(storeId, vertica_TestCred);
		
		HandlerRecord transformerRec = HandlerRecord.create("aly.kafka.tranform.TestStrMsgTransformer");
		int handleID = transformerRec.getInstance().getHandlerID();
		transfomerMap.put(handleID, transformerRec);  
		
		HandlerRecord loaderRec = HandlerRecord.create("aly.kafka.loader.TestStrMsgLoader");
		int loaderID = loaderRec.getInstance().getHandlerID();
		LoaderMap.put(loaderID, loaderRec);
		
		storeId = StoreEnum.MYSQL_TEST_TBL.ordinal();
		StoreDesc mySql_TestCred = StoreDesc.createStoreDesc(storeId, 				
				ConfPlay.MYSQL_CONN_STR, ConfPlay.MYSQL_TEST_USER, 
				ConfPlay.MYSQL_TEST_PASS, "aly.kafka.driver.StrMsgWorker", ConfPlay.BreedStoreEnum.MYSQL);
		storeMap.put(storeId, mySql_TestCred);
		
		ResAccountant accountant = new ResAccountant();
		accountant.configure(storeMap, transfomerMap, LoaderMap);
		
		return accountant;
	}
}
