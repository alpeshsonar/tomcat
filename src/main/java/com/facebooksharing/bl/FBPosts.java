package com.facebooksharing.bl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
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

public class FBPosts 
{
	
	public static CookieStore cookieStore;


	public static void main(String[] args) 
	{
		
		try
		{
			
			//System.out.println(new Test().timeConversion("201508021940"));
			
			/*new FBPosts().doPost("CAACEdEose0cBAIwbSQgBL2HMdAm0rdcJbQPEFs7TZCPR2eKDBUHklh6ZBrtq6Y6uc7QI4nAZA5QoyyLskAYrYZC4TuyDRioDwZBPYqu33HA5V170p4YmL2KjiUIB6KQJcBTzWYJzmY4GZCMZBaCmLvgizqMJVBK1nTEkD1iN1oc7ZBc76p2nTe1mGFPnMdVsK27jm3495Y0QnAZDZD",
					"A à¤†à¤·à¤¾à¥�à¥€ à¤�à¤•à¤¾à¤¦à¤¶à¥€ à¤•à¥€ à¤¹à¤¾à¤°à¥�à¤¦à¤¿à¤• à¤¶à¥�à¤­à¤•à¤¾à¤®à¤¨à¤¾à¤�....â™¥:) \n à¤¦à¥‡à¤–à¤¿à¤¯à¥‡ à¤†à¤ªà¤•à¤¾ à¤­à¤µà¤¿à¤·à¥�à¤¯... \n Click Here â–ºâ–ºâ–ºâ–º http://goo.gl/A86G3N",
					"http://www.codeproject.com/KB/GDI-plus/ImageProcessing2/img.jpg",
					new Test().timeConversion("201508021940"),
					"0",
					"958619350868621");*/
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void doPost(String access_token,String message,String url_publish,String pageId)  throws Exception 
	{

		BufferedReader reader = null;
		DefaultHttpClient httpclient = null;

		try 
		{
			String url;
			String cookiename = null;
			String cookievalue = null;


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
			
			
/*			nvps.add(new BasicNameValuePair("object_attachment", "958620940868462"));	
			nvps.add(new BasicNameValuePair("picture", null));
			nvps.add(new BasicNameValuePair("link",null));
*/			 
			
			
			nvps.add(new BasicNameValuePair("message",message));
			
			nvps.add(new BasicNameValuePair("url",url_publish));

			
			
			
			
			
			
			
			
			
			nvps.add(new BasicNameValuePair("method", "post")); 
			nvps.add(new BasicNameValuePair("format", "json"));
			nvps.add(new BasicNameValuePair("pretty", "0")); 
			nvps.add(new BasicNameValuePair("suppress_http_code", "1")); 
			
			
			url = "https://graph.facebook.com/v2.4/"+pageId+"/photos";
			
			HttpPost httppost = new HttpPost(url);
			
			httppost.addHeader("origin","https://developers.facebook.com");
			
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, FBPosts.cookieStore);
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
	
	
	public void doLinkPost(String access_token,String message,String url_publish,String scheduled_publish_time,String published,String pageId)  throws Exception 
	{

		BufferedReader reader = null;
		DefaultHttpClient httpclient = null;

		try 
		{
			String url;
			String cookiename = null;
			String cookievalue = null;


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
			
			
			nvps.add(new BasicNameValuePair("picture", "http://www.codeproject.com/KB/GDI-plus/ImageProcessing2/img.jpg"));
			nvps.add(new BasicNameValuePair("link","http://goo.gl/A86G3N"));
			
			if(message.length()>=100)
				message = message = message.substring(0,100);
			
			nvps.add(new BasicNameValuePair("message",message));
			
			nvps.add(new BasicNameValuePair("name",message));

			
			nvps.add(new BasicNameValuePair("method", "post")); 
			nvps.add(new BasicNameValuePair("format", "json"));
			nvps.add(new BasicNameValuePair("pretty", "0")); 
			nvps.add(new BasicNameValuePair("suppress_http_code", "1")); 
			
			
			url = "https://graph.facebook.com/v2.4/"+pageId+"/feed";
			
			HttpPost httppost = new HttpPost(url);
			
			httppost.addHeader("origin","https://developers.facebook.com");
			
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, FBPosts.cookieStore);
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
	
	public void doPost(String access_token,String message,String url_publish,String scheduled_publish_time,String published,String pageId)  throws Exception 
	{

		BufferedReader reader = null;
		DefaultHttpClient httpclient = null;

		try 
		{
			String url;
			String cookiename = null;
			String cookievalue = null;


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
			
			
/*			nvps.add(new BasicNameValuePair("object_attachment", "958620940868462"));	
			nvps.add(new BasicNameValuePair("picture", null));
			nvps.add(new BasicNameValuePair("link",null));
*/			 
			
			
			nvps.add(new BasicNameValuePair("message",message));
			
			nvps.add(new BasicNameValuePair("url",url_publish));
			nvps.add(new BasicNameValuePair("scheduled_publish_time",scheduled_publish_time));
			nvps.add(new BasicNameValuePair("published",published));
			
			
			
			
			
			
			
			
			
			nvps.add(new BasicNameValuePair("method", "post")); 
			nvps.add(new BasicNameValuePair("format", "json"));
			nvps.add(new BasicNameValuePair("pretty", "0")); 
			nvps.add(new BasicNameValuePair("suppress_http_code", "1")); 
			
			
			url = "https://graph.facebook.com/v2.4/"+pageId+"/photos";
			
			HttpPost httppost = new HttpPost(url);
			
			httppost.addHeader("origin","https://developers.facebook.com");
			
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, FBPosts.cookieStore);
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
}