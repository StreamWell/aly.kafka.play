package aly.kafka.tranform;

import java.util.List;

import aly.kafka.obu.msg.IHandler;

public interface ITransormer  extends IHandler
{
	List<?> transform(String payload);
}


