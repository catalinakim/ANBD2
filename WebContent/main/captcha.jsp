<%@page contentType="text/html;charset=euc-kr" pageEncoding="euc-kr"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="java.net.*" %>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="nl.captcha.Captcha" %>
<%@page import="nl.captcha.Captcha.Builder" %>
<%@page import="nl.captcha.backgrounds.FlatColorBackgroundProducer" %>
<%@page import="java.awt.Color" %>
<%
//========= 웹 파라메터 분석 구간 [ 시작 ] =====
String sign_mode   = request.getParameter("mode");
if(sign_mode == null)
{
	sign_mode = "";
}
//========= 웹 파라메터 분석 구간 [ 종료 ] =====
if(sign_mode.equals("check"))
{
	//자동등록방지 코드를 검증한다.
	String mAnswer = (String)request.getSession().getAttribute("IMAGE");
	%><%= mAnswer %><%
}else
{
	//자동등록방지 코드를 이미지로 출력한다.
	Captcha mCaptcha;
	Builder mBuilder;
	String  mAnswer;

	mBuilder= new Captcha.Builder(160, 50);
	mBuilder.addText();
	mBuilder.addBackground(new FlatColorBackgroundProducer(Color.WHITE));
	mBuilder.addBorder();
	mBuilder.addNoise();
	mCaptcha = mBuilder.build();
	mAnswer = mCaptcha.getAnswer();

	OutputStream mOut = response.getOutputStream();
	ImageIO.write(mCaptcha.getImage(),"jpg", mOut);
	mOut.close();

	request.getSession().setAttribute("IMAGE",mAnswer);
}
%>