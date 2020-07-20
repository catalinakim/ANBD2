<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<jsp:useBean id="vo" class="anbd.AnbdVO" scope="page"/>
<jsp:useBean id="db" class="anbd.DbInfo" scope="page"/>
<jsp:useBean id="dao" class="anbd.AnbdDAO" scope="page"/>
<%
//==========확인용, 원래는 내가 쓴 글로 넘어감==========
request.setCharacterEncoding("utf-8");
String id = (String)session.getAttribute("loginId");
dao.selLoginUserNo(vo, id);
String uploadPath = request.getRealPath("/upload");
vo.setUploadPath(uploadPath);

dao.inWrite(vo, request, vo.getLoginUserNo());
%>
${vo.no},<c:if test="${vo.menu eq '아나'}">share</c:if><c:if test="${vo.menu ne '아나'}">reuse</c:if>