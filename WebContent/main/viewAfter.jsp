<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
	
	int afterNo = before.afterNo(no);
	
	String mParam  = "";
	
			 mParam += "page=" + pageno;
			 mParam += "&";
		if(afterNo==0){
			 mParam += "no=" + no;
		}else{
			 mParam += "no=" + afterNo;
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
			 
	if(afterNo==0){
			%>
			<script>
				alert("다음 글이 존재하지 않습니다.");
				var mParam = '<%=mParam%>';
				location.href="view.jsp?"+mParam;
			</script>
			<%
	}else{
		 	//response.sendRedirect("view.jsp?no=" + afterNo);
		 	response.sendRedirect("view.jsp?"+mParam);
			 
	}
	out.println(afterNo);
	//실행하고 오류 없으면 페이지 바로 이동하는 거 
%>
 	

