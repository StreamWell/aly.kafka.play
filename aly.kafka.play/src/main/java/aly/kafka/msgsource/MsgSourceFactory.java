package aly.kafka.msgsource;

import java.util.Map;
import java.util.HashMap;

public class MsgSourceFactory
{
	public static void main(String [] args[])
	{
		int storeID1 = CreateSrcEnum.MySqlTEST.getPos();
		int storeID2 = CreateSrcEnum.VerTEST.getPos();
		int storeID3 = CreateSrcEnum.MEMSQLTEST.getPos();
		System.out.println("" + storeID1 + " " + storeID2 + " " + storeID3);
	}
	
	public static IMsgSource create(CreateSrcEnum eSource, Map<String,Object> createConfig)
	{
		IMsgSource source = null;
		int sourceID = eSource.getPos();

		switch(eSource)
		{
			case VerTEST:
			{
				int msgNumberToGenerate = (Integer)createConfig.get(GenMsgSource.MSG_NUM_NAME);
				Map<String,Object> sourceConfig = new HashMap<>();
				sourceConfig.put(GenMsgSource.MSG_NUM_NAME, msgNumberToGenerate);
				sourceConfig.put(GenMsgSource.BASE_MSG, "gen-msg");
				sourceConfig.put(IMsgSource.SOURCE_ID_NAME, new Integer(sourceID));
				source = new GenMsgSource(sourceConfig);
				break;
			}
			
			case MySqlTEST:
			{
				int msgNumberToGenerate = (Integer)createConfig.get(GenMsgSource.MSG_NUM_NAME);
				Map<String,Object> sourceConfig = new HashMap<>();
				sourceConfig.put(GenMsgSource.MSG_NUM_NAME, msgNumberToGenerate);
				sourceConfig.put(GenMsgSource.BASE_MSG, "gen-msg");
				sourceConfig.put(IMsgSource.SOURCE_ID_NAME, new Integer(sourceID));
				source = new GenMsgSource(sourceConfig);
				break;
			}
			
			default:
				throw new RuntimeException("MsgSourceFactory.create(): Not implemented msg source: " + eSource);
		}
		
		
		return source;
	}

}
