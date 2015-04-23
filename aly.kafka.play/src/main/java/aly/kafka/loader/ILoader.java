package aly.kafka.loader;

import java.util.List;

import aly.kafka.obu.msg.IHandler;
import aly.kafka.obu.msg.MetaField;
import aly.kafka.obu.msg.StoreDesc;
import aly.kafka.tools.StreamChannelExeption;

public interface ILoader extends IHandler
{
	void configure(StoreDesc storeCred, int batchSize, IStroreUtil storeUtil) throws StreamChannelExeption;
	boolean load(List<MetaField> fields) throws StreamChannelExeption;
}
