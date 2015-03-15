/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aly.kafka.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import aly.kafka.consumer.group.ConsumerWorker;
import aly.kafka.tools.ConfPlay;
import aly.kafka.tools.KafkaConfigFactory;
import aly.kafka.tools.MyLogger;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class LocalConsumer extends Thread
{
	static Logger myLog = MyLogger.createMyLogger("LocalConsumer");

	public static void main(String[] args)
	{
		myLog.setAdditivity(false);
		myLog.info("LocalConsumerRunner.main() on entry");		
		
		String myBroker = ConfPlay.LOCAL_BROKER_DEF;
		String myGroup = "MAR-7_B";
		String myTopic = "zzz";
		
		LocalConsumer localConsumer = LocalConsumer.create(myBroker, myTopic, myGroup, 1);		
		localConsumer.run();
		myLog.info("LocalConsumerRunner.main() on exit");
	}
	
	static public LocalConsumer create(String skConnectStr, String topic, String sGroup,
			int numThreads)
	{
		ConsumerConfig consConfig = KafkaConfigFactory.createConsumerConfig(skConnectStr, sGroup);
		LocalConsumer locCons = new LocalConsumer(consConfig, topic, numThreads);

		return locCons;
	}

	private static ConsumerConfig buildConsumerConfig(String skConnectStr, String sGroup)
	{
		ConsumerConfig consConfig = KafkaConfigFactory.createConsumerConfig(skConnectStr, sGroup);
		return consConfig;
	}

	private final ConsumerConnector consumerConnector;
	private final String topic;
	int numThreads;
	long count;
	boolean bStop;

	LocalConsumer(ConsumerConfig config, String topic, int numThreads)
	{
		consumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(config);
		this.topic = topic;
		this.numThreads = numThreads;
		count = 0L;
	}

	public void run()
	{
		System.out.println("Entering LocalConsumer.run()");
		
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		// topicCountMap.put(topic, new Integer(1));
		topicCountMap.put(topic, numThreads);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector
				.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();

		try
		{
			while (it.hasNext())
			{
				count++;
//				logger.debug("***********" + new String(it.next().message()));
				byte[] arr  = it.next().message();
				String s = new String(arr);
//				System.out.println(s);
				myLog.debug(s);
			}
		}
		catch( kafka.consumer.ConsumerTimeoutException ex)
		{
			System.out.println("consumer.timeout: probably no more msgs");
		}
		finally
		{
			consumerConnector.shutdown();
		}
		System.out.println("Exiting LocalConsumer.run(): count " + count);
	}

	public long getCount()
	{
		return count;
	}
}
