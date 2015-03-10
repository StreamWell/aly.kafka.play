package aly.kafka.obu.msg;

class HandlerRecord
{
	static HandlerRecord create(String className)
	{
		if(className == null)
			throw new RuntimeException("HandlerRecord.create(): null classname");
		
		IHandler instance = null;
		try
		{
			Class<?> handlClass = Class.forName(className);
			instance = (IHandler)handlClass.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException("HandlerRecord.create(): fatal " + e);
		}
		
		HandlerRecord rec = new HandlerRecord(className, instance);
		return rec;
	}
	
	private HandlerRecord(String className, IHandler instance)
	{
		this.className = className;
		this.instance = instance;
	}
	
	final String className;
	final IHandler instance;
	
	public String getClassName()
	{
		return className;
	}

	public IHandler getInstance()
	{
		return instance;
	}
}