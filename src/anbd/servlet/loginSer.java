package anbd.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loginSer
 */
@WebServlet("/loginBean_ok")
public class loginSer extends HttpServlet {
	private static final long serialVersionUID = 1L; //직렬화...
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginSer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("doGet test");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		PrintWriter out = response.getWriter();
		out.println("doPost test");
		
		request.setCharacterEncoding("utf-8"); //한글깨짐현상 해결
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("utf-8"); //한글깨짐현상 해결
		
		//jsp파일 호출 - 서블릿 디스패처
		String path = "/common/loginBean_ok.jsp"; //절대경로 '/'는 웹사이트의 루트 폴더
		RequestDispatcher dis = request.getRequestDispatcher(path); //클라이언트 요청을 
		dis.forward(request, response); //path(loginBean_ok.jsp)로 제어를 넘기는 역할
	}

}
