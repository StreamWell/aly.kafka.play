package aly.kafka.obu.msg;

import java.util.List;

public interface ObuMessage
{
	ObuKey getKey();
	List<?> getPayload();
}
