package aly.kafka.tools;

import org.apache.log4j.Logger;

import aly.kafka.obu.msg.IHandler;
import aly.kafka.obu.msg.IHandler.FldTypesEnum;

public class StrValueExtractor
{
	static Logger myLog = MyLogger.createMyLogger("StrValueExtractor");

	public static Object extract(String sValueAsStr, FldTypesEnum eTypeHint)
	{
		Object ret = null;
		try
		{
			switch (eTypeHint)
			{
			case E_STRING:
			case E_JSON:
			{
				ret = sValueAsStr;
				break;
			}
			case E_INT:
			{
				ret = Integer.parseInt(sValueAsStr);
				break;
			}
			case E_DOUBLE:
			{
				ret = Double.parseDouble(sValueAsStr);
				break;
			}
			default:
				;
			}
		}
		catch (Exception ex)
		{
			
		}
		return ret;
	}

	public Double convertToDouble(String sVal)
	{
		return null;
	}
}
