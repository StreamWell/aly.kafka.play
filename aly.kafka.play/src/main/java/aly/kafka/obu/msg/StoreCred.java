package aly.kafka.obu.msg;

public class StoreCred
{
	static public StoreCred createStoreCred(int storeID, String url, String user, String password)
	{
		return new StoreCred(storeID, url, user, password);
	}
	
	private StoreCred(int storeID, String url, String user, String password)
	{
		this.storeID = storeID;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	final int storeID;
	final String url;
	final String user;
	final String password;
	
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
}
