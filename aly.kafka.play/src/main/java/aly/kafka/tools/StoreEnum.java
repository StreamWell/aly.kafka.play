package aly.kafka.tools;

public enum StoreEnum {UNDEF, 
			// table in Vertica - dwhd_gold.ALY_PRICE_DET1 
			// transformer - TransTestPriceDetails
			
	
		VER_TEST_TBL, 					// =======VERTICA SECTION=========
			// vertica TEST table
		VER_PRICE_6_TBL, 		
			// Vertica table, all fields from price JSON doc	
		VER_PRICE_ALL_TBL, 		
		// Vertica table, 6 selected fields from offer JSON doc	
		VER_OFFER_6, 	
		// Vertica table, fragments of JSON offer doc.
		VER_OFFER_JSON,
			
		MYSQL_TEST_TBL;					// =========MYSQL SECTION=========	
		// mysql TEST table: 3 fields: fld1 - sreing, fld2 - int, fld3 - double
		
		
//		StoreEnum findByOrdinal(int ordValue)
//		{
//			
//		return null;
//		}
	}