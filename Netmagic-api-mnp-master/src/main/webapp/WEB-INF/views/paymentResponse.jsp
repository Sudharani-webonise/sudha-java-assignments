<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <%@ page import="java.io.*,java.util.*" %>
 
<!DOCTYPE html>
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="no-store, no-cache, must-revalidate">
<META HTTP-EQUIV="PRAGMA" CONTENT="no-store, no-cache, must-revalidate">
 <body onLoad="document.orderres.submit()">
	<p>Please wait ...</p>

<form name="orderres" action="${responseurl}" method="get">
<%
   Enumeration paramNames = request.getParameterNames();

   while(paramNames.hasMoreElements()) {
      String paramName = (String)paramNames.nextElement();
      String paramValue = request.getParameter(paramName);
      %>
      <input type="hidden" name="<%=paramName%>" value="<%=paramValue%>"/> <br>
      <% 
    
   }
%>
<form />
</body>
</html>
    