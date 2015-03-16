package aly.kafka.obu.msg;

import java.util.List;

import aly.kafka.obu.msg.IHandler.FldTypesEnum;

public interface IHandler
{
	enum FldTypesEnum 
	{
		E_STRING("string"), 
		E_INT("int"), 
		E_DOUBLE("double"), 
		E_JSON("json");
		
		private final String name;       

	    private FldTypesEnum(String s) {name = s;}
		
	    public boolean hasName(String otherName)
	    {
	        return (otherName == null)? false:name.equals(otherName);
	    }

	    public String toString(){
	       return name;
	    }
	};
	
	int getHandlerID();
}
