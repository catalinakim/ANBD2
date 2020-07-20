<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../include/header.jsp"%>
<%@include file="../include/fix.jsp"%>

<jsp:useBean id="before" class="anbd.NextDAO" scope="page" />

<% 
	int no = Integer.parseInt(request.getParameter("no"));
	
	webutil.Init(request);
	int pageno = webutil._I("page","1");
	String menu   = webutil._S("menu","");
	String option = webutil._S("option","title");
	String Key = webutil._S("key","");
	String mEncodeKey = webutil._E("key","");
	Integer jusoNo = webutil._I("jusoNo","0");
	String noDoneYN = webutil._S("noDone","N");
	
	int beforeNo = before.beforeNo(no); //이전글 번호
	
	String mParam  = "";
	
			 mParam += "page=" + pageno;
			 mParam += "&";
		if(beforeNo==0){
			 mParam += "no=" + no;
		}else{
			 mParam += "no=" + beforeNo;
		}
			 mParam += "&";
			 mParam += "menu=" + menu;
			 mParam += "&";
			 mParam += "option=" + option;
			 mParam += "&";
			 mParam += "key=" + mEncodeKey;	
			 mParam += "&";
			 mParam += "jusoNo=" + jusoNo;
			 mParam += "&";
			 mParam += "noDone=" + noDoneYN;
			 
	if(beforeNo==0){
			%>
			<script>
				alert("이전 글이 존재하지 않습니다.");
				//location.href="view.jsp?no=<%--= no --%>"
				var mParam = '<%=mParam%>';
				location.href="view.jsp?"+mParam;
			</script>
			<%
	}else{
			//response.sendRedirect("view.jsp?no="+beforeNo);
			response.sendRedirect("view.jsp?"+mParam);
	}
%>
		