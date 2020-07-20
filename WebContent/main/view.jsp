<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../include/headerKgh.jsp"%>
<%@include file="../include/fix.jsp"%>
<%@ page import="java.net.URLEncoder" %> <!-- 브라우저 때문에.. -->
<%@ page import="api.*"%> 
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>

<style>
	h3{ margin-bottom: 10px;}
	b#status{ color:gray; }
	#refresh{ font-size: 10pt; color: #565855; }
	#showEmail, #confirm, #capCancel{ font-size: 11pt; border:0; background-color: #D3D3D3; }
</style>
<div class="container" id="view">
	<% 
	webutil.Init(request);
	int pageno = webutil._I("page","1");
	String menu   = webutil._S("menu","");
	String option = webutil._S("option","title");
	String Key = webutil._S("key","");
	String mEncodeKey = webutil._E("key","");
	Integer jusoNo = webutil._I("jusoNo","0");
	String noDoneYN = webutil._S("noDone","N");
	/*선생님 메소드로 수정
	String key = request.getParameter("key"); 
	if(key==null){ key=""; }
	key = URLEncoder.encode(key, "UTF-8");*/
	int hereNo = Integer.parseInt(request.getParameter("no"));
	dao.selViewBoard(vo, hereNo);
	//dao.selViewComment(vo, hereNo);
	int pNo = vo.getNo();
	String loginId = (String)session.getAttribute("loginId");
	boolean loginYesNo = dao.selLoginUserNo(vo, loginId); //id세션으로 회원번호 얻기, 로그인 여부 
	int loginUserNo = vo.getLoginUserNo();
	int writerUserNo = vo.getUserNo();
	%>
	<c:if test="${vo.getMenu() eq '바다'}">
		<link rel="stylesheet" type="text/css" href="../css/reuseStyle.css">
	</c:if>
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
			%>
		[${vo.getMenu()}]
	 <%-- String menu = vo.getMenu();
			switch(menu) {
				case "share":
					--%><!-- b id="menu">[아나]</b><%--
					break;
				case "reuse":
					--%><b id="menu">[바다]</b> --><%--
					break;
			} --%>
		<b id="title"><%= vo.getTitle() %></b> 
	</h3>
	<p>
		<form id="capForm" onsubmit="return false"><!-- 엔터치면 오류나므로 막음 -->
			<span>이메일 </span>
			<span id="email"><%= vo.getEmail() %></span>
			<input type="button" id="showEmail" value="이메일주소 보기"/>
			<input type="hidden" id="key" name="key">
			<span><img id="captchar" src=""></span>
		   <input type="text" size="15" id="userInput" name="userInput"/>
		   <span id="refresh" style="cursor:pointer"><img src="..\img\Refresh19px.png">새로고침</span>
		   <input type="button" id="confirm" value="확인"/>
		   <input type="button" id="capCancel" value="취소"/>
	   </form>
	</p>
	<p>
		<span>아이디 </span>
		<span id="id"><%= vo.getId() %> </span>
	</p>
	<div id="content">
		<p>
			<div id="slider">
				<% //file도 jstl쓰면 forEach로 가져올 수 있음 
					for(int i=0;i<vo.GetFileCount();i++)
					{
						out.print("<img src='../upload/"+vo.GetFile(i)+"' width='300px'><br>");
					}
				%>
			</div>
			<%= vo.getContent().replace("\n","<br>") %><br>
		</p>
	</div>
	<div class="contentBtn" style="margin-bottom: 10px;">
		<c:if test="${vo.getLoginUserNo() eq vo.getUserNo()}">
			<button class="site-btn" id="modify">수정</button>
			<button class="site-btn" id="remove">삭제</button>
			<c:if test="${vo.getMenu() eq '아나'}">
				<c:choose>
					<c:when test="${vo.status eq 'nostatus'}">
						<button class="site-btn" id="done">거래완료</button>
					</c:when>
					<c:when test="${vo.status eq 'done'}">
						<button class="site-btn" id="cancel">거래완료취소</button>
					</c:when>
					<c:when test="${vo.status eq 'cancel'}">
						<button class="site-btn" id="done">거래완료</button>
					</c:when>
				</c:choose>
			</c:if>
		</c:if>
		<%--	switch(st){
				case "nostatus":
					--%><!-- button class="site-btn" id="done">거래완료</button><%--
					break;	
				case "done" :
					--%><button class="site-btn" id="cancel">거래완료취소</button><%--
					break;
				case "cancel":
					--%><button class="site-btn" id="done">거래완료</button> --><%--
					break;
				}}--%>
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
				if(vo.getId().equals(covo.getId()))
				{
						out.print("<span id='cWriter'>"+covo.getId()+"");
						switch(vo.getMenu())
						{
						case "아나":
							out.print("<img src='../img/writerGreen.png' width='45px'></span>");
							break;
						case "바다":
							out.print("<img src='../img/writerBlue.png' width='45px'></span>");
							break;
						}
				}
				else
				{
					out.print("<span id='cWriter'>"+covo.getId()+"</span>");
				}
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
			<div id="div"></div>
