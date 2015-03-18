package aly.kafka.obu.msg;

import java.io.Serializable;
import java.util.List;

public class ObuStringMsg implements IObuMsg
{
	private static final long serialVersionUID = 5806732019684057597L;

	public static ObuStringMsg create(String payload, ObuKey key)
	{
		ObuStringMsg msg = new ObuStringMsg(payload, key);
		return msg;
	}
	
	private ObuStringMsg(String payload, ObuKey key2)
	{
		
	}

	List<String> payload;
	ObuKey key;
	
	@Override
	public ObuKey getKey()
	{
		return null;
	}

	@Override
	public List<?> getPayload()
	{
		return null;
	}

}
