package com.facebooksharing.bl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.facebooksharing.bean.PageInfo;

public class FeedController {
	public static void main(String[] args) {
	
		String access_token = "CAACEdEose0cBAAlkNv9zfExXAZAlF7dztl29EuIrOCEsMwcTCcFKXDKteWd5WaRhmQi2OJuWsxLscGuxjwn4UmTD4kd6rr5FtZCpZBX1OpgWpq2EZAf5AKkJ9qaXZAQAFodGBRcwmhZCaGOqYCY9q2HW9Ltql2nfQfow7AuZA7uFZBJZB4tVnrPqZAfDOSWf3OMMIZD";
		FeedController f = new FeedController();
		String graph_url = "https://graph.facebook.com/v2.5/me/feed?fields=id&access_token="+access_token+"&format=json&method=get&pretty=0&suppress_http_code=1";
		ArrayList<String> linkId = new ArrayList<String>();
		do
		{
			
			System.out.println("--------------------------------------------------");
		    graph_url = f.urlResult(graph_url,linkId,"link");			
		 	System.out.println(" NEXT : "+graph_url);
		
		}
		while(!graph_url.equalsIgnoreCase("NA"));
		
		try {
				DefaultHttpClient httpclient = f.getConnection();
				
				for(String id : linkId){		
							f.deleteFeed(httpclient,access_token,id);				
				}
				
				if(httpclient!=null){
					
					httpclient.getConnectionManager().shutdown();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFeed(String select_value,HashMap<String,PageInfo> pageInfo,String type,String limit)
	{

		String access_token="";		
		String pages[] = select_value.split(",");
		for(int i=0;i<pages.length;i++)
		{
			if(pageInfo.containsKey(pages[i]))
			{
				try
				{
					int limitInt = -1;
					PageInfo pageInfoBean = pageInfo.get(pages[i]);					
					access_token = pageInfoBean.getAccess_token();
					String limitString = "";
					if(!limit.isEmpty()){
						limitString = "limit="+limit+"&";
						limitInt= Integer.parseInt(limit);

					}
					
					
					String graph_url = "https://graph.facebook.com/v2.5/me/feed?fields=id,type&"+limitString+"access_token="+access_token+"&format=json&method=get&pretty=0&suppress_http_code=1";
					ArrayList<String> linkId = new ArrayList<String>();
					do
					{
						
						System.out.println("--------------------------------------------------");
					    graph_url = urlResult(graph_url,linkId,type);			
					 	System.out.println(" NEXT : "+graph_url);
					 	if(limitInt!=-1 && linkId.size()>limitInt){
					 		break;
					 	}
					
					}
					while(!graph_url.equalsIgnoreCase("NA"));
					
					try {
							DefaultHttpClient httpclient = getConnection();
							limitInt=linkId.size();
							if(!limit.isEmpty()){
							
							 limitInt= Integer.parseInt(limit);
							}
							if(linkId.size()<limitInt)
							{
								limitInt = linkId.size();
							}
							for(i=0;i<limitInt;i++){		
										deleteFeed(httpclient,access_token,linkId.get(i));				
							}						
							if(httpclient!=null){
								
								httpclient.getConnectionManager().shutdown();
							}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				catch(Exception e)
				{
					
				}
			}
			
		}				
	}
	
	
	
	public void deleteAllFeed(String select_value,HashMap<String,PageInfo> pageInfo,String limit)
	{

		String access_token="";		
		String pages[] = select_value.split(",");
		for(int i=0;i<pages.length;i++)
		{
			if(pageInfo.containsKey(pages[i]))
			{
				try
				{
					int limitInt = -1;
					PageInfo pageInfoBean = pageInfo.get(pages[i]);					
					access_token = pageInfoBean.getAccess_token();
					String limitString = "";
					if(!limit.isEmpty()){
						limitString = "limit="+limit+"&";
						limitInt= Integer.parseInt(limit);

					}
					
					
					String graph_url = "https://graph.facebook.com/v2.5/me/feed?fields=id,type&"+limitString+"access_token="+access_token+"&format=json&method=get&pretty=0&suppress_http_code=1";
					ArrayList<String> linkId = new ArrayList<String>();
					do
					{
						
						System.out.println("--------------------------------------------------");
					    graph_url = urlResultAll(graph_url,linkId);			
					 	System.out.println(" NEXT : "+graph_url);
					 	if(limitInt!=-1 && linkId.size()>limitInt){
					 		break;
					 	}
					
					}
					while(!graph_url.equalsIgnoreCase("NA"));
					
					try {
							DefaultHttpClient httpclient = getConnection();
							limitInt=linkId.size();
							if(!limit.isEmpty()){
							
							 limitInt= Integer.parseInt(limit);
							}
							if(linkId.size()<limitInt)
							{
								limitInt = linkId.size();
							}
							for(i=0;i<limitInt;i++){		
										deleteFeed(httpclient,access_token,linkId.get(i));				
							}						
							if(httpclient!=null){
								
								httpclient.getConnectionManager().shutdown();
							}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				catch(Exception e)
				{
					
				}
			}
			
		}				
	}
	
	
	public DefaultHttpClient getConnection(){
		
		DefaultHttpClient httpclient = null;
		String url;

		ClientConnectionManager connManager = new PoolingClientConnectionManager();
		httpclient = new DefaultHttpClient(connManager);
		httpclient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
		httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,CookiePolicy.BROWSER_COMPATIBILITY);
		httpclient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);

		
		url = "https://graph.facebook.com";
		httpclient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		
		(httpclient).addRequestInterceptor(new HttpRequestInterceptor()
		{
			@Override
			public void process(final HttpRequest request,
					final HttpContext context) throws HttpException,
					IOException {

				if (!request.containsHeader("Accept")) {
					request.addHeader("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				}
				if (!request.containsHeader("Accept-Encoding")) {
					request.addHeader("Accept-Encoding", "gzip, deflate");
				}
				if (!request.containsHeader("Accept-Language")) {
					request.addHeader("Accept-Language",
							"en-GB,en;q=0.8,en-US;q=0.6,ga;q=0.4,hi;q=0.2");
				}
				if (!request.containsHeader("Cache-Control")) {
					request.addHeader("Cache-Control", "max-age=0");
				}
				if (!request.containsHeader("Connection")) {
					request.addHeader("Connection", "keep-alive");
				}
				if (!request.containsHeader("Content-Type")) {
					request.addHeader("Content-Type", "application/x-www-form-urlencoded");
				}
			}

		});
		
		(httpclient).addResponseInterceptor(new HttpResponseInterceptor()
		{

			@Override
			public void process(final HttpResponse response,
					final HttpContext context) throws HttpException,
					IOException {
				HttpEntity entity = response.getEntity();
				Header ceheader = entity.getContentEncoding();
				if (ceheader != null) {
					HeaderElement[] codecs = ceheader.getElements();
					for (int i = 0; i < codecs.length; i++) {
						if (codecs[i].getName().equalsIgnoreCase("gzip")) {
							response.setEntity(new GzipDecompressingEntity(
									response.getEntity()));
							return;
						}
					}
				}
			}

		});

		return httpclient;
		
	}
	
	public void deleteFeed(DefaultHttpClient httpclient, String access_token,String feedId)  throws Exception 
	{

		BufferedReader reader = null;

		try 
		{
			

			

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				
			nvps.add(new BasicNameValuePair("access_token", access_token));				
			nvps.add(new BasicNameValuePair("method", "delete")); 
			nvps.add(new BasicNameValuePair("format", "json"));
			nvps.add(new BasicNameValuePair("pretty", "0")); 
			nvps.add(new BasicNameValuePair("suppress_http_code", "1")); 
			
			
			String url = "https://graph.facebook.com/v2.4/"+feedId;
			
			HttpPost httppost = new HttpPost(url);
			
			httppost.addHeader("origin","https://developers.facebook.com");
			
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpContext localContext = new BasicHttpContext();
			HttpResponse response = httpclient.execute(httppost, localContext);			
			
			HttpEntity entity = response.getEntity();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					entity.getContent()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) 
			{
				System.out.println(inputLine);	
			}

			
		} catch (Exception e) 
		{
			 e.printStackTrace();
		} 
		finally 
		{
			if (reader != null) 
			{
				reader.close();
			}
		}
	}
	
	
	
	public String urlResult(String urlString,ArrayList<String> linkId,String type)
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
			return parseJSON(result,linkId,type);
	}
	
	public String parseJSON(String jSonString,ArrayList<String> linkId,String type){
		
		
String next = "NA";
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
					String id =(String) msg_obj.get("id");	
					String typeId =(String) msg_obj.get("type");	
					
					System.out.println(" id "+id+" type "+type);
					if(type.equalsIgnoreCase(typeId)){
						linkId.add(id);
					}
				}
				catch(Exception e)
				{
					
				}
			}
			if(jsonObject.containsKey("paging")){
				
				JSONObject paging = (JSONObject) jsonObject.get("paging");
				if(paging.containsKey("next"))
				  next = (String) paging.get("next");
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return next;
		
	}
	
	
	
	
	

	

	public String urlResultAll(String urlString,ArrayList<String> linkId)
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
			return parseJSONAll(result,linkId);
	}
	
	public String parseJSONAll(String jSonString,ArrayList<String> linkId){
		
		
String next = "NA";
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
					String id =(String) msg_obj.get("id");						
						linkId.add(id);
				}
				catch(Exception e)
				{
					
				}
			}
			if(jsonObject.containsKey("paging")){
				
				JSONObject paging = (JSONObject) jsonObject.get("paging");
				if(paging.containsKey("next"))
				  next = (String) paging.get("next");
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return next;
		
	}
	
	
	
	
	
	
}
