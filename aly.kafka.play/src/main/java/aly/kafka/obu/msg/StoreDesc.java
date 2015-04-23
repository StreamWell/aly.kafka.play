package aly.kafka.obu.msg;

import aly.kafka.tools.ConfPlay;

public class StoreDesc
{
	static public StoreDesc createStoreDesc(int storeID, String url, String user, String password, 
			String storeWorker, ConfPlay.BreedStoreEnum eStoreBreed)
	{
		return new StoreDesc(storeID, url, user, password, storeWorker, eStoreBreed);
	}
	
	private StoreDesc(int storeID, String url, String user, String password, 
			String storeWorker, ConfPlay.BreedStoreEnum eStoreBreed)
	{
		this.storeID = storeID;
		this.url = url;
		this.user = user;
		this.password = password;
		this.storeWorker = storeWorker;
		this.eStoreBreed = eStoreBreed;
	}

	final int storeID;
	final String url;
	final String user;
	final String password;
	final String storeWorker;
	final ConfPlay.BreedStoreEnum eStoreBreed;
	
	public String getUrl()
	{
		return url;
	}

	public String getUser()
	{
		return user;
	}

	public String getPassword()
	{
		return password;
	}
	
	public String getStoreWorker()
	{
		return storeWorker;
	}
	
	public ConfPlay.BreedStoreEnum getStorreBreed()
	{
		return eStoreBreed;
	}
}
