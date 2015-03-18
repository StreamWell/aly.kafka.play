package aly.kafka.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeManipulation
{
	static final String FORMAT = "yyyy-MM-dd - HH:mm:ss:SSS";
	static public void main(String [] args)
	{
		SimpleDateFormat dataFomatter = new SimpleDateFormat(FORMAT);
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    long nowTime = cal.getTime().getTime();
	    String nowAsStr = dataFomatter.format(nowTime);
	    
	    cal.roll(Calendar.HOUR_OF_DAY, false); // adds one hour
	    long backTime = cal.getTime().getTime(); // returns new date object, one hour in the past
	    String backAsStr = dataFomatter.format(backTime);
	    
	    System.out.println("now   " + nowAsStr);
	    System.out.println("back   " + backAsStr);

	}
}
