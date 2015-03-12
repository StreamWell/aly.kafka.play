package aly.kafka.tranform;

import java.util.List;

import aly.kafka.obu.msg.IHandler;

/**
 * 
 * should allow creation with default constructor
 *
 */
public interface ITransormer  extends IHandler
{
	List<?> transform(String payload);
}


