<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../include/header.jsp"%>
<%@include file="../include/fix.jsp"%>

<% 
	int pNo = Integer.parseInt(request.getParameter("no"));
	dao.delDelBoard(pNo);

	webutil.Init(request);
	int pageno = webutil._I("page","1");
	String menu   = webutil._S("menu","");
	String option = webutil._S("option","title");
	String Key = webutil._S("key","");
	String mEncodeKey = webutil._E("key","");
	
	String mParam  = "";
		 mParam += "page=" + pageno;
		 mParam += "&";
		 mParam += "no=" + pNo;
		 mParam += "&";
		 mParam += "menu=" + menu;
		 mParam += "&";
		 mParam += "option=" + option;
		 mParam += "&";
		 mParam += "key=" + mEncodeKey;

	response.sendRedirect("main.jsp?"+mParam); 
%>
		