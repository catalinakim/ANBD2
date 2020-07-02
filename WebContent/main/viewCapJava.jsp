<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../include/header.jsp"%>
<%@include file="../include/fix.jsp"%>
<%@ page import="api.*"%> 
<style>
	h3{ margin-bottom: 10px;}
	b#status{ color:gray; }
</style>
<div class="container" id="view">
	<% 
	int hereNo = Integer.parseInt(request.getParameter("no"));
	
	dao.selViewBoard(vo, hereNo);
	//dao.selViewComment(vo, hereNo);
	
	int pNo = vo.getNo();
	
	String loginId = (String)session.getAttribute("loginId");
	
	boolean loginYesNo = dao.selLoginUserNo(vo, loginId); //id세션으로 회원번호 얻기, 로그인 여부 
	int loginUserNo = vo.getLoginUserNo();
	int writerUserNo = vo.getUserNo();
	%>
	<!-- 	세션 loginId = ${sessionScope.loginId}<br/> -->
	
	<h3>
		<% String sta = vo.getStatus();
			if(sta != null){
				switch(sta) {
				case "done": 
					%><b id="status">[거래완료]</b><%
					break;
				case "cancel":
					%><b id="status">[거래완료취소]</b><%
					break;
				}
			}else{ }
			
			String menu = vo.getMenu();
			switch(menu) {
				case "share":
					%><b id="menu">[아나]</b><%
					break;
				case "reuse":
					%><b id="menu">[바다]</b><%
					break;
			}
		%>
		<b id="title"><%= vo.getTitle() %></b> 
	</h3>
	<p>
		<form id="capForm" onsubmit="return false"><!-- 엔터치면 오류나므로 막음 -->
			<span>이메일 </span>
			<span id="email"><%= vo.getEmail() %></span>
			<input type="button" id="showEmail" value="이메일 보기"/>
			<input type="hidden" id="key" name="key">
			<span><img id="captchar" src=""></span>
		   <input type="text" size="15" id="userInput" name="userInput"/>
		   <input type="button" id="confirm" value="확인"/>
	   </form>
	</p>
	<p>
		<span>아이디 </span>
		<span id="id"><%= vo.getId() %> </span>
	</p>
	<div id="content">
		<p>
			<% //file도 jstl쓰면 forEach로 가져올 수 있음 
			for(int i=0;i<vo.GetFileCount();i++)
			{
				out.print("<img src='../upload/"+vo.GetFile(i)+"'><br>");
			}
			%>
			<%= vo.getContent().replace("\n","<br>") %><br>
			
		</p>
	</div>
	<div class="contentBtn" style="margin-bottom: 10px;">
		<%
		String st = vo.getStatus();
		if(loginUserNo == writerUserNo){
			%>
			<button class="site-btn" id="modify">수정</button>
			<button class="site-btn" id="remove">삭제</button>
				<%
				switch(st){
				case "nostatus":
					%><button class="site-btn" id="done">거래완료</button><%
					break;	
				case "done" :
					%><button class="site-btn" id="cancel">거래완료취소</button><%
					break;
				case "cancel": //거래완료취소 -거래완료취소는 제목에 안보여줘도 되긴하는데..
					%><button class="site-btn" id="done">거래완료</button><%
					break;
				//default:
				}
		}
		%>
	</div>
	<form method="post" id="coForm" name="coForm" action="view.jsp" onsubmit="return false;">
		<input type="text" placeholder="로그인 후 댓글 입력" id="comment" name="comment" style="width:85%" onkeyup="pressEnter();">
		<button type="button" class="readmore-btn" id="cWrite" style="float:none;">댓글쓰기</button>
	</form>
	<% 
		//댓글 영역_v2 - jstl 잘 안됨..if문에서 jsp의 loginUserNo 번호를 못읽어...
		ArrayList<AnbdVO> coList = dao.selViewComment(pNo);
		//session.setAttribute("cc", coList);
		//댓글 영역_v3
			for(int i=0; i<coList.size(); i++){ 
				AnbdVO covo = (AnbdVO)coList.get(i);	
				out.print("<p class='coRow'>");
				out.print("<input type='hidden' id='coNo' value='"+covo.getCoNo()+"'/>");
				
				out.print("<span id='cWriter'>"+covo.getId()+"</span>");
				out.print("<span id='cContent'>"+covo.getcContent()+"</span>");
				out.print("<span id='cWdate'>"+covo.getcWdate()+"</span>");
				
				int coWriter = covo.getCWriterNo();
				
				if (loginUserNo ==coWriter){
					%>
					<span class="coEdit">
						<button type="button" class="site-btn cRemove" id="cRemove">삭제</button>
						<button type="button" class="site-btn cModify" id="cModify" >수정</button>				
					</span>
					<%
				}
			}%>
