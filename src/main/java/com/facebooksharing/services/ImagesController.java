package com.facebooksharing.services;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
 
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
@WebServlet(name = "image",urlPatterns = {"/image/*"})
@MultipartConfig
public class ImagesController extends HttpServlet
{
 
  private static final long serialVersionUID = 2857847752169838915L;
  int BUFFER_LENGTH = 4096;
 
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
	
	 String OPENSHIFT_DATA_DIR= OPENSHIFT_DATA_DIR = System.getenv("OPENSHIFT_DATA_DIR");
//	changeme   String OPENSHIFT_DATA_DIR= OPENSHIFT_DATA_DIR = "D:/data/";
		System.out.println(" OPENSHIFT_DATA_DIR "+OPENSHIFT_DATA_DIR);
	String filePath = request.getRequestURI();
 
    if(filePath.contains("/"))
    	filePath = filePath.substring(filePath.lastIndexOf("/")+1,filePath.length());
    
    File file = new File(OPENSHIFT_DATA_DIR + filePath);
    InputStream input = new FileInputStream(file);
 
    response.setContentLength((int) file.length());
 
    OutputStream output = response.getOutputStream();
    byte[] bytes = new byte[BUFFER_LENGTH];
    int read = 0;
    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) 
    {
        output.write(bytes, 0, read);
        output.flush();
    }
 
    input.close();
    output.close();
  }
}