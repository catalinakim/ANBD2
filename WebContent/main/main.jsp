<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../include/headerKgh.jsp"%>
<%@include file="../include/fix.jsp"%>
<%@include file="../include/searchOption.jsp"%>
<%@ page import="java.net.URLEncoder" %> <!-- 브라우저 때문에.. -->
<jsp:useBean id="pg" class="anbd.PageDAO" scope="page"/>
<% 
request.setCharacterEncoding("utf-8");
int currentPage = 1;  //현재 페이지번호
int pageSize    = 19; //페이지당 게시물 목록 갯수
int count       = 0;  //전체 게시물 갯수
int startRow    = 0;  //페이지 시작행 번호
int seqNo       = 0;  //페이지 목록에 게시글 일련번호
int maxPageNo   = 0;  //최대 페이지 번호
currentPage = webutil._I("page","1");
String mKey = webutil._S("key","");
String mEncodeKey = webutil._E("key","");

/*선생님께서 알려주시기 전 코드 - 아래 코드를 위 코드로 단축!
String mKey = request.getParameter("key"); 
if(mKey==null){     //그냥 검색안하면 null
	mKey="";
}
String mEncodeKey = URLEncoder.encode(mKey, "UTF-8"); //url에 검색어 한글을 %로 바꿔줌(인코딩)*/
request.setAttribute("key", mEncodeKey);
%>
<script language="javascript">
	var key = '<%=mKey%>';
	if( key==""){ 	//검색어가 없으면
		document.title="ANBD | 아나바다-목록";
	}else{
		document.title="ANBD | '"+ key +"'검색 결과";
	}
</script>
<%	startRow  = (currentPage - 1)*pageSize; //페이지 시작행 번호  = (현재 페이지번호 - 1) * 페이지당 출력 할 갯수
	seqNo     = startRow + 1;				    //페이지 목록에 게시글 일련번호
	ArrayList<AnbdVO> mainList = new ArrayList<AnbdVO>();
	String uri = request.getRequestURI();
	//System.out.println("==uri: "+uri);
	
	//서블릿 경우라면..
	String MainPageJSP = "";
	String FromServlet = (String)request.getAttribute("yesServlet");	
	if(FromServlet != null){
		MainPageJSP = "jusoSer";
	}else{
		MainPageJSP = "main/main.jsp";
	}
	request.setAttribute("curpage_jsp",MainPageJSP);
	
	//선생님 지도 후
	Integer jusoNo = 0;
	String noDoneYN = "N";
	String sido = "";
	String sigun = "";
	boolean juso = false;
	boolean noDone = false;
	jusoNo = webutil._I("jusoNo","0");	
		if(jusoNo!=0){
			juso = true;
			sido = pg.selSidoByJusoNo(jusoNo);  //주소번호로 시/도, 시/군/구 값 가져오기
			sigun = pg.selSigunByJusoNo(jusoNo);
		}
	noDoneYN = webutil._S("noDone","N");	 //==request.getParameter("noDone");
		if(noDoneYN.equals("Y")){
			noDone = true;
		}
	pg.selMainListJuDone(mainList, startRow, pageSize, request, juso, noDone);
	count 	 = pg.count; 			 			 //(selMainListJuDone메소드로부터 검색된)총 글 갯수
	pageContext.setAttribute("pgList", mainList);
	
	maxPageNo = count/pageSize;  				 //최대 페이지 번호 계산
	if( (count % pageSize) != 0 ){			 //총 글 갯수를 목록표시갯수(20)로 나눠서 나머지가 남으면, 한 페이지 더 필요
		maxPageNo = maxPageNo + 1;				 //최대 페이지번호에 +1
	}
	dao.selJuso();
	pageContext.setAttribute("selJuso", dao.getBoardList());
	//=======공지 [시작]
	dao.selNotice();
 	pageContext.setAttribute("selNotice", dao.getBoardList());
	//=======공지 [종료]
%>
<c:if test="${param.menu eq 'reuse'}">
	<link rel="stylesheet" type="text/css" href="/anbd2/css/reuseStyle.css">
