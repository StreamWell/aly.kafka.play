package aly.kafka.tools;

import java.util.Date;
import java.util.Random;

public class RandomKey
{
	static private final Random random = new Random((new Date()).getTime());

	static public RandomKey create()
	{
		RandomKey key = new RandomKey();
		return key;
	}
	
	private RandomKey()
	{
		val4Hash = random.nextInt();
	}
	
	int val4Hash;
}
