package aly.kafka.msgsource;

import java.util.Map;

import aly.kafka.obu.msg.IProtoMsg;
import aly.kafka.testEnv.TestProtoMsg;

public class GenMsgSource implements IMsgSource
{	
	public static final String MSG_NUM_NAME = "msgNum";
	public static final String BASE_MSG = "baseMsg";
	
	final int sourceID;
	int msgNumber;
	int count = 0;
	String f1Base = "_GenMsgSource_";
	
	GenMsgSource(Map<String, Object> configMap)
	{
		msgNumber = (Integer)configMap.get(MSG_NUM_NAME);
		sourceID = (Integer)configMap.get(SOURCE_ID_NAME);
	}
	
	GenMsgSource(int msgNum, String f1Base, int sourceID)
	{
		this.f1Base = f1Base;
		msgNumber = msgNum;
		this.sourceID = sourceID;
	}

	@Override
	public int sourceID()
	{
		return sourceID;
	}
	
	@Override
	public IProtoMsg next()
	{
		IProtoMsg proto = null;
		
		if(count < msgNumber)
		{
			String f1 = f1Base + count;
			int f2 = count;
			double f3 = count + 0.01;
			proto = TestProtoMsg.create(f1, f2, f3);
		}	
		count++;
		return proto;
	}	
	
	public String nextAsStr()
	{
		IProtoMsg proto = (IProtoMsg) next();
		if(proto == null)
			return null;
		
		return proto.toString();
	}
}
