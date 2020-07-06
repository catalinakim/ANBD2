package anbd.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import anbd.AnbdDAO;

@WebServlet("/loginBean_ok")
public class loginSer extends HttpServlet {
	private static final long serialVersionUID = 1L; 
       
    public loginSer() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("doGet test");
	}

//  doPost v2 - 로그인 일치하면 세션 생성
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		//if(id.equals("dd") && pw.equals("ddd")) { //로그인 일치 임시용
		AnbdDAO dao = new AnbdDAO();
		boolean login = dao.selLogin(id, pw);
		if(login) {
			HttpSession session = request.getSession();
			session.setAttribute("loginId", id);
//			out.println("<script>");
//			out.println("alert('로그인에 성공하였습니다.')");
//			out.println("location.href='/anbd2/main/main.jsp';"); //메인이동 방법1
//			out.println("</script>");
			response.sendRedirect("/anbd2/main/main.jsp"); 		  //메인이동 방법2
		}else {
			out.println("<script>");
			out.println("alert('id, 비밀번호를 확인해주세요.')");
			out.println("history.back();");
			out.println("</script>");
		}
		
	}
	
//	doPost v1 - loginBean_ok.jsp로 넘기기 (한글 깨짐 해결함)
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");  //한글깨짐현상 해결
//		response.setContentType("text/html; charset=utf-8"); 
//		PrintWriter out = response.getWriter();
//		
//		//jsp파일 호출 - 서블릿 디스패처
//		String path = "/common/loginBean_ok.jsp"; //절대경로 '/'는 웹사이트의 루트 폴더
//		RequestDispatcher dis = request.getRequestDispatcher(path); //클라이언트 요청을 
//		dis.forward(request, response); //path(loginBean_ok.jsp)로 제어를 넘기는 역할
//	}

}//class end
