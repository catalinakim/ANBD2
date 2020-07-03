<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/fix.jsp"%>

<jsp:setProperty name="vo" property="*"/>
<%	
request.setCharacterEncoding("utf-8"); 
boolean result1 = dao.selLogin(vo.getId(), vo.getPw());
pageContext.setAttribute("result", result1);

//<jsp:useBean id="joinBean" class="anbd.AnbdVO" scope="page" /> 
//<jsp:useBean id="joinBean2" class="anbd.AnbdDAO" scope="page" /> <!-- 데이터베이스 mysql에서도 보이게 하는거. 값을 넘기고 받고 하는 거 -->

//<jsp:setProperty name="joinBean" property="*"/>

%>
<!-- <html>
<p> 아이디	 : <%--=joinBean.getId() --%>    </p>
<p> 비번 		 : <%--=joinBean.getPw() --%> 	 </p> -->

<!--  <% %> 열고 닫는 표시는 자바 언어만 쓸 수 있음. html쓰려면 닫고 다시 열기 -->
<% //기존 방법
  /*
	boolean result = joinBean2.selLogin(joinBean.getId(), joinBean.getPw()); 
	if(result==true){
		//out.print(joinBean.getId() +  "님 로그인되었습니다.");
		String id = joinBean.getId();   //id 세션 생성
		session.setAttribute("loginId", id);*/
%>
		<script>
			//alert("로그인 되었습니다.");
			//location.href="./main/main.jsp" //현재폴더 기준(for 서블릿)
			//location.href="../main/main.jsp"
			//<a href="../main/main.jsp"> 메인으로 갈래? </a>
		</script>
<% 
	//}else{
		//out.print("아이디, 비밀번호를 확인해주세요.");
%> 		
		<script>
		//	alert("아이디, 비밀번호를 확인해주세요.");
		//	location.href="login.jsp"
		</script>
		<!-- <a href="login.jsp" > 로그인 페이지로 이동 </a> --> 	
<% //}
	//3차 방법 - id 틀렸는지 pw 틀렸는지
		//int result2 = dao.selLogin2(joinBean.getId(), joinBean.getPw());
		
		// 1 로그인 성공
		/*
		if( result2 == 1){ 				  
			String id = joinBean.getId();  //id 세션 생성
			session.setAttribute("loginId", id);
			out.println(" <script> "); 
			out.println(" alert('로그인 되었습니다.'); "); 
			out.println(" location.href='../main/main.jsp' "); 
			out.println(" </script> "); 
		}*/
		// 0 비밀번호 불일치 
		/*
		else if( result2 == 0){
			out.println(" <script> "); 
			out.println(" alert('비밀번호가 일치하지 않습니다.'); "); 
			out.println(" location.href='login.jsp' "); 
			out.println(" </script> "); 
		}*/
		// -1 아이디 불일치 
		/*
		else if( result2 == -1){
			out.println(" <script> "); 
			out.println(" alert('존재하지 않는 id입니다.'); "); 
			out.println(" location.href='login.jsp' "); 
			out.println(" </script> "); 
		}*/
		// -2 로그인 에러 
		/*
		else if( result2 == -2){
			out.println(" <script> "); 
			out.println(" alert('로그인에 오류가 발생하였습니다.'); "); 
			out.println(" location.href='login.jsp' "); 
			out.println(" </script> "); 
		}*/

%>
<html>
	<c:choose>
		<c:when test="${result eq true}">
			This id & password is true!<br/>
			<% session.setAttribute("loginId", vo.getId()); %>
			${sessionScope.loginId }님이 로그인했음
			<script language="javascript">
			//서블릿 일때   ./main/main.jsp 현재폴더 기준
			//서블릿 아닐때 ../main/main.jsp
			location.href="../main/main.jsp";
			</script>
		</c:when>
		<c:when test="${result eq false}">
			This id & password is NOT true!<br/>
			PLEASE TRY AGIN<br/>
			<script type="text/javascript">
				alert("승인할 수 없는 아이디/비밀번호입니다");
				location.href="login.jsp";
			</script>
		</c:when>
	</c:choose>
</html>

