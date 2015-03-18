package aly.kafka.testEnv;

import aly.kafka.obu.msg.IProtoMsg;

/**
 * 
 * This is a pojo wrapper around raw data to convert to real IObuMessage (when necessary)
 *
 */
public class TestProtoMsg implements IProtoMsg
{
	static public TestProtoMsg create(String f1, int f2, double f3)
	{
		return new TestProtoMsg(f1, f2, f3);
	}
	
	private TestProtoMsg(String f1, int f2, double f3)
	{
		this.f1 = f1;
		this.f2 = f2;
		this.f3 = f3;
	}
	
	final String f1;
	final int f2;
	final double f3;
		
	public String getF1()
	{
		return f1;
	}

	public int getF2()
	{
		return f2;
	}

	public double getF3()
	{
		return f3;
	}	
	
	@Override
	public String toString()
	{
		String buf = f1 + "," + f2 + "," + f3;
		return buf;
	}
}
