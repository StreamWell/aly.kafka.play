package aly.kafka.tools;

import aly.kafka.obu.msg.StoreDesc;

public enum EnumStores 
{	
	VERTICA_TEST("VERTICA_TEST", 0),
	VERTICA_PRICES_DET("VERTICA_PRICES_DET", 1),
	VERTICA_OFFER_6("VERTICA_OFFER_6", 2);
	
	private String storeName;
	private int storeID;
	
	private EnumStores(String vtkName, int vtkID)
	{
		storeName = vtkName;
		storeID = vtkID;
	}
	
	public String getName()
	{
		return storeName;
	}

	public int getID()
	{
		return storeID;
	}

	public static int getSoreIdByName(String sName) //, ResAccountant accountent)
	{
		int storeID = -1;
		for(EnumStores item : EnumStores.values())
		{
			if(sName.equals(item.storeName))
			{
				storeID = item.getID();
				break;
			}
			
//			if(storeID == -1)
//			{
//				StoreCred accountent.getStoreCred(storeID);
//			}
			
		}
		
		return storeID;
	}
	
/*	
	
	private final String storeName;
    private final int storeID;
    
    Double s;
    String storeName;
    
	
    private EnumStores(String storeName, int storeID)
    {
    	
    	this.storeName = storeName;
    	this.storeID = storeID;
    }
//	
//	UNDEF(0, "UNDES"),
//	VERTICA_TEST(1, "VERTICA_TEST"),
//	VERTICA_PRICES_DET(1, "VERTICA_PRICES_DET"),
//	VERTICA_OFFER_6(2, "VERTICA_OFFER_6");
//	
	// table in Vertica - dwhd_gold.ALY_PRICE_DET1 
	// transformer - TransTestPriceDetails
	// 
*/
}
