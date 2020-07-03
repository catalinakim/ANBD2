<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
trimDirectiveWhitespaces="true" %>
<jsp:useBean id="ke" class="api.CaptchaKey" scope="page"/>
<jsp:useBean id="im" class="api.CaptchaImage" scope="page"/>
<jsp:useBean id="re" class="api.CaptchaResult" scope="page"/>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="org.json.simple.*" %>
<% 
String imagePath = request.getServletContext().getRealPath("img\\captchar") + "\\";
//out.print(imagePath);
im.imageMain(imagePath); //캡차 이미지 생성
String key = ke.key;
String imgFileName = im.imgFileName;
String pathFileName = imagePath + imgFileName;
//out.print(imgFileName+"<br>");
//out.print(key);
System.out.println(imgFileName); //console에 출력됨

//jsp에서 json형태로 출력
response.setContentType("application/json");
	JSONObject obj = new JSONObject();
	obj.put("imgFileName", imgFileName);
	obj.put("key", key);
response.getWriter().write(obj.toString());
%>