</c:if>
<div style="padding: 20px 40px 20px;">
	<div align="right">
		<select id="sido" name="sido">
			<option value="시/도">시/도</option>
			<c:forEach items="${selJuso}" var="selJuso">
				<option value="${selJuso.sido}">${selJuso.sido}</option>
			</c:forEach>	
		</select>
		<select id="sigun" name="sigun">
			<option value="시/군/구">시/군/구</option>
		</select>
		<input type="button" id="jusoSort" value="확인"/>
		<img id="noDone" style="width:22px;" src="/anbd2/img/checkGray.png"/> 거래완료 안보기
	</div>
	<table id="board">
		<tr>
			<th width="160px">구분</th>
			<th width="500px">제목</th>
			<th width="200px">시도</th>
			<th width="200px">시군구</th>
			<th width="200px">작성일자</th>
		</tr>
		<!-- 공지 상단 고정 시작============================ -->
			<c:forEach items="${selNotice}" var="selNotice">
				<c:if test="${selNotice.menu eq '공지'}">
					<tr>
						<td>[${selNotice.menu}]</td>
						<td>
						<a href="view.jsp?no=${selNotice.no}&menu=notice">${selNotice.title}
							<c:choose>
								<c:when test="${selNotice.photo eq 'Y'}">
									<img src="../img/green.png" style="width:20px;">
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</a>
					</td>
					<td>
							<c:choose>
								<c:when test="${pageList.sido ne null}">${pageList.sido}</c:when>
								<c:when test="${pageList.sido eq null}">기타</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${pageList.sigun ne null}">${pageList.sigun}</c:when>
								<c:when test="${pageList.sigun eq null}">기타</c:when>
							</c:choose>
						</td>
					<td>${selNotice.wdate}</td>
					</tr>
				</c:if>
			</c:forEach>
		<!--============================ 공지 상단 고정 끝 -->
		
		<!-- 목록 불러오기 시작=========================== -->
		<c:forEach items="${pgList}" var="pageList">
			<tr>
				<c:choose>
					<c:when test="${pageList.menu ne '공지'}">
						<td>[${pageList.menu}]</td>
						<td>
							<!--기존: <a href="view.jsp?menu=reuse&no=${pageList.no}&key=${key}"> -->
							<a href="javascript:doGoPage('/anbd2/main/view.jsp','<%= currentPage %>','${pageList.no}');">
							<c:choose>
								<c:when test="${pageList.status eq 'done'}">
									<span id="status">[거래완료]</span>
								</c:when>
								<c:when test="${pageList.status eq 'cancel'}">
									<span id="status">[거래완료취소]</span>
								</c:when>
								<c:otherwise>
									<span id="status"></span>
								</c:otherwise>
							</c:choose>
							${pageList.title}
							<c:choose>
								<c:when test="${pageList.photo eq 'Y'}">
									<c:choose>
										<c:when test="${param.menu eq 'reuse'}">
											<img src="../img/skyblue.png" style="width:20px;">
										</c:when>
										<c:otherwise>
											<img src="../img/green.png" style="width:20px;">
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							</a>
						</td>
						<td>${pageList.sido}  <c:if test="${pageList.sido  eq null}">기타</c:if> </td>
						<td>${pageList.sigun} <c:if test="${pageList.sigun eq null}">기타</c:if> </td>
						<td>${pageList.wdate}</td>
					</c:when>
				</c:choose>
			</tr>
		</c:forEach>
	</table>
	<!--=========================== 목록 불러오기 끝 -->
	<%
	//세션 변수에 저장된 userId값이 비어있으면 로그인 안한것
	if(session.getAttribute("loginId")==null)
	{
		%>
			<input type="button" id="write" class="Wrt2" value="글쓰기" onclick="javascript:doAlert();">
		<%
	}
	else
	{
		String loginId = (String)session.getAttribute("loginId");
		//out.print(session.getAttribute("loginId"));
		%>
		<input type="button" id="write" class="Wrt2" value="글쓰기" onclick="location.href='write.jsp'">
		<%
	}
	%>
</div>
<!--페이지 번호 -->
<div class="site-pagination" align="center">
<%
	//페이징 시작 블럭 계산
	int startBlock = (((currentPage-1)/10)*10) +1;
	//페이징 종료 블럭 계산
	int endBlock  = startBlock + (10-1); 			  
	//페이징 종료 블럭이 최대 페이지 번호보다 큰 경우 처리
	if(endBlock >maxPageNo){
		endBlock = maxPageNo;
	}
	//첫페이지는 이전블럭 없애기
	if(currentPage >10){  //서블릿 경우 : '/anbd2/${ curpage_jsp }'
		%><a href="javascript:doGoPage('/anbd2/main/main.jsp','<%= startBlock - 10 %>','');"> &lt;이전 </a><% 
	}
	for(int i=startBlock; i<=endBlock; i++)
	{
		if(i==currentPage){
			%><a href="javascript:doGoPage('/anbd2/main/main.jsp','<%= i %>','');" class="active"> <%= i %>.</a><%
		}else{
			%><a href="javascript:doGoPage('/anbd2/main/main.jsp','<%= i %>','');"> <%= i %>.</a><%
		}
	}
	//마지막블럭 다음은 안나오게
	if ( endBlock < maxPageNo ){
		%><a href="javascript:doGoPage('/anbd2/main/main.jsp','<%= endBlock+1 %>','');"> 다음&gt; </a><%
	}
