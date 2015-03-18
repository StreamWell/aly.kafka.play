package aly.kafka.tranform;

import java.util.ArrayList;
import java.util.List;

import aly.kafka.obu.msg.FldNameType;
import aly.kafka.obu.msg.IHandler;
import aly.kafka.obu.msg.MetaField;
import aly.kafka.tools.StrValueExtractor;


// List<E> extends Collection<E>
public class TestStrMsgTransformer implements ITransormer
{
/**
 * This is a test transformer  
 * 
 * The message (payload) should have the following structure:
 * The  has a structure : 
 * 
 * 	<protomsg> := <header>,<body>
 *  <header> := <storID>,<transformerID>,<loaderID>
 *  <body := string,string,...  
 */
	public static final int handlerID = 100;
	
	private static final List<FldNameType> fldNamesTypes;
	
	static
	{
		fldNamesTypes = new ArrayList<FldNameType>();
		
		FldNameType nametype = FldNameType.create("fld1", IHandler.FldTypesEnum.E_STRING);
		fldNamesTypes.add(nametype);
		
		nametype = FldNameType.create("fld2", IHandler.FldTypesEnum.E_INT);
		fldNamesTypes.add(nametype);
		
		nametype = FldNameType.create("fld3", IHandler.FldTypesEnum.E_DOUBLE);
		fldNamesTypes.add(nametype);
	}
	
	public static void main(String[] args)
	{
		TestStrMsgTransformer trans = new TestStrMsgTransformer();
		String payload = "ets_msg_0,0,0.01";
		trans.transform(payload);
	}
	
	public TestStrMsgTransformer() {}
	
	@Override
	public int getHandlerID()
	{
		return handlerID;
	}
	
	@Override
	public List<MetaField> transform(String payload)
	{
		List<MetaField> metaFldList = new ArrayList<>();
		
		String[] elems = payload.split(",");

		int posInElem = 0;
		for(FldNameType nameType : fldNamesTypes)
		{
			String fldName = nameType.getFldName();
			String fldAsStr = elems[posInElem++];
			FldTypesEnum eTypeHint = nameType.getTypeHint();
			MetaField metaFld = MetaField.create(fldName, fldAsStr, eTypeHint);
			metaFldList.add(metaFld);
		}
		return metaFldList;
	}
}
