<%@ page import="java.io.*,java.util.*" %>
<%@ page import="com.facebooksharing.services.Messages" %>

<%

  String user_id = (String) session.getAttribute("user_id");

  if(user_id!=null)
  {
	   response.sendRedirect("dashboard.html");
  }
  else
  {
	  
	      String app_id = "888150491270142";//change this
	    String redirect_url = "http://www.sonarsharing.com/callback.jsp"; //change this
	    out.print(" redirect_url "+redirect_url);
	    String dialog_url = "https://www.facebook.com/dialog/oauth?client_id=" 
	           + app_id + "&redirect_uri=" + redirect_url+"&scope=user_about_me,user_location,manage_pages,publish_pages";
	  /*    
	  
	     //changeme
	    String app_id = "888150491270142";//change this
	    String redirect_url = "http://localhost:9090/FacebookShare/callback.jsp"; //change this
	    out.print(" redirect_url "+redirect_url);
	    String dialog_url = "https://www.facebook.com/dialog/oauth?client_id=" 
	           + app_id + "&redirect_uri=" + redirect_url+"&scope=user_about_me,user_location,manage_pages,publish_pages";
	     
	   */ 
	    
	   response.sendRedirect(dialog_url);
  }
%>
