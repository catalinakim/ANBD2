<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/fix.jsp"%>

<% 
	int pNo = Integer.parseInt(request.getParameter("no"));
	//vo.setNo(pNo);
	
	webutil.Init(request);
	int pageno = webutil._I("page","1");
	String menu   = webutil._S("menu",""); //게시글의 카테고리(메뉴)와 충돌?
	String option = webutil._S("option","title");
	String Key = webutil._S("key","");
	String mEncodeKey = webutil._E("key","");
	Integer jusoNo = webutil._I("jusoNo","0");
	String noDoneYN = webutil._S("noDone","N");
	
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
			 mParam += "&";
			 mParam += "jusoNo=" + jusoNo;
			 mParam += "&";
			 mParam += "noDone=" + noDoneYN;
	System.out.println("mEncodeKey: "+mEncodeKey);

	//String savePath = application.getRealPath("/upload");
	//out.print(savePath);
	
	int size = 1024*1024*10; //10MB
	
	//MultipartRequest multi = new MultipartRequest(request, savePath, size, "utf-8", new DefaultFileRenamePolicy());
	
	//upModifyBoard_v1
	//String pMenu = multi.getParameter("menu");
	//vo.setMenu(pMenu);
	//String pTitle = multi.getParameter("title");
	//vo.setTitle(pTitle);
	//String pContent = multi.getParameter("content");
	//vo.setContent(pContent);
	
	boolean modifyResult = dao.upModifyBoard(vo, request);
	if(modifyResult == true){
		out.print("수정이 완료되었습니다.");
	}else{
		out.print("수정에 실패하였습니다.");
	}
	//response.sendRedirect("view.jsp?no="+pNo);
	response.sendRedirect("view.jsp?"+mParam);
	
%>

<br/><a href="view.jsp?no=<%=pNo%>">글보기 이동</a>
<br/><%--=vo.getTitle() --%>
<br/><%--=vo.getContent() --%>