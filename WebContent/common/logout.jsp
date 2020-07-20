<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
 session.invalidate();

%>
<script type="text/javascript">
	location.href="../main/main.jsp"
	alert("성공적으로 로그아웃 되었습니다");
</script>