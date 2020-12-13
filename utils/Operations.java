package com.datastore.main.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;

import com.datastore.main.bean.User;


public class Operations {
	public static String getProcessName() 
	{
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		return processName;
	}

	
	public static boolean isKeyNameValid(String key) 
	{
		if (key.length() > Responses.KEY_MAX_LENGTH)
		{
			return false;
		}
		return true;
	}

	
	public static boolean isKeyExists(String key, String filePath) 
	{
		boolean isKeyExists = false;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		HashMap<String, User> dataMap = new HashMap<String, User>();
		try 
		{
			File file = new File(filePath);

			if (file.exists()) {
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, User>)objectInputStream.readObject();
				
				if (dataMap.containsKey(key)) 
				{
					isKeyExists = true;
				}

				fileInputStream.close();
				objectInputStream.close();
			}

			
			if (isKeyExists) 
			{
				User data = dataMap.get(key);
				long currentDateTimeMillis = new Date().getTime();
				if (data.getTimeToLive() > 0&& (currentDateTimeMillis - data.getCreationDateTimeMillis()) >= (data.getTimeToLive() * Responses.MILLISECONDS))
				{
					dataMap.remove(key);
					fileOutputStream = new FileOutputStream(file);
					objectOutputStream = new ObjectOutputStream(fileOutputStream);
					objectOutputStream.writeObject(dataMap);
					fileOutputStream.close();
					objectOutputStream.close();
					isKeyExists = false;
				}
			}
		} 
		catch (Exception exception) 
		{
			exception.printStackTrace();
		} 
		finally 
		{
			if (fileInputStream != null)
			{
				try
				{
					fileInputStream.close();
				}
				catch (IOException e) 
				{
				
					e.printStackTrace();
				}
			}
			if (objectInputStream != null) 
			{
				try 
				{
					objectInputStream.close();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return isKeyExists;
	}

	
 public static boolean writeData(User data, String filePath) 
	{
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		HashMap<String, User> dataMap = null;
		try 
		{
			File file = new File(filePath);
			if (file.exists())
			{
				
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, User>) objectInputStream.readObject();

				fileInputStream.close();
				objectInputStream.close();

				
				dataMap.put(data.getKey(), data);

				
				fileOutputStream = new FileOutputStream(file);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(dataMap);
				fileOutputStream.close();
				objectOutputStream.close();

				return true;
		    } 
			else 
			{
				dataMap = new HashMap<String, User>();
				dataMap.put(data.getKey(), data);

				fileOutputStream = new FileOutputStream(file);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(dataMap);
				fileOutputStream.close();
				objectOutputStream.close();

				return true;
			}
		} 
		catch (Exception exception) 
		{
			return false;
		} 
		finally 
		{
			if (fileInputStream != null)
			{
				  try 
				   {
					    fileInputStream.close();
				   }
				 catch (IOException e)
				   {
		         e.printStackTrace();
				   }
			}
			if (objectInputStream != null)
			{
				  try
				  {
					   objectInputStream.close();
				  } 
				  catch (IOException e)
				  {
				     e.printStackTrace();
				  }
			}
			if (fileOutputStream != null) 
		  {
				  try 
				  {
					   fileOutputStream.close();
				  } 
				  catch (IOException e)
				  {
					   e.printStackTrace();
				  }
			}
			if (objectOutputStream != null) 
			{
				  try
				  {
					   objectOutputStream.close();
				  } 
				  catch (IOException e) 
				  {
					   e.printStackTrace();
				  }
			}
		}
	}

	
	public static User readData(String key, String filePath) 
	{
		ObjectInputStream objectInputStream = null;
		HashMap<String, User> dataMap = null;
		try 
		{
			File file = new File(filePath);
			if (file.exists()) 
			{
				
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, User>) objectInputStream.readObject();

				fileInputStream.close();
				objectInputStream.close();
				return dataMap.get(key);
			} 
			else 
			{
				return null;
			}
		} 
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return null;
		} 
		finally 
		{
			if (fileInputStream != null) 
			{
				try
				{
					fileInputStream.close();
				} 
				catch (IOException e) 
				{
					
					e.printStackTrace();
				}
			}
			if (objectInputStream != null)
			{
				try 
				{
					objectInputStream.close();
				} 
				catch (IOException e) 
				{
					
					e.printStackTrace();
				}
			}
		}
	}


	public static boolean deleteData(String key, String filePath)
	{

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		HashMap<String, User> dataMap = null;
		try 
		{
			File file = new File(filePath);
			if (file.exists()) 
			{
				
				fileInputStream = new FileInputStream(file);
				objectInputStream = new ObjectInputStream(fileInputStream);
				dataMap = (HashMap<String, User>) objectInputStream.readObject();
                fileInputStream.close();
				objectInputStream.close();

				
				dataMap.remove(key);

				
				fileOutputStream = new FileOutputStream(file);
				objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(dataMap);
				fileOutputStream.close();
				objectOutputStream.close();

				return true;
			}
			return false;
		} 
		catch (Exception exception) 
		{
			return false;
		} 
		finally 
		{
			if (fileInputStream != null)
			{
				try
				{
					fileInputStream.close();
				} 
				catch (IOException e) 
				{
					
					e.printStackTrace();
				}
			}
			if (objectInputStream != null)
			{
				try 
				{
					objectInputStream.close();
				} 
				catch (IOException e) 
				{
					
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null)
			{
				 try
				 {
					fileOutputStream.close();
				 } 
				 catch (IOException e) 
				 {
				   e.printStackTrace();
				 }
			}
			if (objectOutputStream != null)
			{
				try 
				{
					objectOutputStream.close();
				}
				catch (IOException e) 
				{
					
					e.printStackTrace();
				}
			}
		}

	}
}