</div><!--container 클래스 마지막-->
<div class="Lst">
	<button class="site-btn" id="before" onclick="javascript:doGoPage('viewBefore.jsp');">이전글</button>
	<button class="site-btn" id="list" onclick="javascript:doGoPage('main.jsp');">목록</button>
	<button class="site-btn" id="after" onclick="javascript:doGoPage('viewAfter.jsp');">다음글</button>
</div>
<%@include file="../include/footer.jsp"%>
	<form id="pageForm" name="pageForm" method="post" action="main.jsp">
		<input type="hidden" id="sno" name="sno" value="<%= hereNo %>">
		<input type="hidden" id="spage" name="spage" value="<%= pageno %>">
		<input type="hidden" id="smenu" name="smenu" value="<%=menu%>">
		<input type="hidden" id="soption" name="soption" value="<%=option%>">
		<input type="hidden" id="skey" name="skey" value="<%=mEncodeKey%>">
		<input type="hidden" id="sjusoNo" name="sjusoNo" value="<%=jusoNo%>">
		<input type="hidden" id="snoDone" name="snoDone" value="<%=noDoneYN%>">
	</form>	
<script type="text/javascript"> 
	document.title="ANBD | 아나바다-글보기";
	function doGoPage(url, coNo){
		var f = document.pageForm;
		var mParam  = "";
			mParam += "page=" + f.spage.value;
			mParam += "&";
			mParam += "menu=" + f.smenu.value;
			mParam += "&";
			mParam += "option=" + f.soption.value;
			mParam += "&";
			mParam += "key=" + f.skey.value;	
			mParam += "&";
			mParam += "no=" + f.sno.value;
			mParam += "&";
			mParam += "jusoNo=" + f.sjusoNo.value;
			mParam += "&";
			mParam += "noDone=" + f.snoDone.value;
		if(coNo!=null){
			mParam += "&";
			mParam += "coNo=" + coNo;
		}
		page = url + "?" + mParam;
		document.location = page;
	}	
	//페이지 이동이 아니고 submit해야하는 경우(댓글쓰기, 댓글수정) 파라미터 넘기기
	function doSubmitPage(url, formName, coNo){
		var f = document.pageForm;
		var mParam  = "";
			mParam += "page=" + f.spage.value;
			mParam += "&";
			mParam += "menu=" + f.smenu.value;
			mParam += "&";
			mParam += "option=" + f.soption.value;
			mParam += "&";
			mParam += "key=" + f.skey.value;	
			mParam += "&";
			mParam += "no=" + f.sno.value;
			mParam += "&";
			mParam += "userNo=" + <%=loginUserNo%>;
			mParam += "&";
			mParam += "jusoNo=" + f.sjusoNo.value;
			mParam += "&";
			mParam += "noDone=" + f.snoDone.value;
		if(coNo!=null){
			mParam += "&";
			mParam += "coNo=" + coNo;
		}
		page = url + "?" + mParam;
		formName.action = page;
		formName.submit();  
	}	
	//댓글쓰기 엔터
	function pressEnter(){
		var login= '<%=loginId%>';
		if(window.event.keyCode == 13){
			if(login=='null' ){
				alert("로그인 후 댓글 작성이 가능합니다.");
				location.href="../common/login.jsp";
				return false;	
			}//first if FLOW	
			else{
				if( $("#comment").val().length==0 || $.trim($("#comment").val())==""){
					alert("댓글을 입력하세요.");
					return false;
				}else{
					//return true;
					//document.coForm.action =  "viewCoWrite.jsp?no=<%=pNo%>&userNo=<%=loginUserNo%>";
					//document.coForm.submit(); 
					doSubmitPage('viewCoWrite.jsp', document.coForm);
				}//if, else
			}//first else FLOW
		}//second if FLOW
	};//pressEnter FUNCTION	
