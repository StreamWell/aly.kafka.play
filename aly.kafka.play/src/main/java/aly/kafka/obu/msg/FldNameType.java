package aly.kafka.obu.msg;

import aly.kafka.obu.msg.IHandler.FldTypesEnum;

public class FldNameType
{
	public static FldNameType create(String fldName, IHandler.FldTypesEnum typeHint)
	{
		return new FldNameType(fldName, typeHint);
	}
	
	private FldNameType(String fldName, IHandler.FldTypesEnum typeHint)
	{
		this.fldName = fldName;
		this.eTypeHint = typeHint;
	}
	
	final String fldName;
	final FldTypesEnum eTypeHint;
	
	public String getFldName()
	{
		return fldName;
	}

	public FldTypesEnum getTypeHint()
	{
		return eTypeHint;
	}
}