%>
</div>
<script language="javascript">
	function doGoPage(url,pageno,seqno) //seqno는 글번호
	{
		var f = document.pageForm;
		var mParam = "";
			mParam += "page=" + pageno;
			mParam += "&";
			mParam += "no=" + seqno;		
			mParam += "&";
			mParam += "menu=" + f.smenu.value;
			mParam += "&";
			mParam += "option=" + f.soption.value;
			mParam += "&";
			mParam += "key=" + f.skey.value;	
			mParam += "&";
			mParam += "jusoNo=" + f.sjusoNo.value;
			mParam += "&";
			mParam += "noDone=" + f.snoDone.value;
		//선생님 방법(암호화)
		//mParam = Encrption(mParam);
		//alert("주소 번호 = "+f.sjusoNo.value);
		page = url + "?" + mParam;
		document.location = page;
	}
	function doAlert(){
		alert("글쓰기는 로그인 후 가능합니다");
		location.href="../common/login.jsp";
	}
	$(document).ready(function(){
		var noDone = '<%=noDoneYN%>';
		if(noDone=='Y'){
			$("#noDone").attr("src", "/anbd2/img/checkDarkgray.png");
		}
		var juso = <%=juso%>; 
		if(juso==true){	//지역선택 결과페이지이면, 선택한 시도, 시군구 보여주기..but 시/군/구 클릭시 검색한 시/군/구만 나옴
			$("#sido").val("<%=sido%>").attr("selected", "selected");
			$("#sigun").append("<option value='<%=jusoNo%>:<%=sigun%>'><%=sigun%></option>");
			$("#sigun").val("<%=jusoNo%>:<%=sigun%>").attr("selected", "selected");
		}
		$("#sido").change(function(){ //선택한 시도의 시군구 구하기
			var changeSigun = $("#sido").val();
			$.ajax({
				type:"GET",
				url:"/anbd2/main/selSigun.jsp",
				data: "sido=" + encodeURIComponent(changeSigun),
				dataType: "html",
				success: function (data){
					$("#sigun").html(data);
	        	},error: function(xhr, status, error){
            		alert("지역 정보를 조회할 수 없습니다");
           		}
			});//ajax FLOW
		});//sido
		$("#jusoSort").click(function(){
			var sido = $("#sido option:selected").val();
			var sigun = $("#sigun option:selected").val();
				 sigun = sigun.split(":");
			var jusoNo = sigun[0];
			pageForm.sjusoNo.value = jusoNo;			
			doGoPage("/anbd2/main/main.jsp","1","");
			//선생님 방법1
			//var param = "";
			//param = "page=2&no=&menu=&option=title&key=&startRow=0&pageSize=19";
			//param += "&jusoNo=" + jusoNo;
			//param += "&viewdone=N";
			//alert(param);
			//document.location = "main.jsp?" + param;
		});
		$("#noDone").click(function(){
			//var path = '${pageContext.request.contextPath}';
			if(noDone=='Y'){
				$("#noDone").attr("src", "/anbd2/img/checkGray.png");
				pageForm.snoDone.value = "N";
			}else{
				$("#noDone").attr("src", "/anbd2/img/checkDarkgray.png");
				pageForm.snoDone.value = "Y";
			}
			doGoPage("/anbd2/main/main.jsp","1","");  //doGoPage(path+"/noDoneSer",<%--=currentPage--%>,"");
		});
	})
</script>
<!--  doGoPage()에 값을 보내기 위한 hidden 값  -->
<form id="pageForm" name="pageForm" method="post" action="/anbd2/${ curpage_jsp }">
	<input type="hidden" id="spage" name="page" value="<%=currentPage%>">
	<input type="hidden" id="smenu" name="menu" value="<%=menu%>">
	<input type="hidden" id="soption" name="soption" value="<%=option%>">
	<input type="hidden" id="skey" name="skey" value="<%=mEncodeKey%>">
	<input type="hidden" id="sjusoNo" name="sjusoNo" value="<%=jusoNo%>">
	<input type="hidden" id="snoDone" name="snoDone" value="<%=noDoneYN%>">
</form>
<%@include file="../include/footer.jsp"%>