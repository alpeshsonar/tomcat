package com.facebooksharing.auth.dl;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.facebooksharing.bean.PageInfo;
import com.facebooksharing.bean.UserInfo;


public class AccessToken 
{
	ReadJSon readJSon = new ReadJSon();
	
	public UserInfo getUserInfo(String access_token)
	{
		
		access_token = access_token.replace("access_token=", "");
		String graph_url = "https://graph.facebook.com/me?fields=id,name,location,picture&access_token="+access_token;
		String user = urlResult(graph_url);
		
		UserInfo userInfo = readJSon.getUser(user);
		
		//System.out.println(" userInfo "+userInfo.getId()+" "+userInfo.getName()+" "+userInfo.getPicture()+" "+userInfo.getLocation() ); 
		
		return userInfo;
	}
	
	
	
	public HashMap<String,HashMap<String,String>> getEventDetails(String access_token,HashMap<String,PageInfo> pagesInfo)
	{
		access_token = access_token.replace("access_token=", "");
		
		HashMap<String,HashMap<String,String>> eventDetails = new HashMap<String,HashMap<String,String>>();


		
		for (Entry<String, PageInfo> entry : pagesInfo.entrySet())
		{
		
			HashMap<String,String> eventMap = new HashMap<String,String>();
			String graph_url = "https://graph.facebook.com/v2.4/"+entry.getKey()+"/events?access_token="+entry.getValue().getAccess_token();
			//668504783179403/events
			do
			{
				
				System.out.println("--------------------------------------------------");
				String result = urlResult(graph_url);
				eventMap  = readJSon.getEventInfo(result,eventMap);
			 	graph_url = readJSon.getNextPagesToken(result);
			 	System.out.println(" NEXT : "+graph_url);	
			}
			while(!graph_url.equalsIgnoreCase("NA"));
			eventDetails.put(entry.getKey(), eventMap);
		} 
		System.out.println("eventDetails "+eventDetails);
		return eventDetails;
	}
	
	
	
	public HashMap<String,PageInfo> getAccountInfo(String access_token)
	{
		access_token = access_token.replace("access_token=", "");
		String graph_url = "https://graph.facebook.com/me/accounts?access_token="+access_token;
		
		
		HashMap<String,PageInfo> pageInfoList = new HashMap<String,PageInfo>();
		do
		{
			
			System.out.println("--------------------------------------------------");
			String pageInfo = urlResult(graph_url);
			pageInfoList  = readJSon.getPagesInfo(pageInfo,pageInfoList);
		 	graph_url = readJSon.getNextPagesToken(pageInfo);
		 	
		 	System.out.println(" NEXT : "+graph_url);
		
		}
		while(!graph_url.equalsIgnoreCase("NA"));
		 
		return pageInfoList;
	}
	
   public String getAccessToken(String app_id,String app_secret,String redirect_url,String code)
   {

		String token_url = "https://graph.facebook.com/oauth/access_token?" +"client_id="+ app_id+"&redirect_uri="+redirect_url+"&client_secret="+ app_secret+"&code="+ code;
		
		String params = urlResult(token_url);
		
		if(params.contains("&"))
			params = params.substring(0, params.indexOf("&")).replace("access_token=", "");
	
		
		return params;
		
	}
	
	public String urlResult(String urlString)
	{
		String result = "";
		
		
		System.out.println(" URL : "+urlString);
		BufferedReader in = null;
		try
		{
			URL url = new URL(urlString);
	        URLConnection yc = url.openConnection();
	        in = new BufferedReader(new InputStreamReader(yc.getInputStream(),"UTF-8"));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) 
	        {
	            System.out.println(inputLine);
	            result+=inputLine;
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(in!=null)
					in.close();
				in = null;
			}
			catch(Exception e)
			{
				
			}

		}
			return result;
	}
	
}
