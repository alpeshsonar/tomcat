package com.facebooksharing.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.facebooksharing.bean.PageInfo;

/**
 * Servlet implementation class UserInfoService
 */
@WebServlet("/getEventInfo")
public class EventInfoService extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventInfoService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		//System.out.println(" HERE ");
		 // Create a session object if it is already not  created.
	      HttpSession session = request.getSession(false);
	      PrintWriter out =  new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
	      response.setContentType("text/html; charset=UTF-8");
	      request.setCharacterEncoding("UTF-8");

	      try
	      {
	    	 
	      HashMap<String,PageInfo> pageInfo = (HashMap<String,PageInfo>)session.getAttribute("PageInfo");
	      HashMap<String,HashMap<String,String>> eventInfo = (HashMap<String,HashMap<String,String>>)session.getAttribute("EventDetails");

	      String output= "";
	      for (Map.Entry<String, PageInfo> entry : pageInfo.entrySet()) 
	      {
	    	  PageInfo value = entry.getValue();
	    	  if(eventInfo.get(entry.getKey())!=null)
	    	  {
		  	      for (Entry<String, String> eventEntry : eventInfo.get(entry.getKey()).entrySet()) 
		  	      { 
		    	    output+=value.getId()+":"+eventEntry.getKey()+"," +value.getName()+" - "+eventEntry.getValue()+"|";
		  	      }
	    	  }
	     }
	      output=output.substring(0,output.length()-1);
	     System.out.println(" out "+output); 
	      out.print(output);
	      
	     	       
	    }
	    catch(Exception e)
	    {
	    	out.print( "error");
	    }
	     
	      out.flush();
	      out.close();
	         
	}


}
