package aly.kafka.obu.msg;

import java.util.List;

public interface ITransormer  extends IHandler
{
	List<?> transform(String payload);
}


