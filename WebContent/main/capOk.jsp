<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="getResult" class="api.CaptchaResult" scope="page"/>
<% 
	String key = request.getParameter("key");
	String userInput = request.getParameter("userInput");
	//out.println("key: "+key);
	//out.println("userInput: "+userInput);
	
	String result = getResult.main(key, userInput);
	
	out.println(result);
%>
 	

