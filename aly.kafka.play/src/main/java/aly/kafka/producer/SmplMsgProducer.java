package aly.kafka.producer;

import java.util.Scanner;
import java.util.logging.Logger;

public class SmplMsgProducer
{	
	public static Logger LOGGER = Logger.getLogger("SmplMsgProducer");
	final Scanner scanner = new Scanner(System.in);

	static public void main(String[] args)
	{
		LOGGER.info("main DONE, no errors.");
	}
	
	public SmplMsgProducer() {}

	private String askMsg()
	{
		return null;
	}
}
