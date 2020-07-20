<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../include/headerKgh.jsp"%>
<%@include file="../include/fix.jsp"%>
<style>
*, *::before, *::after
{box-sizing:content-box}
</style>
	<div class="container" id="view">
		<h3>
			<span>[공지]</span>
			<span>필독! 공지입니다</span>
		</h3><br/>
		<p>
			<span>운영자</span>
		</p>
		<div id="content" name="content">
			<p>
저희는 사용하지 않는 자원(물건, 재능, 공간, 시간 등을 본 사이트에서는 '자원'이라 칭함)의 유휴 상태를 최소화하고, 금전 거래를 금지하여 자원 공유에 대한 진입 장벽을 낮추고자 개설하였습니다. 이러한 저희의 생각은 날로 심화되고 있는 환경 문제에서 확장되었습니다.<br/>
이러한 취지를 바탕으로 사이트 이용 가이드를 이용자분들께 전달하고자 합니다.<br/>
1. 금전 거래는 일체 금합니다.<br/>
중고 거래 사이트에서는 사기, 거래 불발로 인한 불편이 많습니다. 저희는 선의의 목적으로 유휴 상태인 자원을 공유하고자 개설한 사이트이므로, 직접적으로 금전에 해당하는 돈, 상품권, 가상화폐, 고가 문서, 그 이외의 고가 물품의 거래는 금지합니다.(단, 선의의 목적으로 나누는 것은 관여하지 않습니다.)<br/>
2. 책임 사항<br/>
저희는 금전 거래를 금하는 항목을 이용자에게 제시하였으나, 이를 위반하고 피해나 손해를 입었을 경우와 이외에 본 사이트를 이용해서 발생한 모든 피해나 손해에 대해 그 어떠한 책임을 지지 않습니다.<br/>
3. 연락 수단<br/>
저희는 회원가입 시 이메일을 수집합니다. 이는 댓글로 연락하기 불편한 사용자도 있을 것이라 예상해 마련한 연락 수단이니, 신속한 연락을 위해서는 이메일을 이용해주세요.(단, 타인의 이메일을 수집해 이를 악용하는 행위는 처벌을 받을 수 있으며, 인신 공격, 정치적, 종교적 기타 요인으로 논란이 될 수 있는 발언은 금합니다.)<br/>
4. 거래 상태<br/>
다른 이용자와 작성자의 편의를 위해 거래가 완료된 글에는 "거래 완료" 버튼을 눌러주세요.<br/>
거래 완료를 취소할 때는 "거래 완료 취소"를 눌러주시면 됩니다.<br/><br/>
<b>이메일 수집 및 이용에 관한 동의</b><br/>
1. 이용자가 제공한 이메일은 다음의 목적을 위해 활용하며, 하기 목적 이외의 용도로는 사용되지 않습니다.<br/>
   1-1. 회원가입 시 본인 인증<br/>
   1-2. 비밀번호 분실 시 재설정<br/>
   1-3. 나눔/교환 글 작성 시에 연락 수단으로 회원에게 이메일 공개<br/>
2. 이메일을 입력하고 회원가입을 진행하면 이메일 공개 동의로 간주합니다.<br/>
3. 회원가입을 하면 인증메일이 발송됩니다. 이메일 url로 인증 후 회원가입이 완료됩니다.<br/>
※ 단, 본 사이트의 이메일을 수집해 이를 악용하는 행위는 처벌을 받을 수 있습니다.<br/>
			</p>
		
		</div>
		
		<input type="text" placeholder="로그인 후 댓글 입력" id="comment" name="comment" style="width:85%">
		<button class="readmore-btn" id="cWrite" style="float:none;">댓글쓰기</button>
		
		<p style="margin-top:10px;">
			<span id="cWriter">라꾸라꾸</span>
			<span id="cContent" name="cContent" style="width:50%">확인했습니다!</span>
			<span id="cWdate">2020-05-15</span>
			<span>
				<button class="site-btn" id="cModify">수정</button>
				<button class="site-btn" id="cRemove">삭제</button>
			</span>
		</p>
	</div><!--container 클래스 마지막-->
	<div class="Lst">
		<button class="site-btn" id="list" onclick="location.href='main.jsp'">목록</button>
	</div>
	<%@include file="../include/footer.jsp"%>
