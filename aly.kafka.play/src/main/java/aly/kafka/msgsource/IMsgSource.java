package aly.kafka.msgsource;

import java.util.Map;

import aly.kafka.obu.msg.IProtoMsg;

public interface IMsgSource
{
	String SOURCE_ID_NAME = "sourceID";
	
	IProtoMsg next();
	String nextAsStr();
	int sourceID();
}
