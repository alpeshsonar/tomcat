package com.facebooksharing.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SinglePagePostDiv
 */
@WebServlet("/getDivInsert")
public class DivInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DivInsert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type = request.getParameter("TYPE");
		if(type==null)
			type = "DASH";
		
		PrintWriter out = response.getWriter();
	      response.setContentType("text/html");

	    String data = "";
		if(type.equalsIgnoreCase("SPSP"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			SINGLE PHOTO SINGLE PAGE<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">   <input id=\"imgfile\" type=\"file\" onchange=\"readURL(this)\"; style=\"display: none;\"/> "+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
		            "            "+
		            "            <div class=\"panel-header\" style=\"text-align: center;\">"+
		            "                <button type=\"button\" onclick=\" document.getElementById('imgfile').click();\" class=\"btn btn-primary fa fa-angle-double-down\">&nbsp;&nbsp;ADD</button>"+
		            "                    "+
		            "            </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		if(type.equalsIgnoreCase("SPMP"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			SINGLE PHOTO SINGLE PAGE<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">   <input id=\"imgfile\" type=\"file\" onchange=\"readURLMultiPages(this)\"; style=\"display: none;\"/> "+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
		            "            "+
		            "            <div class=\"panel-header\" style=\"text-align: center;\">"+
		            "                <button type=\"button\" onclick=\" document.getElementById('imgfile').click();\" class=\"btn btn-primary fa fa-angle-double-down\">&nbsp;&nbsp;ADD</button>"+
		            "                    "+
		            "            </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		if(type.equalsIgnoreCase("MPSP"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			SINGLE PHOTO MULTIPLE PAGE<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">   <input id=\"imgfile\" type=\"file\" onchange=\"readURLMultiple(this)\"; style=\"display: none;\" multiple/> "+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
		            "            "+
		            "            <div class=\"panel-header\" style=\"text-align: center;\">"+
		            "                <button type=\"button\" onclick=\" document.getElementById('imgfile').click();\" class=\"btn btn-primary fa fa-angle-double-down\">&nbsp;&nbsp;ADD</button>"+
		            "                    "+
		            "            </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		
		if(type.equalsIgnoreCase("MPMP"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			SINGLE PHOTO MULTIPLE PAGE<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">   <input id=\"imgfile\" type=\"file\" onchange=\"readURLMultiMultiple(this)\"; style=\"display: none;\" multiple/> "+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
		            "            "+
		            "            <div class=\"panel-header\" style=\"text-align: center;\">"+
		            "                <button type=\"button\" onclick=\" document.getElementById('imgfile').click();\" class=\"btn btn-primary fa fa-angle-double-down\">&nbsp;&nbsp;ADD</button>"+
		            "                    "+
		            "            </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		
		if(type.equalsIgnoreCase("PhotoEventAdd"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			SHARE PHOTO EVENT<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">   <input id=\"imgfile\" type=\"file\" onchange=\"readURLMultiMultipleEvent(this)\"; style=\"display: none;\" multiple/> "+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
		            "            "+
		            "            <div class=\"panel-header\" style=\"text-align: center;\">"+
		            "                <button type=\"button\" onclick=\" document.getElementById('imgfile').click();\" class=\"btn btn-primary fa fa-angle-double-down\">&nbsp;&nbsp;ADD</button>"+
		            "                    "+
		            "            </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		
		if(type.equalsIgnoreCase("MONTHLY"))
		{
			
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DATE, 1);			 
			String dateString = formatter.format(now.getTime());

			 data = 				
						"<div class=\"inner-content\">"+
		                "    <div class=\"panel theme-panel\">"+
		                "        <div class=\"panel-heading\">"+
		                "            <span class=\"panel-title\">"+
						"			MONTHLY SCHEDULER<i class=\"fa fa-bullhorn\"></i>"+
					   	"		    </span>"+
		                "        </div>"+
		                "        "+
		                "<div class=\"panel-body\">   <input id=\"imgfile\" type=\"file\" onchange=\"readMonthly(this)\"; style=\"display: none;\" multiple/> "+
		                "        <div id=\"replaceDiv\">"+
		                " 	     </div>"+
			            "            "+
			            "            <div class=\"panel-header\" style=\"text-align: center;\">" +
			            "DATE :" +
			            "<input id=\"datetime\" type=\"date\" name=\"schedule_id\" value=\""+dateString+"\" >" +
			            "                <button type=\"button\" onclick=\" document.getElementById('imgfile').click();\" class=\"btn btn-primary fa fa-angle-double-down\">&nbsp;&nbsp;ADD</button>"+
			            "                    "+
			            "            </div>"+
		                "    </div>"+
		                "    </div>"+
		                "</div>  ";
		}
		if(type.equalsIgnoreCase("LinkAdd"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			LINK SHARE<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">"+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
		            "            "+
		            "            <div class=\"panel-header\" style=\"text-align: center;\">"+
		            "                <button type=\"button\" onclick=\"linkDivAdd()\" class=\"btn btn-primary fa fa-angle-double-down\">&nbsp;&nbsp;ADD MORE LINKS</button>"+
		            "                    "+
		            "            </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		
		if(type.equalsIgnoreCase("EventAdd"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			EVENTS SHARE <i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">"+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
		            "            "+
		            "            <div class=\"panel-header\" style=\"text-align: center;\">"+
		            "                <button type=\"button\" onclick=\"eventDivAdd()\" class=\"btn btn-primary fa fa-angle-double-down\">&nbsp;&nbsp;ADD MORE EVENT</button>"+
		            "                    "+
		            "            </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		
		if(type.equalsIgnoreCase("deleteLink"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			Link Post Delete<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">"+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		if(type.equalsIgnoreCase("deletePhoto"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			Photo Post Delete<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">"+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		
		if(type.equalsIgnoreCase("deleteStatus"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			Status Post Delete<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">"+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		
		if(type.equalsIgnoreCase("deleteAll"))
		{
			 data = 				
					"<div class=\"inner-content\">"+
	                "    <div class=\"panel theme-panel\">"+
	                "        <div class=\"panel-heading\">"+
	                "            <span class=\"panel-title\">"+
					"			 Clean Page<i class=\"fa fa-bullhorn\"></i>"+
				   	"		    </span>"+
	                "        </div>"+
	                "        "+
	                "<div class=\"panel-body\">"+
	                "        <div id=\"replaceDiv\">"+
	                " 	     </div>"+
	                "    </div>"+
	                "    </div>"+
	                "</div>  ";
		
		}
		
		out.print(data);
		out.flush();
		out.close();
	}

}
