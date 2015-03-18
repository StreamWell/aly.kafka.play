package aly.kafka.tools;

import aly.kafka.containers.StrMsgPojo;

/* 
 * This is a common class to operate on message strMsgPojo and payload. 
 * The message payload is assumed to be a String (can be JSON as text.
 */
public class StrMsgUtil
{
	public static final String SEPARTOR2 = ",";
	public static final char SEPARTOR = ',';
	public static final int NUM_OF_PROTOMSG_PARTS = 6;
	
	static public void main(String [] args) throws StreamChannelExeption
	{
		String msg = "1,100,200,generated_0,0,0.01";
		StrMsgPojo pojo = parse(msg);
	}
	
	public static String decorate(String msgAsStr, int storeID, int transformerID, int loaderID)
	{
		String header = "" + storeID + SEPARTOR + transformerID + SEPARTOR + loaderID;
		String msg = header + SEPARTOR + msgAsStr;
		return msg;
	}
	
	public static StrMsgPojo parse(String protoMsg) throws StreamChannelExeption
	{
		int startPos = 0;
		int endPos = protoMsg.indexOf(SEPARTOR, startPos);
		String token_0 = protoMsg.substring(startPos, endPos);
		int storeID = new Integer(token_0);
		
		startPos = ++endPos;
		endPos = protoMsg.indexOf(SEPARTOR, startPos);
		String token_1 = protoMsg.substring(startPos, endPos);
		int transformerID = new Integer(token_1);
		
		startPos = ++endPos;
		endPos = protoMsg.indexOf(SEPARTOR, startPos);
		String token_3 = protoMsg.substring(startPos, endPos);
		int loaderID = new Integer(token_3);

		String payload = protoMsg.substring(endPos+1);
		StrMsgPojo strMsgPojo = new StrMsgPojo(storeID, transformerID, loaderID, payload);
		
		return strMsgPojo;
	}
}

/*
	public static StrMsgPojo parse2(String protoMsg) throws StreamChannelExeption
	{
		String[] tokens = protoMsg.split(SEPARTOR2);
		if (tokens.length != NUM_OF_PROTOMSG_PARTS)
			throw new StreamChannelExeption("StrMsgUtil.parse(): illegal proto message length");
		
		StrMsgPojo strMsgPojo = null;
		for(int pos=0; pos<NUM_OF_PROTOMSG_PARTS; pos++)
		{
			int storeID = new Integer(tokens[0]);
			int transformerID = new Integer(tokens[1]);
			int loaderID = new Integer(tokens[2]);
			String payload = tokens[3];
			strMsgPojo = new StrMsgPojo(storeID, transformerID, loaderID, payload);
		}
		return strMsgPojo;
	}

 */
