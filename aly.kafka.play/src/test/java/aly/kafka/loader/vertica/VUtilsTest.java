package aly.kafka.loader.vertica;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import aly.kafka.tools.MyLogger;
import aly.kafka.tools.StreamChannelExeption;

public class VUtilsTest
{
	static VUtils vUtil;
	static List<String> fld1Vals;
	static List<Integer> fld2Vals;
	static List<Double> fld3Vals;
	static int size = 10;
	static Logger logger = MyLogger.createMyLoggerDefPath("VUtilsTest", Level.DEBUG);

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		vUtil = new VUtils(3);
		vUtil.prepare();
		fld1Vals = new ArrayList<String>(size);
		fld2Vals = new ArrayList<Integer>(size);
		fld3Vals = new ArrayList<Double>(size);
		
		for(int i=0; i<size; i++)
		{
			fld1Vals.add("_" + i + "-val");
			fld2Vals.add(i);
			fld3Vals.add(i + 0.01 * i);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		vUtil.close();
	}

	@Test
	public void testAddRecord() throws Exception
	{
		logger.debug("testAddRecord() on entry");
		
		for(int i=0; i< size; i++)
		{
			String v1 = fld1Vals.get(i);
			int v2 = fld2Vals.get(i);
			double v3 = fld3Vals.get(i);
			vUtil.addRecord(v1, v2, v3);
		}
		vUtil.flash();
	}
}
