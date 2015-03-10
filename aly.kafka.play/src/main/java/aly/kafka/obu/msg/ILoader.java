package aly.kafka.obu.msg;

import java.util.List;

public interface ILoader extends IHandler
{
	boolean load(List<?> fields);
}
