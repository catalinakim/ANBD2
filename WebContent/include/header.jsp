<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="anbd.DbInfo" %>
<%@ page import="anbd.AnbdVO" %>
<%@ page import="anbd.AnbdDAO" %>
<jsp:useBean id="vo" class="anbd.AnbdVO" scope="page"/>
<jsp:useBean id="db" class="anbd.DbInfo" scope="page"/>
<jsp:useBean id="dao" class="anbd.AnbdDAO" scope="page"/>

<!DOCTYPE html>
<html lang="ko">
<head>
	<title>ANBD | 아나바다</title>
	<meta charset="UTF-8">
	<meta name="description" content="Real estate HTML Template">
	<meta name="keywords" content="real estate, html">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=10">
	
	<!-- Favicon
		 인터넷 탭에 표시되는 아이콘
		 익스플로어에서는 안보임, 크롬에서 보임
	-->
	<link href="../img/anbd favicon.ico" rel="shortcut icon"/>

	<!-- Google font 글씨체 -->
	<link href="https://fonts.googleapis.com/css?family=Lato:400,400i,700,700i,900%7cRoboto:400,400i,500,500i,700,700i&display=swap" rel="stylesheet">

 	<!-- CSS 총 4개 -->
	<link rel="stylesheet" href="../css/bootstrap.css"/>
	<link rel="stylesheet" href="../css/font-awesome.min.css"/>
	<link rel="stylesheet" href="../css/slicknav.min.css"/>

	<!-- Main CSS -->
	<link rel="stylesheet" href="../css/style.css"/>
	
	<script src="../js/jquery-3.2.1.min.js"></script>

	<style>
	</style>
</head>
<body>
	<!-- Header Section -->
	<header class="header-section">
	    <!-- 로고 -->
		<a href="../main/main.jsp" class="site-logo">
			<img src="../img/logo.png" alt="메인로고입니다">
		</a>

		<nav class="header-nav">
			<!-- 상단 메뉴 -->
			<ul class="main-menu">
				<!-- class="active" -->
				<!-- DB 연결 후 파라미터 줘야함 ../main/main.jsp&menu=share -->
				<li><a href="../main/main.jsp?menu=share">아껴쓰고 <b>나눠쓰기</b></a></li>
				<li><a href="../main/main.jsp?menu=reuse">바꿔쓰고 <b>다시쓰기</b></a></li>
			</ul>
			<!-- 로그인, 회원가입 -->
			<div class="header-right">
				<div class="user-panel">
			
			<c:choose>
				<c:when test="${sessionScope.loginId ne null}">
					<span>${sessionScope.loginId}님 반갑습니다.</span>&nbsp;&nbsp;
          <% session.setMaxInactiveInterval(60*60); %>
					<a href="../common/logout.jsp" class="register">로그아웃</a>
				</c:when>
				<c:when test="${sessionScope.loginId eq null}">
					<a href="../common/join.jsp" class="login">회원가입</a>
					<a href="../common/login.jsp" class="register">로그인</a>
				</c:when>
			</c:choose>

					<%
          	/*
				}else{
					String loginId = (String)session.getAttribute("loginId");
					//세션 시간 설정 60초*60 = 1시간
					session.setMaxInactiveInterval(60*60);
          	*/
					%>
          <!-- <span><%--=loginId --%>님 반갑습니다.</span>&nbsp;&nbsp;
					<a href="../common/logout.jsp" class="login">로그아웃</a> -->
					<%
				//}
			%>
				</div>
			</div>
		</nav>
	</header>
	<!-- Header Section end -->