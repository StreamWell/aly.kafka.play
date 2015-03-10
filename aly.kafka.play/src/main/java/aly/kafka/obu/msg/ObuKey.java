package aly.kafka.obu.msg;

import java.io.Serializable;

public interface ObuKey extends Serializable
{
	//	Value to use in kafka provoder partitioning method
	public int getPartValue();
	
	public MsgMetadata getMeta();
}
