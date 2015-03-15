package aly.kafka.obu.msg;

import java.io.Serializable;

import aly.kafka.containers.MsgMetadata;

public interface ObuKey extends Serializable
{
	//	Value to use in kafka provoder partitioning method
	public int getPartValue();
	
	public MsgMetadata getMeta();
}
