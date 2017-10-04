package com.facebooksharing.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import com.facebooksharing.bean.PageInfo;
import com.facebooksharing.bl.FacebookPostController;
import com.facebooksharing.bl.FeedController;


/**
 * Servlet implementation class SinglePagePostService
 */
@WebServlet("/deleteLinkFeed")
public class FeedLinkDeleteService extends HttpServlet {
	private static final long serialVersionUID = 1L;
    int BUFFER_LENGTH = 4096;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedLinkDeleteService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		FeedController controller = new FeedController(); 
		
		String select_value = java.net.URLDecoder.decode(request.getParameter("select_value"));
		String limit = java.net.URLDecoder.decode(request.getParameter("limit"));

		  HttpSession session = request.getSession(false);
	      PrintWriter out = response.getWriter();
	      response.setContentType("text/html");
	      
	      String output="";
	      try
	      {
	    	 
	    	  HashMap<String,PageInfo> pageInfo = (HashMap<String,PageInfo>)session.getAttribute("PageInfo");        
	    	  controller.deleteFeed(select_value,pageInfo,"link",limit);
			
	      }
	      catch(Exception e)
	      {
	    	  
	      }	
	      output="Hurrah!!! Deleted Successfully.";
	      
	      out.print(output);
	      
	}

}
