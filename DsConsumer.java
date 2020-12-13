package com.datastore.main;
import org.json.simple.JSONObject;


public class DsConsumer 
{
	public static void main(String[] args) 
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("firstName", "xyz");
		jsonObject.put("lastName", "xy");
		jsonObject.put("address", "abc");
		
		
		
		System.out.println("CREATE");
		// DataStore ds = new DataStore();
		DataStore ds = new DataStore("C:\\Users\\PS\\Documents\\DataStore");
		System.out.println(ds.create("1", jsonObject, 10));
		System.out.println(ds.create("2", jsonObject, 10));
		System.out.println(ds.create("3", jsonObject, 10));
		
		
		try 
		{
			Thread.sleep(10000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		
		
		System.out.println("AFTER WAIT");
		System.out.println(ds.create("1", jsonObject, 10));
		
		
		
		
		
		System.out.println("READ");
		System.out.println(ds.read("1"));
		System.out.println(ds.read("2"));
		
		
		try 
		{
		Thread.sleep(10000);
		} 
		catch (InterruptedException e) 
		{
		 e.printStackTrace();
		}
		
		
		
		System.out.println("AFTER WAIT");
                System.out.println(ds.read("2"));

		
		
		
		System.out.println("DELETE");
		System.out.println(ds.delete("2"));
	   
		
		
	}
}
