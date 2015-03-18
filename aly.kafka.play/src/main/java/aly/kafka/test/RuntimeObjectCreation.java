package aly.kafka.test;

public class RuntimeObjectCreation
{

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		RuntimeObjectCreation aaa = new RuntimeObjectCreation();
		Class<?> clazz1 = Class.forName("aly.kafka.test.RuntimeObjectCreation");
	    Object obgg1 = clazz1.newInstance();
	    System.out.println(obgg1.getClass().getName());

		Class<?> clazz2 = Class.forName("aly.kafka.testEnv.TestMsgWorker");
	    Runnable obgg2 = (Runnable)clazz2.newInstance();
//	    Object obgg2 = clazz2.newInstance();
	    System.out.println(obgg2.getClass().getName());
	}
}
