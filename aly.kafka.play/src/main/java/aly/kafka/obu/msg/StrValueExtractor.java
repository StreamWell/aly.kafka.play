package aly.kafka.obu.msg;

import org.apache.log4j.Logger;

import aly.kafka.obu.msg.IHandler.FldTypesEnum;
import aly.kafka.play.tools.MyLogger;

public class StrValueExtractor
{
	static Logger myLog = MyLogger.createMyLogger("StrValueExtractor");

	public static Object extract(String sVal, FldTypesEnum eTypeHint)
	{
		Object ret = null;
		try
		{
			switch (eTypeHint)
			{
			case E_STRING:
			case E_JSON:
			{
				ret = sVal;
				break;
			}
			case E_INT:
			{
				ret = Integer.parseInt(sVal);
				break;
			}
			case E_DOUBLE:
			{
				ret = Double.parseDouble(sVal);
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
