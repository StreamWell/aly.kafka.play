package aly.kafka.tools;

enum StoreEnum {UNDEF, 
			// table in Vertica - dwhd_gold.ALY_PRICE_DET1 
			// transformer - TransTestPriceDetails
			// 
		VERTICA_PRICES_DET, 					
			// 6 fields from offer JSON doc	
		VERTICA_OFFER_6, 	
		// fragments of JSON offer doc.
		VERTICA_OFFER_JSON
		
//		StoreEnum findByOrdinal(int ordValue)
//		{
//			
//		return null;
//		}
	}