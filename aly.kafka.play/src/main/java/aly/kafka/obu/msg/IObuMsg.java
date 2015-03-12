package aly.kafka.obu.msg;

import java.util.List;

/**
 * 
 * This is msg how it should comes FROM kafka
 *
 */
public interface IObuMsg extends IProtoMsg
{
	ObuKey getKey();
	List<?> getPayload();
}
