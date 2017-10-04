package com.facebooksharing.bl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import com.facebooksharing.bean.PageInfo;
import com.facebooksharing.services.Messages;

public class FacebookPostController 
{
	
	DateFormat dfm = new SimpleDateFormat("yyyyMMddHHmm");  

    long unixtime=0l;
    public String timeConversion(String time)
    {
        dfm.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));//Specify your timezone 
	    try
	    {
	        unixtime = dfm.parse(time).getTime();  
	        unixtime=unixtime/1000;
	    } 
	    catch (Exception e) 
	    {
	       // e.printStackTrace();
	    }
	    return unixtime+"";
    }
	
	public String executer(String fileName,String datetime_value,String textarea_value,String select_value,HashMap<String,PageInfo> pageInfo)
	{
		String finalResult = "";
		
		
		String access_token="";
		String message="";
		String url_publish="";
		String scheduled_publish_time="";
		String published="";
		String pageId=""; 
		
		String pages[] = select_value.split(",");
		for(int i=0;i<pages.length;i++)
		{
			if(pageInfo.containsKey(pages[i]))
			{
				try
				{
					PageInfo pageInfoBean = pageInfo.get(pages[i]);
					
					access_token = pageInfoBean.getAccess_token();
					pageId = pageInfoBean.getId();
					scheduled_publish_time = timeConversion(datetime_value.replace("-", "").replace("T","").replace(":", "").replace(" ", ""));
					if(scheduled_publish_time.equalsIgnoreCase("0"))
						scheduled_publish_time = "";

			url_publish = "http://www.sonarsharing.com/image/"+fileName;
//			changeme							   url_publish = "http://www.jeevansar.com/wp-content/uploads/2016/02/20022016.png";
										
					finalResult+=pageInfoBean.getName()+":"+doPostPhotos(access_token, textarea_value, url_publish, scheduled_publish_time, published, pageId)+"\n";
				}
				catch(Exception e)
				{
					
				}
			}
			
		}
		
		return finalResult;
		
	}
	
	
	public String linkExecuter(String fileName,String datetime_value,String select_value,HashMap<String,PageInfo> pageInfo,
			String msg_value,
			String linkUrl_value,
			String linkImageUrl_value,
			String linkDesc_value,
			String linkTitle_value)
	{
		String finalResult = "";
		
		
		String access_token="";
		String url_publish="";
		String scheduled_publish_time="";
		String published="";
		String pageId=""; 
		
		String pages[] = select_value.split(",");
		for(int i=0;i<pages.length;i++)
		{
			if(pageInfo.containsKey(pages[i]))
			{
				try
				{
					PageInfo pageInfoBean = pageInfo.get(pages[i]);
					
					access_token = pageInfoBean.getAccess_token();
					pageId = pageInfoBean.getId();
					scheduled_publish_time = timeConversion(datetime_value.replace("-", "").replace("T","").replace(":", "").replace(" ", ""));
					if(scheduled_publish_time.equalsIgnoreCase("0"))
						scheduled_publish_time = "";

			url_publish = "http://www.sonarsharing.com/image/"+fileName;
//			changeme							url_publish = "http://www.jeevansar.com/wp-content/uploads/2016/02/20022016.png";
		
					
					if(fileName.isEmpty()){
						url_publish = linkImageUrl_value;
					}
					
										
					
					   
					finalResult+=pageInfoBean.getName()+" : "+ doLinkPost(access_token, url_publish, scheduled_publish_time, published, pageId,linkUrl_value, msg_value,linkTitle_value,linkDesc_value)+"\n";
				}
				catch(Exception e)
				{
					
				}
			}
			
		}
		
		return finalResult;
		
	}
	
	public String doLinkPost(String access_token,String picture,String scheduled_publish_time,String published,String pageId,
			String link,
			String message, 
			String linkTitle,
			String linkDesc)  throws Exception 
	{
		String finalResponse = "";

		BufferedReader reader = null;
		DefaultHttpClient httpclient = null;

		try 
		{
			String url;
			String cookiename = null;
			String cookievalue = null;
			CookieStore cookieStore = null;

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

			
			

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
							
			nvps.add(new BasicNameValuePair("access_token", access_token));		
			if(!picture.isEmpty())
				nvps.add(new BasicNameValuePair("picture",picture));
			 
			nvps.add(new BasicNameValuePair("link",link));

			// if(message.length()>=100)
			//	message  = message.substring(0,100);
			
			if(!message.isEmpty())
				nvps.add(new BasicNameValuePair("message",message));
			
			if(!scheduled_publish_time.isEmpty())
			{
				nvps.add(new BasicNameValuePair("scheduled_publish_time",scheduled_publish_time));
				nvps.add(new BasicNameValuePair("published",published));
			}
		
			if(!linkTitle.isEmpty())
			nvps.add(new BasicNameValuePair("name",linkTitle));

			
			if(!linkDesc.isEmpty())
			nvps.add(new BasicNameValuePair("description",linkDesc));

			nvps.add(new BasicNameValuePair("method", "post")); 
			nvps.add(new BasicNameValuePair("format", "json"));
			nvps.add(new BasicNameValuePair("pretty", "0")); 
			nvps.add(new BasicNameValuePair("suppress_http_code", "1")); 
			
			
			url = "https://graph.facebook.com/v2.4/"+pageId+"/feed";
			
			HttpPost httppost = new HttpPost(url);
			
			httppost.addHeader("origin","https://developers.facebook.com");
			
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			HttpResponse response = httpclient.execute(httppost, localContext);			
			HttpEntity entity = response.getEntity();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					entity.getContent()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) 
			{
				//System.out.println(inputLine);	
				finalResponse+=inputLine;
			}

			
		} catch (Exception e) 
		{
			finalResponse+=e.getMessage();
			 e.printStackTrace();
		} 
		finally 
		{
			if (reader != null) 
			{
				reader.close();
			}
		}
		return finalResponse;
	}
	
	
	
	public String doPostPhotos(String access_token,String message,String url_publish,String scheduled_publish_time,String published,String pageId)  throws Exception 
	{
		
		CookieStore cookieStore = null;
		BufferedReader reader = null;
		DefaultHttpClient httpclient = null;
		String finalResponse = "";
		try 
		{
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

			
			

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
			nvps.add(new BasicNameValuePair("access_token", access_token));		
			if(!url_publish.isEmpty())
				nvps.add(new BasicNameValuePair("url",url_publish));
	
			if(!message.isEmpty())
				nvps.add(new BasicNameValuePair("message",message));
			
			if(!scheduled_publish_time.isEmpty())
			{
				nvps.add(new BasicNameValuePair("scheduled_publish_time",scheduled_publish_time));
				nvps.add(new BasicNameValuePair("published",published));
			}
		
			nvps.add(new BasicNameValuePair("method", "post")); 
			nvps.add(new BasicNameValuePair("format", "json"));
			nvps.add(new BasicNameValuePair("pretty", "0")); 
			nvps.add(new BasicNameValuePair("suppress_http_code", "1")); 
			
			
			url = "https://graph.facebook.com/v2.4/"+pageId+"/photos";
			
			HttpPost httppost = new HttpPost(url);
			
			httppost.addHeader("origin","https://developers.facebook.com");
			
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			HttpResponse response = httpclient.execute(httppost, localContext);			
			
			HttpEntity entity = response.getEntity();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					entity.getContent()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) 
			{
				//System.out.println(inputLine);	
				finalResponse+=inputLine;
			}

			
		} catch (Exception e) 
		{
			finalResponse+=e.getMessage();
			 e.printStackTrace();
		} 
		finally 
		{
			if (reader != null) 
			{
				reader.close();
			}
			if(httpclient!=null)
			{
				httpclient.getConnectionManager().shutdown();
			}
		}
		
		return finalResponse;
	}
	
	//############################################# Event Post : start
	public String eventExecuter(String fileName,String select_value,HashMap<String,PageInfo> pageInfo,
			String msg_value,
			String linkUrl_value,
			String linkImageUrl_value,
			String linkDesc_value,
			String linkTitle_value)
	{
		String finalResult = "";
		
		
		String access_token="";
		String url_publish="";
		String scheduled_publish_time="";
		String published="";
		String pageId=""; 
		
		String pages[] = select_value.split(",");
		for(int i=0;i<pages.length;i++)
		{
			String pageIdURL = pages[i].split(":")[0];
			String eventIdURL = pages[i].split(":")[1];
			if(pageInfo.containsKey(pageIdURL))
			{
				try
				{
					
					
					PageInfo pageInfoBean = pageInfo.get(pageIdURL);
					
					access_token = pageInfoBean.getAccess_token();
					pageId = pageInfoBean.getId();
				    scheduled_publish_time = "";

			url_publish = "http://www.sonarsharing.com/image/"+fileName;
			//changeme   url_publish = "http://www.jeevansar.com/wp-content/uploads/2016/02/20022016.png";
		
					
					if(fileName.isEmpty()){
						url_publish = linkImageUrl_value;
					}
					
										
					
					   
					finalResult+=pageInfoBean.getName()+" : "+ doEventPost(access_token, url_publish, scheduled_publish_time, published, pageId,linkUrl_value, msg_value,linkTitle_value,linkDesc_value,eventIdURL)+"\n";
				}
				catch(Exception e)
				{
					
				}
			}
			
		}
		
		return finalResult;
		
	}
	
	public String doEventPost(String access_token,String picture,String scheduled_publish_time,String published,String pageId,
			String link,
			String message, 
			String linkTitle,
			String linkDesc,
			String eventIdURL)  throws Exception 
	{
		String finalResponse = "";

		BufferedReader reader = null;
		DefaultHttpClient httpclient = null;

		try 
		{
			String url;
			String cookiename = null;
			String cookievalue = null;
			CookieStore cookieStore = null;

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

			
			

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
							
			nvps.add(new BasicNameValuePair("access_token", access_token));		
			if(!picture.isEmpty())
				nvps.add(new BasicNameValuePair("picture",picture));
			 
			nvps.add(new BasicNameValuePair("link",link));

			// if(message.length()>=100)
			//	message  = message.substring(0,100);
			
			if(!message.isEmpty())
				nvps.add(new BasicNameValuePair("message",message));
			
			if(!scheduled_publish_time.isEmpty())
			{
				nvps.add(new BasicNameValuePair("scheduled_publish_time",scheduled_publish_time));
				nvps.add(new BasicNameValuePair("published",published));
			}
		
			if(!linkTitle.isEmpty())
			nvps.add(new BasicNameValuePair("name",linkTitle));

			
			if(!linkDesc.isEmpty())
			nvps.add(new BasicNameValuePair("description",linkDesc));

			nvps.add(new BasicNameValuePair("method", "post")); 
			nvps.add(new BasicNameValuePair("format", "json"));
			nvps.add(new BasicNameValuePair("pretty", "0")); 
			nvps.add(new BasicNameValuePair("suppress_http_code", "1")); 
			
			
			url = "https://graph.facebook.com/v2.4/"+eventIdURL+"/feed";
			
			HttpPost httppost = new HttpPost(url);
			
			httppost.addHeader("origin","https://developers.facebook.com");
			
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			HttpResponse response = httpclient.execute(httppost, localContext);			
			HttpEntity entity = response.getEntity();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					entity.getContent()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) 
			{
				//System.out.println(inputLine);	
				finalResponse+=inputLine;
			}

			
		} catch (Exception e) 
		{
			finalResponse+=e.getMessage();
			 e.printStackTrace();
		} 
		finally 
		{
			if (reader != null) 
			{
				reader.close();
			}
		}
		return finalResponse;
	}
	

	//############################################# Event Post : end
	
	
	//################### event photo
	public String executerEvent(String fileName,String textarea_value,String select_value,HashMap<String,PageInfo> pageInfo)
	{
		String finalResult = "";
		
		
		String access_token="";
		String message="";
		String url_publish="";
		String scheduled_publish_time="";
		String published="";
		String pageId=""; 
		
		String pages[] = select_value.split(",");
		for(int i=0;i<pages.length;i++)
		{
			String pageIdURL = pages[i].split(":")[0];
			String eventIdURL = pages[i].split(":")[1];
			if(pageInfo.containsKey(pageIdURL))
			{
				try
				{
					
					
					PageInfo pageInfoBean = pageInfo.get(pageIdURL);
					
					access_token = pageInfoBean.getAccess_token();
					pageId = pageInfoBean.getId();
						scheduled_publish_time = "";

			url_publish = "http://www.sonarsharing.com/image/"+fileName;
//			changeme		url_publish = "http://www.jeevansar.com/wp-content/uploads/2016/02/20022016.png";
										
					finalResult+=pageInfoBean.getName()+":"+doEventPostPhotos(access_token, textarea_value, url_publish, scheduled_publish_time, published, pageId,eventIdURL)+"\n";
				}
				catch(Exception e)
				{
					
				}
			}
			
		}
		
		return finalResult;
		
	}
	
	public String doEventPostPhotos(String access_token,String message,String url_publish,String scheduled_publish_time,String published,String pageId,String eventId)  throws Exception 
	{
		
		CookieStore cookieStore = null;
		BufferedReader reader = null;
		DefaultHttpClient httpclient = null;
		String finalResponse = "";
		try 
		{
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

			
			

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
			nvps.add(new BasicNameValuePair("access_token", access_token));		
			if(!url_publish.isEmpty())
				nvps.add(new BasicNameValuePair("url",url_publish));
	
			if(!message.isEmpty())
				nvps.add(new BasicNameValuePair("message",message));
			
			if(!scheduled_publish_time.isEmpty())
			{
				nvps.add(new BasicNameValuePair("scheduled_publish_time",scheduled_publish_time));
				nvps.add(new BasicNameValuePair("published",published));
			}
		
			nvps.add(new BasicNameValuePair("method", "post")); 
			nvps.add(new BasicNameValuePair("format", "json"));
			nvps.add(new BasicNameValuePair("pretty", "0")); 
			nvps.add(new BasicNameValuePair("suppress_http_code", "1")); 
			
			
			url = "https://graph.facebook.com/v2.4/"+eventId+"/photos";
			
			HttpPost httppost = new HttpPost(url);
			
			httppost.addHeader("origin","https://developers.facebook.com");
			
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			HttpResponse response = httpclient.execute(httppost, localContext);			
			
			HttpEntity entity = response.getEntity();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					entity.getContent()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) 
			{
				//System.out.println(inputLine);	
				finalResponse+=inputLine;
			}

			
		} catch (Exception e) 
		{
			finalResponse+=e.getMessage();
			 e.printStackTrace();
		} 
		finally 
		{
			if (reader != null) 
			{
				reader.close();
			}
			if(httpclient!=null)
			{
				httpclient.getConnectionManager().shutdown();
			}
		}
		
		return finalResponse;
	}
	
}