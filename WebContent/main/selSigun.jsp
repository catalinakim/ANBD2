<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:useBean id="vo" class="anbd.AnbdVO" scope="page"/>
<jsp:useBean id="dao" class="anbd.AnbdDAO" scope="page"/>
<%
	String sido = request.getParameter("sido");
%>
 <%
	dao.selSigun(sido);
 	pageContext.setAttribute("selSigun", dao.getBoardList());
 %>
<c:forEach items="${selSigun}" var="selSigun">
	<option value="${selSigun.jusoNo}:${selSigun.sigun}">${selSigun.sigun}</option>
</c:forEach>
