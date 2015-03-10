package aly.kafka.local;

public class LocalConsumerRunner
{
	public static void main(String[] args)
	{
		int grpShift = 15;

		LocalConsumer localConsumerThread = LocalConsumer.create(
				DwHadopCluster.ZOO_CONNECT_LOC,   // ZOO_CONNECT_DW   ZOO_CONNECT_LOC
				"test", //DwHadopCluster.ALY_TOPIC, // "topic1"
				DwHadopCluster.ALY_GROUP + grpShift, 
				1);
		
		localConsumerThread.start();
//		localConsumerThread.run();
		System.out.println("LocalConsumerRunner.main() on exit");

	}
}
