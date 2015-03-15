package aly.kafka.obu.msg;
/**
 * Container for operations with field value and description
 * 
 */
public class MetaField
{
	public static MetaField create(String fldName, String valueAsStr, IHandler.FldTypesEnum eTypeHint)
	{
		return new MetaField(fldName, valueAsStr, eTypeHint);
	}
	
	private MetaField(String fldName, String valueAsStr, IHandler.FldTypesEnum eTypeHint)
	{
		this.fldName = fldName;
		this.valueAsStr = valueAsStr;
		this.eTypeHint = eTypeHint;
	}
	
	final String fldName;
	final String valueAsStr;
	final IHandler.FldTypesEnum eTypeHint;
	
	public String getFldName()
	{
		return fldName;
	}

	public String getValueAsStr()
	{
		return valueAsStr;
	}

	public IHandler.FldTypesEnum geteTypeHint()
	{
		return eTypeHint;
	}
}
