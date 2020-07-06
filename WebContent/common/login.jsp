<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../include/header.jsp"%>

<!-- Contact Section (로그인 화면) -->
<section class="contact-section">
	<div class="container">
		<h2>로그인</h2>
		<!-- <form class="contact-form" id="login" name="login" method="post" action="loginBean_ok.jsp"> -->
		<form class="contact-form" id="login" name="login" method="post" action="../loginBean_ok">
			<div class="row">
				<div class="col-lg-12">
					<p align="left">아이디</p>
					<input type="text"  placeholder="아이디를 입력하세요" id="id" name="id" autofocus maxlength="12"/>
				</div><!--col-lg-12 클래스 마지막-->
				
				<div class="col-lg-12">
					<p align="left">비밀번호</p>
					<input type="password" placeholder="비밀번호를 입력하세요" id="pw" name="pw" autocomplete="off" maxlength="20"/>
				</div><!--col-lg-12 클래스 마지막-->
			</div><!--====row 클래스 마지막-->
			
		<!-- 	<a href="#" style="float:right" id="pwReset">비밀번호 재설정</a> -->
			<br/>
			<input type="submit" class="site-btn sb-big" id="login" value="로그인"/>
						<a  class="site-btn sb-big" id="join" name="join" href="../common/join.jsp">회원가입</a>
			
			<!-- 아이디 입력창 커서 깜빡이게 자동focus -->
			<script type="text/javascript">
				document.forms[0].id.focus();
				document.forms[0].id.select();
			</script>
		</form>
	</div><!--=====container 클래스 마지막-->
</section><!--=====Contact Section end -->

	<%@include file="../include/footer.jsp"%>

