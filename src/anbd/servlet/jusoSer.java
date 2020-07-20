package anbd.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;

import anbd.AnbdDAO;
import anbd.AnbdVO;
import anbd.PageDAO;

@WebServlet("/jusoSer")
public class jusoSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public jusoSer() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("*****************jusoSer 시작, post*****************");
		StringBuffer serPath = request.getRequestURL();
		System.out.println("----------serPath: "+serPath);
		
		int jusoNo = Integer.parseInt(request.getParameter("jusoNo"));
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		System.out.println(jusoNo);
		System.out.println("startRow: "+startRow);
		boolean juso = true;
		boolean noDone = false;
		
		PageDAO p = new PageDAO();
		ArrayList<AnbdVO> mainList = new ArrayList<AnbdVO>();
		p.selMainListJuDone(mainList, startRow, pageSize, request, juso, noDone);

		String path = "/anbd2/main/main.jsp"; 		//절대경로 '/'는 웹사이트의 루트 폴더
		RequestDispatcher dis = request.getRequestDispatcher(path); 
		
//		HttpSession session = request.getSession();
//		session.setAttribute("pgList", mainList);
		
		request.setAttribute("pgList", mainList);
		request.setAttribute("yesServlet", "from servlet");
		request.setAttribute("count", p.count);
		request.removeAttribute("startRow");
		System.out.println("*****************jusoSer 종료 직전*****************");
		//response.sendRedirect(path); 
		dis.forward(request, response);
		
		
		
		
		
		
	}

}
