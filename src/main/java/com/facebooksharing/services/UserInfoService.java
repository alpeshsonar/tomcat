package com.facebooksharing.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.facebooksharing.bean.UserInfo;

/**
 * Servlet implementation class UserInfoService
 */
@WebServlet("/getUserInfo")
public class UserInfoService extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoService() {
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
	      PrintWriter out = response.getWriter();
	      response.setContentType("text/html");

	    try
	    {
	      UserInfo user = (UserInfo)session.getAttribute("User");
	      
	      out.print( "<div class='media'> ");
	      out.print( "<a class='pull-left' href='http://www.facebook.com/me'>");
	      out.print( "<img class='img-circle' src='");
	      out.print( user.getPicture());
	      out.print("'></a> ");
	      out.print("<div class='media-body'>");
	      out.print( "<h5 class='media-heading'>");
	      out.print("Welcome <span>");
	      out.print( user.getName());
	      out.print( "</span></h5>");
	      out.print( "<small>");
	      out.print( user.getLocation());
	      out.print( "</small>");
	      out.print( "</div>");
	      out.print( "</div>");    
	    }
	    catch(Exception e)
	    {
	    	out.print( "error");
	    }
	     
	      out.flush();
	      out.close();
	         
	}


}