$(document).ready(function(){
	//캡차 시작
	var key = ''; //캡차 생성시 발급되는 키
	$("#email, #userInput, #confirm, #refresh, #capCancel, #captchar").hide();
	//이메일보기 클릭시 v2 [시작] Ajax로 이미지 생성
	$("#showEmail").click(function(){ 
		//로그인 안한 경우 alert
		var login = '<%=loginId%>';
		if(login == 'null' ){ 
			alert("로그인 후 이메일 보기가 가능합니다.");
			return false;}
		$.ajax({ 
			url: "getCaptchar.jsp",
			dataType: "json",
			success: function(result){
				//alert(result);													  //object 형태
				//alert(JSON.stringify(result.imgFileName));				  //"" 들어감
				//console.log(result.imgFileName);						     //"" 안들어감
				//var imgFileName = JSON.stringify(result.imgFileName); //"" 들어감
				var imgFileName = result.imgFileName + ""; 				  //string으로 변환 -"" 안들어감
				console.log(imgFileName);
				<% String imagePath = request.getServletContext().getRealPath("img\\captchar")+ "\\" ; 
					//imagePath = imagePath.replace("\\", "\\\\" ); 
					imagePath = "/anbd2/img/captchar/"; //시스템 경로   %>
				//var path = "<%--=imagePath--%>";
				var pathFileName = "<%=imagePath%>" + imgFileName;
				key = result.key+"";
				$("#captchar").attr("src", pathFileName); //이미지에 넣기
				$("#showEmail").hide();
				$("#captchar, #userInput, #confirm, #refresh, #capCancel").show();
				$("#key").val(key); 
			},error: function(xhr, stat, err){
				alert("오류: "+err);
			}
	   });
	});//이메일보기 클릭시 v2 [종료]
	$("#refresh").click(function(){ //새로고침 클릭시
		$("#showEmail").click();
	});
	//이메일보기 클릭시 v1 - 글보기/새로고침 때마다 캡차이미지 생성
	//$("#showEmail").click(function(){ 
		<% /*
		CaptchaImage getImage = new CaptchaImage();
		getImage.main(new String[] {""}); 		   //메인함수 실행하기
		String imgFileName = getImage.imgFileName; //파일명 얻기
		String key = CaptchaKey.key; */ %>
		//key = '<%--=key--%>';
		//var imgFileName= '<%--=imgFileName--%>';
		//var pathFileName = "${pageContext.request.contextPath}/img/captchar/" +imgFileName;
		//$("#captchar").attr("src", pathFileName); //이미지 넣어주기
		//$("#showEmail").hide();
		//$("#userInput, #confirm").show();
		//$("#key").val(key); //hidden에 key값 넣기
	//});
	// 입력값 확인 [시작]
	$("#confirm").click(function(){
		var capFormData = $("#capForm").serialize(); //form의 모든 값 받기
		$.ajax({ 
			type: "post",
			url: "capOk.jsp",
			data: capFormData,
			dataType: "json",//html
			success: function(data){
			   //alert(data);
			   //alert(JSON.stringify(data));
			   var sResult = JSON.stringify(data.result);//object to string
			   if(sResult=='true'){
				   $("#userInput, #confirm, #captchar, #refresh, #capCancel").hide();
				   $("#email").show();
			   }else if(sResult=='false'){
				   alert("입력값이 일치하지 않습니다.\n 다시 입력해주세요");
				   //location.reload(); //페이지 새로고침-이때 이메일 살짝 보였다 사라짐
				   //$("#showEmail").click();
			   }
			},error: function(xhr, stat, err){
				alert("오류: "+err);
			}
	   })
	});//입력값 확인 [종료]
	//캡차 취소 클릭 [시작]
	$("#capCancel").click(function(){
		$("#captchar, #userInput, #confirm, #refresh, #capCancel").hide();
		$("#showEmail").show();
	});//캡차 취소 클릭 [종료]
	$("#done").click(function(){ //거래완료 클릭시  -나중에는 get.parameter받는 변수로?
		//location.href="viewDone.jsp?no= <%=vo.getNo() %>"; 
		doGoPage('viewDone.jsp'); 
	});
	$("#cancel").click(function(){ //거래완료취소 클릭시
		//location.href="viewCancel.jsp?no=<%=vo.getNo() %>";
		doGoPage('viewCancel.jsp'); 
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
			}else{
				//return true;
				//document.coForm.action =  "viewCoWrite.jsp?no=<%=pNo%>&userNo=<%=loginUserNo%>";
				//document.coForm.submit();
				doSubmitPage('viewCoWrite.jsp', document.coForm);
			}
		}
	});
	$("#modify").click(function(){ //글 수정 버튼 클릭시 -onclick="location.href='modify.jsp'"
		//location.href="viewModify.jsp?no=<%=vo.getNo() %>";
		doGoPage('viewModify.jsp');
	});
	$("#remove").click(function(){ //글 삭제 버튼  //jsp이동 안하고, 바로 메소드 가능? https://all-record.tistory.com/145
		var msg = confirm("글을 정말 삭제하시겠습니까?");
		if(msg==true){
			//location.href="viewRemove.jsp?no=<%=vo.getNo() %>";
			doGoPage('viewRemove.jsp');
		}else{
			return false;
		}
	});
	//댓글 수정 클릭시
	var coNo;
	$(document).on('click','#cModify',function(event){
		$(this).parent().prevAll("#cContent").hide();
		$(this).parent().prevAll("#cWdate").hide();
		$(this).parent().hide();
		var cContent = $(this).parent().prevAll("#cContent").text();
		coNo  = $(this).parent().prevAll("#coNo").val();
		var html  = "<span id='cModDiv'>";
			 html += "<form method='post' id='cModForm' name='cModForm' action='viewCoModify.jsp?coNo="+coNo+"&no=<%=pNo%>'>";
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
			//return true;
			doSubmitPage('viewCoModify.jsp', document.cModForm, coNo);
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
		coNo  = $(this).parent().prevAll("#coNo").val();
		//location.href="viewCoRemove.jsp?coNo="+coNo+"&no=<%=pNo%>";
		doGoPage('viewCoRemove.jsp', coNo);
	});
});
</script>