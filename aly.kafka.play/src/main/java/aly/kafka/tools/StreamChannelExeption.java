package aly.kafka.tools;

@SuppressWarnings("serial")
public class StreamChannelExeption extends Exception
{
	public StreamChannelExeption(Exception ex)
	{
		super(ex);
	}
	
	public StreamChannelExeption(String msg)
	{
		super(msg);
	}

	public StreamChannelExeption(String msg, Exception ex)
	{
		super(msg + " " + ex.getMessage());
	}
}
