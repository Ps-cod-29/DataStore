package com.datastore.main;
import java.util.Date;
import org.json.simple.JSONObject;
import com.datastore.main.bean.User;
import com.datastore.main.utils.Operations;
import com.datastore.main.utils.Responses;


public final class DataStore {

	private String dsLocation = "";
	private String dsName = "";

	
	public DataStore()
	{
		try 
		{
			dsLocation = Responses.defaultdsLocation;
			dsName = "datastore-" + Operations.getProcessName();
		} 
		catch (Exception exception) 
		{

		}
	}

	
	
	public DataStore(String filePath) 
	{
		try 
		{
			dsLocation = filePath;
			dsName = "datastore-" + Operations.getProcessName();
		} 
		catch (Exception exception) 
		{

		}

	}

	
	public synchronized String create(String key, JSONObject value)
	{
		try 
		{
			return create(key, value, -1);
		} 
		catch (Exception exception) 
		{
			return Responses.CREATE_FAILURE;
		}
	}

	
	public synchronized String create(String key, JSONObject value,int timeToLive) 
	{
		try 
		{
			String filePath = dsLocation + "\\" + dsName;
			
			if (!Operations.isKeyNameValid(key)) 
			{
				return Responses.KEY_LENGTH_EXCEEDED;
			}
			if (Operations.isKeyExists(key, filePath)) 
			{
				return Responses.KEY_ALREADY_AVAILABLE;
			}
			
			
			User data = new User();
			data.setKey(key);
			
			
			if (timeToLive > 0) 
			{
				data.setTimeToLive(timeToLive);
			}
			else 
			{
				data.setTimeToLive(-1);
			}
			data.setValue(value);
			data.setCreationDateTimeMillis(new User().getTime());

			if (Operations.writeData(data, filePath)) 
			{
				return Responses.CREATE_SUCCESS;
			} 
			else 
			{
				return Responses.CREATE_FAILURE;
			}
		} 
		catch (Exception exception) 
		{
			return Responses.CREATE_FAILURE;
		}
	}

	public synchronized Object read(String key)
	{
		try {
			String filePath = dsLocation + "\\" + dsName;
			
			if (!Operations.isKeyNameValid(key)) 
			{
				return Responses.KEY_LENGTH_EXCEEDED;
			}
			if (!Operations.isKeyExists(key, filePath)) 
			{
				return Responses.KEY_NOT_AVAILABLE;
			}
			

			User data = Operations.readData(key, filePath);
			if (null != data) 
			{
				return data.getValue();
			}
			return Responses.READ_FAILURE;
		} 
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return Responses.READ_FAILURE;
		}
	}

	
	public synchronized Object delete(String key)
	{
		try 
		{
			
			String filePath = dsLocation + "\\" + dsName;
			
			if (!Operations.isKeyNameValid(key))
			{
				return Responses.KEY_LENGTH_EXCEEDED;
			}
			if (!Operations.isKeyExists(key, filePath)) 
			{
				return Responses.KEY_NOT_AVAILABLE;
			}
			

			
			
			if (Operations.deleteData(key, filePath))
			{
				
				return Responses.DELETE_SUCCESS;
			    
			}
			return Responses.DELETE_FAILURE;
		} 
		
		
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return Responses.DELETE_FAILURE;
		}
	}
}