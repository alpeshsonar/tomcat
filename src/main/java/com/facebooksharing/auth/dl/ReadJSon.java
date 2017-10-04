package com.facebooksharing.auth.dl;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.facebooksharing.bean.PageInfo;
import com.facebooksharing.bean.UserInfo;


public class ReadJSon 
{
	
	public HashMap<String,String> getEventInfo(String jSonString,HashMap<String,String> eventList)
	{
		
		JSONParser parser = new JSONParser();
		 
		try {
	 
			Object obj = parser.parse(jSonString);
	 
			JSONObject jsonObject = (JSONObject) obj;
	 
			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("data");
			
			for(int i=0;i<msg.size();i++)
			{
				try
				{
					JSONObject msg_obj = (JSONObject) msg.get(i);
					String name =(String) msg_obj.get("name");
					String id =(String) msg_obj.get("id");
					eventList.put(id,name);
				}
				catch(Exception e)
				{
					
				}
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return eventList;
		
	}
	
	public HashMap<String,PageInfo> getPagesInfo(String jSonString,HashMap<String,PageInfo> pageInfoList)
	{
		
		JSONParser parser = new JSONParser();
		 
		try {
	 
			Object obj = parser.parse(jSonString);
	 
			JSONObject jsonObject = (JSONObject) obj;
	 
			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("data");
			
			for(int i=0;i<msg.size();i++)
			{
				try
				{
					JSONObject msg_obj = (JSONObject) msg.get(i);
					String access_token =(String) msg_obj.get("access_token");
					String name =(String) msg_obj.get("name");
					String id =(String) msg_obj.get("id");
					
					System.out.println(" access_token "+access_token+"\n name "+name+"\n id "+id);
					pageInfoList.put(id,new PageInfo(access_token, name, id));
				}
				catch(Exception e)
				{
					
				}
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return pageInfoList;
		
	}
	
	
	
	
	public String getNextPagesToken(String jSonString)
	{
		
		JSONParser parser = new JSONParser();
		String nextUrl = "NA";
		 
		try 
		{
	 
			Object obj = parser.parse(jSonString);
	 
			JSONObject jsonObject = (JSONObject) obj;
	 
			// loop array
			JSONObject msg = (JSONObject) jsonObject.get("paging");
			if(msg!=null)
			{
				nextUrl = (String)msg.get("next");
			}
		
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		if(nextUrl==null)
			nextUrl = "NA";
		
		return nextUrl;
		
	}
	
	
	
	public UserInfo getUser(String userJSon)
	{
		JSONParser parser = new JSONParser();
	
		String id= "";
		String name= "";
		String loc= "";
		String url= "";
		
		try 
		{
	 
			Object obj = parser.parse(userJSon);
			JSONObject jsonObject = (JSONObject) obj;
			try
			{
				JSONObject locObj = (JSONObject) jsonObject.get("location");	
				JSONObject picture = (JSONObject)((JSONObject) jsonObject.get("picture")).get("data");	

				 id= (String)jsonObject.get("id");
				 name= (String)jsonObject.get("name");
				 loc= (String)locObj.get("name");
				 url= (String)picture.get("url");

			}
			catch(NullPointerException e)
			{
				
			}
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return new UserInfo(id,name,loc,url);
	}
	
	 public static void main(String[] args) {
		 
			JSONParser parser = new JSONParser();
		 
			try {
		 
				Object obj = parser.parse("");
		 
				JSONObject jsonObject = (JSONObject) obj;
		 
				System.out.println(jsonObject.get("id"));

				System.out.println(jsonObject.get("name"));

				JSONObject loc = (JSONObject) jsonObject.get("location");	
				
				System.out.println(loc.get("name"));
				
				System.out.println(jsonObject.get("location"));
		 
				
			}  catch (Exception e) {
				e.printStackTrace();
			}
		 
		}
}
