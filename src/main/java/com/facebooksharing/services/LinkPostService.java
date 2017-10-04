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


/**
 * Servlet implementation class SinglePagePostService
 */
@WebServlet("/LinkPost")
public class LinkPostService extends HttpServlet {
	private static final long serialVersionUID = 1L;
    int BUFFER_LENGTH = 4096;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LinkPostService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		FacebookPostController controller = new FacebookPostController(); 
		
		
	String OPENSHIFT_DATA_DIR= System.getenv("OPENSHIFT_DATA_DIR"); 	
	// changeme			 String OPENSHIFT_DATA_DIR= OPENSHIFT_DATA_DIR = "D:/data/";
		 
		String completeImageData = java.net.URLDecoder.decode(request.getParameter("img_src"));		
		String datetime_value = java.net.URLDecoder.decode(request.getParameter("datetime_value"));
		String select_value = java.net.URLDecoder.decode(request.getParameter("select_value"));
        
		String msg_value = java.net.URLDecoder.decode(request.getParameter("msg_value"));
		String linkUrl_value = java.net.URLDecoder.decode(request.getParameter("linkUrl_value"));
        String linkImageUrl_value = java.net.URLDecoder.decode(request.getParameter("linkImageUrl_value"));
        String linkDesc_value = java.net.URLDecoder.decode(request.getParameter("linkDesc_value"));
        String linkTitle_value = java.net.URLDecoder.decode(request.getParameter("linkTitle_value"));
		
		

		SessionIdentifierGenerator generator = new SessionIdentifierGenerator();
		
		String fileName = "";
		
		if(completeImageData.length()>1){
			
			String imageDataBytes = completeImageData.substring(completeImageData.indexOf(",")+1);
			
			String fileType = "jpg";
			
			if(completeImageData.toLowerCase().contains("jpeg"))
				fileType = "jpg";
			else
				if(completeImageData.toLowerCase().contains("png"))
					fileType = "png";
				else
					if(completeImageData.toLowerCase().contains("gif"))
						fileType = "gif";
				
			
			
			
		    fileName = generator.nextSessionId()+"."+fileType;
			BufferedImage image = null;
	        byte[] imageByte;
	        try 
	        {
	        	
	            imageByte = Base64.decodeBase64(imageDataBytes);
	            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	            image = ImageIO.read(bis);
	            bis.close();
	            File outputfile = new File(OPENSHIFT_DATA_DIR+"/"+fileName); 
	            ImageIO.write(image, fileType, outputfile); 
	            
	            
	        } catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		}
	      HttpSession session = request.getSession(false);
	      PrintWriter out = response.getWriter();
	      response.setContentType("text/html");
	      
	      String output="";
	      try
	      {
	    	 
	    	  HashMap<String,PageInfo> pageInfo = (HashMap<String,PageInfo>)session.getAttribute("PageInfo");        
	    	  //System.out.println(" img_src "+fileName+" datetime_value "+datetime_value+" linkUrl_value "+linkUrl_value+" select_value "+select_value); //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	    	  output = controller.linkExecuter(fileName,datetime_value,select_value,pageInfo,msg_value, linkUrl_value, linkImageUrl_value, linkDesc_value, linkTitle_value);
			 /* 
			  if(!fileName.isEmpty()){
		    	  File f = new File(OPENSHIFT_DATA_DIR+"/"+fileName);
		    	  if(f.exists())
		    		  f.delete();
	    	  }*/
	      }
	      catch(Exception e)
	      {
	    	  
	      }
    	//  out.println(" img_src "+fileName+" datetime_value "+datetime_value+" textarea_value "+textarea_value+" select_value "+select_value); //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	      output=output.substring(0,output.length()-1);
	      
	      out.print(output);
	      
	}

}