</div><!--container 클래스 마지막-->
<div class="Lst">
	<button class="site-btn" id="before" onclick="location.href='viewBefore.jsp?no=<%=hereNo%>'">이전글</button>
	<button class="site-btn" id="list" onclick="location.href='main.jsp'">목록</button>
	<button class="site-btn" id="after" onclick="location.href='viewAfter.jsp?no=<%=hereNo%>'">다음글</button>
</div>
<%@include file="../include/footer.jsp"%>
<script type="text/javascript"> 
	document.title="ANBD | 아나바다-글보기";
	function pressEnter()
	{
		var login= '<%=loginId%>';
		if(window.event.keyCode == 13)
	{
		if(login=='null' )
		{
			alert("로그인 후 댓글 작성이 가능합니다.");
			location.href="../common/login.jsp";
			return false;	
		}//first if FLOW	
		else
		{
			if( $("#comment").val().length==0 || $.trim($("#comment").val())=="")
			{
				alert("댓글을 입력하세요.");
				return false;
			}//if FLOW
			else
			{
				//return true;
				document.coForm.action =  "viewCoWrite.jsp?no=<%=pNo%>&userNo=<%=loginUserNo%>";
				document.coForm.submit(); 
			}//else FLOW
		}//first else FLOW
	}//second if FLOW
};//pressEnter FUNCTION
$(document).ready(function(){
	//캡차 시작
	var key = ''; //캡차 생성시 발급되는 키
	$("#email, #userInput, #confirm").hide();
	//이메일보기 클릭시 v1 (글보기/새로고침 때마다 캡차이미지 생성됨..)
	$("#showEmail").click(function(){ 
		//로그인 안한 경우 alert
		var login = '<%=loginId%>';
		if(login == 'null' ){ 
			alert("로그인 후 이메일 보기가 가능합니다.");
			return false;
		}
		<% //이메일보기 클릭시 생성되는게 아니고, 페이지 새로고침하면 캡차이미지가 생성됨..
		CaptchaImage getImage = new CaptchaImage();
		getImage.main(new String[] {""}); //메인함수 실행하기
		
		String imgFileName = getImage.imgFileName;
		//out.println("img\\captchar\\" + imgFileName);
		String key = CaptchaKey.key;
		%>
		key = '<%=key%>';
		var imgFileName= '<%=imgFileName%>';
		var pathFileName = "${pageContext.request.contextPath}/img/captchar/" +imgFileName;

		$("#captchar").attr("src", pathFileName);
		$("#showEmail").hide();
		$("#userInput, #confirm").show();
		$("#key").val(key); //hidden에 key값 넣기
		console.log(key);
	});
	//캡차 입력한 값 확인
	$("#confirm").click(function(){
		var capFormData = $("#capForm").serialize(); //form의 모든 값 받기
		$.ajax({ 
			type: "post",
			url: "cap_ok.jsp",
			data: capFormData,
			dataType: "json",//html
			success: function(data){
			   //alert(data);
			   //alert(JSON.stringify(data));
			   
			   var sResult = JSON.stringify(data.result);//object to string
			   if(sResult=='true'){
				   $("#userInput, #confirm, #captchar").hide();
				   $("#email").show();
			   }else if(sResult=='false'){
				   alert("입력값이 일치하지 않습니다.\n이메일 보기를 클릭하여 다시 입력해주세요");
				   location.reload(); //페이지 새로고침-이때 이메일 살짝 보였다 사라짐
			   }
			},error: function(xhr, stat, err){
				alert("오류: "+err);
			}
	   })
	});//캡차 끝
	$("#done").click(function(){ //거래완료 클릭시  -나중에는 get.parameter받는 변수로?
		location.href="viewDone.jsp?no=<%=vo.getNo() %>"; 
	});
	$("#cancel").click(function(){ //거래완료취소 클릭시
		location.href="viewCancel.jsp?no=<%=vo.getNo() %>";
	});
	$("#cWrite").click(function(){ //댓글쓰기 클릭시, 내용이 없으면 alert + 로그인 안하고 클릭시 alert 및 이동
		var login= '<%=loginId%>';
		if(login=='null' ){ 
			alert("로그인 후 댓글 작성이 가능합니다.");
			location.href="../common/login.jsp";
			return false;
		}else{
			if( $("#comment").val().length==0 || $.trim($("#comment").val())==""){
				alert("댓글을 입력하세요.");
				return false;
			}else
			{
				//return true;
				document.coForm.action =  "viewCoWrite.jsp?no=<%=pNo%>&userNo=<%=loginUserNo%>";
				document.coForm.submit(); 
			}
		}
	});
	$("#modify").click(function(){ //글 수정 버튼 클릭시 -onclick="location.href='modify.jsp'"
		location.href="viewModify.jsp?no=<%=vo.getNo() %>";
	});
	$("#remove").click(function(){ //글 삭제 버튼  //jsp이동 안하고, 바로 메소드 가능? https://all-record.tistory.com/145
		var msg = confirm("글을 정말 삭제하시겠습니까?");
		if(msg==true){
			location.href="viewRemove.jsp?no=<%=vo.getNo() %>";
		}else{
			return false;
		}
	});
	//댓글 수정 클릭시
	$(document).on('click','#cModify',function(event){
		$(this).parent().prevAll("#cContent").hide();
		$(this).parent().prevAll("#cWdate").hide();
		$(this).parent().hide();
		var cContent = $(this).parent().prevAll("#cContent").text();
		var coNo  = $(this).parent().prevAll("#coNo").val();
		var html  = "<span id='cModDiv'>";
			 html += "<form method='post' id='cModForm' action='viewCoModify.jsp?coNo="+coNo+"&no=<%=pNo%>'>";
			 html += "<input type='text' id='cModContent' name='content'/>";
			 html += "<button type='button' class='site-btn cModNo'>취소</button>";
			 html += "<button class='site-btn cModOk'>수정 완료</button>";
			 html += "</form></span>";
		$(this).parent().parent().append(html);
		$(this).parent().next("#cModDiv").children().children("#cModContent").val(cContent);
	});
	//댓글 수정 - 수정완료 클릭시
	$(document).on('click','.cModOk',function(event){
		if( $(this).prevAll("#cModContent").val().length==0 || $.trim($(this).prevAll("#cModContent").val())==""){
			alert("댓글을 입력하세요.");
			return false;
		}else{
			return true;
		}
	});
	//댓글 수정 - 취소 클릭시
	$(document).on('click','.cModNo',function(event){
		$(this).parent().parent().prevAll("#cContent").show();
		$(this).parent().parent().prevAll("#cWdate").show();
		$(this).parent().parent().prevAll(".coEdit").show();
		$(this).parent().parent().hide();
	});
	//댓글 삭제 버튼 
	$(".cRemove").click(function(){  //#cRemove로 선택하면 위에꺼 버튼에만 먹힘
		var coNo  = $(this).parent().prevAll("#coNo").val();
		location.href="viewCoRemove.jsp?coNo="+coNo+"&no=<%=pNo%>";
	});
});
</script>