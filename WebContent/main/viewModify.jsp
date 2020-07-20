<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../include/header.jsp"%>
<%@include file="../include/fix.jsp"%>
<style>
	#share, #reuse{ width:130px; }
	table{ margin:auto; }
	input [type=text],
	textarea {width:580px !important;}
	td.moreFile span{
		margin-left: 30px;
		margin-right: 4px;
		display: inline-block;
		width: 370px;
	}
	input[type=button].dbFileDel{
		margin-left: 4px;}
	td.file{
		vertical-align:top;}
	.toggled{
		background-color: #1b4343 !important; }
	.dbFile{
		width: 370px !important;
		height: 30px !important;
		margin: 0px 9px 3px 25px !important;
	}
</style>
	<% 
	int hereNo = Integer.parseInt(request.getParameter("no"));
	dao.selViewBoard(vo, hereNo);
	
	webutil.Init(request);
	int pageno = webutil._I("page","1");
	String menu   = webutil._S("menu","");
	String option = webutil._S("option","title");
	String Key = webutil._S("key","");
	String mEncodeKey = webutil._E("key","");
	Integer jusoNo = webutil._I("jusoNo","0");
	String noDoneYN = webutil._S("noDone","N");
	%>
	<script type="text/javascript">
	function doGoPage(url){
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
			page = url + "?" + mParam;
			//document.location = page;
			document.modify.action = page;
			document.modify.submit();  
	}	
	</script>
	<form id="pageForm" name="pageForm" method="post" action="main.jsp">
		<input type="hidden" id="sno" name="sno" value="<%= hereNo %>">
		<input type="hidden" id="spage" name="spage" value="<%= pageno %>">
		<input type="hidden" id="smenu" name="smenu" value="<%=menu%>">
		<input type="hidden" id="soption" name="soption" value="<%=option%>">
		<input type="hidden" id="skey" name="skey" value="<%=mEncodeKey%>">
		<input type="hidden" id="sjusoNo" name="sjusoNo" value="<%=jusoNo%>">
		<input type="hidden" id="snoDone" name="snoDone" value="<%=noDoneYN%>">
	</form>	
<!-- action="viewModifyOk.jsp?no=<%--=hereNo--%>"  //mParam
	  action="javascript:doGoPage('viewModifyOk.jsp');"
-->
<form class="contact-form" id="modify" name="modify" enctype="multipart/form-data" 
		  method="post" action="javascript:doGoPage('viewModifyOk.jsp');">
	<input type="hidden" id="sno" name="no" value="<%= hereNo %>">
	<input type="hidden" id="spage" name="page" value="<%= pageno %>">
	<input type="hidden" id="smenu" name="menu" value="<%=menu%>">
	<input type="hidden" id="soption" name="option" value="<%=option%>">
	<input type="hidden" id="skey" name="key" value="<%=mEncodeKey%>">
	<div class="container" id="Wrt">
		<!--테이블 형식 본문-->
		<table>
			<tr>
				<td>카테고리</td>
				<td>
					<div class="search-type2">
					 	<div class="st-item2">
					 		<%
					 		String getMenu = vo.getMenu();
							switch(getMenu) {
							case "아나":
								%>
								<input type="radio" name="menu" value="아나" id="share" checked> 
								 	<label for="share">아/나</label>
								<input type="radio"  name="menu" value="바다" id="reuse" >
									<label for="reuse">바/다</label>
								<%
								break;
							case "바다":
								%>
								<input type="radio" name="menu" value="아나" id="share"> 
								 	<label for="share">아/나</label>
								<input type="radio"  name="menu" value="바다" id="reuse" checked>
									<label for="reuse">바/다</label>
								<%
								break;
							}
					 		%>
						</div><!-- st-item2  클래스 마지막 -->
					</div><!--==== search-type2 클래스 마지막 -->
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
					<input type="text" placeholder="제목을 입력하세요" id="title" name="title" maxlength="30" 
						    autofocus ime-mode:active;" value="${vo.title}">
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea id="content" name="content" placeholder="내용을 입력하세요">${vo.content}</textarea>
				</td>
			</tr>
			<tr >
				<td class="file">파일</td>
				
				<td class="moreFile" align="left">
					<%
					
					if(vo.GetFileCount() == 0){ //첨부파일이 없던 게시글은
						%>
						<input type="file" id="fileName" name="fileName1" onchange="fileCheck(this)" 
								accept="image/gif, image/jpeg, image/png"  />  
						<input type="button" class="site-btn" id="add" value="추가"/>
						<input type="button" class="site-btn noFile" id="remove" value="삭제"/>
						<%
					}
					//기존 파일 갯수
					int fileCount = vo.GetFileCount();
					out.print("<input type='hidden' name='fileCount' value='"+fileCount+"'>");
					//기존 photo 값
					out.print("<input type='hidden' name='photo' value='"+vo.getPhoto()+"'>");
					
					//기존 파일 표시
					for(int i=0;i<vo.GetFileCount();i++) {
						String fname = vo.GetFile(i);
						
						out.print("<div>");
						out.print("<input type='text' class='dbFile' id='fileName' name='filename"+(i+1)+"' value='"+fname+"' readonly='true'>"); 
						out.print("<input type='hidden' class='dbFile' id='fileNameHide' name='hiddenfilename"+(i+1)+"' value='"+fname+"'>");
						out.print("<input type='button' class='site-btn' id='add' value='추가'/>");
						out.print("<input type='button' class='site-btn dbFileDel' id='remove' value='삭제'/>");
						out.print("</div>");
					}
					%>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="padding-top:30px;">
					<button class="site-btn" id="save">수정</button>
					<!-- <input type="reset" class="site-btn" id="cancel" value="초기화"/> -->
					<button type="button" class="site-btn" id="cancel">취소</button>
				</td>
			</tr>
		</table>
	</div>
</form>
<div class="Lst">
	<!-- button class="site-btn" id="list" onclick="location.href='main.jsp'">목록</button> -->
	<button class="site-btn" id="list" onclick="javascript:doGoPage('main.jsp');">목록</button>
</div>
<script type="text/javascript">
document.title="ANBD | 아나바다-글수정";
	$(document).ready(function()
	{
		$("#save").click(function(){
			var title = $("#title").val(); 
			var content = $("#content").val(); 
			if(title == ""){
				alert($("#title").prop("placeholder"));
				$("#title").focus();
				return false;
			}
			if(content == ""){
				alert($("#content").prop("placeholder"));
				$("#content").focus();
				return false;
			}
		})
		//첨부파일 추가 버튼 방법 - 10개 까지 , name에 '파일명+숫자(count)' 붙임
		var count = 0;
		if(<%=vo.GetFileCount()%>==0){
			count = 2;
		}else{
			count = <%=vo.GetFileCount()%>+1; 
		}
		// 더 추가가능한 갯수 = 10 - an.GetFileCount()
		// count는 an.GetFileCount() + 1 부터 시작해야 함
		$(document).on('click','#add',function(event){
			var html  = "<div class='added'><input type='file' id='fileName' name='fileName"+count+"' accept='image/gif, image/jpeg, image/png'/> ";
				html += "<input type='button' class='site-btn' id='add' value='추가'/> ";
				html += "<input type='button' class='site-btn del' id='remove' value='삭제'/></div>";
			if(count>10){
				alert("최대 10개까지 첨부가능합니다.");
			}else{
				$('.moreFile').append(html);
			}
			count++;
		});
		//첨부파일 없던 게시글 - 첨부파일 첫번째 삭제 버튼 - 파일 값만 사라지게  - if(IE면){} else(다른 브라우저면){}
		$('.noFile').click(function(){
			var agent = navigator.userAgent.toLowerCase();
			if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
			    // ie 일때 input[type=file] init.
			    $("#fileName").replaceWith( $("#fileName").clone(true) );
			} else {
			    //other browser 일때 input[type=file] init.
			    $("#fileName").val("");
			}
		});
		//기존 파일 삭제 클릭시 -> input value값 초기화  => 등록시 반영되게, 파일명 array로?
		$('.dbFileDel').click(function(){
			 //$(this).toggleClass('toggled');  //.css("background-color","#1b4343")
		    //$(this).prevAll("#fileName").attr('disabled', false);
		    $(this).prevAll("#fileName").val("");
		    //$(this).prevAll("#fileNameHide").val("");
		});
		
		//취소 클릭시 이전페이지
		$("#cancel").click(function(){
			window.history.back();
		});
});
		//2번째 삭제부터는 한줄 다 사라지게
		$(document).on('click', '.del', function() {
			$(this).parent().remove();
		});
		//이미지 파일만 가능하게 - https://sqlplus.tistory.com/entry/파일첨부에-이미지만-첨부할-수-있도록-처리-input-file
		function fileCheck(obj) {
		    pathpoint = obj.value.lastIndexOf('.'); //lastIndexOf()는 특정 문자열이 마지막에 나타나는 위치를 반환하는 메서드
		    filepoint = obj.value.substring(pathpoint+1,obj.length); //substring(start, end)은 문자열에서 특정 부분만 골라낼 때
		    filetype = filepoint.toLowerCase(); //toLowerCase() 소문자로 변환해서 반환
		    if(filetype=='jpg' || filetype=='gif' || filetype=='png' || filetype=='jpeg' || filetype=='bmp') {
		        // 정상적인 이미지 확장자 파일일 경우 ...
		    } else {
		        alert('이미지 파일만 선택할 수 있습니다.');
		        parentObj  = obj.parentNode //부모노드.replaceChild(newNode, oldNode) 기존 노드의 교체
		        node = parentObj.replaceChild(obj.cloneNode(true),obj); //cloneNode(true) 노트를 복제(true-자식도 함께 복제)
		        return false;
		    }
		    if(filetype=='bmp') {
		        upload = confirm('BMP 파일은 웹상에서 사용하기엔 적절한 이미지 포맷이 아닙니다.\n그래도 계속 하시겠습니까?');
		        if(!upload) return false;
		    }
		}
</script>
<%@include file="../include/footer.jsp"%>
