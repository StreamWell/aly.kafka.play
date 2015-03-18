package aly.kafka.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aly.kafka.loader.vertica.VUtils;
import aly.kafka.obu.msg.MetaField;
import aly.kafka.obu.msg.StoreCred;
import aly.kafka.tools.StrValueExtractor;
import aly.kafka.tools.StreamChannelExeption;

public class TestStrMsgLoader implements ILoader
{
	public static final int handlerID = 200;
	private static final String FIELDS_IN_ORDER = "fld1,fld2,fld3";
	private boolean bIsConfigured = false;								// replace for AtomicBoolean

//	private StoreCred storeCred;
//	private int batchSize = 3;
	
	private List<String> namesInOrderList;
	private VUtils vUtil = null;
	
	@Override
	public void configure(StoreCred storeCred, int batchSize) throws StreamChannelExeption
	{
		if(bIsConfigured)
			return;
		
		bIsConfigured = true;
		
//		this.storeCred = storeCred;
		String[] fieldNames = FIELDS_IN_ORDER.split(",");
		namesInOrderList = Arrays.asList(fieldNames);
//		this.batchSize = batchSize;
		vUtil = new VUtils(batchSize);
		vUtil.prepare();
	}

	@Override
	public int getHandlerID()
	{
		return handlerID;
	}

	/**
	 * Intellegent configurable batch loader
	 * The method takes the data (List of meta-fields, all together composing ONE row in target database) prepared by transformer.
	 * The method arrange the values from fields in a correct order and invokes the vUtils to insert row into target table
	 */
	@Override
	public boolean load(List<MetaField> fields) throws StreamChannelExeption
	{
		List<Object> valueList = new ArrayList<>();
		
		// NOTE: MetaField contains field value as string (the valueAsStr) 
		// also we here know the order of fields to use in insert statement, but fields (list) not necessary contains fields in the same order
		for(String column : namesInOrderList)
		{
			boolean bFound = false;;
			for(MetaField mField : fields)
			{
				if(column.equals(mField.getFldName()))
				{
					bFound = true;
					FldTypesEnum eTypeHint = mField.getTypeHint();
					String valueAsStr = mField.getValueAsStr();
					Object value = StrValueExtractor.extract(valueAsStr, eTypeHint);
					valueList.add(value);
					break;
				}
			}
			
			if(bFound == false)
				throw new StreamChannelExeption("load() failed: can't find meta for the clumn " + column);
		}
		vUtil.addRecord(valueList);
		return true;
	}
}
