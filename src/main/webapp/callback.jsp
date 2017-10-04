<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.facebooksharing.auth.dl.*" %>
<%@ page import="com.facebooksharing.bean.*" %>
<%@ page import="com.facebooksharing.services.Messages" %>
<%@ page import="java.util.HashMap" %>


<%

try
{
String code = request.getParameter("code");
AccessToken accessToken = new AccessToken();
String accessTokenString = accessToken.getAccessToken("888150491270142","603a28214c3ff0fce9d1dd0b26fc89ea","http://www.sonarsharing.com/callback.jsp",code);
//changeme String accessTokenString = accessToken.getAccessToken("888150491270142","603a28214c3ff0fce9d1dd0b26fc89ea","http://localhost:9090/FacebookShare/callback.jsp",code);

session.setAttribute("AccessToken", accessTokenString);

UserInfo user = accessToken.getUserInfo(accessTokenString);
session.setAttribute("User", user);
session.setAttribute("UserId", user.getId());
session.setAttribute("PageInfo", accessToken.getAccountInfo(accessTokenString));
session.setAttribute("EventDetails", accessToken.getEventDetails(accessTokenString,((HashMap<String,PageInfo>)session.getAttribute("PageInfo"))));

response.sendRedirect("dashboard.html");
}
catch(Exception e)
{	   
	response.sendRedirect("404.jsp");
}
%>
