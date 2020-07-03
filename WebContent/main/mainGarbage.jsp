<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../include/headerKgh.jsp"%>
<%@include file="../include/fix.jsp"%>
<%@include file="../include/searchOption.jsp"%>
<%@ page import="java.net.URLEncoder" %> <!-- 브라우저 때문에.. -->

<jsp:useBean id="pg" class="anbd.PageDAO" scope="page"/>
<script>
	document.title="ANBD | 아나바다-목록";
</script>
<% 
request.setCharacterEncoding("utf-8");

int currentPage = 1;  //현재 페이지번호
int pageSize    = 19; //페이지당 게시물 목록 갯수
int count       = 0;  //전체 게시물 갯수
int startRow    = 0;  //페이지 시작행 번호
int seqNo       = 0;  //페이지 목록에 게시글 일련번호
int maxPageNo   = 0;  //최대 페이지 번호
	
String mTemp = request.getParameter("page");
if(mTemp != null){ //키워드 검색하면 무조건 1p
	currentPage = Integer.parseInt(mTemp);
}

String mKey = request.getParameter("key"); //-> 메소드에서도 받기
if(mKey==null){ //그냥 검색안하면 null
	mKey="";
}
//String menu = request.getParameter("menu"); //searchOption.jsp에 있어 쓰면 중복

//시작행 번호 = (현재 페이지번호 - 1) * 페이지당 출력 할 갯수
startRow    = (currentPage - 1) * pageSize; //페이지 시작행 번호
seqNo       = startRow + 1;				     //페이지 목록에 게시글 일련번호

	ArrayList<AnbdVO> mainList = new ArrayList<AnbdVO>();
	//pg.selMainList(mainList, startRow, pageSize, mKey);
	pg.selMainList2(mainList, startRow, pageSize, request);
	
	count 	  = pg.count;
	
	//최대 페이지 번호 계산
	maxPageNo = count/pageSize;   
	if(  (count % pageSize) != 0){
		maxPageNo = maxPageNo + 1;
	}
	//서버에 attribute를 setting하겠다
	session.setAttribute("pgList", mainList);
	
	//String option = request.getParameter("option");
	//String key = request.getParameter("key");
	
	ArrayList<AnbdVO> blist = new ArrayList<AnbdVO>();
	dao.selBoardList(blist);
	
	//서버에 attribute를 setting하겠다
	pageContext.setAttribute("blist", blist);
%>
<h1>메뉴 = ${vo.menu}</h1>
<div style="padding: 20px 40px 20px;">
	<table id="board">
		<tr>
			<th width="160px">구분</th>
			<th width="500px">제목</th>
			<th width="200px">시도</th>
			<th width="200px">시군구</th>
			<th width="200px">작성일자</th>
		<tr>
		<!-- 공지 상단 고정 시작============================ -->
		<c:forEach items="${blist}" var="blist">
			<c:if test="${blist.menu eq  '공지'}">
				<td>[공지]</td>
				<td>
					<a href="view.jsp?no=${blist.no}&menu=notice">${blist.title}
						<c:choose>
							<c:when test="${pageList.photo eq 'Y'}">
								<img src="../img/이미지.png" style="width:20px;">
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</a>
				</td>
				<td>시도 표시</td>
				<td>시군구 표시</td>
				<td>${blist.wdate}</td>
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
						<c:if test="${pageList.menu eq '아나'}">
							<a href="view.jsp?menu=share&no=${pageList.no}">
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
									<img src="../img/이미지.png" style="width:20px;">
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							</a>
						</c:if>
						<c:if test="${pageList.menu eq '바다'}">
							<a href="view.jsp?menu=reuse&no=${pageList.no}">
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
									<img src="../img/이미지.png" style="width:20px;">
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							</a>
						</c:if>
						</td>
						<td>
							시도 표시
						</td>
						<td>
							시군구 표시
						</td>
						<td>${pageList.wdate}</td>
					</c:when>
				</c:choose>
			</tr>
		</c:forEach>
	</table>
	<% db.conClose(); %>
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
	int startBlock = (((currentPage-1)/10)*10) +1; //((currentPage/10)*10) +1;
	//페이징 종료 블럭 계산
	int endBlock  = startBlock + (10-1); 			  //startBlock + (10-1);
	//페이징 종료 블럭이 최대 페이지 번호보다 큰 경우 처리
	if(endBlock >maxPageNo){
		endBlock = maxPageNo; //maxPageNo+1
	}
	
	String mEncodeKey = URLEncoder.encode(mKey, "UTF-8"); //url에 검색어 한글을 %로 바꿔줌(인코딩)

	//첫페이지는 이전블럭 없애기
	if(currentPage >10) 
	{ 
		%><!--<a href="list.jsp?key=<%=mEncodeKey%>&page=<%= startBlock - 10 %>">[이전블럭]</a>--><% 
		%><a href="javascript:doGoPage(<%= startBlock - 10 %>);"> &lt;이전 </a><% 
	}

	for(int i=startBlock; i<=endBlock; i++)
	{
		if(i==currentPage){
			%><a href="main.jsp?key=<%=mEncodeKey%>&page=<%= i %>" class="active"> <%= i %>. </a> <%
		}else{
			%><a href="main.jsp?key=<%=mEncodeKey%>&page=<%= i %>"> <%= i %>. </a> <%
		}
	}

	//마지막블럭 다음은 안나오게
	if ( endBlock < maxPageNo ) //currentPage < maxPageNo-10
	{
		%><a href="main.jsp?key=<%=mEncodeKey%>&page=<%= endBlock+1 %>"> 다음&gt; </a><%
	}
%>
</div>

<script language="javascript">
	function doGoPage(pageno){
		document.pageForm.page.value = pageno; //페이지 번호 할당
		document.pageForm.submit();
	}
	function doAlert()
	{
		alert("글쓰기는 로그인 후 가능합니다");
		location.href="../common/login.jsp";
	}
</script>

<form id="pageForm" name="pageForm" method="post" action="main.jsp">
	<input type="hidden" id="page" name="page" value="">
	<input type="hidden" id="kw" name="kw" value="<%=mKey%>">
</form>

<%@include file="../include/footer.jsp"%>
