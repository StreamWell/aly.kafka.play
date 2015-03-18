package aly.kafka.msgsource;

import java.util.Map;
import java.util.HashMap;

public class MsgSourceFactory
{
	/**
	 * GEN_SRC - GenMsfSource - msg generator
	 */
	public enum SourceEnum 
	{
		UNDEF("UNDER", 0), 
		TEST("TEST", 1);
		
		private String name;
		private int pos; 
		
		private SourceEnum(String n, int p) { name = n; pos = p; }

		public String getName()
		{
			return name;
		}

		public int getPos()
		{
			return pos;
		}
	}
	
	public static IMsgSource create(SourceEnum eSource, Map<String,Object> createConfig)
	{
		IMsgSource source = null;
		int sourceID = eSource.getPos();

		switch(eSource)
		{
			case TEST:
				int msgNumberToGenerate = (Integer)createConfig.get(GenMsgSource.MSG_NUM_NAME);
				Map<String,Object> sourceConfig = new HashMap<>();
				sourceConfig.put(GenMsgSource.MSG_NUM_NAME, msgNumberToGenerate);
				sourceConfig.put(GenMsgSource.BASE_MSG, "gen-msg");
				sourceConfig.put(IMsgSource.SOURCE_ID_NAME, new Integer(sourceID));
				source = new GenMsgSource(sourceConfig);
				break;
				
			default:
				throw new RuntimeException("MsgSourceFactory.create(): Not implemented msg source: " + eSource);
		}
		
		
		return source;
	}

}
