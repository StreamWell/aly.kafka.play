package aly.kafka.tranform;

import java.util.List;

import aly.kafka.obu.msg.IHandler;
import aly.kafka.obu.msg.MetaField;

/**
 * 
 * should allow creation with default constructor
 *
 */
public interface ITransormer  extends IHandler
{
	List<MetaField> transform(String protoMsg);
}


