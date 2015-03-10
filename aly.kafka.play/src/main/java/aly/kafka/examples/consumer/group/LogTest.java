package aly.kafka.examples.consumer.group;

import org.apache.log4j.Logger;

public class LogTest
{
	final static Logger logger = Logger.getLogger(GroupConsumer.class);

	public static void main(String[] args)
	{
		if(logger.isDebugEnabled()){
		    logger.debug("This is debug log : ");
		}
	 
		//logs an error message with parameter
		logger.error("This is error log : ");

	}
}
